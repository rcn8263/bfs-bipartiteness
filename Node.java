import java.util.*;

public class Node {

    public int id;             // node number
    public int layer;          // BFS layer number
    public Set<Node> adj;      // adjacent nodes

    public Node( int id ) {
        this.id = id;
        adj = new LinkedHashSet<Node>();
        layer = -1;            // not visited yet by BFS
    }

    // add the given node to the adjacency list of this node
    // @ param node  the node to add
    public void addAdj( Node node ) {
        if ( this.id == node.id )
            throw new RuntimeException("loops not allowed");
        adj.add( node );
    }

    // add the given node to the adjacency set of this node
    //   and add this node to the adjacency set of the given node
    // @param node  the node to add
    public void addEdge( Node node ) {
        addAdj( node );      // add node to this adjacency set
        node.addAdj( this ); // and this to node's adjacency set
    }

    public String toString() {
        String str = "Node["+id+"]:";
        for (Node node: adj)
            str += " "+node.id;
        return str;
    }

}
