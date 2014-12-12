package programming.regularpolygon;

import java.util.TimerTask;

import math.geom2d.polygon.Polygon2D;
import programming.regularpolygon.figuras.CurvaCerrada;
import programming.regularpolygon.gui.PanelTPoligono;

public class TPoligonoTask  extends TimerTask {
	private TPoligono transformador ;
	private PanelTPoligono panel ;
	public static double AREA_MAXIMA;
	private static final double TOLERANCIA = 0.000000000001;
	public static int ITERACIONES = 0;
	
	public TPoligonoTask(PanelTPoligono panel) {
		this.panel = panel ;
		transformador = new TPoligono(panel.getCurvaCerrada(), panel.getPoligono());
		Polygon2D poligono = panel.getPoligono();
		CurvaCerrada circle = panel.getCurvaCerrada();
		AREA_MAXIMA = circle.asPolyline(poligono.vertexNumber()).area();
	}
	@Override
	public void run() {
		transformador.transformarPoligono();
		panel.repaint();
		System.out.println("Area = " + panel.getPoligono().area());
		if (Math.abs(AREA_MAXIMA - panel.getPoligono().area()) < TOLERANCIA) {
			System.out.println("Area Maxima = " + AREA_MAXIMA);
			System.out.println("Iteraciones = " + ITERACIONES);
			cancel();
		} else {
			ITERACIONES ++ ;
		}
	}
}
