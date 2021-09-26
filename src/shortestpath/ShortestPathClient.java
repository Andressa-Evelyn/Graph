package shortestpath;

import graphgenerator.Generator;
import org.graphstream.graph.Graph;


import java.io.FileNotFoundException;

import static graphgenerator.GeneralGraphUtils.displayGraph;

public class ShortestPathClient {

    public static void main(String[] args) throws FileNotFoundException {
        System.setProperty("org.graphstream.ui", "swing");

        //Graph graph = Generator.generate(30);
        Graph graph = Generator.createFromFile("/undirected_graphs/cities.gka");

        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(graph, "Hamburg", "Husum");
        breadthFirstSearch.compute();

        System.out.println(breadthFirstSearch.getPath());

        breadthFirstSearch.getPath().edges().forEach(edge -> {
            graph.getEdge(edge.getId()).setAttribute("ui.class", "highlight");
        });

        displayGraph(graph);
    }

}
