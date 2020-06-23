package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.TreeMap;


public class Main extends Application {

    private ObservableList<String> capitals;
    private ObservableList<String> countries;
    private ObservableMap<CountryCapital,Image> countriesFlag;

    private ListView<String> capitalsListView;
    private ListView<String> countriesListView;
    private ImageView flag;

    public void buildData(){
        capitals = FXCollections.observableArrayList(
                "Canberra",
                "Vienna",
                "Brussels",
                "Santiago",
                "Helsinki",
                "New Delhi",
                "San Jose",
                "Taipei",
                "Washington DC",
                "London",
                "Stockholm"
        );
        countries = FXCollections.observableArrayList(
                "Australia",
                "Austria",
                "Belgium",
                "Chile",
                "Finland",
                "India",
                "Costa Rica",
                "Taiwan",
                "USA",
                "UK",
                "Sweden"
        );
        countriesFlag = FXCollections.observableMap(new TreeMap<CountryCapital, Image>(new CountryCapitalComparator()) {{
                put(new CountryCapital("Australia", "Canberra"), new Image("/images/Australia.png"));
                put(new CountryCapital("Austria", "Vienna"), new Image("/images/Austria.gif"));
                put(new CountryCapital("Belgium", "Brussels"), new Image("/images/Belgium.png"));
                put(new CountryCapital("Chile", "Santiago"), new Image("/images/Chile.jpg"));
                put(new CountryCapital("Finland", "Helsinki"), new Image("/images/Finland.png"));
                put(new CountryCapital("India", "New Delhi"), new Image("/images/India.jpg"));
                put(new CountryCapital("Costa Rica", "San Jose"), new Image("/images/Costa Rica.png"));
                put(new CountryCapital("Taiwan", "Taipei"), new Image("/images/Taiwan.jpg"));
                put(new CountryCapital("USA", "Washington DC"), new Image("/images/USA.jpg"));
                put(new CountryCapital("UK", "London"), new Image("/images/UK.png"));
                put(new CountryCapital("Sweden", "Stockholm"), new Image("/images/Sweden.jpg"));
        }});
    }

    public void buildGui(Stage primaryStage){
        primaryStage.setTitle("Arrange Countries and Capitals");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 450);

        capitalsListView = new ListView<>(capitals);
        countriesListView = new ListView<>(countries);
        flag = new ImageView();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        ColumnConstraints column2 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        ColumnConstraints column3 = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        column2.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        Label countriesLabel = new Label("Capitals");
        GridPane.setHalignment(countriesLabel, HPos.CENTER);
        gridPane.add(countriesLabel, 0, 0);

        Label capitalsLabel = new Label("Countries");
        GridPane.setHalignment(capitalsLabel, HPos.CENTER);
        gridPane.add(capitalsLabel, 1, 0);

        Label flagsLabel = new Label("Flag");
        GridPane.setHalignment(flagsLabel, HPos.CENTER);
        gridPane.add(flagsLabel, 2, 0);

        gridPane.add(capitalsListView, 0, 1);
        gridPane.add(countriesListView, 1, 1);
        gridPane.add(flag, 2, 1);

        root.setCenter(gridPane);
        GridPane.setVgrow(root, Priority.ALWAYS);

        primaryStage.setScene(scene);
    }

    public void buildListeners(){
        capitalsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old, String current) -> {
            if(countriesListView.getSelectionModel().getSelectedItem() == null || current == null){
                flag.setImage(null);
            }else {
                CountryCapital key = new CountryCapital(countriesListView.getSelectionModel().getSelectedItem(), current);
                flag.setImage(countriesFlag.get(key));
            }
        });
        countriesListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old, String current) -> {
            if(capitalsListView.getSelectionModel().getSelectedItem() == null || current == null){
                flag.setImage(null);
            }else {
                CountryCapital key = new CountryCapital(current, capitalsListView.getSelectionModel().getSelectedItem());
                flag.setImage(countriesFlag.get(key));
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        buildData();
        buildGui(primaryStage);
        buildListeners();

        primaryStage.show();
    }

}