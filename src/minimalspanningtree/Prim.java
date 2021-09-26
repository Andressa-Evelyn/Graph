package minimalspanningtree;

import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import static minimalspanningtree.MstUtitlities.*;

public class Prim {

    private double weight = 0.0;
    private Graph graph;
    private final Graph mst;
    private final FibonacciHeap<Integer, Edge> fibonacciHeap = new FibonacciHeap<>();

    public Prim(Graph graph) {
        this.graph = graph;
        this.mst = newGraph("P" + graph.getId());
    }

    /**
     * Führt den Prim Algorithmus aus
     *
     * @throws Exception
     */
    public void compute(int startNodeIndex) throws DisconnectedGraphException {
        // Checks whether graph is connected
        if (!isConnectedGraph(this.graph)) {
            throw new DisconnectedGraphException("The passed graph must be connected");
        }

        // Tranform input graph to directed graph for better graph handling
        this.graph = transformToDirectedGraph(graph);

        Node startNode = this.graph.getNode(startNodeIndex);
        // Mark start node visited
        markVisited(startNode);
        // Add leaving edges of start node to priority queue
        addEdges(startNode);

        // Run algorithm while node count of minimal is less than node count of input graph
        while (this.mst.getNodeCount() != this.graph.getNodeCount()) {
            Edge edge = this.fibonacciHeap.extractMin();
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();

            if (!isVisited(targetNode)) {
                markVisited(targetNode);
                this.mst.addEdge(edge.getId(), sourceNode.getId(), targetNode.getId());
                this.weight += (double) getEdgeWeight(edge);
                // adds all edges of current target node to priority queue
                addEdges(targetNode);
            }
        }
    }

    public void compute() throws Exception {
        this.compute(0);
    }

    /**
     * Fügt der PriorityQueue neue Kante hinzu, wenn die betrachtete Node Kanten hat
     *
     * @param targetNode betractete Kante
     */
    private void addEdges(Node targetNode) {
        targetNode.leavingEdges().forEach(edge -> {
            if (!isVisited(edge.getTargetNode()))
                this.fibonacciHeap.add(getEdgeWeight(edge), edge);
        });
    }

    private void markVisited(Node node) {
        node.setAttribute("visited");
    }

    private boolean isVisited(Node node) {
        return node.hasAttribute("visited");
    }

    public Graph getGraph() {
        return this.mst;
    }

    public double getTreeWeight() {
        return this.weight;
    }

}
