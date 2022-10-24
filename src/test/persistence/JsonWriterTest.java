package persistence;

import model.Clothing;
import model.ListOfClothing;
import model.Outfit;
import model.OutfitList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{
    JsonWriter writer;
    JsonReader reader;

    @Test
    public void testInvalidFileNameWriter() {
        writer = new JsonWriter("./packagenotfound/file.json");
        try {
            writer.setWriter();
            fail("Should produce file not found error.");
        } catch (FileNotFoundException e) {
            // passes test
        }
    }

    @Test
    public void testMakeEmptyFile() {
        ListOfClothing listOfClothing = new ListOfClothing();
        OutfitList outfitList = new OutfitList();
        writer = new JsonWriter("./data/testEmptyFileWriter.json");
        try {
            writer.setWriter();
            writer.writeClothingsAndOutfits(listOfClothing, outfitList);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Should not produce file not found error");
        }
        reader = new JsonReader("./data/testEmptyFileWriter.json");
        try {
            ListOfClothing clothings = reader.readListOfClothing();
            assertEquals(listOfClothing.size(), clothings.size());
        } catch (IOException e) {
            fail("Should not produce IO Exception");
        }
        try {
            OutfitList outfits = reader.readOutfitList();
            assertEquals(outfitList.size(), outfits.size());
        } catch (IOException e) {
            fail("Should not produce IO Exception");
        }
    }

    @Test
    public void testMakeFullFile() {
        Clothing blackTop = new Clothing(10.50, "Zara", "TOP", "Black top");
        Clothing khakis = new Clothing(10.50, "Zara", "BOTTOM", "Khakis");
        Clothing boots = new Clothing(40, "DocMartin's", "SHOES", "Boots");
        Clothing ring = new Clothing(10, "Accessorize", "ACCESSORY", "Ring");
        ListOfClothing listOfClothing = new ListOfClothing();
        listOfClothing.addClothing(blackTop);
        listOfClothing.addClothing(khakis);
        listOfClothing.addClothing(boots);
        listOfClothing.addClothing(ring);
        OutfitList outfitList = new OutfitList();
        Outfit outfit = new Outfit();
        outfit.setTop(blackTop);
        outfit.setBottom(khakis);
        outfit.setShoes(2);
        outfit.setAccessory(ring);
        outfitList.add(outfit);
        writer = new JsonWriter("./data/testFullFileWriter.json");
        try {
            writer.setWriter();
            writer.writeClothingsAndOutfits(listOfClothing, outfitList);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Should not produce file not found error");
        }
        reader = new JsonReader("./data/testFullFileWriter.json");
        try {
            ListOfClothing clothings = reader.readListOfClothing();
            testEveryClothing(listOfClothing, clothings);
        } catch (IOException e) {
            fail("Should not produce IO Exception");
        }
        try {
            OutfitList outfits = reader.readOutfitList();
            testEveryOutfit(outfitList, outfits);
        } catch (IOException e) {
            fail("Should not produce IO Exception");
        }
    }
}
