package controllers;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class StageController {
    public static Stage parentStage;
    private double xOffset = 0;
    private double yOffset = 0;
    
    public void createNewStage(Stage parentStage, String fxmlDocument, String title, boolean isModal, boolean closeParentStage) throws IOException {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource(fxmlDocument));
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        stage.setTitle(title);
        stage.setScene(scene);
        if (isModal) {
            stage.initModality(Modality.APPLICATION_MODAL);
          this.parentStage = parentStage;
            stage.showAndWait();
            }
        else if (closeParentStage) {
            parentStage.close();
            stage.show();
        }
        else {
            stage.show();
        }
        
        
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }

        });
        
        
        
        
    }
}
