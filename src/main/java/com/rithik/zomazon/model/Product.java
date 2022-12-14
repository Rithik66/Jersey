package com.rithik.zomazon.model;

public class Product {
    String product_Name;
    String product_Id;
    double product_Price;
    int product_Quantity;

    private Product(){}

    public Product(String product_Name, String product_Id, double product_Price, int product_Quantity) {
        this.product_Name = product_Name;
        this.product_Id = product_Id;
        this.product_Price = product_Price;
        this.product_Quantity = product_Quantity;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getProduct_Id() {
        return product_Id;
    }

    public void setProduct_Id(String product_Id) {
        this.product_Id = product_Id;
    }

    public double getProduct_Price() {
        return product_Price;
    }

    public void setProduct_Price(double product_Price) {
        this.product_Price = product_Price;
    }

    public int getProduct_Quantity() {
        return product_Quantity;
    }

    public void setProduct_Quantity(int product_Quantity) {
        this.product_Quantity = product_Quantity;
    }
}
