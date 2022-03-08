package snaaaaaaaaake.snakegame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    static int speed = 5;
    static int foodColour = 0;
    static int height = 20;
    static int width = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornerSize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Direction direction = Direction.left;
    static boolean gameOver = false;
    static Random random = new Random();


    @Override
    public void start(Stage stage) throws IOException {
        try {
            newFood();
            VBox root = new VBox();
            Canvas canvas = new Canvas(width * cornerSize, height * cornerSize);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            root.getChildren().add(canvas);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(graphicsContext);
                        return;
                    }
                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(graphicsContext);
                    }
                }
            }
                    .start();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), cornerSize, cornerSize);

            //controls
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.UP) {
                    direction = Direction.up;
                }
                if (key.getCode() == KeyCode.DOWN) {
                    direction = Direction.down;
                }
                if (key.getCode() == KeyCode.LEFT) {
                    direction = Direction.left;
                }
                if (key.getCode() == KeyCode.RIGHT) {
                    direction = Direction.right;
                }
            });
            //starting snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));


            stage.setTitle("Snaaaaaaaaaake!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    tick
    public static void tick(GraphicsContext graphicsContext) {
        if (gameOver) {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("", 50));
            graphicsContext.fillText("GAME OVER", 100, 250);
            return;
        }
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }
        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y > height) {
                    gameOver = true;
                }
                break;

            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x > width) {
                    gameOver = true;
                }
                break;
        }

//    eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

//        self eating
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }
//background
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, width * cornerSize, height * cornerSize);

//        score
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.setFont(new Font("", 30));
        graphicsContext.fillText("Rezultatas:", +(speed - 6), 10, 30);


//        random food colour
        Color color = Color.WHITE;


    }


    //    snake food stuff
    public static void newFood() {
        start:
        while (true) {
            foodX = random.nextInt(width);
            foodY = random.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodColour = random.nextInt(5);
            speed++;
            break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}