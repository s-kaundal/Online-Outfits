package model;

import org.json.JSONObject;

import java.util.ArrayList;

public class Outfit {
    // holds all the IDs of the tops, bottoms, shoes, and accessories user wants to group up

    private ListOfClothing topList;
    private ListOfClothing bottomList;
    private int shoes;
    private ListOfClothing accessoryList;

    // MODIFIES: this
    // EFFECTS: constructs new Outfit object with a list of tops, bottoms, accessories, and a pair of shoes
    public Outfit() {
        topList = new ListOfClothing();
        bottomList = new ListOfClothing();
        shoes = -1;
        accessoryList = new ListOfClothing();
    }

    public void setTops(ListOfClothing tops) {
        topList = tops;
    }

    public void setBottoms(ListOfClothing bottoms) {
        bottomList = bottoms;
    }

    public void setAccessories(ListOfClothing accessories) {
        accessoryList = accessories;
    }

    public void setTop(Clothing top) {
        topList.addClothing(top);
    }

    public void setBottom(Clothing bottom) {
        bottomList.addClothing(bottom);
    }

    public void setShoes(int shoeId) {
        shoes = shoeId;
    }

    public void setAccessory(Clothing accessory) {
        accessoryList.addClothing(accessory);
    }

    public ListOfClothing getTops() {
        return topList;
    }

    public ListOfClothing getBottoms() {
        return bottomList;
    }

    public ListOfClothing getAccessories() {
        return accessoryList;
    }

    public int getShoes() {
        return shoes;
    }

    // EFFECTS: returns the list of IDs in either topList, bottomList, or accessoryList
    public ArrayList<Integer> getListIDs(String type) {
        ListOfClothing listOfClothing;
        if (type.equals("TOP")) {
            listOfClothing = topList;
        } else if (type.equals("BOTTOM")) {
            listOfClothing = bottomList;
        } else {
            listOfClothing = accessoryList;
        }
        ArrayList<Integer> idList = new ArrayList<>();
        for (int i = 0; i < listOfClothing.size(); i++) {
            idList.add(listOfClothing.getClothingByIndex(i).getClothingId());
        }
        return idList;
    }

    // EFFECTS: returns the outfit as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Accessories", accessoryList.listOfClothingToJson());
        json.put("Shoe ID", shoes);
        json.put("Bottoms", bottomList.listOfClothingToJson());
        json.put("Tops", topList.listOfClothingToJson());
        return json;
    }
}
