package mapreduce;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Vector;
/*
THE PURPOSE OF THIS CLASS IS TO GIVE CERTAIN BEHAVIORS TO CERTAIN ACTORS. FOR MAP AND REDUCE, THIS WORKS
BECAUSE WE DO NOT HAVE TO ACCESS THE ACTOR'S MEMBER VARIABLES. FOR SHUFFLE WE DO HAVE TO DO ACCESS THE MEMBER
VARIABLES SO IT IS NOT IN THIS FILE AND IS IN SHUFFLE.SALSA .
*/


public class CharCountMapReduce implements MapReduce {

	private int nMappers;
	private int nShufflers;
	private int nReducers;
	private boolean isdoc;
	
	public CharCountMapReduce(int m, int s, int r) {
		nMappers = m;
		nShufflers = s;
		nReducers = r;
	}
	
	public int nMappers() {
		return nMappers;
	}

	public int nReducers() {
		return nReducers;
	}

	public int nShufflers() {
		return nShufflers;
	}

	// Map takes in a pair and returns a Vector
	// CONVERTS PAIR(STRING, STRING) TO VECTOR(PAIR(CHAR, 1),PAIR(CHAR,1) ... ) FOR THE CHAR FREQUENCY
	// CONVERTS PAIR(STRING, STRING) TO VECTOR(PAIR(WORD,PAIR(DOC, 1)), PAIR(WORD,PAIR(DOC, 1))) FOR WORD IN DOCUMENT FREQUENCY
	public Vector map(Pair p) {
		Vector ret = new Vector();
		String s = (String)p.value;
		String first = (String) (p.value);
		// The purpose of the flag is to check if the input key is a integer
		boolean flag = true;
		for (int i =0 ; i < ((String) (p.key)).length(); i++)
		{
			char c = ((String) (p.key)).charAt(i);
			if (!Character.isDigit(c)) flag = false;
		}
		// Distinguish the countwords by tab and integer
		if (first.contains("\t") || !flag)
		{
			String [] strs = ((String) (p.value)).split("\t");
			for (int i =0; i < strs.length;i++)
			{	
				// initialize two pairs, and replace the key and value as we read in 
				Pair r = new Pair("a",0);
				Pair q = new Pair("a",0);
				r.value = 1;
				r.key = p.key;

				// r is q's value and add pair q to the big vector
				q.key = strs[i];
				q.value = r;
				ret.add(q);
			}
		}
		//CountChars- creating a key and a value
		else
		{
			for (int i = 0; i < s.length(); ++i)
			{
				String temp = Character.toString(s.charAt(i));
				ret.add(new Pair(temp, 1));
			} 
		}

		return ret;
	}

	// Reducer takes in a pair and returns a pair 
	//TAKES PAIR(WORD, VECTOR(1, 1, 1)) AND RETURNS PAIRS(WORD, VECTOR(3)) FOR CHARCOUNT
	//TAKES PAIR(WORD, VECTOR(PAIR(DOC, VECTOR(1,1,1)) , PAIR(...))) AND RETURNS PAIR(WORD, VECTOR(PAIR(DOC, VECTOR(3)) PAIR(...)))
	public Pair reduce(Pair p) {
		//Distinguishes WordCount
		if (( ((Vector)(p.value)).get(0)) instanceof Pair)
		{
			Vector v = (Vector) (p.value);
			for (int i =0; i < v.size(); i++)
			{
				// (word, (Doc, [#]))
				Pair q = (Pair) (v.get(i));
				// The temp vector keeps track of the length of the doc value
				Vector temp = new Vector();
				temp.add(((Vector) (q.value)).size());
				q.value = temp;
				v.set(i,q);
			}
			//replacing the value with the vector of pair
			p.value = v;
			return p;
		}
		//CharCount
		else
		{
			Vector v = (Vector)(p.value);
			int sum = 0;
			for (int i = 0; i < v.size(); ++i) sum += (Integer)v.get(i);
			Vector ret = new Vector();
			ret.add(sum);
			return new Pair(p.key, ret);
		}

	}

}