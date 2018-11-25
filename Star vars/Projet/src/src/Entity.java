package src;

public class Entity{

	private double x, y;

	public Entity() {}

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
    public String toString() {
        return "Entity( " + x + ", "  + y + ")";
    }

	public double distance(Entity p) {
		double d1 = p.x - x;
		double d2 = p.y - y;
		return Math.sqrt(d1*d1 + d2*d2);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
