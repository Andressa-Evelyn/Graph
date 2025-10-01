package edu.princeton.cs.algs4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementação do algoritmo de Fleury.
 * Retorna a sequência de vértices da trilha/circuito ou null se não houver.
 *
 * Complexidade: O(E^2) no pior caso (remoção temporária + DFS por aresta).
 */
public class Fleury {

    private final Graph G;

    public Fleury(Graph G) {
        this.G = G;
    }

    private String eulerianType() {
        int odd = 0;
        for (int v = 0; v < G.V(); v++) if ((G.degree(v) & 1) == 1) odd++;
        if (odd == 0) return "circuito";
        if (odd == 2) return "trilha";
        return null;
    }

    public List<Integer> find() {
        String type = eulerianType();
        if (type == null) return null;
        if (!EulerUtils.isConnectedIgnoreIsolated(G)) return null;

        int V = G.V();

        List<LinkedList<Integer>> adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) {
            LinkedList<Integer> list = new LinkedList<>();
            for (int w : G.adj(v)) list.add(w);
            adj.add(list);
        }

        int start = 0;
        if ("trilha".equals(type)) {
            for (int v = 0; v < V; v++) if ((G.degree(v) & 1) == 1) { start = v; break; }
        }

        List<Integer> path = new ArrayList<>();
        int u = start;
        path.add(u);

        while (hasEdges(adj)) {
            boolean moved = false;
            for (int v : new ArrayList<>(adj.get(u))) {
                if (isValidNextEdge(u, v, adj)) {
                    removeEdge(u, v, adj);
                    u = v;
                    path.add(u);
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                // situação inesperada em grafo euleriano; sair para evitar loop infinito
                break;
            }
        }
        return path;
    }

    private boolean hasEdges(List<LinkedList<Integer>> adj) {
        for (LinkedList<Integer> l : adj) if (!l.isEmpty()) return true;
        return false;
    }

    private void removeEdge(int a, int b, List<LinkedList<Integer>> adj) {
        adj.get(a).removeFirstOccurrence(b);
        adj.get(b).removeFirstOccurrence(a);
    }


    private int dfsCount(int s, boolean[] visited, List<LinkedList<Integer>> localAdj) {
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        stack.push(s);
        visited[s] = true;
        int count = 1;
        while (!stack.isEmpty()) { //enquanto houver vértices, continua a busca
            int u = stack.pop(); // remove o vértice e adiciona em u
            for (int v : localAdj.get(u)) {
                if (!visited[v]) { // se o vértice não foi visitado
                    visited[v] = true;
                    count++; // contador de vértices alcançados
                    stack.push(v);
                }
            }
        }
        return count;
    }

    private boolean isValidNextEdge(int u, int v, List<LinkedList<Integer>> adj) {

        if (adj.get(u).size() == 1) return true;

        int V = adj.size();

        boolean[] visited1 = new boolean[V];
        int count1 = dfsCount(u, visited1, adj);


        removeEdge(u, v, adj);


        boolean[] visited2 = new boolean[V];
        int count2 = dfsCount(u, visited2, adj);


        adj.get(u).addFirst(v);
        adj.get(v).addFirst(u);


        return count1 == count2;
    }
}

