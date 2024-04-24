package com.example.html;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

}
