package GUI.Shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;


public class RegularArray {
    public String label;

    public String name;

    public double startX;
    public double startY;

    public double endX;
    public double endY;

    public AnchorPane pane;

    public RegularArray(String label, String name, double startX, double startY,
                        double endX, double endY) {

        this.label = label;
        this.name = name;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        this.pane = regularArray();
    }


    public AnchorPane regularArray() {
        AnchorPane pane = new AnchorPane();
        Path path = new Path();

        MoveTo moveTo = new MoveTo(0, 0);

        path.getElements().addAll(moveTo);

        Label label = new Label(this.label);

        pane.getChildren().addAll(label);

        AnchorPane.setLeftAnchor(label, Math.abs(endX - startX) / 2 * 5 - 35);

        if (startX < endX) {
            QuadCurveTo first = new QuadCurveTo(((endX - startX) / 2) * 5 - 30, ((endY - startY) / 2) * 5 - 20,
                    (endX - startX) * 5 - 60, (endY - startY) * 5);

            LineTo firstLine = new LineTo((endX - startX) * 5 - 66, (endY - startY) * 5);

            MoveTo moveForSecLine = new MoveTo((endX - startX) * 5 - 60, (endY - startY) * 5);

            LineTo secLine = new LineTo((endX - startX) * 5 - 61, (endY - startY) * 5 - 7);

            path.getElements().addAll(first, firstLine, moveForSecLine, secLine);

            AnchorPane.setTopAnchor(label, (endY - startY) * 5 - 30);

        } else {
            QuadCurveTo first;

            if (endY > startY) {
                first = new QuadCurveTo(((startX - endX) / 2) * 5 - 30, ((startY - endY) / 2) * 5 - 20,
                        (startX - endX) * 5 - 60, (startY - endY) * 5);
                MoveTo move = new MoveTo(0, 0);

                LineTo firstLine = new LineTo(2, -9);

                MoveTo moveForSecLine = new MoveTo(0, 0);

                LineTo secLine = new LineTo(5, -3);

                path.getElements().addAll(first, move, firstLine, moveForSecLine, secLine);
                AnchorPane.setTopAnchor(label, (startY - endY) * 5 + 60);

            } else {
                first = new QuadCurveTo(((startX - endX) / 2) * 5 - 30, ((endY - startY) / 2) * 5 + 20,
                        (startX - endX) * 5 - 60, (endY - startY) * 5);

                MoveTo move = new MoveTo(0, 0);

                LineTo firstLine = new LineTo(6, 0);

                MoveTo moveForSecLine = new MoveTo(0, 0);

                LineTo secLine = new LineTo(1, 7);

                path.getElements().addAll(first, move, firstLine, moveForSecLine, secLine);
                AnchorPane.setTopAnchor(label, (endY - startY) * 5 + 10);

            }


        }


        pane.getChildren().addAll(path);


        return pane;
    }
}
