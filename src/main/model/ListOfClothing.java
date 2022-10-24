package model;

import org.json.JSONArray;

import java.util.ArrayList;

public class ListOfClothing {
    // makes a list of clothes being object Clothing, user can add to the list, remove a certain clothing (given the
    // ID of the clothing), get a list of similar type/price/store bought of clothing in the list, get a certain
    // clothing given its ID

    private ArrayList<Clothing> listOfClothing;

    // MODIFIES: this
    // EFFECTS: makes empty list of clothing
    public ListOfClothing() {
        listOfClothing = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds clothing item to list of clothing item
    public void addClothing(Clothing clothing) {
        listOfClothing.add(clothing);
        EventLog.getInstance().logEvent(new Event("Added clothing from list."));
    }

    // REQUIRES: size of listOfClothing cannot be zero
    // MODIFIES: this
    // EFFECTS: remove clothing item with id
    public void removeClothing(int id) {
        int index = 0;
        for (Clothing clothing : listOfClothing) {
            if (clothing.getClothingId() == id) {
                listOfClothing.remove(index);
                EventLog.getInstance().logEvent(new Event("Removed clothing from list."));
                break;
            }
            index++;
        }
    }

    // REQUIRES: listOfClothing is not empty
    // EFFECTS: return all clothing in list with the same price, if none found return empty
    public ListOfClothing returnSamePrice(double price) {
        ListOfClothing samePriceListOfClothing = new ListOfClothing();
        for (Clothing clothing : listOfClothing) {
            if (clothing.getClothingPrice() == price) {
                samePriceListOfClothing.addClothing(clothing);
            }
        }
        return samePriceListOfClothing;
    }

    // EFFECTS: return all clothing in list with the same price, if none found return empty
    public ListOfClothing returnSameStore(String storeName) {
        ListOfClothing sameStoreListOfClothing = new ListOfClothing();
        for (Clothing clothing : listOfClothing) {
            if (clothing.getClothingStore().equals(storeName)) {
                sameStoreListOfClothing.addClothing(clothing);
            }
        }
        return sameStoreListOfClothing;
    }

    // EFFECTS: return all clothing of same type, if none found return empty
    public ListOfClothing returnSameType(String typeName) {
        ListOfClothing sameTypeListOfClothing = new ListOfClothing();
        for (Clothing clothing : listOfClothing) {
            if (clothing.getClothingType().equals(typeName)) {
                sameTypeListOfClothing.addClothing(clothing);
            }
        }
        return sameTypeListOfClothing;
    }

    // REQUIRES: list not empty
    // EFFECTS: returns the Clothing with that index in listOfClothing
    public Clothing getClothing(int id) {
        int index = 0;
        for (Clothing clothing : listOfClothing) {
            if (clothing.getClothingId() == id) {
                return listOfClothing.get(index);
            }
            index++;
        }
        return null;
    }

    // REQUIRES: list not empty, 0 <= index < listOfClothing size or index = -1
    // EFFECTS: returns the Clothing with that index in listOfClothing
    public Clothing getClothingByIndex(int index) {
        return listOfClothing.get(index);
    }

    // EFFECTS: returns size of listOfClothing
    public int size() {
        return listOfClothing.size();
    }

    // EFFECTS: saves each clothing in list as json into a json array
    public JSONArray listOfClothingToJson() {
        JSONArray json = new JSONArray();
        for (Clothing c : listOfClothing) {
            json.put(c.toJson());
        }
        return json;
    }
}
