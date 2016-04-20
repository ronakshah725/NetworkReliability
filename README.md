
INTRODUCTION
Our goal is to design an algorithm to evaluate the reliability of a given network configuration. For obtaining this goal, we rely on the probability of up and down states of links in the network. We use these probabilities to determine the overall probability of the network. We start by assuming a link reliability for all the links in the network. Then, we consider all the possible combinations in which the given network can be connected with the same number of links. For all these combinations, we calculate the overall reliability of the network. This kind of approach is called exhaustive enumeration and is useful for small network topologies without any particular connectivity pattern like series or parallel or series-parallel. 
We also experiment by flipping the states of some links in the network from up to down. Keeping the link reliabilities same, we study the reliability of the network under these network changes. 

SIMULATION ENVIORNMENT

Network Topology
We simulate the network topology as an undirected graph with 5 nodes and 10 edges. In this topology, every node is connected to every other node, and we avoid parallel and self edges, making the number of edges in the network to be 10. This leads to 2^10 = 1024 possible combinations in which the network topology can be laid out.  

Link Reliability
The link reliability, p, for each node is the same for a particular configuration of the network. For the first part of the experiment, we vary the reliability p between [0.0,1.0] in steps of 0.05. For each of these values of p, we will find the network reliability. For the second part of the experiment, we flip the states of links from k randomly chosen network combinations out of the 1024 combinations. Here, k goes from 0 to 25 and for each of this k values, we find the network reliability.


ALGORITHM
PART I
1. To determine the network reliability, we consider all the possible 1024 combinations of the topology as described above. This can be done by generating binary numbers from 0 to 1023 of 10-bit size. For example, consider 5 nodes as n0, n1, n2, n3, n4
1 0 0 1 0 0 0 1 1 1
  0 1 2 3 4 5 6 7 8 9
The first four bits, 0,1,2,3 represent edge state from n0 to n1, n2, n3 and n4 respectively. 
The next 3 bits, 4,5,6, represent edge state from n1 to n2, n3,4 
The next 2 bits, 7,8, represent edge state from n2 to n3, n4
The bit, 9, represent edge state from n3 to n4 
We call this edge_matrix in the Python code.
2. We process edge_matrix to find combinations which represent a connected graph. This is done by transforming the edge-matrix into an adjaceny matrix. Then we run Depth-First-Search to find whether the connected components go through all the nodes in the network, to confirm the network is connected.  
3. For each connected combination, we calculate the reliability of that combination using its edge-matrix. This is done as follows 
Set reliability as 1.0
For each edge
	If edge is up
		reliability = reliability * p
	if edge is down
		reliability = reliability * 1-p
		where 1-p is the probability that the link is down.
4. We calculate the total network reliability by summing up the reliability for all the 1024 possible network combinations
5. We repeat steps 2-5 for p values in [0.0, 1.0] in steps of 0.05

INFERENCE 

-	There is a strong correlation between the link probability and the corresponding networking reliability. 
-	Initially there is a slow start in the improvement in reliability, for smaller values in p. 
-	After a cut-off, there is a steady increase in reliability for higher values in p
-	But, for higher values of p from 0.8, the reliability becomes almost constant
-	More denser networks have a better network reliability
These facts state that higher the probability of individual links staying up, better is the reliability of the network. To guarantee a very high availability, we should make sure that the individual links are configured such that they work at least 8/10 times. But to confirm such a claim, we need to run the experiment changing different parameters like performance of link under low and high traffic, performance with different hardware like co-axial or optical fiber. 


CONCLUSION
From our experimentation, we can confirm that the overall network reliability can be measured by using a probabilistic model for the up/down states of a link. The better the probability to stay up, the better is the reliability. Also, we learn that denser networks have a better network reliability. 


 RUN
1.	Copy paste the code to two separate files as per the given files names above
2.	Download and install python 2.7
3.	To run, Goto the Dir where above files are located by terminal or command prompt
4.	Type commands: python reliability.py

HELPFUL LINKS
-	L. E. Miller, J. J. Kelleher, and L. Wong, "Assessment of Network Reliability Calculation Methods," J. S. Lee Associates, Inc. report JC-2097-FF under contract DAAL02-92-C-0045, January 1993.
-	Python Documentation






