package GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.processData.State;

public class CircleEdit extends Stage {
    // the clicked circle state and the main pane of this stage
    private final State state;
    private final VBox mainPane;

    // TextArea for centerXPart of this stage
    TextArea inputCenterX;

    // TextArea for centerYPart of this stage
    TextArea inputCenterY;

    // TextArea for name of this stage
    TextArea inputName;

    // CheckBox for isFinal part of this stage
    CheckBox isFinalState;

    // CheckBox for isInitial part of this stage
    CheckBox isInitialState;

    public CircleEdit(State state) {
        this.state = state;
        this.mainPane = new VBox(8);
        this.makeScene();
        this.setTitle(String.format("Edit State %s", this.state.name));
        this.show();
    }

    private void makeScene() {
        this.makeMainBody();
        this.makeSaveButton();
        Scene scene = new Scene(this.mainPane, 400, 500);
        this.setScene(scene);
    }

    private void makeSaveButton() {
        Button saveButton = new Button("Apply");
        this.mainPane.getChildren().add(saveButton);
    }

    private void makeMainBody() {
        this.makeName();
        this.makeCenterX();
        this.makeCenterY();
        this.makeIsFinal();
        this.makeIsInitial();
    }

    private void makeCenterX() {
        Label titleCenterX = new Label("CenterX :");
        this.inputCenterX = new TextArea(String.valueOf(this.state.centerX));
        this.mainPane.getChildren().add(this.addNewPart(titleCenterX, this.inputCenterX));
    }

    private void makeCenterY() {
        Label titleCenterY = new Label("CenterY :");
        this.inputCenterY = new TextArea(String.valueOf(this.state.centerY));
        this.mainPane.getChildren().add(this.addNewPart(titleCenterY, this.inputCenterY));
    }

    private void makeName() {
        Label titleName = new Label("Name :");
        this.inputName = new TextArea(this.state.name);
        this.mainPane.getChildren().add(this.addNewPart(titleName, this.inputName));
    }

    private void makeIsFinal() {
        this.isFinalState = new CheckBox("IsFinal");
        this.isFinalState.setSelected(this.state.isFinal);
        this.mainPane.getChildren().add(this.addNewPart(this.isFinalState));
    }

    private void makeIsInitial() {
        this.isInitialState = new CheckBox("IsInitial");
        this.isInitialState.setSelected(this.state.isInitial);
        this.mainPane.getChildren().add(this.addNewPart(this.isInitialState));
    }

    private HBox addNewPart(Label label, TextArea textInput) {
        HBox pane = new HBox(10);
        pane.getChildren().addAll(label, textInput);
        return pane;
    }

    private HBox addNewPart(CheckBox checkBox) {
        HBox pane = new HBox(10);
        pane.getChildren().add(checkBox);
        return pane;
    }
}
