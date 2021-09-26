package graphgenerator;

import edu.princeton.cs.algs4.QuickUnionUF;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static minimalspanningtree.MstUtitlities.setEdgeWeight;
import static org.graphstream.algorithm.Toolkit.randomNode;
import static graphgenerator.GeneralGraphUtils.random;

/**
* A generator to either create graphs from files or randomly
*/
public class Generator {

    public static final String styleSheet = "url(file:resources/stylesheet.css)";
    private static final Pattern ADJACENCY_PATTERN = Pattern.compile(
            "^([a-zA-Z0-9]+)\\s*?(--|->)?\\s*?([a-zA-Z0-9]+)?(?:\\s*?(?::|\\()\\s*?([a-zA-Z0-9]+?)?)?\\)?;$"
    );

    /**
     * Generates a Graph from a .gka-file. A .gka-file is seperated into four different groups (columns):
     * 1st group:   source Node
     * 2nd group:   determines, whether the Graph will be directed (->) or undirected (--)
     * 3rd. group:  target Node
     * 4th group:   edge weight
     *
     * @param fileName .gka-file
     * @return Graph-object
     * @throws FileNotFoundException
     */
    public static Graph createFromFile(String fileName) throws FileNotFoundException {
        Graph graph = new MultiGraph(fileName);
        graph.setStrict(false);
        graph.setAutoCreate(true);                              // enable auto-creation of missing elements
        graph.setAttribute("ui.stylesheet", styleSheet);     // Apply style sheet for visualisation

        Scanner scanner = new Scanner(new File("resources/" + fileName), "Cp1252");

        while(scanner.hasNextLine()) {
            Matcher matcher = ADJACENCY_PATTERN.matcher(scanner.nextLine());

            while (matcher.find()) {
                String sourceId = matcher.group(1);
                String arc = matcher.group(2);
                String targetId = matcher.group(3);
                String weight = matcher.group(4);
                String edgeId = sourceId + targetId;

                if (arc == null) {
                    graph.addNode(sourceId);
                } else {
                    if (arc.equals("--")) {
                        graph.addEdge(edgeId, sourceId, targetId);
                        setEdgeWeight(graph.getEdge(edgeId), weight);
                    } else if (arc.equals("->")) {
                        graph.addEdge(edgeId, sourceId, targetId, true);
                        setEdgeWeight(graph.getEdge(edgeId), weight);
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Generates a random, connected graph.
     * @param nodeCount -> number of nodes the generated graph should contain
     * @param edgeCount -> number of edges the generated graph should contain
     * @return graph
     * @throws NodesExceedEdgesException a graph must contain at least nodeCount - 1 edges
     */
    public static Graph generate(int nodeCount, int edgeCount) throws Exception {
        if (nodeCount > edgeCount)
            throw new NodesExceedEdgesException("EdgeCount must be at least as high as NodeCount - 1");

        Graph graph = generateSpanningTree(nodeCount);
        graph.setAttribute("ui.stylesheet", styleSheet);
        edgeCount -= nodeCount - 1;

        return randomConnectedGraph(nodeCount, edgeCount, graph);
    }

    /**
     * Generates a eulerian graph.
     * @param nodeCount -> number of nodes the generated graph should contain
     * @return
     */
    public static Graph generate(int nodeCount) {
        Graph graph = generateSpanningTree(nodeCount);
        graph.setAttribute("ui.stylesheet", styleSheet);

        List<Node> list = graph.nodes()
                .filter(node -> node.edges().count() % 2 == 1)
                .collect(Collectors.toList());

        return randomEulerianGraph(nodeCount, graph, list);
    }

    /**
     * Creates a minimal spanning tree (MST) by using the QuickUnionUF-algorithm.
     * @param nodeCount -> number of nodes the MST should contain
     * @return graph
     */
    private static Graph generateSpanningTree(int nodeCount) {
        QuickUnionUF quickUnionUF = new QuickUnionUF(nodeCount);
        Graph graph = new MultiGraph("G");
        graph.setStrict(false);
        graph.setAutoCreate(true);
        int edgeId = 0;

        while (quickUnionUF.count() > 1) {
            int node = quickUnionUF.find(random(0, nodeCount));
            int root = quickUnionUF.find(random(0, nodeCount));
            if (node != root) {
                quickUnionUF.union(node, root);
                graph.addEdge(String.valueOf(edgeId), String.valueOf(node), String.valueOf(root));
                edgeId++;
            }
        }
        return graph;
    }

    /**
     * Randomly connects the nodes of the MST until there are as many edges as passed (edgeCount).
     * @param nodeCount -> number of nodes of the MST
     * @param edgeCount -> number of edges to be added
     * @param graph -> MST
     * @return graph
     */
    private static Graph randomConnectedGraph(int nodeCount, int edgeCount, Graph graph) {
        int edgeId = nodeCount;

        while (edgeCount > 0) {
            Node node0 = randomNode(graph);
            Node node1 = randomNode(graph);

            if (node0 != node1) {
                graph.addEdge(String.valueOf(edgeId), node0, node1);
                edgeId++;
                edgeCount--;
            }
        }
        return graph;
    }

    /**
     * Randomly connects two nodes (per recursive call) having odd edge degree in order to generate a eulerian graph.
     * @param edgeId -> Id of the added edge
     * @param graph -> MST
     * @param list -> a list containing all nodes having odd edge degree
     * @return graph
     */
    private static Graph randomEulerianGraph(int edgeId, Graph graph, List<Node> list) {
        if (list.isEmpty()) return graph;

        Node node0 = list.get(random(0, list.size()));
        Node node1 = list.get(random(0, list.size()));

        if (node0 == node1) return randomEulerianGraph(edgeId, graph, list);
        else {
            graph.addEdge(String.valueOf(edgeId), node0, node1);
            edgeId++;
            list.remove(node0);
            list.remove(node1);
        }

        return randomEulerianGraph(edgeId, graph, list);
    }

}
