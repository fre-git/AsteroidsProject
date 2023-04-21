import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


// part 2 28.40 start

public class Asteroids extends Application {
    public static void main(String[] args) {
        try{
            launch(args);
        }
        catch (Exception error){
            error.printStackTrace();
        }
        finally {
            System.exit(0);
        }
    }

    @Override
    public void start(Stage mainStage){
        mainStage.setTitle("Asteroids");

        BorderPane root = new BorderPane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        Canvas canvas = new Canvas(800,600);
        GraphicsContext context = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        //handle continuous inputs (as long as key is pressed)
        ArrayList<String> keyPressedList = new ArrayList<>();
        // handle discrete inputs (once per key pressed)
        ArrayList<String> keyJustPressedList = new ArrayList<>();


        mainScene.setOnKeyPressed(
                event ->
                {
                    String keyName = event.getCode().toString();
                    // avoid adding duplicates to list
                    if (!keyPressedList.contains(keyName)){
                        keyPressedList.add(keyName);
                        keyJustPressedList.add(keyName);
                    }
                }
        );

        mainScene.setOnKeyReleased(
                event ->
                {
                    String keyName = event.getCode().toString();
                    if (keyPressedList.contains(keyName)){
                        keyPressedList.remove(keyName);
                    }
                }
        );

        Sprite background = new Sprite("images/space.png");

        background.position.set(400,300);

        Sprite spaceship = new Sprite("images/Mario.png", 100);
        spaceship.position.set(100,300);
        spaceship.velocity.set(50,0);

        ArrayList<Sprite> laserList = new ArrayList<Sprite>();
        ArrayList<Sprite> asteroidList = new ArrayList<Sprite>();

        int asteroidCount = 15;
        Image rockImage =  new Image("images/rock.png", 100, 100, false, false);
        for(int n = 0; n < asteroidCount; n++){
            Sprite asteroid = new Sprite(rockImage);
//            Sprite asteroid = new Sprite("images/rock.png", 100);
            double x = 500 * Math.random() + 300; //300 - 800
            double y = 400 * Math.random() + 100;// 100-500
            asteroid.position.set(x,y);
            double angle = 360 * Math.random();
//            System.out.println(angle);
            asteroid.velocity.setLength(50);
            asteroid.velocity.setAngle(angle);
            asteroid.rotation = angle;
            asteroidList.add(asteroid);

            System.out.println(n);
        }

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long nanotime) {
                //process user input
                if(keyPressedList.contains("LEFT"))
                    spaceship.rotation -= 5;
                if(keyPressedList.contains("RIGHT"))
                    spaceship.rotation += 5;
                if(keyPressedList.contains("UP")){
                    spaceship.velocity.setLength(500);
                    spaceship.velocity.setAngle(spaceship.rotation);
                } else { // not pressing up
                    spaceship.velocity.setLength(0);
                }
                if(keyPressedList.contains("DOWN")){
                    spaceship.velocity.setLength(300);
                    spaceship.velocity.setAngle(spaceship.rotation + 180);
                }


                if(keyJustPressedList.contains("SPACE")){
                    Sprite laser = new Sprite("images/laser.png", 25);
                    laser.position.set(spaceship.position.x, spaceship.position.y);
                    laser.velocity.setLength(700);
                    laser.velocity.setAngle(spaceship.rotation);
                    laserList.add(laser);
                }

                // after processing user input, clear justPressedList
                keyJustPressedList.clear();


                spaceship.update(1/60.0);
                for(Sprite asteroid: asteroidList)
                    asteroid.update(1/60.0);

                // update lasers; destroy if more than 2 seconds passed
                for(int n = 0; n < laserList.size(); n++){
                    Sprite laser = laserList.get(n);
                    laser.update(1/60.0);
                    if(laser.elapsedTime > 2){
                        laserList.remove(laser);
                    }
                }

                // bug met verwijderen door lijsten waar ge door loopt
//                //when Laser overlaps asteroid, remove both
//                for (int laserNum = 0; laserNum < laserList.size(); laserNum++) {
//                    Sprite laser = laserList.get(laserNum);
//                    for (int asteroidNum = 0; asteroidNum < asteroidList.size(); asteroidNum++) {
//                        Sprite asteroid = asteroidList.get(asteroidNum);
//                        if(laser.overlaps(asteroid)){
//                            laserList.remove(laserNum);
//                            asteroidList.remove(asteroidNum);
//                        }
//                    }
//                }

                // Kan nog Beter
                //when Laser overlaps asteroid, remove both
//                Iterator<Sprite> lasterIterator = laserList.iterator();
//                while (lasterIterator.hasNext()) {
//                    Sprite laser = lasterIterator.next();
//
//                    Iterator<Sprite> astroidIterator = asteroidList.iterator();
//                    while (astroidIterator.hasNext()) {
//                        Sprite asteroid = astroidIterator.next();
//                        if (laser.overlaps(asteroid)) {
//                            astroidIterator.remove();
//                            lasterIterator.remove();
//                        }
//                    }
//                }

                // Heel cool ma kan nog cooler!
//                laserList.removeIf(
//                        laster -> asteroidList.removeIf(
//                                astroid -> laster.overlaps(astroid)
//                        )
//                );


                laserList.removeIf(laster -> asteroidList.removeIf(laster::overlaps));

                background.render(context);
                spaceship.render(context);
                for(Sprite laser: laserList)
                    laser.render(context);
                for(Sprite asteroid: asteroidList)
                    asteroid.render(context);



            }
        };
        gameloop.start();




        mainStage.show();
    }

}
