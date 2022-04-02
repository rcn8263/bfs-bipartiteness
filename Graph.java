/// Author: Ryan Nowak

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Graph {

    private static void BFS(Node[] nodes, Node s) {
        //Set Discovered[s] = true and Discovered[v] = false for all other v
        boolean[] visited = new boolean[nodes.length];
        boolean allVisited = false;
        visited[s.id-1] = true;

        List<Queue<Node>> queues = new ArrayList<>();
        queues.add(new LinkedList<Node>());

        //Graph by default is bipartite until proven otherwise
        boolean bipartite = true;

        //Initialize L[0] to consist of the single element s
        queues.get(0).add(s);

        //Set the layer counter i = 0
        int layer = 0;
        s.layer = layer;
        //Set the current BFS tree T = ∅
        //NOTE: this is not done because function does not return a tree

        System.out.println("connected component: " + s.id);

        while (!allVisited) {
            //While L[i] is not empty
            while (!queues.get(layer).isEmpty()) {
                //Initialize an empty list L[i + 1]
                queues.add(new LinkedList<Node>());

                //For each node u ∈ L[i]
                for (Node u : queues.get(layer)) {
                    u.layer = layer;
                    System.out.print(u.id + "(" + u.layer + ") ");
                    //Consider each edge (u, v) incident to u
                    for (Node v : u.adj) {
                        //If Discovered[v] = false then
                        if (!visited[v.id - 1]) {
                            //Set Discovered[v] = true
                            visited[v.id - 1] = true;
                            v.layer = layer + 1;
                            //Add v to the list L[i + 1]
                            queues.get(layer + 1).add(v);
                        } //Endif
                        //Check for bipartite
                        //If node u is in an even layer, then node v must be in an
                        //odd layer for the graph to be bipartite and vice versa
                        else {
                            if (!((u.layer % 2 == 0 && v.layer % 2 == 1) ||
                                    (u.layer % 2 == 1 && v.layer % 2 == 0))) {
                                bipartite = false;
                            }
                        }
                    } //Endfor
                } //Endfor
                layer++;
            } //Endwhile

            //Print whether or not graph is bipartite
            System.out.println();
            if (bipartite) {
                System.out.println("bipartite");
            } else {
                System.out.println("not bipartite");
            }

            //Check if every node was visited
            allVisited = true;
            for (int i = 0; i < nodes.length; i++) {
                if (!visited[i]) {
                    Node startNode = nodes[i];
                    layer = 0;
                    startNode.layer = layer;
                    visited[i] = true;
                    allVisited = false;
                    queues.clear();
                    queues.add(new LinkedList<Node>());
                    queues.get(0).add(startNode);
                    System.out.println("connected component: " + startNode.id);
                    break;
                }
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        //File input = new File(args[0]);
        File input = new File(args[0]);
        Scanner scan = new Scanner(input);

        String line = scan.nextLine().trim();
        int numNodes = Integer.parseInt(line);

        //Initialize all nodes
        Node[] nodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i+1);
        }

        //Add adjacency list to each node
        for (int i = 0; i < numNodes; i++) {
            line = scan.nextLine().trim();
            String[] splitLine = line.split("\\s+");
            for (int j = 1; j < splitLine.length; j++) {
                nodes[i].addEdge(nodes[Integer.parseInt(splitLine[j])-1]);
            }
        }

        scan.close();

        //Print nodes and graph
        System.out.println(numNodes);
        for (int i = 0; i < numNodes; i++) {
            System.out.println(nodes[i].toString());
        }

        //Print BFS
        System.out.print("\n");
        BFS(nodes, nodes[0]);
    }
}
