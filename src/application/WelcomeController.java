package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

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
    Button signup;

    @FXML
    Button guest;


    @FXML
    public void initialize(){
        welcomePane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.8);
        welcomePane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.8*0.75);

        logo.setFitWidth(welcomePane.getPrefWidth()*0.2);
        logo.fitWidthProperty().bind(welcomePane.prefWidthProperty().divide(5));
        logo.fitHeightProperty().bind(logo.fitWidthProperty().divide(4).multiply(3));
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

        login.prefWidthProperty().bind(username.prefWidthProperty().multiply(0.4));
        login.prefHeightProperty().bind(username.prefHeightProperty().multiply(1.5));
        login.layoutXProperty().bind(username.layoutXProperty());
        login.layoutYProperty().bind(password.layoutYProperty().add(password.heightProperty()).add(15));

        signup.prefWidthProperty().bind(login.prefWidthProperty());
        signup.prefHeightProperty().bind(login.prefHeightProperty());
        signup.layoutXProperty().bind(username.layoutXProperty().add(username.widthProperty()).subtract(signup.widthProperty()));
        signup.layoutYProperty().bind(login.layoutYProperty());

        guest.prefWidthProperty().bind(username.widthProperty());
        guest.prefHeightProperty().bind(login.prefHeightProperty());
        guest.layoutXProperty().bind(username.layoutXProperty());
        guest.layoutYProperty().bind(login.layoutYProperty().add(login.prefHeightProperty()).add(15));

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("X     : " + guest.getPrefWidth());
                System.out.println("Y     : " + guest.getPrefHeight());
                System.out.println("Font  : " + guest.getFont().getSize());
            }
        });
    }

}