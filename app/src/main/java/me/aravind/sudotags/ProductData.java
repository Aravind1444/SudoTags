package me.aravind.sudotags;

public class ProductData {

    //this file is for pushing the data to firebase realtime db

    String productNameDB;
    String userIDDB;
    String userNameDB;
    String userEmailDB;


    public ProductData(String productNameDB, String userIDDB, String userNameDB, String userEmailDB) {
        this.productNameDB = productNameDB;
        this.userIDDB = userIDDB;
        this.userNameDB = userNameDB;
        this.userEmailDB = userEmailDB;
    }

    public String getProductNameDB() {
        return productNameDB;
    }

    public String getUserIDDB() {
        return userIDDB;
    }

    public String getUserNameDB() {
        return userNameDB;
    }

    public String getUserEmailDB() {
        return userEmailDB;
    }
}
