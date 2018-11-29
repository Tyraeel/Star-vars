package src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.scene.input.KeyCode;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 800;

    public static String getRessourcePathByName(String name) {
        return Game.class.getResource('/' + name).toString();
    }

    public static void main(String[] args) {
        launch(args);
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

        Image space = new Image(getRessourcePathByName("images/background.jpg"), WIDTH, HEIGHT, false, false);
        
        
        Entity center = new Entity(100,100);
        Entity center2 = new Entity(1000,300);

        ArrayList<Planet> planetarray = new ArrayList<Planet>();
        planetarray.add(new Planet(center.getX(), center.getY(), 150, "BASIS", 0.9, "images/planet1.png", 2));
        planetarray.add(new Planet(center2.getX(), center2.getY(), 90, "BASIS", 0.9, "images/mars.png", 2));
        
        ArrayList<Image> imgTab = new ArrayList<Image>();
        for (int i = 0; i < planetarray.size(); i++) {
        	imgTab.add(new Image(planetarray.get(i).getPathName(), planetarray.get(i).getRadius(), planetarray.get(i).getRadius(), false, false)); 
        }
        
        /*Image planet1 = new Image(getRessourcePathByName("images/mars.png"), c.getRadius(), c.getRadius(), false, false);

        Image planet2 = new Image(getRessourcePathByName("images/mars.png"), c2.getRadius(), c2.getRadius(), false, false);*/
        
        Sprite spaceship = new Sprite(getRessourcePathByName("images/rocket.png"), 128, 128, WIDTH, HEIGHT);
        spaceship.setPosition(WIDTH / 2 - spaceship.width() / 2, HEIGHT / 2 - spaceship.height() / 2);

        stage.setScene(scene);
        stage.show();

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                spaceship.setSpeed(0, 0);
                spaceship.setPosition(e.getX() - spaceship.width() / 2, e.getY() - spaceship.height() / 2);
                
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
                for (int i = 0; i < planetarray.size(); i++) {
                /*gc.drawImage(planet1, c.getCenter().getX(), c.getCenter().getY());
                gc.drawImage(planet2, c2.getCenter().getX(), c2.getCenter().getY());*/
                gc.drawImage(imgTab.get(i),100*i,200*i);
                }
                
                spaceship.updatePosition();
                spaceship.render(gc);

                String txt = "Score: " + score;
                gc.fillText(txt, WIDTH - 36, 36);
                gc.strokeText(txt, WIDTH - 36, 36);
                gc.setTextAlign(TextAlignment.RIGHT);
            }
        }.start();
    }
}


