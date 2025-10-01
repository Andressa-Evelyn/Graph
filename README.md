# Algoritmos de Hierholzer e Fleury

Este repositório contém a implementação dos algoritmos de **Hierholzer** e **Fleury**, ambos utilizados para encontrar **circuitos eulerianos** em grafos. O projeto foi desenvolvido como parte da cadeira de **Grafos**, com o objetivo de explorar e comparar diferentes estratégias para a construção de trilhas e circuitos eulerianos.

## Objetivo
O foco deste trabalho é analisar e implementar os algoritmos, destacando suas características, limitações e complexidades, além de oferecer exemplos práticos de sua aplicação em grafos simples e não direcionados.

## Estrutura
- **Hierholzer**: Algoritmo eficiente que constrói circuitos eulerianos percorrendo arestas sem repeti-las, garantindo complexidade linear em relação ao número de arestas.
- **Fleury**: Algoritmo mais didático, que escolhe arestas cuidadosamente para evitar a desconexão do grafo, embora apresente complexidade maior.

## Complexidade dos Algoritmos

O algoritmo de **Hierholzer** percorre cada aresta exatamente uma vez para construir o circuito ou trilha euleriana. Assim, sua complexidade de tempo é **O(E)**, onde *E* é o número de arestas do grafo, já que cada aresta é adicionada e removida da estrutura de dados apenas uma vez. Em termos de espaço, ele exige **O(V + E)**, pois precisa armazenar tanto as listas de adjacência do grafo quanto as estruturas auxiliares (pilhas ou listas) usadas para construir o caminho. Isso o torna altamente eficiente e adequado para grafos grandes.

O algoritmo de **Fleury**, por outro lado, é menos eficiente porque a cada passo pode ser necessário verificar se a remoção de uma aresta desconecta o grafo, exigindo uma busca em profundidade (DFS) ou largura (BFS) para checar a conectividade. Como essa verificação pode ocorrer a cada aresta removida, a complexidade total chega a **O(E²)**. Em termos de espaço, o algoritmo também precisa de **O(V + E)**, mas o custo adicional de múltiplas buscas torna sua execução mais lenta em comparação com Hierholzer. Por isso, Fleury é mais usado em contextos educacionais do que práticos.

## Aplicações
Esses algoritmos são úteis em diversos contextos, como:
- Problemas de logística e roteamento.
- Planejamento de rotas.
- Análise de redes.

---
Desenvolvido para fins acadêmicos na disciplina de **Grafos**.



