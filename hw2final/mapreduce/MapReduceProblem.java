package mapreduce;

// Import declarations generated by the SALSA compiler, do not modify.
import java.io.IOException;
import java.util.Vector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

import salsa.language.Actor;
import salsa.language.ActorReference;
import salsa.language.Message;
import salsa.language.RunTime;
import salsa.language.ServiceFactory;
import gc.WeakReference;
import salsa.language.Token;
import salsa.language.exceptions.*;
import salsa.language.exceptions.CurrentContinuationException;

import salsa.language.UniversalActor;

import salsa.naming.UAN;
import salsa.naming.UAL;
import salsa.naming.MalformedUALException;
import salsa.naming.MalformedUANException;

import salsa.resources.SystemService;

import salsa.resources.ActorService;

// End SALSA compiler generated import delcarations.


public class MapReduceProblem extends UniversalActor  {
	public static void main(String args[]) {
		UAN uan = null;
		UAL ual = null;
		if (System.getProperty("uan") != null) {
			uan = new UAN( System.getProperty("uan") );
			ServiceFactory.getTheater();
			RunTime.receivedUniversalActor();
		}
		if (System.getProperty("ual") != null) {
			ual = new UAL( System.getProperty("ual") );

			if (uan == null) {
				System.err.println("Actor Creation Error:");
				System.err.println("	uan: " + uan);
				System.err.println("	ual: " + ual);
				System.err.println("	Identifier: " + System.getProperty("identifier"));
				System.err.println("	Cannot specify an actor to have a ual at runtime without a uan.");
				System.err.println("	To give an actor a specific ual at runtime, use the identifier system property.");
				System.exit(0);
			}
			RunTime.receivedUniversalActor();
		}
		if (System.getProperty("identifier") != null) {
			if (ual != null) {
				System.err.println("Actor Creation Error:");
				System.err.println("	uan: " + uan);
				System.err.println("	ual: " + ual);
				System.err.println("	Identifier: " + System.getProperty("identifier"));
				System.err.println("	Cannot specify an identifier and a ual with system properties when creating an actor.");
				System.exit(0);
			}
			ual = new UAL( ServiceFactory.getTheater().getLocation() + System.getProperty("identifier"));
		}
		RunTime.receivedMessage();
		MapReduceProblem instance = (MapReduceProblem)new MapReduceProblem(uan, ual,null).construct();
		gc.WeakReference instanceRef=new gc.WeakReference(uan,ual);
		{
			Object[] _arguments = { args };

			//preAct() for local actor creation
			//act() for remote actor creation
			if (ual != null && !ual.getLocation().equals(ServiceFactory.getTheater().getLocation())) {instance.send( new Message(instanceRef, instanceRef, "act", _arguments, false) );}
			else {instance.send( new Message(instanceRef, instanceRef, "preAct", _arguments, false) );}
		}
		RunTime.finishedProcessingMessage();
	}

	public static ActorReference getReferenceByName(UAN uan)	{ return new MapReduceProblem(false, uan); }
	public static ActorReference getReferenceByName(String uan)	{ return MapReduceProblem.getReferenceByName(new UAN(uan)); }
	public static ActorReference getReferenceByLocation(UAL ual)	{ return new MapReduceProblem(false, ual); }

