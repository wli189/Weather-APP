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
    Scene forecastScene;
    Font titleFont, dayFont, precipitationLabelFont, valueFont;
    Label threeDayForecast, dayName, precipitation, dayTemp, nightTemp, precipitationLabel, dayLabel, nightLabel;
    HBox dayForecastBox;
    Button backButton;
    ImageView dayIcon, nightIcon, precipitationIcon;
    Region space1, space2;

    public ThreeDayForecast(ArrayList<Period> forecast, Stage primaryStage, Scene mainScene) {
        titleFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25);
        dayFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15);
        precipitationLabelFont = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 10);
        valueFont = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 10);

        threeDayForecast = new Label("3-Day Forecast");
        threeDayForecast.setFont(titleFont);
        threeDayForecast.setAlignment(Pos.CENTER);

        backButton = new Button("Back To Today");
        backButton.setOnAction(e -> {primaryStage.setScene(mainScene);});

        forecastBox = new VBox(50);
        forecastBox.getChildren().addAll(
                threeDayForecast,
                createDayForecast(0, forecast),
                createDayForecast(2, forecast),
                createDayForecast(4, forecast),
                backButton
        );
        forecastBox.setAlignment(Pos.CENTER);

        forecastScene = new Scene(forecastBox, 375, 667);
    }

    private HBox createDayForecast(int day, ArrayList<Period> forecast) {
        dayForecastBox = new HBox();
        dayForecastBox.setAlignment(Pos.CENTER);

        space1 = new Region();
        space2 = new Region();
        space1.setMinWidth(50);
        space2.setMinWidth(30);

        dayName = new Label(forecast.get(day).name);
        dayName.setFont(dayFont);
        dayLabel = new Label("Day");
        dayLabel.setFont(dayFont);
        nightLabel = new Label("Night");
        nightLabel.setFont(dayFont);

        precipitationLabel = new Label("Chance of Precipitation");
        precipitationLabel.setFont(precipitationLabelFont);
        precipitation = new Label(forecast.get(day).probabilityOfPrecipitation.value + "%");
        precipitation.setFont(valueFont);

        dayTemp = new Label(forecast.get(day).temperature + "°" + String.valueOf(forecast.get(0).temperatureUnit));
        dayTemp.setFont(valueFont);

        nightTemp = new Label(forecast.get(day + 1).temperature + "°" + String.valueOf(forecast.get(0).temperatureUnit) );
        nightTemp.setFont(valueFont);

        dayIcon = new ImageView(new Image(forecast.get(day).icon));
        dayIcon.setFitWidth(50);
        dayIcon.setPreserveRatio(true);
        nightIcon= new ImageView(new Image(forecast.get(day + 1).icon));
        nightIcon.setFitWidth(50);
        nightIcon.setPreserveRatio(true);
        precipitationIcon = new ImageView(new Image("file:./assets/icons/rain.png"));
        precipitationIcon.setFitWidth(20);
        precipitationIcon.setPreserveRatio(true);

        dayAndPrecipVBox = new VBox(10, dayName, precipitationLabel, precipitationIcon, precipitation);
        dayTempVBox = new VBox(10, dayLabel, dayTemp, dayIcon);
        nightTempVBox = new VBox(10, nightLabel, nightTemp, nightIcon);

        dayForecastBox.getChildren().addAll(dayAndPrecipVBox, space1, dayTempVBox, space2, nightTempVBox);
        dayForecastBox.setPadding(new Insets(0,20,0,20));
        return dayForecastBox;
    }

    public Scene getScene() {
        return forecastScene;
    }
}
