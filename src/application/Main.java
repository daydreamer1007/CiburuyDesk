package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Mandala");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.sizeToScene();
        primaryStage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.5);
        primaryStage.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.5);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
