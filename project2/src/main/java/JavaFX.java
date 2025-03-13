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
	import javafx.scene.text.Font;
	import javafx.scene.text.FontPosture;
	import javafx.scene.text.FontWeight;
	import javafx.scene.text.Text;
	import javafx.stage.Stage;
	import weather.Period;
	import weather.WeatherAPI;

	import java.util.ArrayList;

	public class JavaFX extends Application {
		Label titleLabel, temperature, weather;
		VBox tempBox, root, root2;
		Font titleFont, tempFont, weatherFont, degreeFont;
		Text degreeSymbol;
		StackPane degreePane;
		Scene basicScene, forecastScene;
		Button more;
		ImageView weatherIcon;

		public static void main(String[] args) {

			launch(args);
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

			root = new VBox(100);
			root.getChildren().addAll(tempBox, more);
			root.setAlignment(Pos.CENTER);

			// set up the scene and show the stage
			basicScene = new Scene(root, 375, 667);
	//		forecastScene = new Scene(root2, 375, 667);
			primaryStage.setScene(basicScene);
			primaryStage.show();
		}

	}
