package eulerianpath;

import minimalspanningtree.DisconnectedGraphException;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import org.graphstream.graph.implementations.Graphs;

import static eulerianpath.eulerianPathUtilities.eachNodeHasEvenDegree;
import static minimalspanningtree.MstUtitlities.*;


public class Fleury {

    private final Graph graph;
    private final Node startNode;
    private final Path path;
    private final ConnectedComponents cc;

    public Fleury(Graph inputGraph) {
        this.graph = Graphs.clone(inputGraph);
        this.startNode = this.graph.getNode(0);
        this.path = new Path();
        this.cc = new ConnectedComponents();
    }

    /**
     * Computes a eulerian graph by removing one edge at a time and checking whether the graph's
     * nodes still lie on one component
     * @param sourceNode -> currently observed Node
     */
    private void compute(Node sourceNode) {
        for (Edge edge : sourceNode) {
            if (edge != null) {
                Node targetNode = edge.getOpposite(sourceNode);
                this.graph.removeEdge(edge);

                if (sourceNode.getDegree() == 0 || this.cc.getConnectedComponentOf(sourceNode).contains(targetNode)) {
                    this.path.add(edge);
                    compute(targetNode);
                } else {
                    this.graph.addEdge(edge.getId(), sourceNode.getId(), targetNode.getId());
                }
            }
        }
    }

    public void compute() throws DisconnectedGraphException, OddNodeDegreeException {
        if (!isConnectedGraph(this.graph))
            throw new DisconnectedGraphException("The passed graph must be connected");
        if (!eachNodeHasEvenDegree(this.graph))
            throw new OddNodeDegreeException("Each node of the graph must have an even edge degree");

        this.cc.init(this.graph);
        this.path.setRoot(this.startNode);
        compute(this.startNode);
    }

    public Path getPath() {
        return this.path;
    }

    public Graph getGraph() {
        return this.graph;
    }

}
