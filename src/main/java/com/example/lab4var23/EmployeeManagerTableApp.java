package com.example.lab4var23;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;


public class EmployeeManagerTableApp extends Application {
    private final TableView<Employee> table = new TableView<>();
    private final ObservableList<Employee> employees = FXCollections.observableArrayList();

    private boolean ascendingSort = true;

    private final TextField nameTextField = new TextField();
    private final TextField salaryTextField = new TextField();
    private final TextField ageTextField = new TextField();
    private final TextField departmentTextField = new TextField();
    private final CheckBox isSecurityGuardPrivateBox = new CheckBox("Is Private");


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employees Table App");

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, Integer> salaryColumn = getSalaryColumn();

        TableColumn<Employee, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Manager) {
                return new SimpleStringProperty(((Manager) cellData.getValue()).getDepartment());
            } else {
                return new SimpleStringProperty("");
            }
        });


        TableColumn<Employee, String> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Employee, Boolean> isSecurityGuardPrivateColumn = getIsSecurityGuardPrivateColumn();


        table.getColumns().addAll(nameColumn, salaryColumn, departmentColumn, ageColumn, isSecurityGuardPrivateColumn);
        table.setItems(employees);

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> addEmployee());

        Button addFromArrayButton = new Button("Add Employees from Array");
        addFromArrayButton.setOnAction(e -> addAllEmployeesFromArray());

        Button sortButton = new Button("Sort by Salary");
        sortButton.setOnAction(e -> sortEmployeesBySalary());


        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(sortButton);
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(
                new Label("Name:"),
                nameTextField,
                new Label("Salary:"),
                salaryTextField,
                new Label("Age:"),
                ageTextField,
                new Label("Department:"),
                departmentTextField,
                new Label("Is Private:"),
                isSecurityGuardPrivateBox,
                addButton
        );

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(table, inputBox, addFromArrayButton, buttonBox);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn<Employee, Integer> getSalaryColumn() {
        TableColumn<Employee, Integer> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        salaryColumn.setCellFactory(column -> new TableCell<Employee, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item));
                    int salaryValue = item;

                    if (salaryValue >= 15000) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
                }
            }
        });

        return salaryColumn;
    }


    private static TableColumn<Employee, Boolean> getIsSecurityGuardPrivateColumn() {
        TableColumn<Employee, Boolean> isSecurityGuardPrivateColumn = new TableColumn<>("Is Private");
        isSecurityGuardPrivateColumn.setCellValueFactory(cellData -> {
            Employee employee = cellData.getValue();
            if (employee instanceof SecurityGuard) {
                return new SimpleBooleanProperty(((SecurityGuard) cellData.getValue()).getIsPrivate());
            }
            return new SimpleBooleanProperty().asObject();
        });
        isSecurityGuardPrivateColumn.setCellFactory(column -> new CheckBoxTableCell<Employee, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setAlignment(Pos.CENTER);
                setAlignment(Pos.CENTER);

                checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                    if (isSelected) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        setStyle("-fx-background-color: lightcoral;");
                    }
                });
            }

            @Override
            public void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                    if (!item) {
                        setStyle("-fx-background-color: lightcoral;");
                    } else {
                        setStyle("-fx-background-color: lightgreen;");
                    }
                }
            }
        });
        return isSecurityGuardPrivateColumn;
    }


    private void addEmployee() {
        try {
            String name = nameTextField.getText();
            int salary = Integer.parseInt(salaryTextField.getText());
            int age = Integer.parseInt(ageTextField.getText());
            String department = departmentTextField.getText();
            boolean isPrivate = isSecurityGuardPrivateBox.isSelected();

            Employee employee;

            if (isPrivate) {
                employee = new SecurityGuard(name, salary, age, isPrivate);
            } else {
                employee = new Manager(name, salary, age, department);
            }

            employees.add(employee);
            nameTextField.clear();
            salaryTextField.clear();
            ageTextField.clear();
            departmentTextField.clear();
            isSecurityGuardPrivateBox.setSelected(false);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid numeric value for Price or Screen Size.");
            alert.showAndWait();
        }
    }


    private void addAllEmployeesFromArray() {
        employees.addAll(getEmployeesFromArray());
    }

    private ObservableList<Employee> getEmployeesFromArray() {
        ObservableList<Employee> employeesArray = FXCollections.observableArrayList();
        employeesArray.add(new Manager("Жданова В. А.", 10000, 30, "IT"));
        employeesArray.add(new Manager("Данилов Г. В.", 12000, 35, "Marketing"));
        employeesArray.add(new Manager("Денисов Р. Д.", 14000, 40, "IT"));
        employeesArray.add(new Manager("Воробьев А. И.", 16000, 45, "IT"));
        employeesArray.add(new Manager("Костина А. Т.", 18000, 50, "Sales"));
        employeesArray.add(new SecurityGuard("Кириллов М. М.", 8000, 25, true));
        employeesArray.add(new SecurityGuard("Блинов М. Г.", 8500, 28, false));
        employeesArray.add(new SecurityGuard("Ковалев В. М.", 9000, 30, false));
        employeesArray.add(new SecurityGuard("Григорьев А. Е.", 9500, 32, false));
        employeesArray.add(new SecurityGuard("Успенский Н. М.", 8200, 26, true));
        return employeesArray;
    }

    private void sortEmployeesBySalary() {
        ascendingSort = !ascendingSort;
        Comparator<Employee> comparator = ascendingSort
                ? Comparator.comparingDouble(Employee::getSalary)
                : (r1, r2) -> Double.compare(r2.getSalary(), r1.getSalary());

        employees.sort(comparator);
    }
}