package org.example;

public class Employee {
    private String name;
    private  String workNumber;

    public Employee() {
    }

    public Employee(String name, String workNumber) {
        this.name = name;
        this.workNumber = workNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", workNumber='" + workNumber + '\'' +
                '}';
    }
}
