package application;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FullScreenViewer {
    public int currentIndex;

    public FullScreenViewer(List<File> imageList, File imageFile){
        try {
            this.currentIndex = imageList.indexOf(imageFile);

            Pane overallContainer = new Pane();
            StackPane container = new StackPane();
            ImageView imageView = new ImageView();
            Image image = new Image(new FileInputStream(imageFile));
            BorderPane watermarkContainer = new BorderPane();
            Label watermark = new Label();
            Pane buttonPane = new Pane();
            Pane prevPane = new Pane();
            Pane nextPane = new Pane();
            ImageView prev = new ImageView("/prev.png");
            ImageView next = new ImageView("/next.png");

            imageView.setImage(image);
            imageView.setStyle("-fx-background-color: BLACK");

            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            watermarkContainer.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
            watermarkContainer.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
            watermarkContainer.setCenter(watermark);

            watermark.setText("CIBURUY DESK");
            watermark.setTextFill(Color.WHITE);
            watermark.setFont(Font.font("System", FontWeight.EXTRA_BOLD, Screen.getPrimary().getVisualBounds().getHeight() * 0.07));
            watermark.setGraphic(new ImageView("/logo.png"));
            ((ImageView) watermark.getGraphic()).setFitHeight(watermark.getFont().getSize() * 2);
            ((ImageView) watermark.getGraphic()).setPreserveRatio(true);
            ((ImageView) watermark.getGraphic()).setSmooth(true);
            watermark.setOpacity(0.5);

            if(imageView.getImage().getWidth() > imageView.getImage().getHeight()) {
                imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.9);
                container.setPrefWidth(imageView.getFitWidth());
                container.setPrefHeight(imageView.getFitWidth() / imageView.getImage().getWidth() * imageView.getImage().getHeight());
                watermark.setRotate(0);
            }
            else{
                imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
                container.setPrefHeight(imageView.getFitHeight());
                container.setPrefWidth(imageView.getFitHeight() / imageView.getImage().getHeight() * imageView.getImage().getWidth());
                watermark.setRotate(90);
            }

            container.getChildren().add(imageView);

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
            prevPane.setCursor(Cursor.HAND);
            prevPane.setVisible(false);

            nextPane.setPrefWidth(prevPane.getPrefWidth());
            nextPane.setPrefHeight(prevPane.getPrefHeight());
            nextPane.getChildren().add(next);
            nextPane.setLayoutX(nextPane.getPrefWidth() * 9);
            nextPane.setCursor(Cursor.HAND);
            nextPane.setVisible(false);

            buttonPane.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
            buttonPane.setPrefHeight(prevPane.getPrefHeight());
            buttonPane.setLayoutY(Screen.getPrimary().getVisualBounds().getHeight() - (buttonPane.getPrefHeight() * 1.1));
            buttonPane.getChildren().add(prevPane);
            buttonPane.getChildren().add(nextPane);

            overallContainer.getChildren().add(container);
            overallContainer.getChildren().add(watermarkContainer);
            overallContainer.getChildren().add(buttonPane);
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

            buttonPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(prevPane.isVisible() == false){
                        prevPane.setVisible(true);
                        nextPane.setVisible(true);
                    }
                }
            });

            buttonPane.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(prevPane.isVisible() == true){
                        prevPane.setVisible(false);
                        nextPane.setVisible(false);
                    }
                }
            });

            prevPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        imageView.setImage(new Image(new FileInputStream(imageList.get(currentIndex - 1))));

                        if(imageView.getImage().getWidth() > imageView.getImage().getHeight()) {
                            imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.9);
                            container.setPrefWidth(imageView.getFitWidth());
                            container.setPrefHeight(imageView.getFitWidth() / imageView.getImage().getWidth() * imageView.getImage().getHeight());
                            watermark.setRotate(0);
                        }
                        else{
                            imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
                            container.setPrefHeight(imageView.getFitHeight());
                            container.setPrefWidth(imageView.getFitHeight() / imageView.getImage().getHeight() * imageView.getImage().getWidth());
                            watermark.setRotate(90);
                        }

                        container.setLayoutX((Screen.getPrimary().getVisualBounds().getWidth() - container.getPrefWidth()) / 2);
                        container.setLayoutY((Screen.getPrimary().getVisualBounds().getHeight() - container.getPrefHeight()) / 2);

                        if (currentIndex == imageList.size() - 1) {
                            nextPane.setDisable(false);
                        }

                        if (currentIndex == 1) {
                            prevPane.setDisable(true);
                        }

                        currentIndex -= 1;

                        newStage.setTitle(imageList.get(currentIndex).getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            nextPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        imageView.setImage(new Image(new FileInputStream(imageList.get(currentIndex + 1))));

                        if(imageView.getImage().getWidth() > imageView.getImage().getHeight()) {
                            imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() * 0.9);
                            container.setPrefWidth(imageView.getFitWidth());
                            container.setPrefHeight(imageView.getFitWidth() / imageView.getImage().getWidth() * imageView.getImage().getHeight());
                            watermark.setRotate(0);
                        }
                        else{
                            imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() * 0.8);
                            container.setPrefHeight(imageView.getFitHeight());
                            container.setPrefWidth(imageView.getFitHeight() / imageView.getImage().getHeight() * imageView.getImage().getWidth());
                            watermark.setRotate(90);
                        }

                        container.setLayoutX((Screen.getPrimary().getVisualBounds().getWidth() - container.getPrefWidth()) / 2);
                        container.setLayoutY((Screen.getPrimary().getVisualBounds().getHeight() - container.getPrefHeight()) / 2);

                        if (currentIndex == 0) {
                            prevPane.setDisable(false);
                        }

                        if (currentIndex == imageList.size() - 2) {
                            nextPane.setDisable(true);
                        }

                        currentIndex += 1;

                        newStage.setTitle(imageList.get(currentIndex).getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
