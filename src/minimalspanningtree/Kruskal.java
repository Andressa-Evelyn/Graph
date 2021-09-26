package minimalspanningtree;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import graphgenerator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static minimalspanningtree.MstUtitlities.getEdgeWeight;
import static minimalspanningtree.MstUtitlities.isConnectedGraph;

public class Kruskal {

    private double weight = 0.0;
    private final Graph graph;
    private final Graph mst;

    public Kruskal(Graph graph) {
        this.graph = graph;
        this.mst = Generator.newGraph("K" + graph.getId());
    }

    /**
     * Computes the passed graph's minimal spanning tree by using the WeightedQuickUnionUF-Algorithm
     * (algs4.cs.princeton.edu).
     * @throws Exception
     */
    public void compute() throws DisconnectedGraphException {
        // Checks whether graph is connected
        if (!isConnectedGraph(this.graph)) {
            throw new DisconnectedGraphException("The passed graph must be connected");
        }

        // Add all weighted edges of the graph to the list
        List<Edge> edges = new ArrayList<>(this.graph.edges().collect(Collectors.toList()));
        // Sort list of edges by weight
        edges.sort(Comparator.comparingInt(MstUtitlities::getEdgeWeight));

        WeightedQuickUnionUF weightedQUF = new WeightedQuickUnionUF(this.graph.getNodeCount());

        for (Edge edge : edges) {
            Node sourceNode = edge.getSourceNode();
            Node targetNode = edge.getTargetNode();
            int sourceParent = weightedQUF.find(sourceNode.getIndex());
            int targetParent = weightedQUF.find(targetNode.getIndex());

            if (sourceParent != targetParent) {
                this.weight += getEdgeWeight(edge);
                this.mst.addEdge(edge.getId(), sourceNode.toString(), targetNode.toString());
                weightedQUF.union(sourceNode.getIndex(), targetNode.getIndex());
            }
        }
    }

    public Graph getGraph() {
        return this.mst;
    }

    public double getTreeWeight() {
        return this.weight;
    }
}
