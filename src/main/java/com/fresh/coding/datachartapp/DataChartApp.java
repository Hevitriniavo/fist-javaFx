package com.fresh.coding.datachartapp;

import com.fresh.coding.datachartapp.entities.DataEntry;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataChartApp extends Application {

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Date Range Chart App");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        Button generateButton = new Button("Generate Chart");

        gridPane.add(startDatePicker, 0, 0);
        gridPane.add(endDatePicker, 1, 0);
        gridPane.add(generateButton, 2, 0);

        LineChart<String, Number> lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setTitle("Montant par date sur une période");

        generateButton.setOnAction(event -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
                List<DataEntry> data = generateData(startDate, endDate);
                showChart(lineChart, data);
            }
        });

        Scene scene = new Scene(new GridPane(), 800, 600);
        ((GridPane) scene.getRoot()).add(gridPane, 0, 0);
        ((GridPane) scene.getRoot()).add(lineChart, 0, 1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<DataEntry> generateData(LocalDate startDate, LocalDate endDate) {
        List<DataEntry> data = new ArrayList<>();
        double amount = 1000.0;

        while (!startDate.isAfter(endDate)) {
            data.add(new DataEntry(startDate, amount));
            startDate = startDate.plusDays(7);
            amount += Math.random() * 200 - 100;
        }

        return data;
    }

    private void showChart(LineChart<String, Number> chart, List<DataEntry> data) {
        chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Montant");

        for (DataEntry entry : data) {
            series.getData().add(new XYChart.Data<>(entry.date().toString(), entry.amount()));
        }

        chart.getData().add(series);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
