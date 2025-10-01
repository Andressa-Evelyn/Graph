package edu.princeton.cs.algs4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Implementação de Hierholzer para grafos simples não direcionados.
 * Retorna uma lista de vértices representando o circuito (ou null se não euleriano).
 *
 * Complexidade: O(E) se a cópia mutável da adjacência usar listas (remoção O(1)
 * se usar estruturas apropriadas). Aqui usamos LinkedList para remoção por ocorrência.
 */
public class Hierholzer {

    private final Graph G;

    public Hierholzer(Graph G) {
        this.G = G;
    }

    public List<Integer> findCircuit(int start) {

        if (!EulerUtils.isConnectedIgnoreIsolated(G) || !EulerUtils.allDegreesEven(G)) {
            return null;
        }

        int V = G.V();


        List<LinkedList<Integer>> adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) {
            LinkedList<Integer> list = new LinkedList<>();
            for (int w : G.adj(v)) list.add(w);
            adj.add(list);
        }

        Stack<Integer> stack = new Stack<>();
        List<Integer> circuit = new ArrayList<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            if (!adj.get(v).isEmpty()) {
                int u = adj.get(v).removeFirst();
                adj.get(u).removeFirstOccurrence(v);
                stack.push(u);
            } else {
                circuit.add(stack.pop());
            }
        }

        java.util.Collections.reverse(circuit);
        return circuit;
    }
}
