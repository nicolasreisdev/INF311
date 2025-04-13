package com.example.imc;

import java.io.Serializable;

public class IMC implements Serializable {

    private String name;
    private int age;
    private double weight;
    private double height;
    private double IMC;

    public IMC(String name, int age, double weight, double height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    protected void calcIMC(){
        IMC = weight / (height * height);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getIMC() {
        calcIMC();
        return IMC;
    }



}
