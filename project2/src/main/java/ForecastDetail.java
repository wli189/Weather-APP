import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

public class ForecastDetail {
    Scene detailScene;
    VBox detailBox;
    Button backButton;
    boolean isDay;


    public ForecastDetail(ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene, int day) {
        detailBox = new VBox(20);
        detailBox.setAlignment(Pos.CENTER);

        isDay = forecast.get(day).isDaytime;

        // back button
        backButton = new Button("Back To 3-Day Forecast");
        backButton.setOnAction(e -> primaryStage.setScene(forecastScene));
        backButton.setStyle("-fx-background-color: rgb(169, 169, 169); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-font-size: 15px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5,0,1,1 );");

        // add all elements to the VBox
        detailBox.getChildren().addAll(
                backButton
        );

        detailScene = new Scene(detailBox, 390, 750);
    }

    public Scene getScene() {
        return detailScene;
    }
}
