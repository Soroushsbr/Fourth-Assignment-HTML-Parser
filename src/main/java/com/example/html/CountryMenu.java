package com.example.html;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CountryMenu {
    private List<Country> countries = new ArrayList<>();
    @FXML
    private ComboBox<String> sortOptions;
    public void setCountries(){
        Parser parser = new Parser();
        Collections.addAll(sortOptions.getItems() , "Name" , "Population" , "Area");
        parser.setUp(countries);
        viewList(countries);
    }

    @FXML
    private VBox vbox;

    public void viewList(List<Country> countries){
        vbox.getChildren().clear();
        try {
            for (Country country : countries) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vboxCountry.fxml"));
                AnchorPane anchorPane = loader.load();
                ((Label) anchorPane.getChildren().get(0)).setText(country.getName());
                ((Label) anchorPane.getChildren().get(1)).setText(country.getCapital());
                ((Label) anchorPane.getChildren().get(2)).setText(String.valueOf(country.getPopulation()));
                ((Label) anchorPane.getChildren().get(3)).setText(country.getArea() + " Km²");
                ((Hyperlink) anchorPane.getChildren().get(4)).setOnAction(event -> selectedCountry(country.getName() , country.toString()));
                //todo set action to button
                vbox.getChildren().add(anchorPane);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void selectSort(){
        Parser parser = new Parser();
        String selectedSort = sortOptions.getValue();
        if(selectedSort.equals("Name")){
          List<Country> sortByName = new ArrayList<>(parser.sortByName(countries));
          viewList(sortByName);
        }else if(selectedSort.equals("Population")){
            List<Country> sortByPopulation = new ArrayList<>(parser.sortByPopulation(countries));
            viewList(sortByPopulation);
        }else if(selectedSort.equals("Area")){
            List<Country> sortByArea = new ArrayList<>(parser.sortByArea(countries));
            viewList(sortByArea);
        }
    }
    @FXML
    AnchorPane countryPane;
    @FXML
    Label countryLabel;
    @FXML
    ImageView flagImage;
    @FXML
    Rectangle backPane;
    @FXML
    Label contentLabel;
    public void selectedCountry(String name , String info){
        countryPane.setVisible(true);
        backPane.setVisible(true);
        try {
            countryLabel.setText(info);
            name = name.toLowerCase();
            name = name.replace(" " , "-");
            Document document = Jsoup.connect("https://www.countryflags.com/flag-of-" + name + "/").get();
            Elements datas = document.select(".container");
            for (Element data : datas) {
                Elements images = data.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
                for (Element image : images) {
                    String imageUrl = image.absUrl("src");
                    flagImage.setImage(new Image((imageUrl)));
                    break;
                }
                contentLabel.setText(data.select(".content").text().replace("." , ".\n"));
                break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void closeSelectedCountry(){
        countryPane.setVisible(false);
        backPane.setVisible(false);
        flagImage.setImage(null);
        contentLabel.setText(null);
    }
    Parent root;
    Stage stage;
    Scene scene;
    public void Menu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
