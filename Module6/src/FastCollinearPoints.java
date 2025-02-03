import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;
    private int noOfLines = 0;

    /**
     * Finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("argument to BruteCollinearPoints constructor is null");
        }

        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("one of the point is null");
            }
        }

        int n = points.length;
        Point[] tempPoint = new Point[n];
        System.arraycopy(points, 0, tempPoint, 0, n);
        Arrays.sort(tempPoint);

        for (int i = 1; i < n; i++) {
            if (tempPoint[i].slopeTo(tempPoint[i-1]) == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("Argument to constructor contains repeated points");
            }
        }
        LineSegment[] tmp = new LineSegment[n * 4];
        findLineSegments(tempPoint, tmp, n);

        lineSegments = new LineSegment[noOfLines];
        System.arraycopy(tmp, 0, lineSegments, 0, noOfLines);
    }

    private void findLineSegments(Point[] points, LineSegment[] tmp, int n) {
        for (int i = 0; i < n; i++) {
            Point p = points[i];

            int copySize = n - i - 1;
            Point[] tempPoint = new Point[copySize];
            System.arraycopy(points, i+1, tempPoint, 0, copySize);
            Arrays.sort(tempPoint, p.slopeOrder());

            int c = 2;
            for (int j = 1; j < copySize; j++) {
                if (Double.compare(p.slopeTo(tempPoint[j-1]), p.slopeTo(tempPoint[j])) == 0) {
                    c++;
                } else {
                    if (c >= 4) {
                        LineSegment lineSegment = new LineSegment(p, tempPoint[j-1]);
                        tmp[noOfLines++] = lineSegment;
                    }
                    c = 2;

                }
            }
            if (c >= 4) {
                LineSegment lineSegment = new LineSegment(p, tempPoint[copySize-1]);
                tmp[noOfLines++] = lineSegment;
            }
        }
    }

    /**
     * The number of line segments
     * @return
     */
    public int numberOfSegments() {
        return noOfLines;
    }

    /**
     * The line segments
     * @return
     */
    public LineSegment[] segments() {
        LineSegment[] resulCopy = new LineSegment[noOfLines];
        System.arraycopy(lineSegments, 0, resulCopy, 0, noOfLines);
        return resulCopy;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
