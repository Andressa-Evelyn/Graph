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
        java.util.Arrays.sort(arquivos);

        for (File f : arquivos) {
            System.out.println(">>> Arquivo: " + f.getName());
            Graph G = new Graph(new In(f.getPath()));

            if (!EulerUtils.isConnectedIgnoreIsolated(G)) {
                System.out.println("Desconexo (ignorando isolados).\n");
                continue;
            }

            int odd = 0;
            for (int v = 0; v < G.V(); v++) if ((G.degree(v) & 1) == 1) odd++;

            if (odd != 0) {
                System.out.println("Não é um ciclo euleriano: existem vértices com grau ímpar.\n");
                continue;
            }

            // -------- Hierholzer --------
            Hierholzer h = new Hierholzer(G);
            List<Integer> circuitH = h.findCircuit(0);
            if (circuitH == null || circuitH.isEmpty()) {
                System.out.println("Hierholzer: não foi possível construir o circuito.");
            } else {
                System.out.println("Hierholzer: " + joinList(circuitH));
            }

            // -------- Fleury --------
            Fleury fAlg = new Fleury(G);
            List<Integer> circuitF = fAlg.find();
            if (circuitF == null || circuitF.isEmpty()) {
                System.out.println("Fleury: não foi possível construir o circuito.\n");
            } else {
                System.out.println("Fleury:     " + joinList(circuitF) + "\n");
            }
        }
    }

    private static String joinList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(list.get(i));
        }
        return sb.toString();
    }
}
