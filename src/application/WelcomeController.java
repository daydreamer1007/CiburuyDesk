package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import java.sql.*;

public class WelcomeController {
    @FXML
    Pane welcomePane;

    @FXML
    ImageView logo;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Button login;

    @FXML
    Button guest;


    @FXML
    public void initialize(){
        welcomePane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        welcomePane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        logo.setFitWidth(welcomePane.getPrefWidth() * 0.2);
        logo.fitWidthProperty().bind(welcomePane.prefWidthProperty().divide(8));
        logo.fitHeightProperty().bind(logo.fitWidthProperty());
        logo.setSmooth(true);
        logo.setPreserveRatio(true);
        logo.layoutXProperty().bind(welcomePane.prefWidthProperty().subtract(logo.fitWidthProperty()).divide(2));
        logo.layoutYProperty().bind(welcomePane.prefHeightProperty().divide(20));

        username.prefWidthProperty().bind(welcomePane.prefWidthProperty().divide(4));
        username.prefHeightProperty().bind(welcomePane.prefHeightProperty().divide(25));
        username.layoutXProperty().bind(welcomePane.prefWidthProperty().subtract(username.prefWidthProperty()).divide(2));
        username.layoutYProperty().bind(logo.layoutYProperty().add(logo.fitHeightProperty()).add(10));

        password.prefWidthProperty().bind(username.prefWidthProperty());
        password.prefHeightProperty().bind(username.prefHeightProperty());
        password.layoutXProperty().bind(username.layoutXProperty());
        password.layoutYProperty().bind(username.layoutYProperty().add(username.prefHeightProperty()).add(10));
        password.setDisable(true);

        login.setCursor(Cursor.HAND);
        login.prefWidthProperty().bind(username.prefWidthProperty());
        login.prefHeightProperty().bind(username.prefHeightProperty().multiply(1.5));
        login.layoutXProperty().bind(username.layoutXProperty());
        login.layoutYProperty().bind(password.layoutYProperty().add(password.heightProperty()).add(30));
        login.setDisable(true);

        guest.setCursor(Cursor.HAND);
        guest.prefWidthProperty().bind(username.prefWidthProperty());
        guest.prefHeightProperty().bind(login.prefHeightProperty());
        guest.layoutXProperty().bind(username.layoutXProperty());
        guest.layoutYProperty().bind(login.layoutYProperty().add(login.prefHeightProperty()).add(15));

        username.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 50){
                    password.setDisable(true);
                }
                else{
                    password.setDisable(false);
                }
            }
        });

        password.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0 || newValue.length() > 50){
                    login.setDisable(true);
                }
                else{
                    login.setDisable(false);
                }
            }
        });

        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Gagal");
                    alert.setHeaderText(null);

                    Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
                    PreparedStatement statement = conn.prepareCall("SELECT  * FROM user WHERE username='" + username.getText() + "'");
                    statement.execute();
                    ResultSet result = statement.getResultSet();

                    if(result.next()){
                        System.out.println(result.getString("password"));

                        if(!result.getString("password").equals(password.getText())){
                            alert.setContentText("Password salah!");
                            alert.showAndWait();
                        }
                        else{
                            Controller.loggedName = username.getText();
                            Controller.loggedName = result.getString("status");

                            alert.setAlertType(Alert.AlertType.INFORMATION);
                            alert.setTitle("Login Berhasil");
                            alert.setContentText("Berhasil login sebagai " + Controller.loggedName + "!");
                            alert.showAndWait();

                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#galeri")).setDisable(false);
                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#naskah")).setDisable(false);
                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#penelitian")).setDisable(false);
                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#permainan")).setDisable(false);
                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#pencarian")).setDisable(false);
                            ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#about")).setDisable(false);
                        }
                    }
                    else{
                        alert.setContentText("Username yang dimasukkan tidak ditemukan!");
                        alert.showAndWait();
                    }

                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });

        guest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Pane)welcomePane.getParent().getParent().lookup("#sidePane").lookup("#galeri")).setDisable(false);
            }
        });
    }
}
