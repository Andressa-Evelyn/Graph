package edu.princeton.cs.algs4;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        File pasta = new File("data");
        if (!pasta.exists() || !pasta.isDirectory()) {
            System.err.println("Pasta 'data' não encontrada.");
            System.exit(1);
        }


        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".txt"));
        if (arquivos == null || arquivos.length == 0) {
            System.err.println("Nenhum arquivo .txt encontrado em data/");
            System.exit(1);
        }

        for (File f : arquivos) {
            System.out.println(">>> Arquivo: " + f.getName());
            Graph G = new Graph(new In(f.getPath()));

            if (!EulerUtils.isConnectedIgnoreIsolated(G)) {
                System.out.println("Desconexo (ignorando isolados).\n");
                continue;
            }

            if (!EulerUtils.allDegreesEven(G)) {
                System.out.println("Não euleriano: existem vértices com grau ímpar.\n");
                continue;
            }

            Hierholzer h = new Hierholzer(G);
            List<Integer> circuit = h.findCircuit(0);
            if (circuit == null || circuit.isEmpty()) {
                System.out.println("Não foi possível construir o circuito.\n");
                continue;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < circuit.size(); i++) {
                if (i > 0) sb.append(' ');
                sb.append(circuit.get(i));
            }
            System.out.println(sb.toString() + "\n");
        }
    }
}
