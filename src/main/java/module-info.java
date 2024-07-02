module com.fresh.coding.datachartapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fresh.coding.datachartapp to javafx.fxml;
    exports com.fresh.coding.datachartapp;
}