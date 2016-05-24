exampleInput.txt and exampleOutput.txt are input and output files for the character counting problem.

salsa.bat, salsac.bat, wwwcns.bat, wwctheater.bat are batch files for Windows.

cshrc.txt are commands to be added to the .cshrc file for Unix.

theaters.txt is a sample name server and theaters description file.

In the mapreduce directory:
MapReduce.java is the interface for specifying Map-Reduce problems.
CharCountMapReduce.java is a sample specification of a Map-Reduce problem.
MapReduceProblem.salsa, Map.salsa and Reduce.salsa show how to use a problem specification.
Pair.java, PairReader.java and PairWriter.java are I/O utilities compatible with the Map-Reduce I/O specifications.

You need to write a Shuffle behavior and complete the runMapReduce message handler of the MapReduceProblem behavior.

After implementing the Map-Reduce framework, the concurrent program should be able to run with the following commands:
salsac mapreduce/*
salsa mapreduce.MapReduceProblem exampleInput.txt exampleOutput.txt 3 3 3

And the distributed program should be able to run with the following commands:
salsac mapreduce/*
salsa mapreduce.MapReduceProblem exampleInput.txt exampleOutput.txt 3 3 3 theaters.txt

