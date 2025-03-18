import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

public class ForecastDetail extends JavaFX {
    Scene detailScene;
    VBox detailBox;
    Button backButton;
    boolean isDay;
    Label dayName;
    protected VBox DayOrNight (int day, ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene){
        isDay = forecast.get(day).isDaytime;
        dayName = new Label(forecast.get(day).name);
        if()
        // back button
        backButton = new Button("Back To 3-Day Forecast");
        backButton.setOnAction(e -> primaryStage.setScene(forecastScene));
        backButton.setStyle("-fx-background-color: rgb(169, 169, 169); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-font-size: 15px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5,0,1,1 );");

        // set up background
        time = forecast.get(day).startTime;
        int hour = time.getHours();
        setBackgroundAndTitleColor(hour);

        // add background behind the title
        titlePane = new StackPane();
        titlePane.getChildren().addAll(background, titleLabel);
        titlePane.setAlignment(Pos.CENTER);

        // temperature
        temperature = new Label();
        tempFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 75);
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
        weatherFont = Font.font("San Francisco", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20);
        weather.setText(forecast.get(day).shortForecast);
        weather.setFont(weatherFont);

        // icon
        weatherIcon = new ImageView(new Image(setWeatherIcon(forecast.get(day).shortForecast)));
        weatherIcon.setFitWidth(50); // resize the icon
        weatherIcon.setPreserveRatio(true);
        tempBox = new VBox(10);
        tempBox.getChildren().addAll(degreePane, weather, weatherIcon);
        tempBox.setAlignment(Pos.CENTER);
        // create the main VBox with title, temperature, and weather
        // add all elements to the VBox
        tempAndButton = new VBox(50);
        tempAndButton.getChildren().addAll(tempBox);
        tempAndButton.setAlignment(Pos.CENTER);
        tempAndButton.setPadding(new Insets(20, 20, 20, 20));
        tempAndButton.setStyle("-fx-background-color: white; " +
                "-fx-background-radius: 20 20 0 0; ");
        tempAndButton.setMaxHeight(250);
        tempAndButton.setMinHeight(250);
        return tempAndButton;
    }
    public ForecastDetail(ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene, int day) {

        detailBox = new VBox(20);
        detailBox.setAlignment(Pos.CENTER);
        titleFont = Font.font("San Francisco", FontWeight.BOLD, FontPosture.REGULAR, 25);
        titleLabel = new Label(forecast.get(day).name);
        titleLabel.setFont(titleFont);

        detailBox.getChildren().addAll(
                titleLabel,
                DayOrNight (day, forecast, primaryStage, forecastScene),
                DayOrNight (day+1, forecast, primaryStage, forecastScene),
                backButton
        );

        detailScene = new Scene(detailBox, 390, 750);
    }

    public Scene getScene() {
        return detailScene;
    }
}
