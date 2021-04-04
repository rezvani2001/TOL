package GUI.Shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;


public class RegularArrow {
    public String label;

    public String name;

    public double startX;
    public double startY;

    public double endX;
    public double endY;

    public AnchorPane pane;

    public RegularArrow(String label, String name, double startX, double startY,
                        double endX, double endY) {

        this.label = label;
        this.name = name;
        this.startX = startX * 5;
        this.startY = startY * 5;
        this.endX = endX * 5;
        this.endY = endY * 5;

        this.pane = regularArray();
    }


    public AnchorPane regularArray() {
        AnchorPane pane = new AnchorPane();
        Path path = new Path();


        Label label = new Label(this.label);

        pane.getChildren().addAll(label);

        if (startX < endX && startY < endY) {
            MoveTo moveTo = new MoveTo(-30, 30);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 60);
            first.setY((endY - startY));
            first.setControlX(-30);
            first.setControlY((endY - startY));

            LineTo firstLine = new LineTo((endX - startX) - 63, (endY - startY) + 4);

            MoveTo moveForSecLine = new MoveTo((endX - startX) - 60, (endY - startY));

            LineTo secLine = new LineTo((endX - startX) - 63, (endY - startY) - 4);

            path.getElements().addAll(moveTo, first, firstLine, moveForSecLine, secLine);

            AnchorPane.setLeftAnchor(label, (endX - startX) / 4 - 20);
            AnchorPane.setTopAnchor(label, (endY - startY) * 0.75);

        } else if (startX > endX && startY < endY) {
            MoveTo moveTo = new MoveTo(0, 0);

            if (startX - endX >= 60 ) {

            } else {
                QuadCurveTo first = new QuadCurveTo();
                first.setY((endY - startY) / 2);
                first.setX((endX - startX) / 2);
                first.setControlY(0);
                first.setControlX((endY - startY) / 2);

                QuadCurveTo second = new QuadCurveTo();
                second.setY((endY - startY));
                second.setX(endX - startX);
                second.setControlY((endY - startY));
                second.setControlX(0);

                path.getElements().addAll(moveTo , first , second);
            }



//            QuadCurveTo first = new QuadCurveTo();
//            first.setX((startX - endX) - 30);
//            first.setY((startY - endY) + 30);
//            first.setControlX((startX - endX) - 30);
//            first.setControlY(0);
//
//            LineTo firstLine = new LineTo(3, -4);
//
//            LineTo secLine = new LineTo(3, 4);
//
//
//            path.getElements().addAll(moveTo, first, moveTo, firstLine, moveTo, secLine);


            AnchorPane.setLeftAnchor(label, (startX - endX) / 2 + 5);
            AnchorPane.setTopAnchor(label, (startY - endY) / 4);

        } else if (startX < endX && startY > endY) {
            MoveTo moveTo = new MoveTo(0, 0);

            QuadCurveTo first = new QuadCurveTo();
            first.setX((endX - startX) - 60);
            first.setY((endY - startY));
            first.setControlX(0);
            first.setControlY(endY - startY);

            LineTo firstLine = new LineTo((endX - startX) - 63, (endY - startY) - 4);

            MoveTo moveForSecLine = new MoveTo((endX - startX) - 60, (endY - startY));


            LineTo secLine = new LineTo((endX - startX) - 63, (endY - startY) + 4);


            path.getElements().addAll(moveTo, first, firstLine, moveForSecLine, secLine);

            AnchorPane.setLeftAnchor(label, (startX - endX) / 2 + 5);
            AnchorPane.setTopAnchor(label, (startY - endY) / 4);

        }
        path.setStrokeWidth(2);
        path.setStroke(Color.BLACK);
        pane.getChildren().addAll(path);

        return pane;
    }
}
