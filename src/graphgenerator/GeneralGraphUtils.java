package graphgenerator;

import org.graphstream.graph.Graph;

public class GeneralGraphUtils {

    public static void displayGraph(Graph graph) {
        graph.setAttribute("ui.stylesheet", Generator.styleSheet);

        graph.nodes().forEach(node -> node.setAttribute("ui.label", node.getId()));
        graph.edges().forEach(edge -> edge.setAttribute("ui.label", edge.getAttribute("weight")));
        graph.display();
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
