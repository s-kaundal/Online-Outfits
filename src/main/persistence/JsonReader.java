package persistence;

import model.Clothing;
import model.ListOfClothing;
import model.Outfit;
import model.OutfitList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// takes data from a json file and assigns its values to listOfClothing and outfitList
public class JsonReader {
    private String fileName;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String file) {
        this.fileName = file;
    }

    // EFFECTS: reads listOfClothing from file and returns it
    // throws IOException for incorrect reading of file
    public ListOfClothing readListOfClothing() throws IOException {
        String jsonData = readFile(fileName);
        JSONArray jsonArray = new JSONArray(jsonData);
        return getListOfClothingJsonFromArray(jsonArray);
    }

    // EFFECTS: reads outfitList from file and returns it
    // throws IOException for incorrect reading of file
    public OutfitList readOutfitList() throws IOException {
        String jsonData = readFile(fileName);
        JSONArray jsonArray = new JSONArray(jsonData);
        return parseOutfitList(jsonArray);
    }

    // Method taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: finds each data corresponding to a field of Clothing to make full listOfClothing
    private ListOfClothing getListOfClothingJsonFromArray(JSONArray jsonArray) {
        JSONArray json = jsonArray.getJSONArray(0);
        return parseOutfitListOfClothing(json);
    }

    // EFFECTS: finds each data corresponding to a field of Outfit to make full outfitList
    private OutfitList parseOutfitList(JSONArray jsonArray) {
        OutfitList outfits = new OutfitList();
        Outfit outfit = new Outfit();
        JSONArray json = jsonArray.getJSONArray(1);
        for (int i = 0; i < json.length(); i++) {
            JSONObject jsonObjectOutfits = json.getJSONObject(i);
            JSONArray jsonTopList = jsonObjectOutfits.getJSONArray("Tops");
            ListOfClothing topList = parseOutfitListOfClothing(jsonTopList);
            outfit.setTops(topList);
            JSONArray jsonBottomList = jsonObjectOutfits.getJSONArray("Bottoms");
            ListOfClothing bottomList = parseOutfitListOfClothing(jsonBottomList);
            outfit.setBottoms(bottomList);
            outfit.setShoes(jsonObjectOutfits.getInt("Shoe ID"));
            JSONArray jsonAccessoryList = jsonObjectOutfits.getJSONArray("Accessories");
            ListOfClothing accessoryList = parseOutfitListOfClothing(jsonAccessoryList);
            outfit.setAccessories(accessoryList);
            outfits.add(outfit);
        }
        return outfits;
    }

    // EFFECTS:  helper to make lists of clothing from a given json array
    private ListOfClothing parseOutfitListOfClothing(JSONArray jsonArray) {
        ListOfClothing listOfClothing = new ListOfClothing();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            Clothing clothing = new Clothing(o.getInt("ID"), o.getDouble("Price"),
                    o.getString("Store"), o.getString("Type"), o.getString("Description"));
            Clothing.setNextID(o.getInt("Next ID"));
            listOfClothing.addClothing(clothing);
        }
        return listOfClothing;
    }
}
