package com.example.html;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HockeyMenu {
    List<Hockey> hockeys = new ArrayList<>();
    @FXML
    ComboBox<String> sortOptions;
    public void setHockeys(){
        Parser parser = new Parser();
        Collections.addAll(sortOptions.getItems() , "Name" , "Year" , "Win");
        parser.setUpHocky(hockeys);
        viewList(hockeys);
    }

    public List<Hockey> sortByName(){
        List<Hockey> sortedByName = new ArrayList<>(hockeys);
        Collections.sort(sortedByName, new Comparator<Hockey>() {
            @Override
            public int compare(Hockey o1, Hockey o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return sortedByName;
    }

    public List<Hockey> sortByYear(){
        List<Hockey> sortedByYear = new ArrayList<>(hockeys);
        Collections.sort(sortedByYear, new Comparator<Hockey>() {
            @Override
            public int compare(Hockey o1, Hockey o2) {
                return Integer.compare(o1.getYear(), o2.getYear());
            }
        });
        Collections.reverse(sortedByYear);
        return sortedByYear;
    }

    public List<Hockey> sortByWins(){
        List<Hockey> sortedByWins = new ArrayList<>(hockeys);
        Collections.sort(sortedByWins, new Comparator<Hockey>() {
            @Override
            public int compare(Hockey o1, Hockey o2) {
                return Integer.compare(o1.getWins(), o2.getWins());
            }
        });
        Collections.reverse(sortedByWins);
        return sortedByWins;
    }
    @FXML
    VBox vbox;
    public void viewList(List<Hockey> hockeys){
        vbox.getChildren().clear();
        try {
            for (Hockey hockey : hockeys) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vboxCountry.fxml"));
                AnchorPane anchorPane = loader.load();
                ((Label) anchorPane.getChildren().get(0)).setText(hockey.getName());
                ((Label) anchorPane.getChildren().get(1)).setText(String.valueOf(hockey.getYear()));
                ((Label) anchorPane.getChildren().get(2)).setText(String.valueOf(hockey.getWins()));
                ((Label) anchorPane.getChildren().get(3)).setText(String.valueOf(hockey.getLosses()));
                vbox.getChildren().add(anchorPane);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void selectSort(){
        String selectedSort = sortOptions.getValue();
        if(selectedSort.equals("Name")){
            List<Hockey> sortedByName = sortByName();
            viewList(sortedByName);
        }else if(selectedSort.equals("Year")){
            List<Hockey> sortedByYear = sortByYear();
            viewList(sortedByYear);
        }else if(selectedSort.equals("Win")){
            List<Hockey> sortedByWins = sortByWins();
            viewList(sortedByWins);
        }
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
