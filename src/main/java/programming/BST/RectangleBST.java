package programming.BST;

import math.geom2d.polygon.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by claudio on 12/25/16.
 */
public class RectangleBST {

    private IntervalBST x_intervals;
    private IntervalBST y_intervals;
    private Map<Integer, Rectangle2D> rectanglesById = new HashMap<Integer, Rectangle2D>();

    public RectangleBST(List<Rectangle2D> rectangles) {

        this.x_intervals = new IntervalBST();
        this.y_intervals = new IntervalBST();

        for (Rectangle2D rectangle : rectangles) {
            Integer rectangleId = rectangle.hashCode();
            this.rectanglesById.put(rectangle.hashCode(), rectangle);
            IntervalBST.Interval x_interval = new IntervalBST.Interval(rectangle.getX(),
                rectangle.getX() + rectangle.getWidth(), rectangleId);
            IntervalBST.Interval y_interval = new IntervalBST.Interval(rectangle.getY(),
                rectangle.getY() + rectangle.getHeight(), rectangleId);
            this.x_intervals.put(x_interval);
            this.y_intervals.put(y_interval);
        }
    }

    public List<Rectangle2D> find(double x, double y) {
        List<IntervalBST.Interval> x_intersectsIntervals = x_intervals.intersectIntervals(new IntervalBST.Interval(x, x));
        List<IntervalBST.Interval> y_intersectsIntervals = y_intervals.intersectIntervals(new IntervalBST.Interval(y, y));

        List<Rectangle2D> response = new ArrayList<Rectangle2D>();
        for (IntervalBST.Interval x_interval : x_intersectsIntervals) {
            for (IntervalBST.Interval y_interval : y_intersectsIntervals) {
                if (x_interval.getId() == y_interval.getId()) {
                    response.add(rectanglesById.get(x_interval.getId()));
                    break;
                }
            }
        }

        return response;
    }

    public static void main(String... args) {
        List<Rectangle2D> rectangles = new ArrayList<Rectangle2D>();
        rectangles.add(new Rectangle2D(1.0, 1.0, 2, 2));
        rectangles.add(new Rectangle2D(2.0, 4.0, 2, 2));
        rectangles.add(new Rectangle2D(5.0, 2.0, 2, 2));
        rectangles.add(new Rectangle2D(1.0, 2.0, 3, 3));
        RectangleBST rectangleBST = new RectangleBST(rectangles);
        List<Rectangle2D> intersects = rectangleBST.find(2.5, 5.5);

        for (Rectangle2D rectangle : intersects) {
            System.out.println(
                rectangle.getX() + "," + rectangle.getY() + "," + rectangle.getWidth() + "," + rectangle.getHeight());
        }
    }
}
