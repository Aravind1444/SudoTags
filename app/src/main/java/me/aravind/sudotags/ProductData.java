package me.aravind.sudotags;

public class ProductData {

    String productNameDB;
    String uniqueIDDB;
    String userIDDB;


    public ProductData(String productNameDB, String uniqueIDDB, String userIDDB) {
        this.productNameDB = productNameDB;
        this.uniqueIDDB = uniqueIDDB;
        this.userIDDB = userIDDB;
    }

    public String getProductNameDB() {
        return productNameDB;
    }

    public String getUniqueIDDB() {
        return uniqueIDDB;
    }

    public String getUserIDDB() {
        return userIDDB;
    }
}
