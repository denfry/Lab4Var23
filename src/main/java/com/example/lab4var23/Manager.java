package com.example.lab4var23;

public class Manager extends Employee {
    private String department;
    public Manager(String name, int salary, int age, String department) {
        super(name, salary, age);
        this.department = department;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String displayInfo() {
        return "Имя менеджера: " + getName() + ", возраст: " + getAge() + " , отдел: " + department;
    }
}
