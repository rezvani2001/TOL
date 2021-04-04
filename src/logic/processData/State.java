package logic.processData;


import javafx.scene.shape.Circle;

public class State {
    public boolean isFinal = false;
    public boolean isInitial = false;

    public boolean hasLoop = false;

    public int inputTR = 0;
    public int outputTR = 0;

    public String name;

    public double centerX;
    public double centerY;

}
