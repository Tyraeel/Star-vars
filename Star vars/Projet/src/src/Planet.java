package src;

import java.util.ArrayList;
import java.util.Random;

public class Planet extends Entity{
	
	private double radius;
	private int randomDimensions;
	private double productionRate;
	private String productionType;
	private String pathname;
	private int nbOfVessels;
	private ArrayList<Spaceship> reserveOfVessels;  /* à ajouter quand on aura la classe Ship */
	private Player owner; 	/* à ajouter quand on aura la classe Player */
	
	public Planet(double x, double y, int randomDimensions, String productionType, double productionRate, String pathname) {
        super(x, y);
		this.randomDimensions = randomDimensions;
		this.pathname=pathname;
		this.productionType = productionType;
		this.productionRate = productionRate;
		this.nbOfVessels = 25 + (int)((int)randomDimensions*0.5);
		this.reserveOfVessels = new ArrayList<Spaceship>();
		
		this.radius = randomDimensions/2;
		
		for (int i = 1; i < nbOfVessels; i++) {
			this.reserveOfVessels.add(new Spaceship(this.productionType, this));
		}
	}
	
	public double getRadius() {
		return radius;
	}
	
	public String getPathName() {
		return this.pathname;
	}
	
	public int getRandomDimensions() {
		return this.randomDimensions;
	}
	
	public int getNbVessels() {
		return this.nbOfVessels;
	}
	
	public void setOwner(Player p) {
		this.owner = p;
	}
	
	public int randomize(int min, int max) {
		Random rand = new Random();
	    int randomNumber = rand.nextInt(max-min + 1) + min;
	    return randomNumber;
	}
	
	public void produceSpaceship() {
		this.reserveOfVessels.add(new Spaceship(this.productionType, this));
		this.nbOfVessels++;
	}
	
	public void reduceNbSpaceship() {
		if (this.reserveOfVessels.size() > 0) {
			this.reserveOfVessels.remove(this.reserveOfVessels.get(0));
			this.nbOfVessels--;
		}	
	}
	
	public double distance(Planet p) {
		return Math.sqrt((Math.pow(p.getX()-this.getX(), 2) + Math.pow(p.getY()-this.getY(), 2)));
	}
	
	public void correctPlanetCollisionsAndBorders(ArrayList<Planet> planets, int width, int height) {
		
		if (this.getX()+this.getRadius()>width) 
			this.setX(this.getX()-this.getRadius());
			
        
		if (this.getY()+this.getRadius()>height)
			this.setY(this.getY()-this.getRadius());
    	
        
        if (this.getX()-this.getRadius()<0)
        	this.setX(this.getX()+this.getRadius());
        
        
        if (this.getY()-this.getRadius()<0)
        	this.setY(this.getY()+this.getRadius());
    	
	    
        for(int i = 0; i < planets.size(); i++) {
        	System.out.println("================ Planet " + i + " ==========================");
        	System.out.println("X : " + planets.get(i).getX());
        	System.out.println("Y : " + planets.get(i).getY());
        	System.out.println("Radius : " + planets.get(i).getRadius());
        	System.out.println("Dimension : " + this.getRandomDimensions());
        }
        
		for(int i = 0; i < planets.size()-1; i++) {
			if (this.distance(planets.get(i))-(this.radius + planets.get(i).radius) <= 0) {
				this.setX(this.randomize(0, width));
				this.setY(this.randomize(0, height));
				this.correctPlanetCollisionsAndBorders(planets, width, height);
			}
		}
	}
}
