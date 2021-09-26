package minimalspanningtree;

import graphgenerator.Generator;
import org.graphstream.graph.Graph;

import static graphgenerator.GeneralGraphUtils.displayGraph;

public class MinimalSpanningTreeClient {

    public static void main(String[] args) throws Exception {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = Generator.generate(100);

        Kruskal kruskal = new Kruskal(graph);
        kruskal.compute();

        System.out.println(kruskal.getTreeWeight());
        displayGraph(kruskal.getGraph());
    }

}
