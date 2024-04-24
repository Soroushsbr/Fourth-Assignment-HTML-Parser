import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Country> countries = new ArrayList<>();

    public List<Country> sortByName(){
        List<Country> sortedByName = new ArrayList<>(countries);
        Collections.sort(sortedByName, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });
        return  sortedByName;
    }

    public List<Country> sortByPopulation(){
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

    public List<Country> sortByArea(){
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

    public void setUp() throws IOException {
        File htmlFile = new File("C:\\Users\\Lenovo\\Desktop\\Java Projects\\Fourth-Assignment-HTML-Parser\\src\\Resources\\country-list.html");
//        File htmlFile = new File("Fourth-Assignment-HTML-Parser\\src\\Resources\\country-list.html");
        Document document;
        document = Jsoup.parse(htmlFile, "UTF-8");
        Elements extractedCountries = document.select(".country");
        countries.clear();
        for (Element extractedCountry : extractedCountries) {         //
            String countryName = extractedCountry.select(".country-name").text();
            String capital = extractedCountry.select(".country-capital").text();
            int population = Integer.parseInt(extractedCountry.select(".country-population").text());
            double area =Double.parseDouble(extractedCountry.select(".country-area").text());
            Country country = new Country(countryName , capital ,population ,area);
            countries.add(country);
        }
    }

    public static void main(String[] args) throws IOException {

    }
}
