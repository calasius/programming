package programming.regularpolygon.figuras;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.convhull.GrahamScan2D;

public enum EnumFiguras {
	
	CIRCUMFERENCIA(new Circulo(new Point2D(250,250), 200)),
	ELLIPSE(new Elipse(new Point2D(250,250), 220, 100)),
	POLIGONO_ALEATORIO(crearPoligonoAleatorio()),
	POLIGONO_REGULAR(new Poligono(new Circulo(new Point2D(250,250), 200).asPolyline(7).vertices()));
	
	private CurvaCerrada curva ;
	
	private EnumFiguras(CurvaCerrada curva) {
		this.curva = curva ;
	}
	
	private static CurvaCerrada crearPoligonoAleatorio() {
		List<Point2D> points = new ArrayList<Point2D>(100);
		for(int i=0; i < 100; i++){
		    points.add(new Point2D(
		            Math.random()*300+50,
		            Math.random()*300+50));
		}
		
		GrahamScan2D hullCalculator = new GrahamScan2D();
		Polygon2D poligono = hullCalculator.convexHull(points);
		return new Poligono(poligono.vertices());
	}

	public CurvaCerrada getCurva() {
		return curva;
	}

	public void setCurva(CurvaCerrada curva) {
		this.curva = curva;
	}

}
