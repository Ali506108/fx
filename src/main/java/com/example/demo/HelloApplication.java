package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class HelloApplication extends Application {

    private final int DEFAULT_ROWS = 5;
    private final int DEFAULT_COLS = 5;

    private int rows;
    private int cols;
    private int[][] array;
    private TableView<Integer[]> tableView;
    private Label minLabel;
    private Label maxLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Two Dimensional Array App");

        // Initialize array and fill it with random values
        initializeArray(DEFAULT_ROWS, DEFAULT_COLS);
        fillArrayWithRandomValues();

        // Create TableView to display the array
        tableView = new TableView<>();
        setupTableView();

        // Create UI components
        Label rowsLabel = new Label("Rows:");
        TextField rowsField = new TextField(Integer.toString(DEFAULT_ROWS));
        Label colsLabel = new Label("Columns:");
        TextField colsField = new TextField(Integer.toString(DEFAULT_COLS));
        Button fillButton = new Button("Fill Array");
        minLabel = new Label("Min: ");
        maxLabel = new Label("Max: ");

        // Event handler for fillButton
        fillButton.setOnAction(e -> {
            try {
                rows = Integer.parseInt(rowsField.getText());
                cols = Integer.parseInt(colsField.getText());
                initializeArray(rows, cols);
                fillArrayWithRandomValues();
                updateTableView();
                findMinMax();
            } catch (NumberFormatException ex) {
                // Handle invalid input
                System.out.println("Invalid input. Please enter valid integers for rows and columns.");
            }
        });

        // Create layout
        VBox root = new VBox(10);
        root.getChildren().addAll(
                tableView,
                rowsLabel, rowsField,
                colsLabel, colsField,
                fillButton,
                minLabel, maxLabel
        );

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeArray(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        array = new int[rows][cols];
    }

    private void fillArrayWithRandomValues() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = random.nextInt(100); // Generate random integers between 0 and 99
            }
        }
    }

    private void updateTableView() {
        tableView.getItems().clear();
        for (int i = 0; i < rows; i++) {
            tableView.getItems().add(convertRowToArray(array[i]));
        }
    }

    private Integer[] convertRowToArray(int[] row) {
        Integer[] result = new Integer[cols];
        for (int i = 0; i < cols; i++) {
            result[i] = row[i];
        }
        return result;
    }

    private void setupTableView() {
        tableView.setEditable(false);
        for (int i = 0; i < cols; i++) {
            TableColumn<Integer[], Integer> column = new TableColumn<>("Column " + (i + 1));
            final int colIndex = i;
            column.setCellValueFactory(cellData -> {
                Integer[] row = cellData.getValue();
                return new javafx.beans.property.SimpleObjectProperty<>(row[colIndex]);
            });
            tableView.getColumns().add(column);
        }
    }

    private void findMinMax() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                min = Math.min(min, array[i][j]);
                max = Math.max(max, array[i][j]);
            }
        }
        minLabel.setText("Min: " + min);
        maxLabel.setText("Max: " + max);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
