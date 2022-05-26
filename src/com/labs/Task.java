package com.labs;

import javafx.util.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Task {

    /**
     * Calculates the distance from one point to the other
     * @param p1 start point
     * @param p2 end point
     * @return distance
     */
    private double getDistance(Point2D p1, Point2D p2)
    {
        return p1.distance(p2);
    }

    /**
     *
     * @param x x of the leftmost point of the house
     * @param y y of the leftmost point of the house
     * @return list of house coordinates
     */
    private List<Point2D> getHouse(double x, double y)
    {
        List<Point2D> houseCoordinates = new ArrayList<>();
        houseCoordinates.add(new Point2D.Double(x, y));
        houseCoordinates.add(new Point2D.Double(x + 5, y));
        houseCoordinates.add(new Point2D.Double(x, y + 5));
        houseCoordinates.add(new Point2D.Double(x + 5, y + 5));
        return houseCoordinates;
    }

    /**
     * Get list of houses and their points
     * @param x vector of x values
     * @param y vector of y values
     * @return a pair -> key - list of points, value - list of houses
     */
    public Pair getListOfHouses(double[] x, double[] y)
    {
        if (x.length != y.length)
            return null;

        int n = x.length;
        List<Point2D> houseCoordinates = new ArrayList<>();
        List<House> houses = new ArrayList<>();
        houseCoordinates.add(new Point2D.Double(0,0));
        for(int i = 0; i < n; i++)
        {
            List<Point2D> tmp = getHouse(x[i], y[i]);
            houseCoordinates.addAll(tmp);
            houses.add(new House(tmp));
        }

        houseCoordinates.add(new Point2D.Double(100,100));
        return new Pair<>(houseCoordinates, houses);
    }

    /**
     * Connects the houses
     * @param x houses' x coordinates
     * @param y houses' y coordinates
     * @return a pair -> key - graph of connections, value - points for mapping the graph
     */
    public Pair connectTheHouses(double[] x, double[] y)
    {
        Pair tmp = getListOfHouses(x,y);
        List<Point2D> points = (List<Point2D>) tmp.getKey();
        List<House> houses = (List<House>) tmp.getValue();
        assert points != null;
        int n = points.size();
        double[][] graph = new double[n][n];

        for(int k = 0; k < n - 1; k++)
            for (int j = 1; j < n; j++)
            {
                Point2D pk = points.get(k);
                Point2D pj = points.get(j);
                boolean add = true;
                double xj = points.get(j).getX();
                double xk = points.get(k).getX();
                for (double i = xk; i <= xj; i+=5)
                {
                    for (House house: houses)
                        if (house.containsX(i))
                            if (house.checkIntersect(pk, pj)) {
                                add = false;
                                break;
                            }

                    if (!add)
                        break;
                }

                if(add && k == 0)
                    graph[k][j] = graph[j][k] = getDistance(pk,pj);
                else if(add)
                    graph[k][j] = getDistance(pk,pj);
            }

/*        System.out.println("Needed points:");
        System.out.println("0/0 - 10/5 " + graph[0][2]);
        System.out.println("10/5 - 20/13 " + graph[2][6]);
        System.out.println("20/13 - 30/25 " + graph[6][15]);
        System.out.println("30/25 - 55/50 " + graph[15][22]);
        System.out.println("55/50 - 100/100 " + graph[22][25]);

        for(int i = 0; i < n; i++)
        {
            System.out.print(points.get(i) + "\t");
            System.out.println(Arrays.toString(graph[i]));
        }*/

        return new Pair(graph, points);
    }


    /**
     * Solves given task
     * @param x houses' x coordinates
     * @param y houses' y coordinates
     * @return a pair -> key - the shortest distance, value - path
     */
    public Pair solveTheTask(double[] x, double[] y)
    {
        Graph graph = new Graph();
        Pair adjMatrix = connectTheHouses(x,y);
        List list = (List) adjMatrix.getValue();
        graph.createGraphFromMatrix((double[][]) adjMatrix.getKey());

        Pair p = graph.dijkstra(0, ((double[][]) adjMatrix.getKey()).length - 1);
        int[] pathIndexes = (int[])p.getValue();
        List<Point2D> path = new ArrayList<>();
        /*System.out.println(Arrays.toString(pathIndexes));*/
        for (int i =0; i < pathIndexes.length; i++)
        {
            Point2D po = (Point2D) list.get(pathIndexes[i]);
            path.add(po);
        }
        return new Pair<>(p.getKey(), path);
    }
}
