import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);

        Circle blueCircle1 = new Circle(300,300, 180, Color.BLUE);
        Circle blackCircle1 = new Circle(300,300, 151, Color.BLACK);
        Circle blueCircle2 = new Circle(300,300, 150, Color.BLUE);

        Circle redCircle1 = new Circle(300,300, 120, Color.RED);
        Circle blackCircle2 = new Circle(300,300, 91, Color.BLACK);
        Circle redCircle2 = new Circle(300,300, 90, Color.RED);

        Circle yellowCircle1 = new Circle(300,300, 61, Color.YELLOW);
        Circle blackCircle3 = new Circle(300,300, 31, Color.BLACK);
        Circle yellowCircle2 = new Circle(300,300, 30, Color.YELLOW);

        Line hr = new Line(300 - 3, 300, 300 + 3, 300);
        Line vr = new Line(300, 300 - 3, 300, 300 + 3);

        root.getChildren().add(blueCircle1);
        root.getChildren().add(blackCircle1);
        root.getChildren().add(blueCircle2);
        root.getChildren().add(redCircle1);
        root.getChildren().add(blackCircle2);
        root.getChildren().add(redCircle2);
        root.getChildren().add(yellowCircle1);
        root.getChildren().add(blackCircle3);
        root.getChildren().add(yellowCircle2);
        root.getChildren().addAll(hr,vr);



        scene.setFill(Color.BLACK);

        primaryStage.setTitle("Lab1 by Matyukha Nikita kp-72");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}