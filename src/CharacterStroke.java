import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
public class CharacterStroke {
	private List<Point> points;
	private Stroke paintStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

	public CharacterStroke() {
		points = new ArrayList<Point>();
	}

	public void addPoint(Point p) {
		points.add(p);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.setStroke(this.paintStroke);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		for (int i = 0; i < points.size() - 1; ++i) {
	         g2d.draw(new Line2D.Double((double)points.get(i).x, (double)points.get(i).y, (double)points.get(i+1).x,
	               (double)points.get(i+1).y));
	         //System.out.println("Point "+i+" :"+points.get(i).getLocation());
	      }
	}

	public List<Point> getPoints() {
		return points;
	}
}
