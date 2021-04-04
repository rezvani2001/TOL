package GUI;

import GUI.Shapes.ArrowToItSelf;
import GUI.Shapes.RegularArrow;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import logic.*;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.processData.State;
import logic.processData.Transitions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


public class Draw extends Application {
    @Override
    public void start(Stage primaryStage) {

        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();

        Button selectInput = new Button("Select File");
        selectInput.setId("SelectInputFileButton");
        selectInput.setTooltip(new Tooltip("Select Input XML File"));

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        AtomicBoolean inputIsOpen = new AtomicBoolean(false);

        buttonBox.getChildren().addAll(selectInput);
        borderPane.setTop(buttonBox);

        FileChooser filePath = new FileChooser();
        filePath.setTitle("select file");

        BorderPane.setMargin(buttonBox, new Insets(20));

        AtomicReference<AnchorPane> pane = new AtomicReference<>(new AnchorPane());
        pane.get().setId("WorkSpacePane");

        BorderPane.setMargin(pane.get(), new Insets(0, 20, 20, 20));
        borderPane.setCenter(pane.get());


        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        selectInput.setOnAction(event -> {
            if (!inputIsOpen.get()) {
                inputIsOpen.set(true);

                File selectedFile = filePath.showOpenDialog(stage);

                inputIsOpen.set(false);

                if (selectedFile != null) {

                    if (selectedFile.getPath().contains(".xml")) {
                        Thread thread = new Thread(() -> Main.main(selectedFile));

                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        pane.set(new AnchorPane());
                        pane.get().setId("WorkSpacePane");
                        borderPane.setCenter(pane.get());

                        BorderPane.setMargin(pane.get(), new Insets(0, 20, 20, 20));
                        borderPane.setCenter(pane.get());

                        UiElements elements = new UiElements();


                        thread = new Thread(() -> {
                            for (State state : Main.automatas.states) {
                                AnchorPane circlePane = new AnchorPane();

                                Circle circle = new Circle(state.centerX, state.centerY, 26);

                                if (state.isFinal) {
                                    circle.setStyle("-fx-fill: lightgray; -fx-stroke: red; -fx-stroke-width: 4");
                                } else if (state.isInitial) {
                                    circle.setStyle("-fx-fill: lightgray; -fx-stroke: blue; -fx-stroke-width: 3");
                                    circle.setRadius(27);
                                } else {
                                    circle.setStyle("-fx-fill: lightgray; -fx-stroke: black; -fx-stroke-width: 2");
                                    circle.setRadius(28);
                                }

                                Label label = new Label(state.name);
                                circlePane.getChildren().addAll(circle, label);

                                AnchorPane.setLeftAnchor(circle, 0.0);
                                AnchorPane.setTopAnchor(circle, 0.0);

                                AnchorPane.setTopAnchor(label, 19.5);
                                AnchorPane.setLeftAnchor(label, 27 - label.getText().length() * 2.5);

                                Platform.runLater(() -> {
                                    pane.get().getChildren().addAll(circlePane);

                                    AnchorPane.setTopAnchor(circlePane, state.centerY * 5);
                                    AnchorPane.setLeftAnchor(circlePane, state.centerX * 5);
                                });

                                elements.circles.add(circlePane);
                            }


                            for (Transitions transition : Main.automatas.transitions) {
                                AnchorPane transitionPane;

                                if (transition.isLoop) {
                                    transitionPane = new ArrowToItSelf(transition.start.centerX,
                                            transition.start.centerY, transition.label, transition.name).pane;

                                    Platform.runLater(() -> {
                                        pane.get().getChildren().addAll(transitionPane);

                                        AnchorPane.setLeftAnchor(transitionPane, transition.start.centerX * 5);
                                        AnchorPane.setTopAnchor(transitionPane, transition.start.centerY * 5 - 50);
                                    });
                                } else {
                                    transitionPane = new RegularArrow(
                                            transition.label, transition.name,
                                            transition.start.centerX, transition.start.centerY,
                                            transition.end.centerX, transition.end.centerY
                                    ).pane;


                                    Platform.runLater(() -> {
                                        pane.get().getChildren().addAll(transitionPane);

                                        if (transition.start.centerY < transition.end.centerY &&
                                                transition.start.centerX > transition.end.centerX) {

                                            AnchorPane.setLeftAnchor(transitionPane, transition.end.centerX * 5 + 60);
                                            AnchorPane.setTopAnchor(transitionPane, transition.end.centerY * 5 + 30);

                                        } else {
                                            AnchorPane.setLeftAnchor(transitionPane, transition.start.centerX * 5 + 60);
                                            AnchorPane.setTopAnchor(transitionPane, transition.start.centerY * 5 + 30);
                                        }
                                    });
                                }
                                elements.transitions.add(transitionPane);
                            }
                        });
                        thread.start();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "this file format is not supported").showAndWait();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "file note found").showAndWait();
                }
            } else {
                stage.requestFocus();
            }
        });

        String cssFilePath = "GUI/CssFiles/MainPageStyle.css";
        scene.getStylesheets().add(cssFilePath);
        primaryStage.setTitle("TOL Project");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}


class UiElements {
    List<AnchorPane> circles = new ArrayList<>();
    List<AnchorPane> transitions = new ArrayList<>();
}