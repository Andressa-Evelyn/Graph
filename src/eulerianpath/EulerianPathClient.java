package eulerianpath;

import graphgenerator.Generator;
import minimalspanningtree.DisconnectedGraphException;
import org.graphstream.graph.Graph;

import static graphgenerator.GeneralGraphUtils.displayGraph;

public class EulerianPathClient {

    public static void main(String[] args) throws OddNodeDegreeException, DisconnectedGraphException {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = Generator.generate(20);

        Hierholzer hierholzer = new Hierholzer(graph);
        hierholzer.compute();

        System.out.println(hierholzer.getPath());
        displayGraph(graph);
    }

}
