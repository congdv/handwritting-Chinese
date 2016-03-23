package HandDraw.Character;


public class SubStroke {
	private double direction;
	private double length;
	public SubStroke(double direction, double length) {
		this.direction = direction;
		this.length = length;
	}
	public double getDirection() {
		return direction;
	}

	public double getLength() {
		return length;
	}
}
