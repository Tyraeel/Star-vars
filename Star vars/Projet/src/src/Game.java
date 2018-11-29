package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javafx.scene.input.KeyCode;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {
    private int score;
    private final static int WIDTH = 1600;
    private final static int HEIGHT = 900;
    private static boolean move = false;
    private static boolean moving = false;
    private static double destinationX = 0;
    private static double destinationY = 0;
    
    public static String getRessourcePathByName(String name) {
        return Game.class.getResource('/' + name).toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static int randomize(int min, int max) {
		Random rand = new Random();
	    int randomNumber = rand.nextInt(max-min + 1) + min;
	    return randomNumber;
	}
    
    public static void checkMovementOrder(Sprite spaceship) {
    	if (move)
        	move = spaceship.travel(destinationX, destinationY);
    	System.out.println(spaceship.toString());
    	System.out.println(spaceship.getRotationAngle());
    		
    }
    
    public void start(Stage stage) {

        stage.setTitle("Star vars");
        stage.setResizable(false);

        Group root = new Group();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        gc.setFill(Color.BISQUE);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);

        Image space = new Image(getRessourcePathByName("images/Background2.png"), WIDTH, HEIGHT, false, false);
        
        ArrayList<Planet> planetArray = new ArrayList<Planet>();
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet1.png"));
        planetArray.get(0).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet2.png"));
        planetArray.get(1).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet3.png"));
        planetArray.get(2).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet4.png"));
        planetArray.get(3).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet5.png"));
        planetArray.get(4).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        planetArray.add(new Planet(randomize(0, WIDTH-100), randomize(0, HEIGHT-100), randomize(64, 256),"BASIS", 0.9, "images/planet6.png"));
        planetArray.get(5).correctPlanetCollisionsAndBorders(planetArray, WIDTH-100, HEIGHT-100);
        
        ArrayList<Image> imgTab = new ArrayList<Image>();
        for (int i = 0; i < planetArray.size(); i++) {
        	imgTab.add(new Image(planetArray.get(i).getPathName(), planetArray.get(i).getRandomDimensions(), planetArray.get(i).getRandomDimensions(), false, false)); 
        }
        
        /*Image planet1 = new Image(getRessourcePathByName("images/mars.png"), c.getRadius(), c.getRadius(), false, false);

        Image planet2 = new Image(getRessourcePathByName("images/mars.png"), c2.getRadius(), c2.getRadius(), false, false);*/
        
        Sprite spaceship = new Sprite(getRessourcePathByName("images/rocket.png"), 32, 32, WIDTH, HEIGHT);
        spaceship.setPosition(WIDTH / 2 - spaceship.width() / 2, HEIGHT / 2 - spaceship.height() / 2);

        stage.setScene(scene);
        stage.show();

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                move = true;
                destinationX = e.getX() - spaceship.width() / 2;
                destinationY = e.getY() - spaceship.height() / 2;
            }
        };
        
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMousePressed(mouseHandler);
        
        MediaPlayer mediaPlayer = null;
        try {
            mediaPlayer = new MediaPlayer(new Media(getRessourcePathByName("sounds/Engine.mp4")));// Only format allowed
                                                                                                    // in the context of
                                                                                                    // the project (mp4)

        } catch (MediaException e) {
            // in case of a platform without sound capabilities
        }
        MediaPlayer mediaPlayerPffft = null;
        try {
            mediaPlayerPffft = new MediaPlayer(new Media(getRessourcePathByName("sounds/Explosion.mp4")));

        } catch (MediaException e) {
            // in case of a platform without sound capabilities
        }

        final MediaPlayer mediaPlayerFinalCopy = mediaPlayer;// final copies are needed to transmit to inner classes
        final MediaPlayer mediaPlayerBoomFinalCopy = mediaPlayerPffft;

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                spaceship.changeSpeed(e.getCode());
                
                if (mediaPlayerFinalCopy != null) {
                    mediaPlayerFinalCopy.stop();
                    mediaPlayerFinalCopy.play();
                }
            }
        });
        
        new AnimationTimer() {
        	
            public void handle(long arg0) {
                gc.drawImage(space, 0, 0);
                for (int i = 0; i < imgTab.size(); i++) {
                /*gc.drawImage(planet1, c.getCenter().getX(), c.getCenter().getY());
                gc.drawImage(planet2, c2.getCenter().getX(), c2.getCenter().getY());*/
            
                gc.drawImage(imgTab.get(i), planetArray.get(i).getX(), planetArray.get(i).getY());
                String nbOfVessels = "" + planetArray.get(i).getNbVessels();
                gc.setFill(Color.BLACK);
                gc.setStroke(Color.BLACK);
                gc.fillText(nbOfVessels, planetArray.get(i).getX()+planetArray.get(i).getRadius()-18, planetArray.get(i).getY()+planetArray.get(i).getRadius()+10);
 
                }
                
                checkMovementOrder(spaceship);
               
                spaceship.updatePosition();
                spaceship.render(gc);
                         
                //String txt = "Score: " + score;
                //gc.fillText(txt, WIDTH - 36, 36);
                //gc.strokeText(txt, WIDTH - 36, 36);
                //gc.setTextAlign(TextAlignment.RIGHT);
            }
        }.start();
    }
}


