package minimalspanningtree;

import edu.princeton.cs.algs4.Stopwatch;
import org.graphstream.graph.Graph;
import graphgenerator.Generator;


public class DoublingTest {

    public static void main(String[] args) throws Exception {
        for (int i = 6000; i < 200000; i+= i) {
            Graph randGraph = Generator.generate(i, i * 2);

            Kruskal kruskal = new Kruskal(randGraph);
            Prim prim = new Prim(randGraph);

            double primTime = testPrimPerformance(prim);
            double kruskalTime = testKruskalPerformance(kruskal);

            System.out.println(i + " Nodes\t\tPrim: " + primTime + "\t\tKruskal: " + kruskalTime);
        }
    }

    public static double testPrimPerformance(Prim p2) throws Exception {
        Stopwatch timer = new Stopwatch();
        p2.compute();
        return timer.elapsedTime();
    }

    public static double testKruskalPerformance(Kruskal k2) throws Exception {
        Stopwatch timer = new Stopwatch();
        k2.compute();
        return timer.elapsedTime();
    }

}
