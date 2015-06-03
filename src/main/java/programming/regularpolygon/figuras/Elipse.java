package programming.regularpolygon.figuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import math.geom2d.Point2D;
import math.geom2d.conic.Ellipse2D;
import math.geom2d.line.LinearShape2D;
import math.geom2d.line.StraightLine2D;
import math.geom2d.polygon.LinearRing2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class Elipse extends Ellipse2D implements CurvaCerrada {
	
	public Elipse(Point2D center, double ancho, double alto) {
		super(center, ancho, alto);
	}
	
	
	public Polygon2D getPoligonoAleatorio(int cantVertices) {
		// TODO Auto-generated method stub
		LinearRing2D polyLine = asPolyline(3*cantVertices);
		List<Point2D> puntos = (List<Point2D>) polyLine.vertices();
		List<Integer> indices = new ArrayList<Integer>();
		List<Point2D> puntosPoligono = new ArrayList<Point2D>();
		Random random = new Random();
		while (indices.size() < cantVertices) {
			int indice = random.nextInt(3*cantVertices);
			if (!indices.contains(Integer.valueOf(indice)))
				indices.add(indice);
		}
		Collections.sort(indices);
		for (Integer indice : indices) {
			puntosPoligono.add(puntos.get(indice));
		}
		return new SimplePolygon2D(puntosPoligono);
	}

	public List<Point2D> calcularInterseccion(Point2D p1, Point2D p2) {
		// TODO Auto-generated method stub
		LinearShape2D line = new StraightLine2D(p1,p2);
		return (List<Point2D>) intersections(line);
	}


	public boolean isPoligono() {
		// TODO Auto-generated method stub
		return false;
	}


	public int cantidadLados() {
		// TODO Auto-generated method stub
		return 0;
	}

}
