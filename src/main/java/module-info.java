module com.example.html {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.example.html to javafx.fxml;
    exports com.example.html;
}