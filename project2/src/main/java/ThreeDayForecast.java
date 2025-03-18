import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import weather.Period;

import java.util.ArrayList;

public class ThreeDayForecast {
    VBox forecastBox, dayAndPrecipVBox, dayTempVBox, nightTempVBox;
    Scene forecastScene, detailScene;
    Font titleFont, dayFont, precipitationLabelFont, valueFont;
    Label threeDayForecast, dayName, precipitationDay, precipitationNight, dayTemp, nightTemp, precipitationLabel, dayLabel, nightLabel;
    HBox dayForecastBox, labelWithIcon;
    Button backButton, dayForecastButton;
    ImageView dayIcon, nightIcon, precipitationIcon;
    Region space1, space2;
    boolean isDay;

    public ThreeDayForecast(ArrayList<Period> forecast, Stage primaryStage, Scene mainScene) {
        titleFont = Font.font("San Francisco", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25);
        dayFont = Font.font("San Francisco", FontWeight.BOLD, FontPosture.REGULAR, 15);
        precipitationLabelFont = Font.font("San Francisco", FontWeight.MEDIUM, FontPosture.REGULAR, 15);
        valueFont = Font.font("San Francisco", FontWeight.MEDIUM, FontPosture.REGULAR, 15);
        threeDayForecast = new Label("3-Day Forecast");
        threeDayForecast.setFont(titleFont);
        threeDayForecast.setAlignment(Pos.CENTER);

        backButton = new Button("Back To Today");
        backButton.setOnAction(e -> {primaryStage.setScene(mainScene);});

        isDay = forecast.get(0).isDaytime;

        forecastBox = new VBox(30);
        forecastBox.setAlignment(Pos.CENTER);
        forecastScene = new Scene(forecastBox, 390, 750);

        if (isDay) {
            forecastBox.getChildren().addAll(
                    threeDayForecast,
                    createDayForecast(0, forecast, primaryStage, forecastScene),
                    createDayForecast(2, forecast, primaryStage, forecastScene),
                    createDayForecast(4, forecast, primaryStage, forecastScene),
                    createDayForecast(6, forecast, primaryStage, forecastScene),
                    backButton
            );
        } else {
            forecastBox.getChildren().addAll(
                    threeDayForecast,
                    createDayForecast(0, forecast, primaryStage, forecastScene),
                    createDayForecast(1, forecast, primaryStage, forecastScene),
                    createDayForecast(3, forecast, primaryStage, forecastScene),
                    createDayForecast(5, forecast, primaryStage, forecastScene),
                    backButton
            );
        }
    }

    private Button createDayForecast(int day, ArrayList<Period> forecast, Stage primaryStage, Scene forecastScene) {
        // set up more detail scene
        ForecastDetail forecastDetail = new ForecastDetail(forecast, primaryStage, forecastScene, day);
        detailScene = forecastDetail.getScene();

        dayForecastBox = new HBox();
        dayForecastBox.setAlignment(Pos.CENTER);

        space1 = new Region();
        space2 = new Region();
        space1.setMinWidth(50);
        space2.setMinWidth(30);

        isDay = forecast.get(day).isDaytime;

        dayName = new Label(forecast.get(day).name);
        dayName.setFont(dayFont);

        dayLabel = new Label("Day");
        dayLabel.setFont(dayFont);
        nightLabel = new Label("Night");
        nightLabel.setFont(dayFont);

        precipitationIcon = new ImageView(new Image("file:./assets/icons/rain.png"));
        precipitationIcon.setFitWidth(20);
        precipitationIcon.setPreserveRatio(true);
        precipitationLabel = new Label("Chance of Precipitation");
        precipitationLabel.setFont(precipitationLabelFont);
        labelWithIcon = new HBox(5, precipitationIcon, precipitationLabel);

        precipitationDay = new Label("Day:" + forecast.get(day).probabilityOfPrecipitation.value + "%");
        precipitationDay.setFont(valueFont);
        precipitationNight = new Label("Night:" + forecast.get(day + 1).probabilityOfPrecipitation.value + "%");
        precipitationNight.setFont(valueFont);

        dayTemp = new Label(forecast.get(day).temperature + "°" + String.valueOf(forecast.get(0).temperatureUnit));
        dayTemp.setFont(valueFont);
        nightTemp = new Label(forecast.get(day + 1).temperature + "°" + String.valueOf(forecast.get(0).temperatureUnit));
        nightTemp.setFont(valueFont);

        dayIcon = new ImageView(new Image(forecast.get(day).icon));
        dayIcon.setFitWidth(50);
        dayIcon.setPreserveRatio(true);
        nightIcon = new ImageView(new Image(forecast.get(day + 1).icon));
        nightIcon.setFitWidth(50);
        nightIcon.setPreserveRatio(true);

        if (!isDay) {
            precipitationDay = new Label("Day: N/A");
            precipitationDay.setFont(valueFont);
            precipitationNight = new Label("Night:" + forecast.get(day).probabilityOfPrecipitation.value + "%");
            precipitationNight.setFont(valueFont);

            dayTemp = new Label("N/A" + "°" + String.valueOf(forecast.get(0).temperatureUnit));
            dayTemp.setFont(valueFont);
            nightTemp = new Label(forecast.get(day).temperature + "°" + String.valueOf(forecast.get(0).temperatureUnit));
            nightTemp.setFont(valueFont);

            dayIcon = new ImageView();
            nightIcon = new ImageView(new Image(forecast.get(day).icon));
            nightIcon.setFitWidth(50);
            nightIcon.setPreserveRatio(true);
        }

        dayAndPrecipVBox = new VBox(10, dayName, labelWithIcon, precipitationDay, precipitationNight);
        dayTempVBox = new VBox(10, dayLabel, dayTemp, dayIcon);
        nightTempVBox = new VBox(10, nightLabel, nightTemp, nightIcon);

        dayForecastBox.getChildren().addAll(dayAndPrecipVBox, space1, dayTempVBox, space2, nightTempVBox);

        dayForecastButton = new Button("",dayForecastBox);
        dayForecastButton.setMinSize(375, 100);
        dayForecastButton.setOnAction(e -> {
            primaryStage.setScene(detailScene);
        });

        return dayForecastButton;
    }

    public Scene getScene() {
        return forecastScene;
    }
}
