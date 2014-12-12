package programming.regularpolygon.figuras;

import java.awt.Graphics2D;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.polygon.LinearRing2D;
import math.geom2d.polygon.Polygon2D;

public interface CurvaCerrada {
	public Polygon2D getPoligonoAleatorio(int cantVertices) ;
	public List<Point2D> calcularInterseccion (Point2D p1, Point2D p2) ;
	public void draw(Graphics2D g2);
	public LinearRing2D asPolyline(int vertexNumber);
	public boolean isPoligono();
	public int cantidadLados();
}
