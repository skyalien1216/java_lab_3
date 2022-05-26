package com.labs;

import javafx.util.Pair;

import java.util.Objects;

public class Node {
    private final int i;
    private Pair<Object, Double> vert;

    public Node(Object obj, double weight, int i) {
        this.vert = new Pair<>(obj, weight);
        this.i = i;
    }

    public Object getObj() {
        return vert.getKey();
    }

    public void set(Object obj, double weight) {
        this.vert = new Pair<>(obj, weight);
    }

    public double getWeight() {
        return vert.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getWeight() == node.getWeight() && getObj().equals(node.getObj());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getObj(), getWeight(), getI());
    }

    public int getI() {
        return i;
    }
}
