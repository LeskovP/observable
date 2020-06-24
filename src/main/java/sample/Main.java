package sample;

//import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;


public class Main extends Application {

    private ObservableList<String> capitals;
    private ObservableList<String> countries;
    private ObservableMap<CountryCapital,Image> countriesFlag;

    private ListView<String> capitalsListView;
    private ListView<String> countriesListView;
    private ImageView flag;


    public void buildData(){
        //SvgImageLoaderFactory.install();
        capitals = FXCollections.observableArrayList();
        countries = FXCollections.observableArrayList();
        TreeMap<CountryCapital, Image> treeTmp = new TreeMap<>(new CountryCapitalComparator());

        try {
            Scanner in = new Scanner(new File("countries.txt"));
            String s;
            while (in.hasNextLine()){
                s = in.nextLine();
                String words[] = s.split("__");
                capitals.add(words[1]);
                countries.add(words[0]);
                treeTmp.put(new CountryCapital(words[0],words[1]),new Image(words[2]));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        countriesFlag = FXCollections.observableMap(treeTmp);
    }

    public void buildGui(Stage primaryStage){
        primaryStage.setTitle("Arrange Countries and Capitals");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 450);

        capitalsListView = new ListView<>(capitals);
        countriesListView = new ListView<>(countries);
        flag = new ImageView();
        flag.setFitWidth(300);
        flag.setFitHeight(150);

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