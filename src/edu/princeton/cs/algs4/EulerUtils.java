package edu.princeton.cs.algs4;

import java.util.ArrayDeque;
import java.util.Deque;

public final class EulerUtils {

    private EulerUtils() {}

    /**
     * Verifica conectividade ignorando vértices isolados.
     * Compatível com Graph da algs4 (métodos: V(), adj(v), degree(v)).
     */
    public static boolean isConnectedIgnoreIsolated(Graph G) {
        int V = G.V();
        boolean[] visited = new boolean[V];


        int start = -1;
        for (int v = 0; v < V; v++) {
            if (G.degree(v) > 0) {
                start = v;
                break;
            }
        }
        if (start == -1) {
            return true;
        }


        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = true;
        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (int w : G.adj(u)) {
                if (!visited[w]) {
                    visited[w] = true;
                    stack.push(w);
                }
            }
        }

        for (int v = 0; v < V; v++) {
            if (G.degree(v) > 0 && !visited[v]) return false;
        }
        return true;
    }

    /** Retorna true se todos os graus são pares. */
    public static boolean allDegreesEven(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            if ((G.degree(v) & 1) == 1) return false;
        }
        return true;
    }
}
