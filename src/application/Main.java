package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.sql.*;

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

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try{
                    Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
                    PreparedStatement check = conn.prepareCall("SELECT * FROM log WHERE end IS NULL");
                    PreparedStatement update = conn.prepareCall("UPDATE log SET end=NOW() WHERE end IS NULL");
                    ResultSet result;

                    check.execute();

                    result = check.getResultSet();

                    if(result.next() && update.executeUpdate() == 0){
                        event.consume();
                    }

                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
