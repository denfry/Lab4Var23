package com.example.lab4var23;

public class SecurityGuard extends Employee {
    private boolean isPrivate;
    public SecurityGuard(String name, int salary, int age, boolean isPrivate) {
        super(name, salary, age);
        this.isPrivate = isPrivate;
    }
    public boolean getIsPrivate() {
        return isPrivate;
    }
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public String displayInfo() {
        String description = isPrivate ? "Сотрудник фирмы" : "Частный охранник";
        return "Имя охранника: " + getName() + ", возраст: " + getAge() + ", " + description;
    }
}
