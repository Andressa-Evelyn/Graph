package eulerianpath;

import minimalspanningtree.DisconnectedGraphException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Path;

import org.graphstream.graph.implementations.Graphs;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static eulerianpath.eulerianPathUtilities.eachNodeHasEvenDegree;
import static graphgenerator.Generator.*;
import static minimalspanningtree.MstUtitlities.isConnectedGraph;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static graphgenerator.GeneralGraphUtils.random;

public class TestClient {

    // graphs created from .gka-file graphs
    private static Graph eulerGraph01;
    private static Graph eulerGraph02;
    private static Graph eulerGraph03;

    // randomly generated eulerian graphs
    private static Graph randEulerGraph01;
    private static Graph randEulerGraph02;
    private static Graph randEulerGraph03;
    private static Graph randEulerGraph04;
    private static Graph randEulerGraph05;

    private static Fleury fleury;
    private static Hierholzer hierholzer;

    private static Graph[] allGraphs;
    private static Graph[] nonEulerianGraphs;

    private static Graph[] smallGraphs;
    private static Graph[] randGraphs;

    private static List<Path> pathsFleury = new ArrayList<>();

    @BeforeClass
    public static void setup() throws FileNotFoundException {
        eulerGraph01 = createFromFile("/eulerian_graphs/eulerian_graph01.gka");
        eulerGraph02 = createFromFile("/eulerian_graphs/eulerian_graph02.gka");
        eulerGraph03 = createFromFile("/eulerian_graphs/eulerian_graph03.gka");

        randEulerGraph01 = generate(5);
        randEulerGraph02 = generate(10);
        randEulerGraph03 = generate(15);
        randEulerGraph04 = generate(20);
        randEulerGraph05 = generate(25);

        smallGraphs = new Graph[] {eulerGraph01, eulerGraph02, eulerGraph03};
        randGraphs = new Graph[] {randEulerGraph01, randEulerGraph02, randEulerGraph03, randEulerGraph04,
                randEulerGraph05};
        allGraphs = new Graph[] {eulerGraph01, eulerGraph02, eulerGraph03, randEulerGraph01, randEulerGraph02,
                randEulerGraph03, randEulerGraph04, randEulerGraph05};

        nonEulerianGraphs = new Graph[allGraphs.length];

        /* add another edge to a eulerian graph to get an odd nodeDegree */
        for (int i = 0; i < allGraphs.length; i++) {
            Graph g = Graphs.clone(allGraphs[i]);
            g.addEdge("000", g.getNode(0).getId(), g.getNode(1).getId());
            nonEulerianGraphs[i] = g;
        }
    }

    @Test
    public void eachNodeHasEvenDegreeAssertTrue() {
        for (Graph graph : allGraphs) {
            assertTrue(eachNodeHasEvenDegree(graph));
        }
    }

    @Test
    public void isEulerianCyle() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : smallGraphs) {
            fleury = new Fleury(graph);
            fleury.compute();
            hierholzer = new Hierholzer(graph);
            hierholzer.compute();

            Path fleuryPath = fleury.getPath();
            Path hierholzerPath = hierholzer.getPath();

            assertEquals(fleuryPath.getRoot(), fleuryPath.peekNode());
            assertEquals(hierholzerPath.getRoot(), hierholzerPath.peekNode());
        }
    }

    @Test
    public void fleuryEqualsHierholzer() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : allGraphs) {
            fleury = new Fleury(graph);
            fleury.compute();

            hierholzer = new Hierholzer(graph);
            hierholzer.compute();

            assertEquals(fleury.getPath().getEdgeCount(), hierholzer.getPath().getEdgeCount());
        }
    }

    @Test
    public void isEulerCycleFleuryAssertTrue() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : smallGraphs) {
            fleury = new Fleury(graph);
            fleury.compute();

            Path path = fleury.getPath();
            List<Edge> graphEdges = graph.edges().collect(Collectors.toList());
            List<Edge> pathEdges = path.edges().collect(Collectors.toList());

            assertEquals(graph.getEdgeCount(), path.getEdgeCount());
            assertEquals(graphEdges.containsAll(pathEdges), pathEdges.containsAll(graphEdges));
        }
    }

    @Test
    public void isEulerCycleHierholzerAssertTrue() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : smallGraphs) {
            hierholzer = new Hierholzer(graph);
            hierholzer.compute();

            Path path = hierholzer.getPath();
            List<Edge> graphEdges = graph.edges().collect(Collectors.toList());
            List<Edge> pathEdges = path.edges().collect(Collectors.toList());

            assertEquals(graph.getEdgeCount(), path.getEdgeCount());
            assertEquals(graphEdges.containsAll(pathEdges), pathEdges.containsAll(graphEdges));
        }
    }

    @Test(expected = OddNodeDegreeException.class)
    public void computeFleuryWithNonEulerGraph() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : nonEulerianGraphs) {
            fleury = new Fleury(graph);
            fleury.compute();
        }
    }

    @Test(expected = OddNodeDegreeException.class)
    public void computeHierholzerWithNonEulerGraph() throws OddNodeDegreeException, DisconnectedGraphException {
        for (Graph graph : nonEulerianGraphs) {
            hierholzer = new Hierholzer(graph);
            hierholzer.compute();
        }
    }

    @Test
    public void randomEulerianGraphEachNodeEvenDegreeCheck() {
        for (Graph graph : randGraphs) {
            assertTrue(eachNodeHasEvenDegree(graph));
        }
    }

    @Test
    public void randomEulerianGraphIsConnected() {
        for (Graph graph : randGraphs) {
            assertTrue(isConnectedGraph(graph));
        }
    }

    @Test
    public void randomEulerianGraphIsEulerian() {
        int iterations = random(20, 101);
        for (int i = 0; i < iterations; i++) {
            Graph graph = generate(i * 3);

            assertTrue(eachNodeHasEvenDegree(graph));
            assertTrue(isConnectedGraph(graph));
        }
    }

}
