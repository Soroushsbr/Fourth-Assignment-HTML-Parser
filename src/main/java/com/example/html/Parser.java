package com.example.html;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Parser extends Application {
    public List<Country> sortByName(List<Country> countries){
        List<Country> sortedByName = new ArrayList<>(countries);
        Collections.sort(sortedByName, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return  sortedByName;
    }

    public List<Country> sortByPopulation(List<Country> countries){
        List<Country> sortedByPopulation = new ArrayList<>(countries);
        Collections.sort(sortedByPopulation, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return Integer.compare(c1.getPopulation() , c2.getPopulation());
            }
        });
        Collections.reverse(sortedByPopulation);
        return sortedByPopulation;
    }

    public List<Country> sortByArea(List<Country> countries){
        List<Country> sortedByArea = new ArrayList<>(countries);
        Collections.sort(sortedByArea, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return Double.compare(c1.getArea() , c2.getArea());
            }
        });
        Collections.reverse(sortedByArea);  //to make it from the most to least
        return sortedByArea;
    }

    public void setUp(List<Country> countries){
        try {
//            File htmlFile = new File("C:\\Users\\Lenovo\\Desktop\\Java Projects\\Fourth-Assignment-HTML-Parser\\src\\country_resources\\country-list.html");
//        File htmlFile = new File("Fourth-Assignment-HTML-Parser\\src\\country_resources\\country-list.html");
            File htmlFile = new File(getClass().getResource("country-list.html").toURI());
            Document document;
            document = Jsoup.parse(htmlFile, "UTF-8");
            Elements extractedCountries = document.select(".country");
            countries.clear();
            for (Element extractedCountry : extractedCountries) {         //
                String countryName = extractedCountry.select(".country-name").text();
                String capital = extractedCountry.select(".country-capital").text();
                int population = Integer.parseInt(extractedCountry.select(".country-population").text());
                double area = Double.parseDouble(extractedCountry.select(".country-area").text());
                Country country = new Country(countryName, capital, population, area);
                countries.add(country);
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    Button countryBtn;
    @FXML
    Button hockeyBtn;
    @FXML
    Label loadingLabel;

    public void setUpHocky(List<Hockey> hockeys){
        try{
            //parse every page of website
            hockeys.clear();
            for(int i = 1 ; i < 10 ; i++) {
                Document document = Jsoup.connect("https://www.scrapethissite.com/pages/forms/?page_num=" + i).get();
                Elements datas = document.select(".team");
                for (Element data : datas) {
                  String name = data.select(".name").text();
                  int year = Integer.parseInt(data.select(".year").text());
                  int wins = Integer.parseInt(data.select(".wins").text());
                  int losses = Integer.parseInt(data.select(".losses").text());
                  Hockey hockey = new Hockey(name , year , wins , losses);
                  hockeys.add(hockey);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("Menu.fxml"))));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private Parent root;
    private Stage stage;
    private Scene scene;
    public void countryScene(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CountryMenu.fxml"));
        root = loader.load();
        CountryMenu countryMenu = loader.getController();
        countryMenu.setCountries();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void hockeyScene(ActionEvent event) throws IOException{
        loadingLabel.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HockeyMenu.fxml"));
        root = loader.load();
        HockeyMenu hockeyMenu = loader.getController();
        hockeyMenu.setHockeys();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
