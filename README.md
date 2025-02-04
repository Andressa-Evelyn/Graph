# GraphWorks

## Overview
This project implements several fundamental graph algorithms, organized into three categories:

1. **Shortest Path:**  
  Find the shortest path between two nodes in a graph using **Breadth-First Search (BFS)**.
2. **Minimum Spanning Tree:**  
  Compute the minimal spanning tree of a graph using **Kruskal's** and **Prim's** algorithms.
3. **Eulerian Path:**  
  Determine an Eulerian path in a graph using **Fleury's Algorithm** and **Hierholzer's Algorithm**.  
  
Additionally, this project allows for graph generation through two methods:
1. **File-based input:** Load graphs from a file.
2. **Random graph generation:** Generate graphs of various sizes for testing and illustration purposes.
  
Graphs can be visualized for better understanding and analysis using the built-in display functionality.

## Technologies Used
This project relies on the following libraries:
- **algs4.jar** - A collection of algorithms from [Princeton's Algorithms, Part I course](https://algs4.cs.princeton.edu/home/).
- **GraphStream (version 2.0)** - For graph visualization, utilizing core, algorithm, and UI components.
- **JUnit 4.13.1** - For unit testing and ensuring code reliability.

## Setup Instructions 
To set up the project:
1. Download the required libraries:  
    - **algs4.jar:** [Download Link](https://algs4.cs.princeton.edu/home/)
    - **GraphStream 2.0:** [GraphStream Website](https://graphstream-project.org/)
    - **JUnit 4.13.1:** Available via [Maven Central](https://mvnrepository.com/artifact/junit/junit/4.13.2).
2. Add the libraries to your project’s classpath.
3. You should now be ready to run the algorithms and generate or load graphs for visualization and analysis.

## Example Usage
- You can either **load a graph from a file** or **generate a random graph of your choice**.
- Use the provided graph visualization tools to display and analyze the structure of the graph.

## License
This project is licensed under the MIT License.
