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
        // pré-condições
        if (!EulerUtils.isConnectedIgnoreIsolated(G) || !EulerUtils.allDegreesEven(G)) {
            return null;
        }

        int V = G.V();
        // cópia mutável: LinkedList por vértice
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
                int u = adj.get(v).removeFirst(); // pega vizinho
                // remove a aresta reversa u -> v
                adj.get(u).removeFirstOccurrence(v);
                stack.push(u);
            } else {
                circuit.add(stack.pop());
            }
        }

        // circuito está em ordem reversa (construído do fim para o início)
        // aqui já está na ordem de percurso (circuit foi preenchido do último ao primeiro),
        // mas como adicionamos ao final a cada pop, a lista resultante está do final para o início.
        // portanto invertê-la:
        java.util.Collections.reverse(circuit);
        return circuit;
    }
}
