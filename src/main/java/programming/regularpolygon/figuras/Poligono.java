package programming.regularpolygon.figuras;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.LinearShape2D;
import math.geom2d.line.StraightLine2D;
import math.geom2d.polygon.LinearRing2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class Poligono extends SimplePolygon2D implements CurvaCerrada {
	
	public Poligono(Collection<Point2D> puntos) {
		super(puntos);
	}

	@Override
	public Polygon2D getPoligonoAleatorio(int cantVertices) {
		List<Integer> indices = new ArrayList<Integer>();
		List<Point2D> puntosPoligono = new ArrayList<Point2D>();
		Random random = new Random();
		while (indices.size() < cantVertices) {
			int indice = random.nextInt(this.vertexNumber());
			if (!indices.contains(Integer.valueOf(indice)))
				indices.add(indice);
		}
		Collections.sort(indices);
		List<LineSegment2D> lados = (List<LineSegment2D>) edges();
		for (Integer indice : indices) {
			puntosPoligono.add(lados.get(indice).point(0.5));
		}
		return new SimplePolygon2D(puntosPoligono);
	}

	@Override
	public List<Point2D> calcularInterseccion(Point2D p1, Point2D p2) {
		LinearShape2D line = new StraightLine2D(p1,p2);
		List<Point2D> res = new ArrayList<Point2D>();
		Point2D punto = null ;
		for (LineSegment2D lado : edges()) {
				if (!lado.contains(p1)){
					
					punto = lado.intersection(line);
					if (punto != null)
						break;
				}
		}
		res.add(null);
		res.add(punto);
		return res ;
	}

	@Override
	public LinearRing2D asPolyline(int vertexNumber) {
		// TODO Auto-generated method stub
		return super.getRing();
	}

	@Override
	public boolean isPoligono() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int cantidadLados() {
		// TODO Auto-generated method stub
		return vertexNumber();
	}

}
