package com.example.html;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HockeyMenu {
    List<Hockey> hockeys = new ArrayList<>();
    public void setHockeys(){
        Parser parser = new Parser();
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
        return sortedByYear;
    }

    public List<Hockey> sortByWins(){
        List<Hockey> sortedByWins = new ArrayList<>(hockeys);
        Collections.sort(sortedByWins, new Comparator<Hockey>() {
            @Override
            public int compare(Hockey o1, Hockey o2) {
                return Integer.compare(o1.getYear(), o2.getYear());
            }
        });
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
}
