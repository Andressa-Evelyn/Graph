package edu.princeton.cs.algs4;

import java.util.List;

/**
 * Leitura em formato algs4 (arquivo com V E e E pares) usando Graph da algs4.
 * Se você não tem algs4, altere a leitura para a sua Graph.fromTxt.
 *
 * Execução:
 *   java EulerMain data/c4.txt
 *
 * Saída:
 *   Se circuito: linha única com vértices separados por espaço.
 *   Caso contrário: mensagem apropriada.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Uso: java EulerMain <arquivo_grafo.txt>");
            System.exit(1);
        }

        // requer algs4 Graph / In:
        Graph G = new Graph(new In(args[0])); // se usar algs4
        // se tiver Graph.fromTxt adaptado, substitua acima.

        if (!EulerUtils.isConnectedIgnoreIsolated(G)) {
            System.out.println("Desconexo (ignorando isolados).");
            return;
        }

        int odd = 0;
        for (int v = 0; v < G.V(); v++) if ((G.degree(v) & 1) == 1) odd++;

        if (odd != 0) {
            System.out.println("Não euleriano: existem vértices com grau ímpar.");
            return;
        }

        Hierholzer h = new Hierholzer(G);
        List<Integer> circuit = h.findCircuit(0);
        if (circuit == null || circuit.isEmpty()) {
            System.out.println("Não foi possível construir o circuito.");
            return;
        }

        // imprime em uma linha
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < circuit.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(circuit.get(i));
        }
        System.out.println(sb.toString());
    }
}
