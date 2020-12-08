package geometry;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Polygon2D implements Shape, Cloneable
{
    public int npoints;
    public double[] xpoints;
    public double[] ypoints;

    protected Rectangle2D bounds;

    private Path2D.Double path;
    private Path2D.Double closedPath;

    public Polygon2D()
    {
        super();

        npoints = 0;
        xpoints = new double[0];
        ypoints = new double[0];
        bounds = new Rectangle2D.Double();
        path = null;
    }

    public Polygon2D(Rectangle2D rec)
    {
        super();

        if (rec == null)
            throw new IllegalArgumentException("null Rectangle");

        npoints = 4;
        xpoints = new double[4];
        ypoints = new double[4];

        xpoints[0] = rec.getMinX();
        ypoints[0] = rec.getMinY();
        xpoints[1] = rec.getMaxX();
        ypoints[1] = rec.getMinY();
        xpoints[2] = rec.getMaxX();
        ypoints[2] = rec.getMaxY();
        xpoints[3] = rec.getMinX();
        ypoints[3] = rec.getMaxY();

        calculatePath();
    }

    public Polygon2D(Polygon pol)
    {
        super();

        if (pol == null)
            throw new IllegalArgumentException("null Polygon");

        this.npoints = pol.npoints;
        this.xpoints = new double[pol.npoints];
        this.ypoints = new double[pol.npoints];

        for (int i = 0; i < pol.npoints; i++)
        {
            xpoints[i] = pol.xpoints[i];
            ypoints[i] = pol.ypoints[i];
        }

        calculatePath();
    }

    public Polygon2D(double[] xpoints, double[] ypoints, int npoints)
    {
        super();

        if (npoints > xpoints.length || npoints > ypoints.length)
            throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");

        this.npoints = npoints;
        this.xpoints = new double[npoints];
        this.ypoints = new double[npoints];

        System.arraycopy(xpoints, 0, this.xpoints, 0, npoints);
        System.arraycopy(ypoints, 0, this.ypoints, 0, npoints);

        calculatePath();
    }

    public Polygon2D(int[] xpoints, int[] ypoints, int npoints)
    {
        super();

        if (npoints > xpoints.length || npoints > ypoints.length)
            throw new IndexOutOfBoundsException("npoints > xpoints.length || npoints > ypoints.length");

        this.npoints = npoints;
        this.xpoints = new double[npoints];
        this.ypoints = new double[npoints];

        for (int i = 0; i < npoints; i++)
        {
            this.xpoints[i] = xpoints[i];
            this.ypoints[i] = ypoints[i];
        }

        calculatePath();
    }

    public void reset()
    {
        npoints = 0;
        xpoints = new double[0];
        ypoints = new double[0];
        bounds = new Rectangle2D.Double();
        path = null;
        closedPath = null;
    }

    @Override
    public Object clone()
    {
        Polygon2D pol = new Polygon2D();

        for (int i = 0; i < npoints; i++)
            pol.addPoint(xpoints[i], ypoints[i]);

        return pol;
    }

    public void calculatePath()
    {
        path = new Path2D.Double();

        path.moveTo(xpoints[0], ypoints[0]);
        for (int i = 1; i < npoints; i++)
            path.lineTo(xpoints[i], ypoints[i]);

        bounds = path.getBounds2D();
        closedPath = null;
    }

    private void updatePath(double x, double y)
    {
        if (path == null)
        {
            path = new Path2D.Double(Path2D.WIND_EVEN_ODD);
            path.moveTo(x, y);
            bounds = new Rectangle2D.Double(x, y, 0, 0);
        }
        else
        {
            path.lineTo(x, y);

            double _xmax = bounds.getMaxX();
            double _ymax = bounds.getMaxY();
            double _xmin = bounds.getMinX();
            double _ymin = bounds.getMinY();

            if (x < _xmin)
                _xmin = x;
            else if (x > _xmax)
                _xmax = x;
            if (y < _ymin)
                _ymin = y;
            else if (y > _ymax)
                _ymax = y;

            bounds = new Rectangle2D.Double(_xmin, _ymin, _xmax - _xmin, _ymax - _ymin);
        }

        closedPath = null;
    }

    public Polygon getPolygon()
    {
        int[] _xpoints = new int[npoints];
        int[] _ypoints = new int[npoints];

        for (int i = 0; i < npoints; i++)
        {
            _xpoints[i] = (int) xpoints[i];
            _ypoints[i] = (int) ypoints[i];
        }

        return new Polygon(_xpoints, _ypoints, npoints);
    }

    public void addPoint(Point2D p)
    {
        addPoint(p.getX(), p.getY());
    }

    public void addPoint(double x, double y)
    {
        if (npoints == xpoints.length)
        {
            double[] tmp;

            tmp = new double[npoints * 2];
            System.arraycopy(xpoints, 0, tmp, 0, npoints);
            xpoints = tmp;

            tmp = new double[npoints * 2];
            System.arraycopy(ypoints, 0, tmp, 0, npoints);
            ypoints = tmp;
        }

        xpoints[npoints] = x;
        ypoints[npoints] = y;
        npoints++;

        updatePath(x, y);
    }

    public boolean contains(Point p)
    {
        return contains(p.x, p.y);
    }

    public boolean contains(int x, int y)
    {
        return contains((double) x, (double) y);
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return (Rectangle2D) bounds.clone();
    }

    @Override
    public Rectangle getBounds()
    {
        return bounds.getBounds();
    }

    @Override
    public boolean contains(double x, double y)
    {
        if (npoints <= 2 || !bounds.contains(x, y))
            return false;

        return updateComputingPath().contains(x, y);
    }

    private Path2D.Double updateComputingPath()
    {
        Path2D.Double result = closedPath;

        if (result == null)
        {
            if (path != null)
            {
                result = (Path2D.Double) path.clone();
                result.closePath();
            }
            else
                result = new Path2D.Double();

            closedPath = result;
        }

        return result;
    }

    @Override
    public boolean contains(Point2D p)
    {
        return contains(p.getX(), p.getY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h)
    {
        if (npoints <= 0 || !bounds.intersects(x, y, w, h))
            return false;

        return updateComputingPath().intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r)
    {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public boolean contains(double x, double y, double w, double h)
    {
        if (npoints <= 0 || !bounds.intersects(x, y, w, h))
            return false;

        return updateComputingPath().contains(x, y, w, h);
    }

    @Override
    public boolean contains(Rectangle2D r)
    {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at)
    {
        return updateComputingPath().getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness)
    {
        return getPathIterator(at);
    }
}

