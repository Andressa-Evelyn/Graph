package eulerianpath;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class eulerianPathUtilities {

    public static boolean eachNodeHasEvenDegree(Graph graph) {
        for (Node node : graph) {
            if ((node.getDegree()) % 2 != 0) {
                return false;
            }
        }
        return true;
    }

}
