import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;
import java.util.Date;

public class JavaFX extends Application {
	private Label titleLabel, temperature, weather;
	private VBox tempBox, tempAndButton, root;
	private Font titleFont, tempFont, weatherFont, degreeFont;
	private Text degreeSymbol;
	private StackPane degreePane, titlePane, tempPane;
	private Scene mainScene, forecastScene;
	private Button more;
	private ImageView weatherIcon, background;
	private Date time;
	private String backgroundImagePath;
	private Color titleColor;
	private String iconPath;

	public static void main(String[] args) {

		launch(args);
	}

	protected void setBackgroundAndTitleColor(int hour) {
		if (hour >= 6 && hour < 14) {
			backgroundImagePath = "file:./assets/background/day.png";
			titleColor = Color.rgb(70, 130, 180);
		} else if (hour >= 14 && hour < 18) {
			backgroundImagePath = "file:./assets/background/afternoon.png";
			titleColor = Color.rgb(255, 165, 0);
		} else if (hour >= 18 && hour < 21) {
			backgroundImagePath = "file:./assets/background/evening.png";
			titleColor = Color.rgb(128, 128, 128);
		} else {
			backgroundImagePath = "file:./assets/background/night.png";
			titleColor = Color.WHITE;
		}
		background = new ImageView(new Image(backgroundImagePath));
		background.setFitWidth(390);
		background.setPreserveRatio(true);
		titleLabel.setTextFill(titleColor); // set title color
	}

	protected String setWeatherIcon(String forecastText) {
		iconPath = "file:./assets/icons/";
		forecastText = forecastText.toLowerCase();

		if (forecastText.contains("clear") && forecastText.contains("night")) {
			iconPath += "clear_night.png";
		} else if (forecastText.contains("clear") || forecastText.contains("sunny")) {
			iconPath += "sunny.png";
		} else if (forecastText.contains("cloudy") && forecastText.contains("partly") && forecastText.contains("night")) {
			iconPath += "partly_cloudy_night.png";
		} else if (forecastText.contains("cloudy") && forecastText.contains("partly")) {
			iconPath += "partly_cloudy.png";
		} else if (forecastText.contains("cloudy")) {
			iconPath += "cloudy.png";
		} else if (forecastText.contains("fog")) {
			iconPath += "fog.png";
		} else if (forecastText.contains("thunderstorm")) {
			iconPath += "thunderstorm.png";
		} else if (forecastText.contains("heavy") && forecastText.contains("snow")) {
			iconPath += "heavy_snow.png";
		} else if (forecastText.contains("snow")) {
			iconPath += "snow.png";
		} else if (forecastText.contains("heavy") && forecastText.contains("rain")) {
			iconPath += "heavy_rain.png";
		} else if (forecastText.contains("freezing") && forecastText.contains("rain")) {
			iconPath += "freezing_rain.png";
		} else if (forecastText.contains("rain")) {
			iconPath += "rain.png";
		} else if (forecastText.contains("drizzle") && forecastText.contains("night")) {
			iconPath += "drizzle_night.png";
		} else if (forecastText.contains("drizzle")) {
			iconPath += "drizzle.png";
		} else if (forecastText.contains("haze")) {
			iconPath += "haze.png";
		} else if (forecastText.contains("windy")) {
			iconPath += "windy.png";
		} else if (forecastText.contains("sunrise")) {
			iconPath += "sunrise.png";
		} else if (forecastText.contains("sunset")) {
			iconPath += "sunset.png";
		} else {
			iconPath += "cloudy.png";
		}

		return iconPath;
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

		// set up background
		time = forecast.get(0).startTime;
		int hour = time.getHours();
		setBackgroundAndTitleColor(hour);

		// add background behind the title
		titlePane = new StackPane();
		titlePane.getChildren().addAll(background, titleLabel);
		titlePane.setAlignment(Pos.CENTER);

		// temperature
		temperature = new Label();
		tempFont = Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 75);
		temperature.setText(String.valueOf(forecast.get(0).temperature));
		temperature.setFont(tempFont);

		// set up the degree symbol
		degreeFont = Font.font("Arial", FontWeight.MEDIUM , FontPosture.REGULAR, 15);
		degreeSymbol = new Text("°" + String.valueOf(forecast.get(0).temperatureUnit));
		degreeSymbol.setFont(degreeFont);

		// align the degree value and symbol
		degreePane = new StackPane();
		degreePane.setAlignment(Pos.CENTER);
		degreePane.setMaxSize(110,40);
		degreePane.setMinHeight(40);
		StackPane.setAlignment(degreeSymbol, Pos.TOP_RIGHT);
		StackPane.setAlignment(temperature, Pos.CENTER);
		degreePane.getChildren().addAll(temperature, degreeSymbol);

		// weather
		weather = new Label();
		weatherFont = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20);
		weather.setText(forecast.get(0).shortForecast);
		weather.setFont(weatherFont);
		weather.setWrapText(true);
		weather.setTextAlignment(TextAlignment.CENTER);

		// icon
		weatherIcon = new ImageView(new Image(setWeatherIcon(forecast.get(0).shortForecast)));
		weatherIcon.setFitWidth(50); // resize the icon
		weatherIcon.setPreserveRatio(true);

		// create the main VBox with title, temperature, and weather
		tempBox = new VBox(10);
		tempBox.getChildren().addAll(degreePane, weather, weatherIcon);
		tempBox.setAlignment(Pos.CENTER);

		more = new Button("3-Day Forecast");
		more.setAlignment(Pos.CENTER);
		more.setStyle("-fx-background-color: rgb(169, 169, 169); " +
				"-fx-text-fill: white; " +
				"-fx-font-weight: bold; " +
				"-fx-background-radius: 20; " +
				"-fx-font-size: 15px; " +
				"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5,0,1,1 );");


		// group button and tempBox
		tempAndButton = new VBox(50);
		tempAndButton.getChildren().addAll(tempBox, more);
		tempAndButton.setAlignment(Pos.CENTER);
		tempAndButton.setPadding(new Insets(20, 20, 20, 20));
		tempAndButton.setStyle("-fx-background-color: white; " +
				"-fx-background-radius: 20 20 0 0; " +
				"-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 5,0,1,1 );");
		tempAndButton.setMaxHeight(530);
		tempAndButton.setMinHeight(530);

		// add title pane to root
		root = new VBox();
		root.getChildren().add(titlePane);

		// put button and tempBox as overlap
		tempPane = new StackPane();
		tempPane.getChildren().add(tempAndButton);
		tempPane.setAlignment(Pos.CENTER);
		tempPane.setMargin(tempAndButton, new Insets(-20, 0, 0, 0));
		root.getChildren().add(tempPane);
		root.setAlignment(Pos.TOP_CENTER);
		root.setStyle("-fx-background-color: white;");

		// set up the scene and show the stage
		mainScene = new Scene(root, 390, 750);

		// set up the 3-day forecast scene
		ThreeDayForecast threeDayForecast = new ThreeDayForecast(forecast, primaryStage, mainScene);
		forecastScene = threeDayForecast.getScene();

		more.setOnAction(e -> {primaryStage.setScene(forecastScene);});

		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

}