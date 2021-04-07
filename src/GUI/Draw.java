package GUI;

import GUI.Shapes.ArrowToItSelf;
import GUI.Shapes.RegularArrow;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import logic.*;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.processData.State;
import logic.processData.Transitions;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;


public class Draw extends Application {
    public static AnchorPane pane = new AnchorPane();

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

        pane.setId("WorkSpacePane");

        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));
        borderPane.setCenter(pane);


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
                        pane = new AnchorPane();
                        pane.setId("WorkSpacePane");
                        borderPane.setCenter(pane);

                        BorderPane.setMargin(pane, new Insets(0, 20, 20, 20));
                        borderPane.setCenter(pane);

                        thread = new Thread(() -> {
                            for (State state : Main.automatas.states) {
<<<<<<< HEAD
                                AnchorPane circlePane = new AnchorPane();

                                circlePane.setOnMouseClicked(event1 -> {
                                    if (event1.getButton() == MouseButton.PRIMARY) {
                                        Platform.runLater(() -> new CircleEdit(state));
                                    } else if (event1.getButton() == MouseButton.SECONDARY) {
                                        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this state " + state.name + "?", ButtonType.YES, ButtonType.NO)
                                                .showAndWait()
                                                .ifPresent(buttonType -> {
                                                    if (buttonType == ButtonType.YES) {

                                                        Main.automatas.states.remove(state);

                                                        Platform.runLater(() -> {
                                                            pane.get().getChildren().remove(circlePane);
                                                        });

                                                        for (Transitions tr : state.inputTR) {
                                                            Main.automatas.transitions.remove(tr);
                                                            tr.start.outputTR.remove(tr);
                                                            Platform.runLater(() -> pane.get().getChildren().remove(tr.uiTR));
                                                        }

                                                        for (Transitions tr : state.outputTR) {
                                                            Main.automatas.transitions.remove(tr);
                                                            tr.end.inputTR.remove(tr);
                                                            Platform.runLater(() -> pane.get().getChildren().remove(tr.uiTR));
                                                        }
                                                    }
                                                });
                                    }
                                });

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

                                    AnchorPane.setTopAnchor(circlePane, state.centerY * 6);
                                    AnchorPane.setLeftAnchor(circlePane, state.centerX * 6);
                                });

                                state.UIState = circlePane;
=======
                                Platform.runLater(() -> pane.getChildren().add(state.statePane()));
>>>>>>> 5d1a3a4752be0e2c1da32ab70d9ff184d28dfec3
                            }

                            for (Transitions transition : Main.automatas.transitions) {
                                transition.transitionPane();
                            }
                        });
                        thread.start();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "this file format is not supported").showAndWait();
                    }
                }
            } else {
                stage.setAlwaysOnTop(true);
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


