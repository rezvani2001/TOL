package GUI;

import GUI.Shapes.ArrayToItSelf;
import GUI.Shapes.RegularArray;
import States.MainState;
import Transitions.MainTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import logic.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Draw extends Application {
    public static List<Circle> circles = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();

        Button selectInput = new Button("select file");
        selectInput.setTooltip(new Tooltip("select input xml file"));

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        AtomicBoolean inputIsOpen = new AtomicBoolean(false);

        buttonBox.getChildren().addAll(selectInput);
        borderPane.setTop(buttonBox);

        FileChooser filePath = new FileChooser();
        filePath.setTitle("select file");

        BorderPane.setMargin(buttonBox, new Insets(20));

        AnchorPane pane = new AnchorPane();
        borderPane.setCenter(pane);
        pane.setStyle("-fx-border-color: black ; -fx-border-width: 5");

        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));


        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);


        selectInput.setOnAction(event -> {
            if (!inputIsOpen.get()) {
                inputIsOpen.set(true);

                File selectedFile = filePath.showOpenDialog(stage);

                inputIsOpen.set(false);

                if (selectedFile != null) {

                    Thread thread = new Thread(() -> Main.main(selectedFile));

                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    while (!pane.getChildren().isEmpty()){
                        pane.getChildren().remove(0);
                    }

                    for (MainState state : Main.automata.States.state) {
                        Circle circle = new Circle(state.positionX, state.positionY, 30, Color.RED);
                        circles.add(circle);

                        circle.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2");
                        circle.setAccessibleText(state.name);

                        Label stateName = new Label(state.name);

                        pane.getChildren().addAll(circle , stateName);

                        AnchorPane.setLeftAnchor(circle, state.positionX * 5);
                        AnchorPane.setTopAnchor(circle, state.positionY * 5);

                        AnchorPane.setLeftAnchor(stateName, state.positionX * 5 + 22);
                        AnchorPane.setTopAnchor(stateName, state.positionY * 5 + 22);
                    }

                    Thread transition = new Thread(() -> {
                        Circle source;
                        Circle dest;
                        for (MainTransition transitions : Main.automata.Transitions.transition) {
                            dest = null;
                            source = null;
                            for (Circle circle : circles) {
                                if (transitions.source.equals(circle.getAccessibleText())) source = circle;
                                if (transitions.destination.equals(circle.getAccessibleText())) dest = circle;

                                if (source != null && dest != null) break;
                            }


                            if (dest == source) {
                                Circle finalSource = source;
                                Platform.runLater(() -> {
                                    ArrayToItSelf arrayToItSelf = new ArrayToItSelf(finalSource.getCenterX(),
                                            finalSource.getCenterY(), transitions.label, transitions.name);

                                    pane.getChildren().add(arrayToItSelf.pane);

                                    AnchorPane.setLeftAnchor(arrayToItSelf.pane, finalSource.getCenterX() * 5);
                                    AnchorPane.setTopAnchor(arrayToItSelf.pane, finalSource.getCenterY() * 5 - 50);
                                });
                            } else {
                                Circle finalDest = dest;
                                Circle finalSource = source;
                                Platform.runLater(() -> {
                                    RegularArray regularArray = new RegularArray(
                                            transitions.label, transitions.name,
                                            finalSource.getCenterX(), finalSource.getCenterY(),
                                            finalDest.getCenterX(), finalDest.getCenterY()
                                    );

                                    pane.getChildren().add(regularArray.pane);

                                    if (finalDest.getCenterX() >= finalSource.getCenterX()){
                                        AnchorPane.setLeftAnchor(regularArray.pane, finalSource.getCenterX() * 5 + 60);
                                        AnchorPane.setTopAnchor(regularArray.pane, finalSource.getCenterY() * 5 + 30);
                                    } else {
                                        AnchorPane.setLeftAnchor(regularArray.pane, finalDest.getCenterX() * 5 + 60);
                                        AnchorPane.setTopAnchor(regularArray.pane, finalDest.getCenterY() * 5 + 30);
                                    }
                                });
                            }
                        }
                    });

                    transition.start();
                }
            } else {
                stage.requestFocus();
            }
        });

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}