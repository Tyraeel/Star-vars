package src;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Spaceship {
	private Image image;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private double width;
	private double height;
	private double maxX;
	private double maxY;
	private int rotationAngle;
	
	public Spaceship(String path, double width, double height, double maxX, double maxY) {
		image = new Image(path, width, height, false, false);
		this.width = width;
		this.height = height;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public Spaceship(Spaceship s) {
		image = s.image;
		width = s.width;
		height = s.height;
		maxX = s.maxX;
		maxY = s.maxY;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	
	public int getRotationAngle() {
		return this.rotationAngle;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public double width() {
		return width;
	}

	public double height() {
		return height;
	}

	public void validatePosition() {
		if (x + width >= maxX) {
			x = maxX - width;
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}

		if (y + height >= maxY) {
			y = maxY - height;
			ySpeed *= -1;
		} else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}

	public void setSpeed(double xSpeed, double ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void changeSpeed(KeyCode code) {
		switch (code) {
		case LEFT:
			xSpeed--;
			break;
		case RIGHT:
			xSpeed++;
			break;
		case UP:
			ySpeed--;
			break;
		case DOWN:
			ySpeed++;
			break;
		case SPACE:
			ySpeed = xSpeed = 0;
			break;
		default:
		}
	}

	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
		validatePosition();
	}

	public void render(GraphicsContext gc) {
		
		ImageView iv = new ImageView(this.image);
        iv.setRotate(rotationAngle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(params, null);
        gc.drawImage(rotatedImage, this.x, this.y);
	}

	public boolean intersects(Spaceship s) {
		return ((x >= s.x && x <= s.x + s.width) || (s.x >= x && s.x <= x + width))
				&& ((y >= s.y && y <= s.y + s.height) || (s.y >= y && s.y <= y + height));
	}

	public String toString() {
		return "Sprite<" + x + ", " + y + ">";
	}
	
	public static double distance(double currentXPosition, double currentYPosition, double destinationX, double destinationY) {
		return Math.sqrt((Math.pow(destinationX-currentXPosition, 2) + Math.pow(destinationY-currentYPosition, 2)));
	}
	
	public void rotate() {
		
		if (this.xSpeed == -1) {
			this.rotationAngle = -90;
		}
		
		if (this.xSpeed == 1) {
			this.rotationAngle = 90;
		}
		
		if (this.ySpeed == -1) {
			this.rotationAngle = 0;
		}
		
		if (this.ySpeed == 1) {
			this.rotationAngle = -180;
		}
		
		if (this.xSpeed == -1 && this.ySpeed == -1)
			rotationAngle = -45;
		
		if (this.xSpeed == 1 && this.ySpeed == -1)
			rotationAngle = 45;
		
		if (this.xSpeed == -1 && this.ySpeed == 1)
			rotationAngle = -135;
		
		if (this.xSpeed == 1 && this.ySpeed == 1)
			rotationAngle = 135;
	}
	
	public boolean travel(double destinationX, double destinationY) {

			double distanceToDestination = distance(x, y, destinationX, destinationY);
			
			xSpeed = 0;
			ySpeed = 0;
			
			if (distance(this.x-1, this.y, destinationX, destinationY) < distanceToDestination) 
				this.xSpeed--;
			
				
			
			if (distance(this.x, this.y-1, destinationX, destinationY) < distanceToDestination) 
				this.ySpeed--;
			
				
			
			if (distance(this.x+1, this.y, destinationX, destinationY) < distanceToDestination) 
				this.xSpeed++;
			
				
			
			if (distance(this.x, this.y+1, destinationX, destinationY) < distanceToDestination) 
				this.ySpeed++;
		
			
				
			if (this.x == destinationX && this.y == destinationY) {
				xSpeed = 0;
				ySpeed = 0;
				return false;
			}
			
			this.rotate();
			
		return true;
	}
	
}