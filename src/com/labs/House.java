package com.labs;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class House {
    Point2D startingPoint;
    List<Point2D> houseCoords;

    @Override
    public String toString() {
        return "House{" + houseCoords +
                "}\n";
    }

    public House(List<Point2D> houseCoords) {
        startingPoint = houseCoords.get(0);
        this.houseCoords = houseCoords;
    }

    public Point2D getStartingPoint() {
        return startingPoint;
    }

    /**
     * Checks if the point is in the house
     * @param p point to be checked
     * @return true if the house contains the point, otherwise - false
     */
    public boolean isInHouse(Point2D p)
    {
        return houseCoords.contains(p);
    }

    /**
     * Checks if a point with the given x coordinate is in the house
     * @param x x to be checked for
     * @return true if the house contains a point with the given x, otherwise - false
     */
    public boolean containsX(double x)
    {
        for (Point2D p : houseCoords)
        {
            if(p.getX() == x)
                return true;
        }
        return false;
    }

    /**
     * Check if two lines intersect
     * @param p11 starting point of the first line
     * @param p12 ending point of the first line
     * @param p21 starting point of the second line
     * @param p22 ending point of the second line
     * @return true if line intersect, otherwise - false
     */
    private boolean checkIfIntersect(Point2D p11, Point2D p12, Point2D p21, Point2D p22)
    {
        return Line2D.linesIntersect(p11.getX(), p11.getY(), p12.getX(), p12.getY(), p21.getX(), p21.getY(), p22.getX(), p22.getY());
    }

    /**
     * Check if a line has an intersection with the house
     * @param p1 starting point of the line
     * @param p2 starting point of the line
     * @return true if there's an intersection, otherwise - false
     */
    public boolean checkIntersect(Point2D p1, Point2D p2)
    {
        if(isInHouse(p1) && isInHouse(p2))
            return true;

        Point2D pp1 = houseCoords.get(0);
        Point2D pp2 = houseCoords.get(1);
        Point2D pp3 = houseCoords.get(2);
        Point2D pp4 = houseCoords.get(3);

        if(p2 != pp1 && p2 != pp2 && p1 != pp1 && p1 != pp2)
            if(checkIfIntersect(p1, p2, pp1, pp2))
                return true;

        if(p2 != pp1 && p2 != pp3 && p1 != pp1 && p1 != pp3)
            if(checkIfIntersect(p1, p2, pp1, pp3))
                return true;

        if(p2 != pp2 && p2 != pp4 && p1 != pp2 && p1 != pp4)
            if(checkIfIntersect(p1, p2, pp2, pp4))
                return true;

        if(p2 != pp4 && p2 != pp3 && p1 != pp4 && p1 != pp3)
            return checkIfIntersect(p1, p2, pp4, pp3);

        return false;
    }
}
