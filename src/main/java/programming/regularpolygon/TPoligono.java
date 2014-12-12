package programming.regularpolygon;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import programming.regularpolygon.figuras.CurvaCerrada;

public class TPoligono {
	
	private double t = 0.5d;
	private Polygon2D poligono ;
	private CurvaCerrada curvaCerrada ;
	
	public TPoligono(CurvaCerrada curvaCerrada, Polygon2D poligono) {
		this.curvaCerrada = curvaCerrada;
		this.poligono = poligono;
	}
	
	public TPoligono(CurvaCerrada curvaCerrada, Polygon2D poligono, double t) {
		this.curvaCerrada = curvaCerrada;
		this.poligono = poligono;
		this.t = t;
	}
	
	public void transformarPoligono() {
		
		List<Point2D> puntosMedios = new ArrayList<Point2D>();
		
		for (LineSegment2D segmento : poligono.edges()) {
			puntosMedios.add(segmento.point(t));
		}
		//El primer punto lo pongo a lo ultimo
		puntosMedios.add(puntosMedios.get(0));
		puntosMedios.remove(0);
		
		for (int i = 0; i < poligono.vertexNumber(); i++) {
			Point2D p1 = poligono.vertex(i);
			Point2D p2 = puntosMedios.get(i);
			List<Point2D> intersecciones = curvaCerrada.calcularInterseccion(p1,p2);
			Point2D i2 = intersecciones.get(1);
			poligono.setVertex(i, i2);
		}
	}
}
