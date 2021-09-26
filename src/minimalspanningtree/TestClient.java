package minimalspanningtree;

import graphgenerator.Generator;
import org.graphstream.graph.Graph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static minimalspanningtree.MstUtitlities.isConnectedGraph;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestClient {

    // connected graphs
    private static Graph graph101;
    private static Graph graph03;
    private static Graph graph04;
    private static Graph graph05;
    private static Graph graph07;
    private static Graph graph08;
    private static Graph graph10;

    // not connected graphs
    private static Graph graph_nc_1;
    private static Graph graph_nc_2;
    private static Graph graph_nc_3;

    // random graphs
    private static Graph randGraph1;
    private static Graph randGraph2;
    private static Graph randGraph3;
    private static Graph randGraph4;
    private static Graph randGraph5;
    private static Graph randGraph6;

    // own algos
    private static Prim ownPrim;
    private static Kruskal ownKruskal;

    // graphstream algos
    private static org.graphstream.algorithm.Kruskal libKruskal;
    private static org.graphstream.algorithm.Prim libPrim;

    private final Graph[] allGraphs = {graph101, graph03, graph04, graph05, graph07, graph08, graph10};
    private final Graph[] allNcGraphs = {graph_nc_1, graph_nc_2, graph_nc_3};
    private final Graph[] allRandGraphs = {randGraph1, randGraph2, randGraph3, randGraph4, randGraph5, randGraph6};
    private static final double[] expectedWeights = new double[40];

    @BeforeClass
    public static void setup() throws Exception {
        // connected graphs
        graph101 = Generator.createFromFile("/undirected_graphs/graph101.gka");
        graph03 = Generator.createFromFile("/undirected_graphs/cities.gka");
        graph04 = Generator.createFromFile("/undirected_graphs/graph_weighted_02.gka");
        graph05 = Generator.createFromFile("/undirected_graphs/graph_weighted_03.gka");
        graph07 = Generator.createFromFile("/undirected_graphs/graph_not_connected_01.gka");
        graph08 = Generator.createFromFile("/undirected_graphs/graph08.gka");
        graph10 = Generator.createFromFile("/undirected_graphs/graph10.gka");

        // not connected graphs
        graph_nc_1 = Generator.createFromFile("/undirected_graphs/graph04_not_connected.gka");
        graph_nc_2 = Generator.createFromFile("/undirected_graphs/graph05_not_connected.gka");
        graph_nc_3 = Generator.createFromFile("/undirected_graphs/graph07_not_connected.gka");

        // randpm graphs
        randGraph1 =  Generator.generate(50, 50);
        randGraph2 =  Generator.generate(100, 100);
        randGraph3 =  Generator.generate(200, 200);
        randGraph4 =  Generator.generate(300, 300);
        randGraph5 =  Generator.generate(400, 400);
        randGraph6 =  Generator.generate(500, 500);

        Arrays.sort(expectedWeights);

        // graphstream's kruskal algo
        libKruskal = new org.graphstream.algorithm.Kruskal("ui.class", "intree", "notintree");
        // graphstream's prim algo
        libPrim = new org.graphstream.algorithm.Prim("ui.class", "intree", "notintree");
    }

    @Test
    public void graphGenerateFromFileTest() {
        assertEquals(7, graph101.getNodeCount());
    }

    @Test
    public void isConnectedGraphReturnsTrue() {
        for (Graph graph : allGraphs) {
            assertTrue(isConnectedGraph(graph));
        }
    }

    @Test
    public void isConnectedGraphReturnsFalse() {
        for (Graph graph : allNcGraphs) {
            assertFalse(isConnectedGraph(graph));
        }
    }

    @Test
    public void ownKruskalEqualsLibKruskal() throws Exception {
        for (Graph graph : allGraphs) {
            ownKruskal = new Kruskal(graph);
            ownKruskal.compute();
            libKruskal.init(graph);
            libKruskal.compute();

            assertEquals((long) libKruskal.getTreeWeight(), (long) ownKruskal.getTreeWeight());
        }
    }

    @Test
    public void ownPrimEqualsLibPrim() throws Exception {
        for (Graph graph : allGraphs) {
            ownPrim = new Prim(graph);
            ownPrim.compute();
            libPrim.init(graph);
            libPrim.compute();
            assertEquals((long) libPrim.getTreeWeight(), (long) ownPrim.getTreeWeight());
        }
    }

    @Test
    public void kruskalWeightEqualsPrimWeightUsingGKAFiles() throws Exception {
        for (Graph graph : allGraphs) {
            ownKruskal = new Kruskal(graph);
            ownPrim = new Prim(graph);
            ownKruskal.compute();
            ownPrim.compute();
            assertEquals((long) ownKruskal.getTreeWeight(), (long) ownPrim.getTreeWeight());
        }
    }

    @Test
    public void kruskalEqualsPrimUsingRandomGraph() throws Exception {
        for (Graph graph : allRandGraphs) {
            ownKruskal = new Kruskal(graph);
            ownPrim = new Prim(graph);
            ownKruskal.compute();
            ownPrim.compute();
            assertEquals((long) ownKruskal.getTreeWeight(), (long) ownPrim.getTreeWeight());
        }
    }
}