	public static ActorReference getReferenceByLocation(String ual)	{ return MapReduceProblem.getReferenceByLocation(new UAL(ual)); }
	public MapReduceProblem(boolean o, UAN __uan)	{ super(false,__uan); }
	public MapReduceProblem(boolean o, UAL __ual)	{ super(false,__ual); }
	public MapReduceProblem(UAN __uan,UniversalActor.State sourceActor)	{ this(__uan, null, sourceActor); }
	public MapReduceProblem(UAL __ual,UniversalActor.State sourceActor)	{ this(null, __ual, sourceActor); }
	public MapReduceProblem(UniversalActor.State sourceActor)		{ this(null, null, sourceActor);  }
	public MapReduceProblem()		{  }
	public MapReduceProblem(UAN __uan, UAL __ual, Object obj) {
		//decide the type of sourceActor
		//if obj is null, the actor must be the startup actor.
		//if obj is an actorReference, this actor is created by a remote actor

		if (obj instanceof UniversalActor.State || obj==null) {
			  UniversalActor.State sourceActor;
			  if (obj!=null) { sourceActor=(UniversalActor.State) obj;}
			  else {sourceActor=null;}

			  //remote creation message sent to a remote system service.
			  if (__ual != null && !__ual.getLocation().equals(ServiceFactory.getTheater().getLocation())) {
			    WeakReference sourceRef;
			    if (sourceActor!=null && sourceActor.getUAL() != null) {sourceRef = new WeakReference(sourceActor.getUAN(),sourceActor.getUAL());}
			    else {sourceRef = null;}
			    if (sourceActor != null) {
			      if (__uan != null) {sourceActor.getActorMemory().getForwardList().putReference(__uan);}
			      else if (__ual!=null) {sourceActor.getActorMemory().getForwardList().putReference(__ual);}

			      //update the source of this actor reference
			      setSource(sourceActor.getUAN(), sourceActor.getUAL());
			      activateGC();
			    }
			    createRemotely(__uan, __ual, "mapreduce.MapReduceProblem", sourceRef);
			  }

			  // local creation
			  else {
			    State state = new State(__uan, __ual);

			    //assume the reference is weak
			    muteGC();

			    //the source actor is  the startup actor
			    if (sourceActor == null) {
			      state.getActorMemory().getInverseList().putInverseReference("rmsp://me");
			    }

			    //the souce actor is a normal actor
			    else if (sourceActor instanceof UniversalActor.State) {

			      // this reference is part of garbage collection
			      activateGC();

			      //update the source of this actor reference
			      setSource(sourceActor.getUAN(), sourceActor.getUAL());

			      /* Garbage collection registration:
			       * register 'this reference' in sourceActor's forward list @
			       * register 'this reference' in the forward acquaintance's inverse list
			       */
			      String inverseRefString=null;
			      if (sourceActor.getUAN()!=null) {inverseRefString=sourceActor.getUAN().toString();}
			      else if (sourceActor.getUAL()!=null) {inverseRefString=sourceActor.getUAL().toString();}
			      if (__uan != null) {sourceActor.getActorMemory().getForwardList().putReference(__uan);}
			      else if (__ual != null) {sourceActor.getActorMemory().getForwardList().putReference(__ual);}
			      else {sourceActor.getActorMemory().getForwardList().putReference(state.getUAL());}

			      //put the inverse reference information in the actormemory
			      if (inverseRefString!=null) state.getActorMemory().getInverseList().putInverseReference(inverseRefString);
			    }
			    state.updateSelf(this);
			    ServiceFactory.getNaming().setEntry(state.getUAN(), state.getUAL(), state);
			    if (getUAN() != null) ServiceFactory.getNaming().update(state.getUAN(), state.getUAL());
			  }
		}

		//creation invoked by a remote message
		else if (obj instanceof ActorReference) {
			  ActorReference sourceRef= (ActorReference) obj;
			  State state = new State(__uan, __ual);
			  muteGC();
			  state.getActorMemory().getInverseList().putInverseReference("rmsp://me");
			  if (sourceRef.getUAN() != null) {state.getActorMemory().getInverseList().putInverseReference(sourceRef.getUAN());}
			  else if (sourceRef.getUAL() != null) {state.getActorMemory().getInverseList().putInverseReference(sourceRef.getUAL());}
			  state.updateSelf(this);
			  ServiceFactory.getNaming().setEntry(state.getUAN(), state.getUAL(),state);
			  if (getUAN() != null) ServiceFactory.getNaming().update(state.getUAN(), state.getUAL());
		}
	}

