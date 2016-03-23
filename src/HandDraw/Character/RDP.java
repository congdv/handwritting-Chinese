package HandDraw.Character;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RDP {
	private static double pointToLineDistance(Point A, Point B, Point P) {
	    double normalLength = Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.y-A.y)*(B.y-A.y));
	    return Math.abs((P.x-A.x)*(B.y-A.y)-(P.y-A.y)*(B.x-A.x))/normalLength;
	}
	private static List<Point> SplitList(List<Point> pointList,int i,int j) {
		List<Point> smallList = new ArrayList<Point>();
		for(int p = i; p <= j;p++) {
			smallList.add(pointList.get(p));
		}
		return smallList;
	}
	public static List<Point> DouglasPeucker(List<Point> pointList,double epsilon) {
		List<Point> resultList = new ArrayList<Point>();
		double dmax = 0;
		int index = 0;
		int end = pointList.size() - 1;
		double d = 0;
		for(int i = 1; i < end; i++) {
			d = pointToLineDistance(pointList.get(0), pointList.get(end), pointList.get(i));
			if (d>dmax) {
				index = i;
				dmax = d;
			}
		}
		//If max distance is greater than epsilon, recursively simplify
		if(dmax > epsilon) {
			//Recursive call
			List<Point> resultList1 = new ArrayList<Point>();
			List<Point> resultList2 = new ArrayList<Point>();
			resultList1 = DouglasPeucker(SplitList(pointList, 0, index), epsilon);
			resultList2 = DouglasPeucker(SplitList(pointList, index, end), epsilon);
			
			resultList.addAll(resultList1);
			resultList.addAll(resultList2);
		} else {
			resultList.add(pointList.get(0));
			resultList.add(pointList.get(end));
		}
		return resultList;
	}
	public static void main(String[] args) {
		List<Point> temp = new ArrayList<Point>();
		Point a = new Point(1,1);
		temp.add(a);
		Point b = new Point(3,1);
		temp.add(b);
		Point c = new Point(4,1);
		temp.add(c);
		Point d = new Point(5,20);
		temp.add(d);
		Point e = new Point(6,1);
		temp.add(e);
		Point f = new Point(7,1);
		temp.add(f);
		Point g = new Point(8,1);
		temp.add(g);
		
		List<Point> result = DouglasPeucker(temp, 10);
		for(int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).x + "--" + result.get(i).y);
		}
	}
}
