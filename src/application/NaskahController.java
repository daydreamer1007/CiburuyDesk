package application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Screen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NaskahController {
    @FXML
    Pane naskahPane;

    @FXML
    public void initialize(){
        ScrollPane naskahScroll = new ScrollPane();
        naskahScroll.setId("naskahScroll");
        TilePane tile = new TilePane();

        naskahPane.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
        naskahPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.8 * 0.75);

        naskahScroll.prefWidthProperty().bind(naskahPane.prefWidthProperty());
        naskahScroll.prefHeightProperty().bind(naskahPane.prefHeightProperty());
        naskahScroll.setStyle("-fx-background-color: FFD700");

        tile.prefHeightProperty().bind(naskahScroll.prefHeightProperty());
        tile.prefWidthProperty().bind(naskahScroll.prefWidthProperty());
        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setVgap(20);
        tile.setPrefColumns(1);
        tile.setStyle("-fx-background-color: FFD700");

        String path = "res/naskah/";

        File folder = new File(path);
        List<File> listOfFiles = Arrays.asList(folder.listFiles());

        for(File file : listOfFiles){
            ImageView imageView = createImageView(listOfFiles, file);
            imageView.fitWidthProperty().bind(tile.prefWidthProperty().subtract(45.0));
            imageView.setCursor(Cursor.HAND);
            tile.getChildren().add(imageView);
        }

        naskahScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal
        naskahScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        naskahScroll.setFitToWidth(true);
        naskahScroll.setContent(tile);

        naskahPane.getChildren().add(naskahScroll);
    }

    private ImageView createImageView(final List<File> imageList, File imageFile) {
        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile));
            imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    new FullScreenViewer(imageList, imageFile);
                }
            });

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }

    private void FullScreenViewer(List<File> imageList, File imageFile){
        try {
            int currentIndex = imageList.indexOf(imageFile);

            Pane overallContainer = new Pane();
            StackPane container = new StackPane();
            ImageView imageView = new ImageView();
            Image image = new Image(new FileInputStream(imageFile));
            Label watermark = new Label();
            Pane prevPane = new Pane();
            Pane nextPane = new Pane();
            ImageView prev = new ImageView("/prev.png");
            ImageView next = new ImageView("/next.png");

            imageView.setImage(image);
            imageView.setStyle("-fx-background-color: BLACK");
            imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() - 30);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            watermark.setText("CIBURUY DESK");
            watermark.setTextFill(Color.WHITE);
            watermark.setFont(Font.font("System", FontWeight.EXTRA_BOLD, Screen.getPrimary().getVisualBounds().getHeight() * 0.07));
            watermark.setGraphic(new ImageView("/logo.png"));
            ((ImageView) watermark.getGraphic()).setFitHeight(watermark.getFont().getSize() * 2);
            ((ImageView) watermark.getGraphic()).setPreserveRatio(true);
            ((ImageView) watermark.getGraphic()).setSmooth(true);
            watermark.setOpacity(0.5);

            container.setPrefWidth(imageView.getFitWidth());
            container.setPrefHeight(imageView.getFitWidth() / image.getWidth() * image.getHeight());
            container.getChildren().add(imageView);
            container.getChildren().add(watermark);

            prev.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
            prev.setPreserveRatio(true);
            prev.setSmooth(true);

            next.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
            next.setPreserveRatio(true);
            next.setSmooth(true);

            prevPane.setPrefWidth(prev.getFitWidth());
            prevPane.setPrefHeight(prev.getFitWidth() * prev.getImage().getHeight() / prev.getImage().getWidth());
            prevPane.getChildren().add(prev);
            prevPane.setLayoutX(prevPane.getPrefWidth() * 5);
            prevPane.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight() - (prevPane.getPrefHeight() * 1.1));
            prevPane.setCursor(Cursor.HAND);

            nextPane.setPrefWidth(prevPane.getPrefWidth());
            nextPane.setPrefHeight(prevPane.getPrefHeight());
            nextPane.getChildren().add(next);
            nextPane.setLayoutX(nextPane.getPrefWidth() * 9);
            nextPane.setLayoutY(prevPane.getLayoutY());
            nextPane.setCursor(Cursor.HAND);

            overallContainer.getChildren().add(container);
            overallContainer.getChildren().add(prevPane);
            overallContainer.getChildren().add(nextPane);
            overallContainer.setStyle("-fx-background-color: BLACK");

            container.setLayoutX((Screen.getPrimary().getVisualBounds().getWidth() - container.getPrefWidth()) / 2);
            container.setLayoutY((Screen.getPrimary().getVisualBounds().getHeight() - container.getPrefHeight()) / 2);

            if (currentIndex == 0) {
                prevPane.setDisable(true);
            }

            if (currentIndex == imageList.size() - 1) {
                nextPane.setDisable(true);
            }

            Stage newStage = new Stage();
            newStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
            newStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            newStage.setTitle(imageFile.getName());
            Scene scene = new Scene(overallContainer, Color.BLACK);
            newStage.setScene(scene);
            newStage.setMaximized(true);
            newStage.setResizable(false);
            newStage.setTitle(imageFile.getName());
            newStage.show();

            prevPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FullScreenViewer(imageList, imageList.get(imageList.indexOf(imageFile) - 1));
                    newStage.close();
                }
            });

            nextPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FullScreenViewer(imageList, imageList.get(imageList.indexOf(imageFile) + 1));
                    newStage.close();
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
