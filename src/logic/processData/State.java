package logic.processData;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class State {
    public boolean isFinal = false;
    public boolean isInitial = false;

    public boolean hasLoop = false;

    public String name;

    public double centerX;
    public double centerY;

    public AnchorPane UIState;

    public List<Transitions> inputTR = new ArrayList<>();
    public List<Transitions> outputTR = new ArrayList<>();
}
