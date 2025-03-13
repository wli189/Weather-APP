import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

public class JavaFX extends Application {
	Label titleLabel, temperature, weather, threeDayForecast, firstDayName, dayLabel, nightLabel, precipitation, dayTemperature, nightTemperature;
	VBox tempBox, root, forecastBox, root2, nightAndTempVBox, dayAndTempVBox, dayAndPrecipVbox;
	HBox firstDayWeatherHbox;
	Font titleFont, tempFont, weatherFont, degreeFont, title2ndSceneFont, DayFonts;
	Text degreeSymbol;
	StackPane degreePane;
	Scene basicScene, forecastScene;
	Button more;
	ImageView weatherIcon;

	public static void main(String[] args) {

		launch(args);
	}
	private HBox threeDays(int day, ArrayList<Period> forecast) {
		int i = 0;
		firstDayWeatherHbox = new HBox(90);
		firstDayName = new Label(forecast.get(day).name);
		firstDayName.setFont(DayFonts);
		precipitation = new Label(String.valueOf(forecast.get(day).probabilityOfPrecipitation.value)+"%");
		precipitation.setFont(DayFonts);
		dayAndPrecipVbox = new VBox(10);
		dayTemperature = new Label(String.valueOf(forecast.get(day).temperature));
		nightTemperature = new Label(String.valueOf(forecast.get(day+1).temperature));
		dayTemperature.setFont(DayFonts);
		nightTemperature.setFont(DayFonts);
		dayLabel = new Label("Day");
		dayLabel.setFont(DayFonts);
		nightLabel = new Label("Night");
		nightLabel.setFont(DayFonts);
		nightAndTempVBox = new VBox(10);
		dayAndTempVBox = new VBox(10);
		nightAndTempVBox.getChildren().addAll(nightLabel, nightTemperature);
		dayAndTempVBox.getChildren().addAll(dayLabel,dayTemperature);
		//dayAndPrecipVbox.setAlignment(Pos.CENTER_LEFT);
		//dayAndTempVBox.setAlignment(Pos.TOP_CENTER);
		//nightAndTempVBox.setAlignment(Pos.BASELINE_RIGHT);
		dayAndPrecipVbox.getChildren().addAll(firstDayName, precipitation);
		firstDayWeatherHbox.getChildren().addAll(dayAndPrecipVbox, dayAndTempVBox, nightAndTempVBox);
		//firstDayWeatherHbox.setAlignment(Pos.CENTER);
		return firstDayWeatherHbox;
	}
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Weather");
		//int temp = WeatherAPI.getTodaysTemperature(77,70);
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

		// title
		titleFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25);
		titleLabel = new Label("Today's weather");
		titleLabel.setFont(titleFont);

		// temperature
		temperature = new Label();
		tempFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 50);
		temperature.setText(String.valueOf(forecast.get(0).temperature));
		temperature.setFont(tempFont);

		// set up the degree symbol
		degreeFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15);
		degreeSymbol = new Text("°" + String.valueOf(forecast.get(0).temperatureUnit));
		degreeSymbol.setFont(degreeFont);

		// align the degree value and symbol
		degreePane = new StackPane();
		degreePane.setAlignment(Pos.CENTER);
		degreePane.setMaxSize(90,40);
		degreePane.setMinHeight(40);
		StackPane.setAlignment(degreeSymbol, Pos.TOP_RIGHT);
		StackPane.setAlignment(temperature, Pos.CENTER);
		degreePane.getChildren().addAll(temperature, degreeSymbol);

		// weather
		weather = new Label();
		weatherFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20);
		weather.setText(forecast.get(0).shortForecast);
		weather.setFont(weatherFont);

		// icon
		weatherIcon = new ImageView(new Image(forecast.get(0).icon));
		weatherIcon.setFitWidth(50); // resize the icon
		weatherIcon.setPreserveRatio(true);

		// create the main VBox with title, temperature, and weather
		tempBox = new VBox(10);
		tempBox.getChildren().addAll(titleLabel, degreePane, weather, weatherIcon);
		tempBox.setAlignment(Pos.CENTER);

		more = new Button("More");
		more.setAlignment(Pos.CENTER);
		more.setOnAction(e -> {primaryStage.setScene(forecastScene);});
		title2ndSceneFont = Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 25);
		DayFonts =  Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15);
		threeDayForecast = new Label("3-Day Forecast");
		threeDayForecast.setFont(title2ndSceneFont);
		threeDayForecast.setTextAlignment(TextAlignment.CENTER);

		forecastBox = new VBox(150);
		forecastBox.getChildren().addAll(threeDayForecast, threeDays(1, forecast), threeDays(3, forecast), threeDays(5, forecast));
		forecastBox.setAlignment(Pos.CENTER);

		root = new VBox(100);
		root.getChildren().addAll(tempBox, more);
		root.setAlignment(Pos.CENTER);
		root2 = new VBox(100);
		root2.getChildren().addAll(forecastBox);

		// set up the scene and show the stage
		basicScene = new Scene(root, 375, 667);

		forecastScene = new Scene(root2, 375, 667);
		primaryStage.setScene(basicScene);
		primaryStage.show();
	}

}