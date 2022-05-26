package com.labs;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
    private Map<Node, LinkedHashSet<Node>> vertices;

    public Graph() {
        this.vertices = new HashMap<>();
    }

    /**
     * Create a graph using specified matrix.
     * @param matrix specified matrix
     */
    public void createGraphFromMatrix(double[][] matrix)
    {
        this.vertices = new HashMap<>();

        for(int i =0; i < matrix.length; i++)
        {
            LinkedHashSet<Node> neighbors = new LinkedHashSet<>();
            for(int j =0; j < matrix[i].length; j++)
                if(matrix[i][j] != 0)
                    neighbors.add(new Node(j, matrix[i][j], j));

            vertices.put(new Node(i, matrix[i][i], i), neighbors);
        }
    }

    /**
     * Add vertex to a graph.
     * @param obj identifying object
     * @param weight weight of the vertex, any number other than 0 creates a cycle
     */
    void addVertex(Object obj, double weight) {
        vertices.putIfAbsent(new Node(obj, weight, vertices.size()), new LinkedHashSet<>());
    }

    /**
     * Add an edge to a graph.
     * @param o1 identifying object for the first vertex
     * @param w1 weight of the first vertex
     * @param o2 identifying object for the second vertex
     * @param w2 weight of the second vertex
     */
    void addEdge(Object o1, double w1, Object o2, double w2) {
        Node n1 = findNode(o1), n2 = findNode(o2);
        LinkedHashSet<Node> v1 = vertices.get(n1);
        LinkedHashSet<Node> v2 = vertices.get(n2);
        assert n2 != null;
        v1.add(new Node(o2,w2, n2.getI()));
        assert n1 != null;
        v2.add(new Node(o1,w1, n1.getI()));
    }

    /**
     * Creates adjacency matrix from adjacency list.
     * @return adjacency matrix
     */
    public double[][] getAdjacencyMatrix() {
        Set<Node> keys = vertices.keySet();
        double[][] matrix = new double[keys.size()][keys.size()];

        for (Node key : keys) {
            Set<Node> adj = vertices.get(key);
            for (Node a : adj)
                matrix[key.getI()][a.getI()] = a.getWeight();
        }
        return matrix;
    }

    private Node findNode(Object o){
        for (Node key: vertices.keySet())
            if (key.getObj().equals(o))
                return key;
        return null;
    }

    private Object[] getAdjVertices(Object obj) {
        Node n = findNode(obj);
        return vertices.get(n).toArray();
    }

    /**
     * Depth First Traversal of the graph
     * @param root starting vertex
     * @return set of traversed objects
     */
    Set<Object> depthFirstTraversal(Object root) {
        Set<Object> visited = new LinkedHashSet<>();
        Stack<Object> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Object vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Object v : getAdjVertices(vertex)) {
                    stack.push(((Node)v).getObj());
                }
            }
        }
        return visited;
    }

    /**
     * Breadth First Traversal of the graph
     * @param root starting vertex
     * @return set of traversed objects
     */
    Set<Object> breadthFirstTraversal(Object root) {
        Set<Object> visited = new LinkedHashSet<>();
        Queue<Object> queue = new LinkedList<>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            Object vertex = queue.poll();
            for (Object v : getAdjVertices(vertex)) {
                if (!visited.contains(((Node)v).getObj())) {
                    visited.add(((Node)v).getObj());
                    queue.add(((Node)v).getObj());
                }
            }
        }
        return visited;
    }

    private int minDistance(double[] path_array, Boolean[] sPV)   {
        double min = Double.MAX_VALUE;
        int min_index = -1;
        for (int v = 0; v < path_array.length; v++)
            if (!sPV[v] && path_array[v] <= min) {
                min = path_array[v];
                min_index = v;
            }
        return min_index;
    }

    /**
     *  Dijkstra algorithm, finds the shortest path + its length
     * @param start starting vertex of the path
     * @param end ending vertex of the path
     * @return Pair(length, path)
     */
    public Pair dijkstra(int start, int end)  {
        int n = vertices.size();
        double[][] graph = getAdjacencyMatrix();
        double[] distance = new double[n];
        int[] prev = new int [n];
        Boolean[] visited = new Boolean[n];

        for (int i = 0; i < n; i++) {
            distance[i] = Double.MAX_VALUE;
            visited[i] = false;
            prev[i] = -1;
        }
        distance[start] = 0;
        for (;;) {
            int u = minDistance(distance, visited);
            if (u == -1 || u == end) break;
            visited[u] = true;
            for (int v = 0; v < n; v++)
                if (!visited[v] && graph[u][v] != 0 && distance[u] !=
                        Integer.MAX_VALUE && distance[u]
                        + graph[u][v] < distance[v])
                {
                    distance[v] = distance[u] + graph[u][v];
                    prev[v] = u;
                }
        }

        Stack<Integer> stack = new Stack();
        for (int v = end; v != -1; v = prev[v])
            stack.push(v);
        int[] sp = new int [stack.size()];
        for (int i = 0; i < sp.length; i++)
            sp[i] = stack.pop();

        return new Pair(distance[end], sp);
    }

}
