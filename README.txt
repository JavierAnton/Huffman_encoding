THIS PROGRAM USES HUFFMAN ENCODING TO ENCODE SHOTS OF A POKER DICE. TO SEE THE WORK, 
THE DICE IS TRICKED AND EVERY FACE HAS IT'S OWN PROBABILITY.

Probability:
	Diamond (D) - 0.3
	K - 0.2
	Q - 0.2
	J - 0.15
	10- 0.10
	9 - 0.05

1.CreateSequence.java 
	-Creates an aleatory data of "D K Q J 10 9" (with establish probability)
	-Saves in "huffman.txt"

2.HuffmanEncode.java
	-Creates the Huffman Tree form the  simbol-probability Map
	-Open the file "huffman.txt" (previously created , there is one already of 10.000.000 elements)
	-Shows the compresion factor accomplished

OBSERVATIONS:
	-There is a huffman.txt already created of aleatory 10.000.000 elements (by it's probablility)
	-You would notice that when longer is the huffman.txt file, the compresión factor gets closer to it's theoretical factor.
	-The number of used bits between the entropy determines the highest compresion reachable for our data. 
	 And the number of normal bits in our code binary file shows the compresion factor.
	 Due to our factor is close to the ideal entropy , it mean that our compresion is optimum.

