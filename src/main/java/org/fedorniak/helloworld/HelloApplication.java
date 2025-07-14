package org.fedorniak.helloworld;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        var speedLabel = new Label("Speed: 0");
        var labelBox = new HBox(speedLabel);
        labelBox.setAlignment(Pos.CENTER);

        var rec = new Rectangle(200, 100);
        var recBox = new HBox(rec);
        recBox.setMinHeight(300);
        recBox.setAlignment(Pos.CENTER);

        var controller = new HelloController(speedLabel, rec);

        var rotateButton = new Button("Rotate!");
        rotateButton.setOnAction(event -> controller.onRotateButtonClick());
        var stopButton = new Button("Stop!");
        stopButton.setOnAction(event -> controller.onStopButtonClick());
        var resetButton = new Button("Reset");
        resetButton.setOnAction(event -> controller.onResetButtonClick());
        var buttons = new HBox(50, rotateButton, stopButton, resetButton);
        buttons.setAlignment(Pos.CENTER);

        var parent = new VBox(20, buttons, labelBox, recBox);
        parent.setAlignment(Pos.CENTER);

        Scene scene = new Scene(parent, 800, 600);
        stage.setTitle("Maksym Fedorniak Test Task");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}