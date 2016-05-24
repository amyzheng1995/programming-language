package mapreduce;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

public class PairWriter {

	PrintWriter pw = null;

	public PairWriter(String filename) {
		try {
			pw = new PrintWriter(new FileWriter(filename));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writePair(Pair p) {
		pw.print(p.key);
		Vector vlist = (Vector) p.value;
		for (int i = 0; i < vlist.size(); ++i) {
			if (vlist.get(i) instanceof Pair)
			{
				Pair q = (Pair)(vlist.get(i));
				String str ="(";
				str = str + (String) (q.key);
				str = str + ",";
				str = str + Integer.toString((int) ((Vector)(q.value)).get(0));
				str = str + ")";
				pw.print("\t" + str);
			}
			else
			{
				pw.print("\t" + vlist.get(i));				
			}

		}
		pw.println();
	}

	public void close() {
		pw.close();
	}
}
