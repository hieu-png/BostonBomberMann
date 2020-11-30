package uet.oop.bomberman.entities;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;

public class A extends Application {
    @Override
    public void start(Stage stage) {
        double x = 8*64;
        double y = 9*32;
        Group root = new Group();
        try {
            Image image = new Image(new FileInputStream("C:\\Users\\pc\\IdeaProjects\\BostonBomberMann\\src\\texture\\map\\7.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(y);
            imageView.setFitWidth(x);
            root.getChildren().addAll(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Creating a Text object
        Text text = new Text();

        //Setting font to the text
        text.setFont(new Font("vgafix",36));

        //setting the position of the text
        text.setX((x-36*3.2)/2);
        text.setY(y/2);

        //Setting the text to be added.
        text.setText("START");
        text.setFill(Color.RED);
        text.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                text.setFill(Color.BLUE);
            }
        });
        //Creating a Group object


        //Retrieving the observable list object
        ObservableList list = root.getChildren();

        //Setting the text object as a node to the group object
        root.getChildren().add(text);

        //Creating a scene object

        Scene scene = new Scene(root, x , y);
        scene.setFill(Color.LIGHTGRAY);
        //Setting title to the Stage
        stage.setTitle("Test");
        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
} 