	public UniversalActor construct (MapReduce mr) {
		Object[] __arguments = { mr };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public UniversalActor construct() {
		Object[] __arguments = { };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public class State extends UniversalActor .State {
		public MapReduceProblem self;
		public void updateSelf(ActorReference actorReference) {
			((MapReduceProblem)actorReference).setUAL(getUAL());
			((MapReduceProblem)actorReference).setUAN(getUAN());
			self = new MapReduceProblem(false,getUAL());
			self.setUAN(getUAN());
			self.setUAL(getUAL());
			self.activateGC();
		}

		public void preAct(String[] arguments) {
			getActorMemory().getInverseList().removeInverseReference("rmsp://me",1);
			{
				Object[] __args={arguments};
				self.send( new Message(self,self, "act", __args, null,null,false) );
			}
		}

		public State() {
			this(null, null);
		}

		public State(UAN __uan, UAL __ual) {
			super(__uan, __ual);
			addClassName( "mapreduce.MapReduceProblem$State" );
			addMethodsForClasses();
		}

		public void construct() {}

		public void process(Message message) {
			Method[] matches = getMatches(message.getMethodName());
			Object returnValue = null;
			Exception exception = null;

			if (matches != null) {
				if (!message.getMethodName().equals("die")) {activateArgsGC(message);}
				for (int i = 0; i < matches.length; i++) {
					try {
						if (matches[i].getParameterTypes().length != message.getArguments().length) continue;
						returnValue = matches[i].invoke(this, message.getArguments());
					} catch (Exception e) {
						if (e.getCause() instanceof CurrentContinuationException) {
							sendGeneratedMessages();
							return;
						} else if (e instanceof InvocationTargetException) {
							sendGeneratedMessages();
							exception = (Exception)e.getCause();
							break;
						} else {
							continue;
						}
					}
					sendGeneratedMessages();
					currentMessage.resolveContinuations(returnValue);
					return;
				}
			}

			System.err.println("Message processing exception:");
			if (message.getSource() != null) {
				System.err.println("\tSent by: " + message.getSource().toString());
			} else System.err.println("\tSent by: unknown");
			System.err.println("\tReceived by actor: " + toString());
			System.err.println("\tMessage: " + message.toString());
			if (exception == null) {
				if (matches == null) {
					System.err.println("\tNo methods with the same name found.");
					return;
				}
				System.err.println("\tDid not match any of the following: ");
				for (int i = 0; i < matches.length; i++) {
					System.err.print("\t\tMethod: " + matches[i].getName() + "( ");
					Class[] parTypes = matches[i].getParameterTypes();
					for (int j = 0; j < parTypes.length; j++) {
						System.err.print(parTypes[j].getName() + " ");
					}
					System.err.println(")");
				}
			} else {
				System.err.println("\tThrew exception: " + exception);
				exception.printStackTrace();
			}
		}

		MapReduce kernel;
		Map[] mapActors;
		Shuffle[] shuffleActors;
		Reduce[] reduceActors;
		public void construct(MapReduce mr){
			kernel = mr;
			mapActors = new Map[mr.nMappers()];
			for (int i = 0; i<mapActors.length; ++i)mapActors[i] = ((Map)new Map(this).construct(mr));
			shuffleActors = new Shuffle[mr.nShufflers()];
			for (int i = 0; i<shuffleActors.length; ++i)shuffleActors[i] = ((Shuffle)new Shuffle(this).construct(mr));
			reduceActors = new Reduce[mr.nReducers()];
			for (int i = 0; i<reduceActors.length; ++i)reduceActors[i] = ((Reduce)new Reduce(this).construct(mr));
		}
		public int hashF(String val, int nActors) {
			int sum = 0;
			for (int i = 0; i<val.length(); i++){
				sum += (int)val.charAt(i);
			}
			return sum%nActors;
		}
		public Vector bubbleSort(Vector vec) {
			Pair temp;
			for (int i = 0; i<vec.size()-1; i++){
				for (int j = 1; j<vec.size(); j++){
					String str1 = ((String)(((Pair)vec.get(j-1)).key));
					String str2 = ((String)(((Pair)vec.get(j)).key));
					if (str1.compareTo(str2)>=0) {{
						temp = (Pair)(vec.get(j-1));
						vec.set(j-1, (Pair)vec.get(j));
						vec.set(j, temp);
					}
}				}
			}
			return vec;
		}
		public void format(Object[] a, String outputF) {
			Vector results = new Vector();
			for (int i = 0; i<a.length; i++){
				results.add((Pair)a[i]);
			}
			Vector results0 = bubbleSort(results);
			{
				// output(results0, outputF)
				{
					Object _arguments[] = { results0, outputF };
					Message message = new Message( self, self, "output", _arguments, null, null );
					__messages.add( message );
				}
			}
		}
		public void reduce_func(Object[] a, String output) {
			Vector shuf_vec = new Vector();
			for (int i = 0; i<a.length; i++){
				Vector temp = (Vector)(a[i]);
				for (int j = 0; j<temp.size(); j++){
					Pair p = (Pair)(temp.get(j));
					shuf_vec.add(p);
				}
			}
			{
				Token token_2_0 = new Token();
				// join block
				token_2_0.setJoinDirector();
				for (int i = 0; i<shuf_vec.size(); i++){
					{
						// reduceActors[i%reduceActors.length]<-reduce(shuf_vec.get(i))
						{
							Object _arguments[] = { shuf_vec.get(i) };
							Message message = new Message( self, reduceActors[i%reduceActors.length], "reduce", _arguments, null, token_2_0 );
							__messages.add( message );
						}
					}
				}
				addJoinToken(token_2_0);
				// format(token, output)
				{
					Object _arguments[] = { token_2_0, output };
					Message message = new Message( self, self, "format", _arguments, token_2_0, null );
					__messages.add( message );
				}
			}
		}
		public void shuffle_func(Object[] a, String output) {
			Vector all_pairs = new Vector();
			for (int j = 0; j<a.length; j++){
				Vector temp = (Vector)(a[j]);
				for (int i = 0; i<temp.size(); i++){
					Pair p = (Pair)(temp.get(i));
					all_pairs.add(p);
				}
			}
			{
				Token token_2_0 = new Token();
				Token token_2_1 = new Token();
				// join block
				token_2_0.setJoinDirector();
				for (int i = 0; i<all_pairs.size(); i++){
					Pair p = (Pair)all_pairs.get(i);
					{
						// shuffleActors[hashF((String)p.key, shuffleActors.length)]<-shuffle(p)
						{
							Object _arguments[] = { p };
							Message message = new Message( self, shuffleActors[hashF((String)p.key, shuffleActors.length)], "shuffle", _arguments, null, token_2_0 );
							__messages.add( message );
						}
					}
				}
				addJoinToken(token_2_0);
				// join block
				token_2_1.setJoinDirector();
				for (int i = 0; i<shuffleActors.length; i++){
					{
						// shuffleActors[i]<-get()
						{
							Object _arguments[] = {  };
							Message message = new Message( self, shuffleActors[i], "get", _arguments, token_2_0, token_2_1 );
							__messages.add( message );
						}
					}
				}
				addJoinToken(token_2_1);
				// reduce_func(token, output)
				{
					Object _arguments[] = { token_2_1, output };
					Message message = new Message( self, self, "reduce_func", _arguments, token_2_1, null );
					__messages.add( message );
				}
			}
		}
		public void runMapReduce(String inputFilename, String outputFilename) {
			Vector vec0 = new Vector();
			PairReader pr = new PairReader(inputFilename);
			Pair p;
			while ((p=pr.readPair())!=null) vec0.add(p);
			{
				Token token_2_0 = new Token();
				// join block
				token_2_0.setJoinDirector();
				for (int i = 0; i<vec0.size(); ++i){
					{
						// mapActors[(i%mapActors.length)]<-map(vec0.get(i))
						{
							Object _arguments[] = { vec0.get(i) };
							Message message = new Message( self, mapActors[(i%mapActors.length)], "map", _arguments, null, token_2_0 );
							__messages.add( message );
						}
					}
				}
				addJoinToken(token_2_0);
				// shuffle_func(token, outputFilename)
				{
					Object _arguments[] = { token_2_0, outputFilename };
					Message message = new Message( self, self, "shuffle_func", _arguments, token_2_0, null );
					__messages.add( message );
				}
			}
		}
		public void output(Vector v, String outputFilename) {
			PairWriter pw = new PairWriter(outputFilename);
			for (int i = 0; i<v.size(); ++i)pw.writePair((Pair)v.get(i));
			pw.close();
		}
		public void act(String[] args) {
			int m = Integer.parseInt(args[2]);
			int s = Integer.parseInt(args[3]);
			int r = Integer.parseInt(args[4]);
			MapReduce cc = new CharCountMapReduce(m, s, r);
			MapReduceProblem charCount = ((MapReduceProblem)new MapReduceProblem(this).construct(cc));
			String inputFilename = args[0];
			String outputFilename = args[1];
			{
				// charCount<-runMapReduce(inputFilename, outputFilename)
				{
					Object _arguments[] = { inputFilename, outputFilename };
					Message message = new Message( self, charCount, "runMapReduce", _arguments, null, null );
					__messages.add( message );
				}
			}
		}
	}
}