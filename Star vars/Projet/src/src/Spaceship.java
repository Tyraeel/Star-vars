package src;

import java.sql.Time;

public class Spaceship extends Entity{
	
	private int speed;
	private int firePower;
	private long productionTime;
	private String type;
	private Planet homePlanet;
	private Squadron squadron;
	
	public Spaceship(String type, Planet homePlanet) {
		super(homePlanet.getX(), homePlanet.getY());
		
		if (type.equals("BASIS")) {
			this.speed = 1;
			this.firePower = 1;
			this.productionTime = 1;
			this.homePlanet = homePlanet;
			this.squadron = squadron;
		}
	}
}	
