package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class Controller {
    @FXML
    Pane mainPane;

    @FXML
    Pane sidePane;

    @FXML
    Label titleLabel;

    @FXML
    Pane welcome;

    @FXML
    public Pane galeri;

    @FXML
    Pane naskah;

    @FXML
    Pane penelitian;

    @FXML
    Pane permainan;

    @FXML
    Pane about;

    @FXML
    Pane pencarian;

    @FXML
    BorderPane content;

    @FXML
    Label welcomeText;

    @FXML
    Label galeriText;

    @FXML
    Label naskahText;

    @FXML
    Label penelitianText;

    @FXML
    Label permainanText;

    @FXML
    Label pencarianText;

    @FXML
    Label aboutText;

    public static String dbms = "mysql";
    public static String address = "localhost";
    public static String port = "3306";
    public static String database = "mandala";
    public static String user = "root";
    public static String pass = "12345";

    public static String loggedName;
    public static String loggedStatus;
    public static Timeline timeline;

    @FXML
    public void initialize(){
        timeline = new Timeline(
            new KeyFrame(Duration.minutes(1.0), e -> logout())
        );

        DoubleProperty titleX = new SimpleDoubleProperty(10.0);
        DoubleProperty titleY = new SimpleDoubleProperty(14.4);

        titleX.bind(sidePane.widthProperty().subtract(titleLabel.widthProperty()).divide(2));
        titleY.bind(sidePane.heightProperty().divide(50));

        titleLabel.layoutXProperty().bind(titleX);
        titleLabel.layoutYProperty().bind(titleY);

        galeri.setDisable(true);
        naskah.setDisable(true);
        penelitian.setDisable(true);
        permainan.setDisable(true);
        pencarian.setDisable(true);
        about.setDisable(true);

        welcome.setCursor(Cursor.HAND);
        galeri.setCursor(Cursor.HAND);
        naskah.setCursor(Cursor.HAND);
        penelitian.setCursor(Cursor.HAND);
        permainan.setCursor(Cursor.HAND);
        pencarian.setCursor(Cursor.HAND);
        about.setCursor(Cursor.HAND);

        sidePane.widthProperty().addListener(event -> resizeChange(sidePane.getChildren()));
        sidePane.heightProperty().addListener(event -> resizeChange(sidePane.getChildren()));
        welcome.widthProperty().addListener(event -> resizeChange(welcome.getChildren()));
        welcome.heightProperty().addListener(event -> resizeChange(welcome.getChildren()));
        galeri.widthProperty().addListener(event -> resizeChange(galeri.getChildren()));
        galeri.heightProperty().addListener(event -> resizeChange(galeri.getChildren()));
        naskah.widthProperty().addListener(event -> resizeChange(naskah.getChildren()));
        naskah.heightProperty().addListener(event -> resizeChange(naskah.getChildren()));
        penelitian.widthProperty().addListener(event -> resizeChange(penelitian.getChildren()));
        penelitian.heightProperty().addListener(event -> resizeChange(penelitian.getChildren()));
        permainan.widthProperty().addListener(event -> resizeChange(permainan.getChildren()));
        permainan.heightProperty().addListener(event -> resizeChange(permainan.getChildren()));
        pencarian.widthProperty().addListener(event -> resizeChange(pencarian.getChildren()));
        pencarian.heightProperty().addListener(event -> resizeChange(pencarian.getChildren()));
        about.widthProperty().addListener(event -> resizeChange(about.getChildren()));
        about.heightProperty().addListener(event -> resizeChange(about.getChildren()));

        welcomeText.fontProperty().addListener(event -> labelResize(welcomeText.getGraphic()));
        galeriText.fontProperty().addListener(event -> labelResize(galeriText.getGraphic()));
        naskahText.fontProperty().addListener(event -> labelResize(naskahText.getGraphic()));
        penelitianText.fontProperty().addListener(event -> labelResize(penelitianText.getGraphic()));
        permainanText.fontProperty().addListener(event -> labelResize(permainanText.getGraphic()));
        pencarianText.fontProperty().addListener(event -> labelResize(pencarianText.getGraphic()));
        aboutText.fontProperty().addListener(event -> labelResize(aboutText.getGraphic()));

        loadWelcome();
    }

    private void loadWelcome(){
        content.getChildren().clear();

        if(loggedName == null || loggedName.isEmpty()){
           try {
                content.getChildren().add(FXMLLoader.load(getClass().getResource("login.fxml")));
                ((Pane)content.lookup("#loginPane")).prefHeightProperty().bind(content.prefHeightProperty());
                ((Pane)content.lookup("#loginPane")).prefWidthProperty().bind(content.prefWidthProperty());

                ((Button)content.lookup("#loginPane").lookup("#login")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Login Gagal");
                            alert.setHeaderText(null);

                            Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
                            PreparedStatement statement = conn.prepareCall("SELECT  * FROM user WHERE username='" + ((TextField)content.lookup("#loginPane").lookup("#username")).getText() + "'");
                            statement.execute();
                            ResultSet result = statement.getResultSet();

                            if(result.next()){
                                if(!result.getString("password").equals(((PasswordField)content.lookup("#loginPane").lookup("#password")).getText())){
                                    alert.setContentText("Password salah!");
                                    alert.showAndWait();
                                }
                                else{
                                    loggedName = ((TextField)content.lookup("#loginPane").lookup("#username")).getText();
                                    loggedStatus = result.getString("status");

                                    alert.setAlertType(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Login Berhasil");
                                    alert.setContentText("Berhasil login sebagai " + Controller.loggedName + "!");
                                    alert.showAndWait();

                                    galeri.setDisable(false);
                                    naskah.setDisable(false);
                                    penelitian.setDisable(false);
                                    permainan.setDisable(false);
                                    pencarian.setDisable(false);
                                    about.setDisable(false);

                                    loadWelcome();
                                    timeline.play();
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

                ((Button)content.lookup("#loginPane").lookup("#guest")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        content.getChildren().clear();

                        try{
                            content.getChildren().add(FXMLLoader.load(getClass().getResource("guest.fxml")));
                            ((Pane)content.lookup("#guestPane")).prefHeightProperty().bind(content.prefHeightProperty());
                            ((Pane)content.lookup("#guestPane")).prefWidthProperty().bind(content.prefWidthProperty());

                            ((Button)content.lookup("#guestPane").lookup("#batal")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    loadWelcome();
                                }
                            });

                            ((Button)content.lookup("#guestPane").lookup("#masuk")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    String lNama = ((TextField)content.lookup("#guestPane").lookup("#namaPane").lookup("#nama")).getText();
                                    String lGender = ((RadioButton)content.lookup("#guestPane").lookup("#genderPane").lookup("#genderL")).isSelected() ? ((RadioButton)content.lookup("#guestPane").lookup("#genderPane").lookup("#genderL")).getUserData().toString() : ((RadioButton)content.lookup("#guestPane").lookup("#genderPane").lookup("#genderP")).getUserData().toString();
                                    int lUmur = Integer.parseInt(((TextField)content.lookup("#guestPane").lookup("#umurPane").lookup("#umur")).getText());
                                    String lAlamat = ((TextField)content.lookup("#guestPane").lookup("#alamatPane").lookup("#alamat")).getText();

                                    try {
                                        Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
                                        PreparedStatement statement = conn.prepareCall("INSERT INTO log(nama,jenis_kelamin,umur,alamat,start) VALUES('" + lNama + "','" + lGender + "'," + lUmur + ",'" + lAlamat + "',NOW())");
                                        int count = statement.executeUpdate();

                                        if(count > 0){
                                            loggedName = lNama;
                                            loggedStatus = "user";

                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Berhasi");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Data berhasil dimasukkan!");
                                            alert.showAndWait();

                                            galeri.setDisable(false);
                                            naskah.setDisable(false);
                                            penelitian.setDisable(false);
                                            permainan.setDisable(false);
                                            pencarian.setDisable(false);
                                            about.setDisable(false);

                                            loadWelcome();
                                            timeline.play();
                                        }

                                        conn.close();
                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else{
            if(loggedStatus.equals("admin")){
                try {
                    content.getChildren().add(FXMLLoader.load(getClass().getResource("admin.fxml")));
                    ((Pane) content.lookup("#adminPane")).prefHeightProperty().bind(content.prefHeightProperty());
                    ((Pane) content.lookup("#adminPane")).prefWidthProperty().bind(content.prefWidthProperty());

                    ((Button)content.lookup("#adminPane").lookup("#logout")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            logout();
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            else{
                try{
                    content.getChildren().add(FXMLLoader.load(getClass().getResource("welcome.fxml")));
                    ((Pane)content.lookup("#welcomePane")).prefHeightProperty().bind(content.prefHeightProperty());
                    ((Pane)content.lookup("#welcomePane")).prefWidthProperty().bind(content.prefWidthProperty());

                    ((Button)content.lookup("#welcomePane").lookup("#logout")).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            logout();
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void resizeChange(List<Node> list){
        Double sizeX = mainPane.getWidth();
        Double sizeY = mainPane.getHeight();
        Double fontSize = Math.hypot(sizeX, sizeY) / 80;

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getClass().equals(Label.class)){
                ((Label)list.get(i)).setFont(Font.font("System", FontWeight.BOLD, fontSize));
            }
        }
    }

    private void labelResize(Node graphic){
        Double graphX = sidePane.getWidth();
        Double graphY = sidePane.getHeight();
        Double graphSize = Math.hypot(graphX, graphY) / 26;

        ((ImageView)graphic).setFitWidth(graphSize);
    }

    private void logout(){
        boolean loggedOut = true;

        if(loggedStatus.equals("user")){
            try {
                loggedOut = false;
                Connection conn = DriverManager.getConnection(("jdbc:" + Controller.dbms + "://" + Controller.address + ":" + Controller.port + "/" + Controller.database + "?verifyServerCertificate=false&useSSL=true"), Controller.user, Controller.pass);
                PreparedStatement statement = conn.prepareCall("UPDATE log SET end=NOW() WHERE end IS NULL ");

                if (statement.executeUpdate() > 0) {
                    loggedOut = true;
                }

                conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }

        if(loggedOut) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Berhasi");
            alert.setHeaderText(null);
            alert.setContentText("Logout berhasil!");
            if(timeline.getCurrentTime().equals(Duration.ZERO)) {
                alert.showAndWait();
            }

            loggedName = null;
            loggedStatus = null;

            loadWelcome();
        }

        welcome.setDisable(true);
        welcome.setStyle("-fx-background-color: FFD700");
        galeri.setDisable(true);
        galeri.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(true);
        naskah.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(true);
        penelitian.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(true);
        permainan.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(true);
        pencarian.setStyle("-fx-background-color: FFA500");
        about.setDisable(true);
        about.setStyle("-fx-background-color: FFA500");

        timeline.stop();
    }

    @FXML
    public void welcomeClicked(){
        welcome.setStyle("-fx-background-color: FFD700");
        welcome.setDisable(true);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        loadWelcome();
        timeline.stop();
        timeline.play();
    }

    @FXML
    public void galeriClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFD700");
        galeri.setDisable(true);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        try{
            content.getChildren().add(FXMLLoader.load(getClass().getResource("Galeri.fxml")));
            ((Pane)content.lookup("#galeriPane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane)content.lookup("#galeriPane")).prefWidthProperty().bind(content.prefWidthProperty());
        }catch(IOException e){
            e.printStackTrace();
        }

        timeline.stop();
        timeline.play();
    }

    @FXML
    public void naskahClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFD700");
        naskah.setDisable(true);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        try{
            content.getChildren().add(FXMLLoader.load(getClass().getResource("Naskah.fxml")));
            ((Pane)content.lookup("#naskahPane")).prefHeightProperty().bind(content.prefHeightProperty());
            ((Pane)content.lookup("#naskahPane")).prefWidthProperty().bind(content.prefWidthProperty());
        }catch(IOException e){
            e.printStackTrace();
        }

        timeline.stop();
        timeline.play();
    }

    @FXML
    public void penelitianClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFD700");
        penelitian.setDisable(true);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        timeline.stop();
        timeline.play();
    }

    @FXML
    public void permainanClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFD700");
        permainan.setDisable(true);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        timeline.stop();
        timeline.play();
    }

    @FXML
    public void pencarianClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFD700");
        pencarian.setDisable(true);
        about.setStyle("-fx-background-color: FFA500");
        about.setDisable(false);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        timeline.stop();
        timeline.play();
    }

    @FXML
    public void aboutClicked(){
        welcome.setStyle("-fx-background-color: FFA500");
        welcome.setDisable(false);
        galeri.setStyle("-fx-background-color: FFA500");
        galeri.setDisable(false);
        naskah.setStyle("-fx-background-color: FFA500");
        naskah.setDisable(false);
        penelitian.setStyle("-fx-background-color: FFA500");
        penelitian.setDisable(false);
        permainan.setStyle("-fx-background-color: FFA500");
        permainan.setDisable(false);
        pencarian.setStyle("-fx-background-color: FFA500");
        pencarian.setDisable(false);
        about.setStyle("-fx-background-color: FFD700");
        about.setDisable(true);

        //Function that will change the pane's contents go below here
        content.getChildren().clear();

        timeline.stop();
        timeline.play();
    }


    //Functions to change the menue panes' colors when mouse is over them. Just for aestethic purpose

    @FXML
    public void welcomeMouseEnterExit(){
        if(!welcome.isDisabled()){
            if(welcome.getStyle().contains("FFA500")){
                welcome.setStyle("-fx-background-color: FFD700");
            }
            else{
                welcome.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void galeriMouseEnterExit(){
        if(!galeri.isDisabled()) {
            if (galeri.getStyle().contains("FFA500")) {
                galeri.setStyle("-fx-background-color: FFD700");
            } else {
                galeri.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void naskahMouseEnterExit(){
        if(!naskah.isDisabled()) {
            if (naskah.getStyle().contains("FFA500")) {
                naskah.setStyle("-fx-background-color: FFD700");
            } else {
                naskah.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void penelitianMouseEnterExit(){
        if(!penelitian.isDisabled()) {
            if (penelitian.getStyle().contains("FFA500")) {
                penelitian.setStyle("-fx-background-color: FFD700");
            } else {
                penelitian.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void permainanMouseEnterExit(){
        if(!permainan.isDisabled()) {
            if (permainan.getStyle().contains("FFA500")) {
                permainan.setStyle("-fx-background-color: FFD700");
            } else {
                permainan.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void pencarianMouseEnterExit(){
        if(!pencarian.isDisabled()) {
            if (pencarian.getStyle().contains("FFA500")) {
                pencarian.setStyle("-fx-background-color: FFD700");
            } else {
                pencarian.setStyle("-fx-background-color: FFA500");
            }
        }
    }

    @FXML
    public void aboutMouseEnterExit(){
        if(!about.isDisabled()) {
            if (about.getStyle().contains("FFA500")) {
                about.setStyle("-fx-background-color: FFD700");
            } else {
                about.setStyle("-fx-background-color: FFA500");
            }
        }
    }
}
