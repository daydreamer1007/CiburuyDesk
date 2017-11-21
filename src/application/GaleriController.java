package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Screen;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class GaleriController {
    @FXML
    Pane galeriPane;

    @FXML
    public void initialize(){
        ScrollPane galeriScroll = new ScrollPane();
        galeriScroll.setId("galeriScroll");
        TilePane tile = new TilePane();

        galeriPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        galeriPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        galeriScroll.prefWidthProperty().bind(galeriPane.prefWidthProperty());
        galeriScroll.prefHeightProperty().bind(galeriPane.prefHeightProperty());
        galeriScroll.setStyle("-fx-background-color: FFD700");
        //galeriScroll.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.8);
        //galeriScroll.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.8*0.75);

        tile.prefHeightProperty().bind(galeriScroll.prefHeightProperty());
        tile.prefWidthProperty().bind(galeriScroll.prefWidthProperty());
        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setVgap(15);
        tile.setHgap(15);
        tile.setPrefColumns(4);
        tile.setStyle("-fx-background-color: FFD700");

        String path = "res/galeri/";

        File folder = new File(path);
        List<File> listOfFiles = Arrays.asList(folder.listFiles());

        DoubleProperty bindWidth = new SimpleDoubleProperty();
        DoubleProperty bindHeight = new SimpleDoubleProperty();

        bindWidth.bind(tile.prefWidthProperty().subtract(80).divide(3));
        bindHeight.bind(galeriScroll.prefHeightProperty().multiply(bindWidth).divide(galeriScroll.prefWidthProperty()));

        for(File file : listOfFiles){
            ImageView imageView = createImageView(listOfFiles, file);
            BorderPane imagePane = new BorderPane();

            imagePane.prefWidthProperty().bind(bindWidth);
            imagePane.prefHeightProperty().bind(bindHeight);
            imagePane.setStyle("-fx-background-color: FFA500");

            if(imageView.getImage().getWidth() > imageView.getImage().getHeight()){
                imageView.fitWidthProperty().bind(imagePane.prefWidthProperty().multiply(0.9));
            }
            else{
                imageView.fitHeightProperty().bind(imagePane.prefHeightProperty().multiply(0.9));
            }

            imagePane.setCenter(imageView);
            imagePane.setCursor(Cursor.HAND);

            imagePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new FullScreenViewer(listOfFiles, file);
                }
            });

            imagePane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imagePane.setStyle("-fx-background-color: FFC000");
                }
            });

            imagePane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    imagePane.setStyle("-fx-background-color: FFA500");
                }
            });

            tile.getChildren().add(imagePane);
        }

        galeriScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal
        galeriScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        galeriScroll.setFitToWidth(true);
        galeriScroll.setContent(tile);

        galeriPane.getChildren().add(galeriScroll);
    }

    private ImageView createImageView(final List<File> imageList, File imageFile) {
        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile));
            imageView = new ImageView(image);
            imageView.setFitWidth(((Screen.getPrimary().getVisualBounds().getWidth()*0.8*0.75) - 70) / 3);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
}
