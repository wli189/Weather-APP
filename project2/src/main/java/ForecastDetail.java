import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

public class ForecastDetail extends JavaFX {
    private Scene detailScene;
    private VBox root, detailBox, tempBox;
    private Button backButton;
    private boolean isDay;
    private Label dayName, temperature, weather, dayLabel, nightLabel;
    private Font titleFont, tempFont, weatherFont, degreeFont, labelFont;
    private Text degreeSymbol;
    private StackPane degreePane;
    private ImageView weatherIcon;
    private Region space;


    protected VBox DayOrNight (int day, ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene){
        // back button
        backButton = new Button("Back To 3-Day Forecast");
        backButton.setOnAction(e -> primaryStage.setScene(forecastScene));
        backButton.setStyle("-fx-background-color: rgb(169, 169, 169); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-font-size: 15px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5,0,1,1 );");

        // temperature
        temperature = new Label();
        tempFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50);
        temperature.setText(String.valueOf(forecast.get(day).temperature));
        temperature.setFont(tempFont);

        // set up the degree symbol
        degreeFont = Font.font("San Francisco", FontWeight.MEDIUM, FontPosture.REGULAR, 15);
        degreeSymbol = new Text("°" + String.valueOf(forecast.get(day).temperatureUnit));
        degreeSymbol.setFont(degreeFont);

        // align the degree value and symbol
        degreePane = new StackPane();
        degreePane.setAlignment(Pos.CENTER);
        degreePane.setMaxSize(110, 40);
        degreePane.setMinHeight(40);
        StackPane.setAlignment(degreeSymbol, Pos.TOP_RIGHT);
        StackPane.setAlignment(temperature, Pos.CENTER);
        degreePane.getChildren().addAll(temperature, degreeSymbol);

        // weather
        weather = new Label();
        weatherFont = Font.font("San Francisco", FontWeight.MEDIUM, FontPosture.REGULAR, 15);
        weather.setText(forecast.get(day).detailedForecast);
        weather.setFont(weatherFont);
        weather.setWrapText(true);
        weather.setTextAlignment(TextAlignment.CENTER);
        weather.setMaxWidth(200);

        // icon
        weatherIcon = new ImageView(new Image(setWeatherIcon(forecast.get(day).shortForecast)));
        weatherIcon.setFitWidth(50); // resize the icon
        weatherIcon.setPreserveRatio(true);
        tempBox = new VBox(10);
        tempBox.getChildren().addAll(degreePane, weather, weatherIcon);
        tempBox.setAlignment(Pos.CENTER);

        return tempBox;
    }
    public ForecastDetail(ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene, int day) {
        labelFont = Font.font("San Francisco", FontWeight.BOLD, FontPosture.REGULAR, 15);
        dayLabel = new Label("Day");
        dayLabel.setFont(labelFont);
        nightLabel = new Label("Night");
        nightLabel.setFont(labelFont);

        detailBox = new VBox(20);
        detailBox.setAlignment(Pos.CENTER);

        space = new Region();
        space.setPrefHeight(100);

        isDay = forecast.get(0).isDaytime;
        if(!isDay && day == 0) {
            detailBox.getChildren().addAll(
                    nightLabel,
                    DayOrNight (day, forecast, primaryStage, forecastScene),
                    backButton);
        } else {
            detailBox.getChildren().addAll(
                    dayLabel,
                    DayOrNight(day, forecast, primaryStage, forecastScene),
                    space,
                    nightLabel,
                    DayOrNight(day + 1, forecast, primaryStage, forecastScene),
                    backButton
            );
        }
        detailBox.setMinSize(375, 500);
        detailBox.setStyle("-fx-background-color: rgb(230, 230, 250); -fx-background-radius: 20px;");
        detailBox.setPadding(new Insets(20,0,20,0));

        root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        titleFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25);
        dayName = new Label(forecast.get(day).name);
        dayName.setFont(titleFont);

        root.getChildren().addAll(
                dayName,
                detailBox,
                backButton
        );

        detailScene = new Scene(root, 390, 750);
    }

    public Scene getScene() {
        return detailScene;
    }
}
