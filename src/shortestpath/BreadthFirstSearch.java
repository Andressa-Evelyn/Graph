package shortestpath;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.Stack;

public class BreadthFirstSearch {

    private final Node startNode;
    private final Node targetNode;
    private final Graph graph;
    private final Stack<Node> stack;
    private final Path path;

    public BreadthFirstSearch(Graph graph, String startNode, String targetNode) {
        this.graph = graph;
        this.startNode = graph.getNode(startNode);
        this.targetNode = graph.getNode(targetNode);
        this.stack = new Stack<>();
        this.path = new Path();
    }

    /**
     * Calls the BfIterator in order to push all nodes onto the queue.
     */
    public void compute() {
        BfIterator<Node> bfIterator = new BfIterator<>(this.graph, this.startNode);

        while (bfIterator.hasNext()) {
            Node nextNode = bfIterator.next();

            if (nextNode.equals(this.targetNode)) {
                traceBack(nextNode);
                createPath();
                return;
            }
        }
    }

    /**
     * Traces back the path from target to source node.
     * @param node -> currently observed node
     */
    private void traceBack(Node node) {
        if (node == null) return;
        this.stack.push(node);

        Node precedingNode = node.neighborNodes()
                .filter(neighborNode ->
                        BfIterator.indices[neighborNode.getIndex()] == BfIterator.indices[node.getIndex()] - 1)
                .findFirst()
                .orElse(null);

        traceBack(precedingNode);
    }

    /**
     * Creates the path by popping one element off the stack and adding the edge between that node
     * and the stack's peek element to the path.
     */
    private void createPath() {
        this.path.setRoot(this.startNode);

        while (this.stack.size() > 1) {
            Node node0 = this.stack.pop();
            Node node1 = this.stack.peek();
            this.path.add(node0.getEdgeBetween(node1));
        }
    }

    public Path getPath() {
        return this.path;
    }

}
