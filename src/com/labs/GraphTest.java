package com.labs;

import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void getAdjacencyMatrix() {
        Graph gr = new Graph();
        double[][] tmp = new double[][]
                { { 0, 2, 1, 0, 0, 0},
                { 2, 0, 7, 0, 8, 4},
                { 1, 7, 0, 7, 0, 3},
                { 0, 0, 7, 0, 8, 4},
                { 0, 8, 0, 8, 0, 5},
                { 0, 4, 3, 4, 5, 0} };
        gr.createGraphFromMatrix(tmp);
        Assertions.assertArrayEquals(tmp, gr.getAdjacencyMatrix());
    }

    @Test
    void depthFirstTraversal() {
        Graph gr = new Graph();

        gr.addVertex("Bob", 0);
        gr.addVertex("Alice", 0);
        gr.addVertex("Mark",0);
        gr.addVertex("Rob",0);
        gr.addVertex("Maria",0);
        gr.addEdge("Bob",1, "Alice", 1);
        gr.addEdge("Bob",2, "Rob",2);
        gr.addEdge("Alice",3, "Mark",3);
        gr.addEdge("Rob",4, "Mark",4);
        gr.addEdge("Alice",5, "Maria",5);
        gr.addEdge("Rob",6, "Maria", 6);

        Assertions.assertEquals("[Bob, Rob, Maria, Alice, Mark]", gr.depthFirstTraversal("Bob").toString());
    }

    @Test
    void breadthFirstTraversal() {
        Graph gr = new Graph();

        gr.addVertex("Bob", 0);
        gr.addVertex("Alice", 0);
        gr.addVertex("Mark",0);
        gr.addVertex("Rob",0);
        gr.addVertex("Maria",0);
        gr.addEdge("Bob",1, "Alice", 1);
        gr.addEdge("Bob",2, "Rob",2);
        gr.addEdge("Alice",3, "Mark",3);
        gr.addEdge("Rob",4, "Mark",4);
        gr.addEdge("Alice",5, "Maria",5);
        gr.addEdge("Rob",6, "Maria", 6);

        Assertions.assertEquals(
                "[Bob, Alice, Rob, Mark, Maria]", gr.breadthFirstTraversal("Bob").toString());
    }

    @Test
    void dijkstra() {
        Graph gr = new Graph();
        gr.createGraphFromMatrix(new double[][]
                { { 0, 2, 4, 1},
                { 2, 0, 0, 3},
                { 4, 0, 0, 6},
                { 1, 3, 6, 0} });

        Pair result = gr.dijkstra(2,3);
        Assertions.assertArrayEquals(new int[]{2, 0, 3}, (int[]) result.getValue());
        Assertions.assertEquals(5.0, result.getKey());
    }
}