package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Corp {
    @Value("ADADASD")
    private String corpName;
    @Autowired
    private List<Employee>staff;

    public Corp(String corpName, List<Employee> staff) {
        this.corpName = corpName;
        this.staff = staff;
    }

    public Corp() {
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Corp{" +
                "corpName='" + corpName + '\'' +
                ", staff=" + staff +
                '}';
    }

    public void addWorker(Employee emp){
        System.out.println("注解的原方法正在进行中");
        this.staff.add(emp);
    }
    public void addWorker2(Employee emp){
        System.out.println("xlm的原方法正在进行中");
        this.staff.add(emp);
    }

}
