package application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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

public class GaleriController {

    @FXML
    AnchorPane galeri;

    @FXML
    public void initialize(){
        ScrollPane galeriScroll = new ScrollPane();
        galeriScroll.setId("galeriScroll");
        TilePane tile = new TilePane();
        galeriScroll.setStyle("-fx-background-color: FFD700");
        galeriScroll.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight()*0.8);
        galeriScroll.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.8*0.75);
        tile.prefHeightProperty().bind(galeriScroll.prefHeightProperty());
        tile.prefWidthProperty().bind(galeriScroll.prefWidthProperty());
        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setVgap(20);
        tile.setPrefColumns(1);
        tile.setStyle("-fx-background-color: FFD700");

        String path = "res/naskah/";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for(File file : listOfFiles){
            ImageView imageView = createImageView(file);
            imageView.fitWidthProperty().bind(tile.prefWidthProperty().subtract(45.0));
            tile.getChildren().add(imageView);
        }

        galeriScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Horizontal
        galeriScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        galeriScroll.setFitToWidth(true);
        galeriScroll.setContent(tile);
        galeri.getChildren().add(galeriScroll);
    }

    private ImageView createImageView(final File imageFile) {
        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile));
            imageView = new ImageView(image);
            imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth()*0.8*0.75*0.95);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
                                BorderPane borderPane = new BorderPane();
                                StackPane container = new StackPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                Label watermark = new Label();

                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() - 30);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);

                                container.setPrefHeight(imageView.getFitWidth());
                                container.setPrefHeight(imageView.getFitHeight());

                                watermark.setText("CIBURUY DESK V2.0");
                                watermark.setTextFill(Color.WHITE);
                                watermark.setFont(Font.font("System", FontWeight.EXTRA_BOLD, Screen.getPrimary().getVisualBounds().getHeight() * 0.07));
                                watermark.setGraphic(new ImageView("/logo.png"));
                                ((ImageView)watermark.getGraphic()).setFitHeight(watermark.getFont().getSize() * 2);
                                ((ImageView)watermark.getGraphic()).setPreserveRatio(true);
                                ((ImageView)watermark.getGraphic()).setSmooth(true);
                                watermark.setOpacity(0.5);

                                container.getChildren().add(imageView);
                                container.getChildren().add(watermark);
                                borderPane.setCenter(container);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                                newStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                                newStage.setTitle(imageFile.getName());
                                Scene scene = new Scene(borderPane, Color.BLACK);
                                newStage.setScene(scene);
                                newStage.setMaximized(true);
                                newStage.setResizable(false);
                                newStage.setTitle(imageFile.getName());
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
}
