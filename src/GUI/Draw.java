package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.Main;
import logic.processData.State;

import java.io.File;
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


        Scene scene = new Scene(borderPane, 400, 400);
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

                        for (State state : Main.automatas.states) {
                            Circle circle = new Circle(state.centerX, state.centerY, 30);

                            if (state.isFinal) {
                                circle.setStyle("-fx-fill: lightgray; -fx-stroke: red; -fx-stroke-width: 4");
                            } else if (state.isInitial) {
                                circle.setStyle("-fx-fill: lightgray; -fx-stroke: blue; -fx-stroke-width: 3");
                            } else {
                                circle.setStyle("-fx-fill: lightgray; -fx-stroke: blue; -fx-stroke-width: 3");
                            }

                            Label label = new Label(state.name);
                            pane.get().getChildren().addAll(circle, label);

                            AnchorPane.setTopAnchor(circle, state.centerY * 5);
                            AnchorPane.setLeftAnchor(circle, state.centerX * 5);

                            AnchorPane.setTopAnchor(label, state.centerY * 5 - 10);
                            AnchorPane.setLeftAnchor(label, state.centerX * 5 - 10);
                        }
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
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}