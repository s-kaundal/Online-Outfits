package model;

import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Clothing {
    // creates a clothing object that has an ID, a type, a price, a store, and a description
    // where ID is the number of clothes that have already been added, type is one of TOP/BOTTOM/SHOES/ACCESSORY
    // store is where the outfit was bought, and description is words to differentiate clothes from each other more

    private static int nextId = 0;
    private int id;
    private double price;
    private String storeName;
    private String typeName;
    private String description;

    // MODIFIES: this
    // EFFECTS: instantiates a Clothing having only ID
    public Clothing() {
        this.id = nextId++;
    }

    // helper for tests
    // REQUIRES: price >= 0, type is one of top, bottom, shoes, accessory
    // MODIFIES: this
    // EFFECTS: instantiates a Clothing with an ID, price, store, and type
    public Clothing(double price, String storeName, String typeName, String description) {
        this.id = nextId++;
        this.price = price;
        this.storeName = storeName;
        this.typeName = typeName;
        this.description = description;
    }

    // helper for json testing
    // REQUIRES: price >= 0, type is one of top, bottom, shoes, accessory
    // MODIFIES: this
    // EFFECTS: instantiates a Clothing with an ID, price, store, and type
    public Clothing(int id, double price, String store, String type, String desc) {
        this.id = id;
        this.price = price;
        storeName = store;
        typeName = type;
        description = desc;
    }

    // REQUIRES: amount >= 0
    public void setPrice(double amount) {
        price = amount;
    }

    public void setStoreName(String store) {
        storeName = store;
    }

    public void setTypeName(String type) {
        typeName = type;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    // EFFECTS: makes a unique file location string for this clothing
    public String getPath() {
        if (getClothingType().equals("TOP")) {
            return "./data/TOP/" + id + ".png";
        } else if (getClothingType().equals("BOTTOM")) {
            return "./data/BOTTOM/" + id + ".png";
        } else if (getClothingType().equals("SHOES")) {
            return "./data/SHOES/" + id + ".png";
        } else if (getClothingType().equals("ACCESSORY")) {
            return "./data/ACCESSORY/" + id + ".png";
        }
        return null;
    }

    public double getClothingPrice() {
        return price;
    }

    public String getClothingStore() {
        return storeName;
    }

    public String getClothingType() {
        return typeName;
    }

    public String getClothingDesc() {
        return description;
    }

    public int getClothingId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextID(int nxtID) {
        nextId = nxtID;
    }

    // helper for tests
    // EFFECTS: resets nextId
    public static void resetNextId() {
        nextId = 0;
    }

    // EFFECTS : saves each field in a clothing as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ID", id);
        json.put("Next ID", nextId);
        json.put("Price", price);
        json.put("Store", storeName);
        json.put("Type", typeName);
        json.put("Description", description);
        return json;
    }
}
