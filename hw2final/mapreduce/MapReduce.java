package mapreduce;

import java.util.Vector;
import java.io.Serializable;

public interface MapReduce extends Serializable { // Implement this interface for specific problems

	public int nMappers();
	public int nReducers();
	public int nShufflers();
	
	public Vector map(Pair p);
	
	public Pair reduce(Pair p);
	//THIS WAS COMMENTED OUT WHEN WE REALIZED WE NEEDED TO DO A LOT MORE WITH THE SHUFFLE FUNCTION
	//public Pair shuffle(Vector v);
}

