import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

public class ForecastDetail {
    Scene detailScene;
    VBox detailBox;
    Button backButton;

    public ForecastDetail(ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene) {
        detailBox = new VBox(20);
        detailBox.setAlignment(Pos.CENTER);

        // back button
        backButton = new Button("Back To 3-Day Forecast");
        backButton.setOnAction(e -> primaryStage.setScene(forecastScene));

        // add all elements to the VBox
        detailBox.getChildren().addAll(
                backButton
        );

        detailScene = new Scene(detailBox, 375, 750);
    }

    public Scene getScene() {
        return detailScene;
    }
}
