package com.labs;

import javafx.util.Pair;

public class Main {

    public static void main(String[] args) {
        Pair result = new Task().solveTheTask(new double[]{5,15,15,30,35,50}, new double[]{5,13,20,20,40,50});
        System.out.println(result.getKey());
        System.out.println(((result).getValue()));
    }
}
