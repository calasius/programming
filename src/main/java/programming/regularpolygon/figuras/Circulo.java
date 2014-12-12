package programming.regularpolygon.figuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.conic.Circle2D;
import math.geom2d.line.LinearShape2D;
import math.geom2d.line.StraightLine2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class Circulo extends Circle2D  implements CurvaCerrada {
	
	private final static int MAX_CANTIDAD = 100;
	public Circulo(Point2D center, int radius) {
		// TODO Auto-generated constructor stub
		super(center,radius);
	}
	public Polygon2D getPoligonoAleatorio(int cantVertices) {
		List<Point2D> puntosBorde = new ArrayList<Point2D>();
		List<Integer> indices = new ArrayList<Integer>();
		List<Point2D> puntosPoligono = new ArrayList<Point2D>();
		for (int i = 0; i < MAX_CANTIDAD; i++) {
			double x = radius()*Math.cos((Math.PI*2*i)/MAX_CANTIDAD) ;
			double y = radius()*Math.sin((Math.PI*2*i)/MAX_CANTIDAD) ;
			Point2D punto = new Point2D(x,y);
			puntosBorde.add(punto);
		}
		for (int i = 0; i < cantVertices; i++) {
			Random random = new Random();
			int indice = random.nextInt(MAX_CANTIDAD);
			indices.add(indice);
		}
		Collections.sort(indices);
		for (Integer integer : indices) {
			puntosPoligono.add(puntosBorde.get(integer));
		}
		Polygon2D poligono = new SimplePolygon2D(puntosPoligono);
		AffineTransform2D tra = AffineTransform2D.createTranslation(center().x(), center().y());
		poligono = poligono.transform(tra);
		return poligono ;
	}
	@Override
	public List<Point2D> calcularInterseccion(Point2D p1, Point2D p2) {
		LinearShape2D line = new StraightLine2D(p1,p2);
		return (List<Point2D>) intersections(line);
	}
	@Override
	public boolean isPoligono() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int cantidadLados() {
		// TODO Auto-generated method stub
		return 0;
	}
}
