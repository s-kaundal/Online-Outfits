package model;

import org.json.JSONArray;

import java.util.ArrayList;

// object for a list of outfits
public class OutfitList {
    private ArrayList<Outfit> outfitList;

    // MODIFIES: this
    // EFFECTS: constructs an arraylist of Outfit
    public OutfitList() {
        outfitList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an outfit to the list
    public void add(Outfit outfit) {
        outfitList.add(outfit);
        EventLog.getInstance().logEvent(new Event("Added outfit to list."));
    }

    // MODIFIES: this
    // EFFECTS: removes an outfit given its index
    public void remove(int index) {
        outfitList.remove(index);
        EventLog.getInstance().logEvent(new Event("Removed outfit from list."));
    }

    // EFFECTS: returns the size of the outfit list
    public int size() {
        return outfitList.size();
    }

    public Outfit getOutfit(int index) {
        return outfitList.get(index);
    }

    // EFFECTS: saves each outfit in list as json into a json array
    public JSONArray outfitListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Outfit o : outfitList) {
            jsonArray.put(o.toJson());
        }
        return jsonArray;
    }
}
