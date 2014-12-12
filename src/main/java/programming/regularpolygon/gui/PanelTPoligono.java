package programming.regularpolygon.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import math.geom2d.polygon.Polygon2D;
import programming.regularpolygon.TPoligonoTask;
import programming.regularpolygon.figuras.CurvaCerrada;
import programming.regularpolygon.figuras.EnumFiguras;

public class PanelTPoligono extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CurvaCerrada getCurvaCerrada() {
		return curvaCerrada;
	}

	public Polygon2D getPoligono() {
		return poligono;
	}

	public void setPoligono(Polygon2D poligono) {
		this.poligono = poligono;
	}

	private CurvaCerrada curvaCerrada ;
	private Polygon2D poligono ;
	
	public PanelTPoligono() {
		curvaCerrada = EnumFiguras.ELLIPSE.getCurva();
		if (curvaCerrada.isPoligono()) {
			poligono = curvaCerrada.getPoligonoAleatorio(curvaCerrada.cantidadLados());
		} else {
			poligono = curvaCerrada.getPoligonoAleatorio(6);
		}
	}
	
	public void paintComponent(Graphics g){
	    super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		curvaCerrada.draw(g2);
		g2.setColor(Color.BLUE);
		poligono.draw(g2);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PanelTPoligono panel = new PanelTPoligono();
		JFrame frame = new JFrame("Convex hull test");
		frame.setContentPane(panel);
		frame.setSize(500, 500);
		frame.setVisible(true);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TPoligonoTask(panel), 0, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
