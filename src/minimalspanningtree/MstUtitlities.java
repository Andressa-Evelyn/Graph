package minimalspanningtree;

import graphgenerator.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import shortestpath.BfIterator;

import java.util.Iterator;

public class MstUtitlities {

    public static int getEdgeWeight(Edge edge) {
        if (edge.hasAttribute("weight")) {
            return Integer.parseInt((String) edge.getAttribute("weight"));
        }

        return 1;
    }

    public static void setEdgeWeight(Edge edge, String weight) {
        if (weight == null) {
            edge.setAttribute("weight", "1");
        } else {
            edge.setAttribute("weight", weight);
        }
    }

    /**
     * Replaces all undirected edges by directed ones.
     * @param graph undirected graph
     * @return directed graph
     */
    public static Graph transformToDirectedGraph(Graph graph) {
        Graph directedGraph = new SingleGraph("D" + graph.getId());
        directedGraph.setStrict(false);
        directedGraph.setAutoCreate(true);             // An edge can create its source and target node in graph
        directedGraph.setAttribute("ui.stylesheet.css", Generator.styleSheet);  // Apply style sheet for visualisation

        for (Node node : graph) {
            node.edges().forEach(edge -> {
                Node node0 = edge.getNode0();
                Node node1 = edge.getNode1();
                String weight = String.valueOf(edge.getAttribute("weight"));

                directedGraph.addEdge(edge.getId(), node0, node1, true);
                setEdgeWeight(directedGraph.getEdge(edge.getId()), weight);
                directedGraph.addEdge(node1.getId() + node0.getId(), node1, node0, true);
                setEdgeWeight(directedGraph.getEdge(node1.getId() + node0.getId()), weight);
            });
        }

        return directedGraph;
    }

    public static boolean isConnectedGraph(Graph graph) {
        Iterator<Node> bfIterator = new BfIterator(graph, graph.getNode(0));
        int nodeCount = graph.getNodeCount();
        int count = 0;

        while (bfIterator.hasNext()) {
            bfIterator.next();
            count++;
        }

        return count == nodeCount;
    }

}
