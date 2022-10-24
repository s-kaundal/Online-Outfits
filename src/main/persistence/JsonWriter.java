package persistence;

import model.ListOfClothing;
import model.OutfitList;
import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// writes data into a json file as a json array
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String saveFileLocation;

    // MODIFIES : this
    // EFFECTS: sets given file location to saveFileLocation
    public JsonWriter(String fileLocation) {
        saveFileLocation = fileLocation;
    }

    // MODIFIES: this
    // EFFECTS: constructs PrintWriter object pointing to saveFileLocation file
    // throws FileNotFoundException if file is not in code files
    public void setWriter() throws FileNotFoundException {
        writer = new PrintWriter(saveFileLocation);
    }

    // MODIFIES: this
    // EFFECTS: writes list of clothing and outfit list both as json arrays into file as a json array
    public void writeClothingsAndOutfits(ListOfClothing listOfClothing, OutfitList outfitList) {
        JSONArray clothingAndOutfitList = new JSONArray();
        clothingAndOutfitList.put(listOfClothing.listOfClothingToJson());
        clothingAndOutfitList.put(outfitList.outfitListToJson());
        writer.print(clothingAndOutfitList.toString(TAB));
    }

    // EFFECTS: stops writing into file
    public void close() {
        writer.close();
    }
}