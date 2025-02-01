import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int noOfLines = 0;

    /**
     * Finds all line segments containing 4 points
     * @param points
     * @throws NullPointerException if <tt>points</tt> is <tt>null</tt>
     *         or <tt>points</tt> contains repeated points.
     */
    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new IllegalArgumentException("argument to BruteCollinearPoints constructor is null");
        }

        for(Point p : points) {
            if(p == null) {
                throw new IllegalArgumentException("one of the point is null");
            }
        }
        MergeX.sort(points);
        int n = points.length;
        for(int i = 1;i < n; i++) {
            if(points[i] == points[i-1]) {
                throw new IllegalArgumentException("Argument to constructor contains repeated points");
            }
        }


        LineSegment[] tmp = new LineSegment[n];
        findLineSegments(points, tmp, n);
//        for(int p = 0; p < n; p++){
//            for(int q = p+1; q < n; q++){
//                for(int r = q+1; r < n; r++){
//                    for(int s = r+1; s < n; s++){
//                        double slp1 = points[p].slopeTo(points[q]);
//                        double slp2 = points[p].slopeTo(points[r]);
//                        double slp3 = points[p].slopeTo(points[s]);
//                        if(slp1 == slp2 && slp2 == slp3) // p,q,r,s are collinear
//                        {
//                            LineSegment line = new LineSegment(points[p], points[s]);
//                            tmp[noOfLines++] = line;
//                        }
//                    }
//                }
//            }
//        }
        lineSegments = new LineSegment[noOfLines];
        System.arraycopy(tmp, 0, lineSegments, 0, noOfLines);
    }

    private void findLineSegments(Point[] points, LineSegment[] lineSegments, int n) {
        for(int p = 0; p < n; p++){
            for(int q = p+1; q < n; q++){
                for(int r = q+1; r < n; r++){
                    for(int s = r+1; s < n; s++){
                        double slp1 = points[p].slopeTo(points[q]);
                        double slp2 = points[p].slopeTo(points[r]);
                        double slp3 = points[p].slopeTo(points[s]);
                        if(slp1 == slp2 && slp2 == slp3) // p,q,r,s are collinear
                        {
                            LineSegment line = new LineSegment(points[p], points[s]);
                            lineSegments[noOfLines++] = line;
                        }
                    }
                }
            }
        }
    }

    /**
     * The number of line segments
     * @return count of line segments containing 4 points.
     */
    public int numberOfSegments() {
        return noOfLines;
    }

    /**
     * The line segments
     * @return The list of line segments
     */
    public LineSegment[] segments() {
        return lineSegments;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
