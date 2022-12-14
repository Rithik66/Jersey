package com.rithik.zomazon.model;

public class Merchant {
    int merchent_id;
    String merchent_name;
    String merchant_email;

    private Merchant() {
    }

    public Merchant(int merchent_id, String merchent_name, String merchant_email) {
        this.merchent_id = merchent_id;
        this.merchent_name = merchent_name;
        this.merchant_email = merchant_email;
    }

    public int getMerchent_id() {
        return merchent_id;
    }

    public void setMerchent_id(int merchent_id) {
        this.merchent_id = merchent_id;
    }

    public String getMerchent_name() {
        return merchent_name;
    }

    public void setMerchent_name(String merchent_name) {
        this.merchent_name = merchent_name;
    }

    public String getMerchant_email() {
        return merchant_email;
    }

    public void setMerchant_email(String merchant_email) {
        this.merchant_email = merchant_email;
    }

    @Override
    public String toString() {
        return "{" +
                "merchent_id=" + merchent_id +
                ", \nmerchent_name='" + merchent_name + '\'' +
                ", \nmerchant_email='" + merchant_email + '\'' +
                '}';
    }
}
