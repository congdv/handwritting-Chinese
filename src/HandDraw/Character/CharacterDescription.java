package HandDraw.Character;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CharacterDescription {
	private List<Point> points;
	private List<Point> curvePoints;
	private List<SubStroke> subStroke;
	
	public CharacterDescription() {
		points = new ArrayList<Point>();
		curvePoints = new ArrayList<Point>();
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public void drawCurve(Graphics g) {
		determineCurve();
		g.setColor(Color.BLACK);
		System.out.println(curvePoints.size()+"--size");
		for(int i = 0; i < curvePoints.size();i++) {
			g.fillOval(curvePoints.get(i).x, curvePoints.get(i).y, 6, 6);
			//System.out.println(curvePoints.get(i).x+"--"+curvePoints.get(i).y);
		}
	}
	public void determineCurve() {
		curvePoints = RDP.DouglasPeucker(points, 10);
		
	}
	public void drawPoint(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < points.size();i++) {
			
			g.fillOval(points.get(i).x, points.get(i).y, 6, 6);
		}
	}
	
	private List<PointSubStroke> getPointsSubStroke() {
		List<PointSubStroke> subStrokeList = new ArrayList<PointSubStroke>();
		//go through all of points in stroke and check for begin and end of sub stroke
		Iterator<Point> pointSubStroke = curvePoints.iterator();
		int beginSub,endSub = 0;
		beginSub = points.indexOf(pointSubStroke.next());
		while(pointSubStroke.hasNext()) {
			endSub = points.indexOf(pointSubStroke.next());
			if(beginSub != endSub) {
				PointSubStroke subStroke = new PointSubStroke();
				subStroke.setPointsSubStroke(points.subList(beginSub, endSub));
				subStrokeList.add(subStroke);
				beginSub = endSub;
			}
		}
		return subStrokeList;
	}
	public void calculateSubStroke() {
		List<PointSubStroke> pointsSubStroke = getPointsSubStroke();
		System.out.println(pointsSubStroke.size());
		Iterator<PointSubStroke> subStroke = pointsSubStroke.iterator();
		while(subStroke.hasNext()) {
			PointSubStroke sub = subStroke.next();
			System.out.println(sub.getPointsSubStroke().get(0).getLocation() + "---"+sub.getPointsSubStroke().get(sub.getPointsSubStroke().size() - 1).getLocation());
			System.out.println("direction = "+sub.direction()+" distance"+sub.distance());
		}
		
	}
	public List<SubStroke> getSubStroke() {
		return subStroke;
	}
	class PointSubStroke{
		List<Point> pointsSubStroke;
		
		private double leftX;
		private double righX;
		private double topY;
		private double bottomY;
		
		public PointSubStroke() {
			pointsSubStroke = new ArrayList<Point>();
		}
		public void addPoint(Point e) {
			pointsSubStroke.add(e);
		}
		public List<Point> getPointsSubStroke() {
			return pointsSubStroke;
		}
		public void setPointsSubStroke(List<Point> pointsSubStroke) {
			this.pointsSubStroke = pointsSubStroke;
		}
		//Set size square box 
		private void setSizeBox() {
			this.leftX =  pointsSubStroke.get(0).getX();
			this.righX =  pointsSubStroke.get(0).getX();
			this.topY = pointsSubStroke.get(0).getY();
			this.bottomY = pointsSubStroke.get(0).getY();
			
			for(int i = 0; i < pointsSubStroke.size();i++) {
				double x = pointsSubStroke.get(i).getX();
				double y = pointsSubStroke.get(i).getX();
				
				this.leftX = Math.min(x, leftX);
				this.righX = Math.max(x, righX);
				this.topY = Math.min(y, topY);
				this.bottomY = Math.max(y, bottomY);
			}
		}
		public double distance() {
			this.setSizeBox();
			double width = this.righX - this.leftX;
			System.out.println("width "+width);
			double height = this.bottomY - this.topY;
			int size = pointsSubStroke.size() - 1;
			double dimensionSquared = width > height ? width * width : height * height;
			System.out.println("dimensionsquared "+dimensionSquared);
			double normalize = Math.sqrt(dimensionSquared + dimensionSquared);
			double distanceNormalize = points.get(0).distance(points.get(size)) / normalize;
			distanceNormalize = Math.min(distanceNormalize, 1.0); 
			return distanceNormalize;
		}
		public double direction() {
			int size = pointsSubStroke.size() - 1;
			double dx = pointsSubStroke.get(0).getX() - pointsSubStroke.get(size).getX();
			double dy = pointsSubStroke.get(0).getY() - pointsSubStroke.get(size).getY();

			double direction = Math.PI - Math.atan2(dy, dx);
			return direction;
		}
		
	}
}
