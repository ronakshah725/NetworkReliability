Program code:
import java.io.BufferedReader;
import java.io.InputStreamReader; import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
public class FWNetworkTopology
{
/*----initialising variables----*/
public static int node = 35;
public static int outgoingLink_k = 0; public static int shrtstPathCost = 0; public static double networkDensity = 0; public static double finalEdge = 0;
/*----initalising arrays---*/
public static int[][] t_Demand_b = new int[35][35]; public static int[][] estimatedCost = new int[35][35]; public static int[][] fWPath = new int[35][35];
public static int[][] networkRoute = new int[35][35]; public static int[][] edge = new int[35][35];
public static int[][] totalFWPathCost = new int[35][35];
public static void main(String args[]) {
try
{
BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); System.out.println("Enter k value:");
outgoingLink_k = Integer.parseInt(userInput.readLine());
/*--method calls--*/
if(outgoingLink_k>2 && outgoingLink_k<16)
{
randomGeneration(); FWAlgImplementation(); fWCostCalculation(); densityComputation();
}
else
{
System.out.println("Please enter k value between 3 and 15");
System.exit(0); }
}
catch (Exception e) { e.printStackTrace(); }
}
public static void randomGeneration() {
/*-- taking array list for storing generated random values of demand and cost values*/ List<Integer> nodesList = new ArrayList<Integer>();
/*for the initialization of 35 nodes*/
for (int start_i_Count = 0; start_i_Count < node; ++start_i_Count) {
nodesList.add(start_i_Count);
}
Collections.shuffle(nodesList);
int kCount = 0;
/*-- Matrix generation --*/
for (int start_i_Count = 0; start_i_Count < node; ++start_i_Count) {
nodesList.remove((Integer) start_i_Count);
kCount = 0;
for (int end_j_count = 0; end_j_count < node; ++end_j_count)
{
estimatedCost[start_i_Count][nodesList.get(end_j_count)] = 1; kCount++;
if (kCount == outgoingLink_k)
{
break;
}
} nodesList.add(start_i_Count); Collections.shuffle(nodesList); }
/*---cost generation to generate random aij values---*/
for (int start_i_Count = 0; start_i_Count < node; ++start_i_Count) for (int end_j_count = 0; end_j_count < node; ++end_j_count)
{
if (start_i_Count == end_j_count) estimatedCost[start_i_Count][end_j_count] = 0;
else if (estimatedCost[start_i_Count][end_j_count] != 1) estimatedCost[start_i_Count][end_j_count] = 300;
}
/*---loop for generating traffic demand bij---*/
Random randomGenerator = new Random();
for (int start_i_Count = 0; start_i_Count < node; ++start_i_Count) for (int end_j_count = 0; end_j_count < node; ++end_j_count)
{
if (start_i_Count != end_j_count) t_Demand_b[start_i_Count][end_j_count] = sRIntGeneration(0, 3, randomGenerator);
else
t_Demand_b[start_i_Count][end_j_count] = 0;
}
}
private static int sRIntGeneration(int aStart, int aEnd, Random aRandom) {
if (aStart > aEnd)
{
throw new IllegalArgumentException("Start cannot exceed End."); }
long range = (long) aEnd - (long) aStart + 1;
long fraction = (long) (range * aRandom.nextDouble());
int randomNumber = (int) (fraction + aStart); return randomNumber;
}
/*---implemantation of Floyd Warshall Algorithm---*/ private static void FWAlgImplementation()
{
for (int kCount = 0; kCount < node; ++kCount)
{
/*---loop for iterative process---*/
for (int start_i_Count = 0; start_i_Count < node; ++start_i_Count)
for (int end_j_count = 0; end_j_count < node; ++end_j_count)
{
if ((estimatedCost[start_i_Count][kCount] * estimatedCost[kCount][end_j_count] != 0)
&& (start_i_Count != end_j_count)
&& (estimatedCost[start_i_Count][end_j_count] != 0))
{
if ((estimatedCost[start_i_Count][kCount] + estimatedCost[kCount][end_j_count]) < estimatedCost[start_i_Count][end_j_count])
{
fWPath[start_i_Count][kCount] += t_Demand_b[start_i_Count][end_j_count]; fWPath[kCount][end_j_count] += t_Demand_b[start_i_Count][end_j_count]; estimatedCost[start_i_Count][end_j_count] = estimatedCost[start_i_Count][kCount] + estimatedCost[kCount][end_j_count];
/*----edge Insertion for satisfied condition according to generated matrix----*/ edgeInsertion(start_i_Count, kCount);
edgeInsertion(kCount, end_j_count);
networkRoute[start_i_Count][end_j_count] = kCount;
}
else
{
fWPath[start_i_Count][end_j_count] += t_Demand_b[start_i_Count][end_j_count]; edgeInsertion(start_i_Count, end_j_count);
networkRoute[start_i_Count][end_j_count] = 0;
}
}
else if (estimatedCost[start_i_Count][end_j_count] != 0)
{
fWPath[start_i_Count][end_j_count] += t_Demand_b[start_i_Count][end_j_count]; edgeInsertion(start_i_Count, end_j_count);
networkRoute[start_i_Count][end_j_count] = 0;
}
}
}
}
public static void edgeInsertion(int a, int b)
{
if (b > a)
edge[b][a] = 0;
else
edge[a][b] = 1;
}
/*--- calculation of total cost according to shortest path calculated---*/
public static void fWCostCalculation()
{
System.out.println("The traffic cost between the nodes for given traffic demand\n"); for (int start_i_Count = 0; start_i_Count < node; start_i_Count++)
{
for (int end_j_count = 0; end_j_count < node; end_j_count++)
{
totalFWPathCost[start_i_Count][end_j_count] = t_Demand_b[start_i_Count][end_j_count] * estimatedCost[start_i_Count][end_j_count];
shrtstPathCost += t_Demand_b[start_i_Count][end_j_count] * estimatedCost[start_i_Count][end_j_count];
System.out.print("\t" + totalFWPathCost[start_i_Count][end_j_count]);
}
System.out.println();
}
}
public static void densityComputation()
{
System.out.println("The edges in the shortest fWPath are");
for (int start_i_Count = 0; start_i_Count < node; start_i_Count++)
{
for (int end_j_count = 0; end_j_count < node; end_j_count++)
{
if (edge[start_i_Count][end_j_count] != 0)
finalEdge += 1;
System.out.print("\t" + edge[start_i_Count][end_j_count]);
}
System.out.println();
}
networkDensity = (double)(finalEdge) / (double)(node * (node - 1)); System.out.println("Total no.of directed edges is " + node* (node - 1)); System.out.println("Total no.of edges in the shortest fWPath graph is "+ finalEdge); System.out.println("networkDensity is " + networkDensity);
System.out.println("Total cost for entire traffic is " + shrtstPathCost);
}
}