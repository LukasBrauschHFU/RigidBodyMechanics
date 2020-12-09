package geometry;

import java.io.*;
import java.util.*;
import java.awt.geom.Point2D;

public class RandomPolygon {

    public static List<Point2D.Double> generate_points(int nPoints) {
        List<Point2D.Double> points = new ArrayList<>();
        for (int i = 0; i < nPoints; i++) {
            points.add(new Point2D.Double(Math.random() * 1.4 - 0.7, Math.random() * 1.4 - 0.7));
        }
        return points;
    }

    public static List<Point2D.Double> generate_polygon(int nPoints) {
        List<Point2D.Double> points = generate_points(nPoints);
        Point2D.Double p1 = removeRandom(points);
        Point2D.Double p2 = removeRandom(points);

        List<Point2D.Double> group1 = new ArrayList<>();
        List<Point2D.Double> group2 = new ArrayList<>();
        partition(points, p1, p2, group1, group2);

        List<Point2D.Double> path1 = buildPath(p1, p2, group1);
        List<Point2D.Double> path2 = buildPath(p2, p1, group2);

        List<Point2D.Double> result = new ArrayList<>();
        result.add(p1);
        result.addAll(path1);
        result.add(p2);
        result.addAll(path2);
        return result;
    }

    public static List<Point2D.Double> buildPath(Point2D.Double p1, Point2D.Double p2, List<Point2D.Double> points) {
        if (points.isEmpty()) {
            return new ArrayList<>();
        } else {
            Point2D.Double c = removeRandom(points);
            Point2D.Double c2 = randomBetween(p1, p2);

            List<Point2D.Double> group1 = new ArrayList<>();
            List<Point2D.Double> group2 = new ArrayList<>();
            partition(points, c2, c, group1, group2);

            List<Point2D.Double> result = new ArrayList<>();
            if (isLeft(c2, c, p1)) {
                result.addAll(buildPath(p1, c, group1));
                result.add(c);
                result.addAll(buildPath(c, p2, group2));
            } else {
                result.addAll(buildPath(p1, c, group2));
                result.add(c);
                result.addAll(buildPath(c, p2, group1));
            }
            return result;
        }
    }

    /** Split points into two groups by p1-p2 line */
    public static void partition(List<Point2D.Double> points, Point2D.Double p1, Point2D.Double p2,
                                 List<Point2D.Double> group1, List<Point2D.Double> group2) {
        for (Point2D.Double p : points) {
            if (isLeft(p1, p2, p)) {
                group1.add(p);
            } else {
                group2.add(p);
            }
        }
    }

    public static Point2D.Double randomBetween(Point2D.Double p1, Point2D.Double p2) {
        double t = Math.random();
        return new Point2D.Double(p1.getX() * t + p2.getX() * (1 - t),
                                  p1.getY() * t + p2.getY() * (1 - t));
    }

    public static boolean isLeft(Point2D.Double l1, Point2D.Double l2, Point2D.Double p) {
        double lx = l2.getX() - l1.getX();
        double ly = l2.getY() - l1.getY();
        return lx * (p.getY() - l1.getY()) - ly * (p.getX() - l1.getX()) > 0;
    }

    public static Point2D.Double removeRandom(List<Point2D.Double> pts) {
        return pts.remove(new Random().nextInt(pts.size()));
    }

    public static void writeToSvg(String fname, List<Point2D.Double> points) throws FileNotFoundException {
        PrintWriter w = new PrintWriter(fname);
        w.println("<svg width=\"1024px\" height=\"1024px\">");
        for (int q = 0; q < points.size() - 1; q++) {
            Point2D.Double p1 = points.get(q);
            Point2D.Double p2 = points.get(q+1);
            w.printf("<polyline points=\"%f,%f %f,%f\" fill=\"none\" stroke-width=\"2\" stroke=\"black\" />\n",
                     p1.getX()*512 + 512, -p1.getY()*512 + 512,
                     p2.getX()*512 + 512, -p2.getY()*512 + 512);
        }
        Point2D.Double p1 = points.get(points.size() - 1);
        Point2D.Double p2 = points.get(0);
        w.printf("<polyline points=\"%f,%f %f,%f\" fill=\"none\" stroke-width=\"2\" stroke=\"black\" />\n",
                 p1.getX()*512 + 512, -p1.getY()*512 + 512,
                 p2.getX()*512 + 512, -p2.getY()*512 + 512);
        w.println("</svg>");
        w.close();
    }

    public static void writeToText(String fname, List<Point2D.Double> points) throws FileNotFoundException {
        PrintWriter w = new PrintWriter(fname);
        for (Point2D.Double p : points) {
            w.printf("%s,%s\n", p.getX(), p.getY());
        }
        w.close();
    }
    public static List<Point2D.Double> readFromText(String fname) throws IOException {
        List<Point2D.Double> result = new ArrayList<>();
        BufferedReader r = new BufferedReader(new FileReader(fname));
        String l;
        while ((l = r.readLine()) != null) {
            String[] p = l.split(",");
            result.add(new Point2D.Double(Double.valueOf(p[0]), Double.valueOf(p[1])));
        }
        return result;
    }
}