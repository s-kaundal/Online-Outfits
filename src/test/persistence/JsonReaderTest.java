package persistence;

import model.ListOfClothing;
import model.OutfitList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    private JsonReader reader;

    @Test
    public void testFileNotFound() {
        reader = new JsonReader("./data/noFile.json");
        try {
            OutfitList outfitList = reader.readOutfitList();
            fail("Needs to output IOException");
        } catch (IOException exception) {
            // passes test
        }
        try {
            ListOfClothing listOfClothing = reader.readListOfClothing();
            fail("Needs to output IOException");
        } catch (IOException e) {
            // passes test
        }
    }

    @Test
    public void testEmptyReader() {
        reader = new JsonReader("./data/testEmptyFileReader.json");
        try {
            ListOfClothing listOfClothing = reader.readListOfClothing();
            assertEquals(0, listOfClothing.size());
        } catch (IOException e) {
            fail("Needs to read listOfClothing");
        }
        try {
            OutfitList outfitList = reader.readOutfitList();
            assertEquals(0, outfitList.size());
        } catch (IOException e) {
            fail("Needs to read outfitList");
        }
    }

    @Test
    public void testFullReader() {
        reader = new JsonReader("./data/testFullFileReader.json");
        try {
            ListOfClothing listOfClothing = reader.readListOfClothing();
            testEachClothing(0, 4, 10.50, "Zara", "TOP", "A striped tee"
                    , listOfClothing.getClothingByIndex(0));
            testEachClothing(1,4, 50, "Aritzia", "BOTTOM", "Blue jeans"
                    , listOfClothing.getClothingByIndex(1));
            testEachClothing(2, 4, 29.99, "Sketchers", "SHOES", "Black sneakers"
                    , listOfClothing.getClothingByIndex(2));
            testEachClothing(3, 4, 0, "Gift", "ACCESSORY", "Pearl necklace"
                    , listOfClothing.getClothingByIndex(3));
        } catch (IOException e) {
            fail("Needs to read listOfClothing");
        }
        try {
            OutfitList outfitList = reader.readOutfitList();
            testEachClothing(0, 4, 10.50, "Zara", "TOP", "A striped tee"
                    , outfitList.getOutfit(0).getTops().getClothingByIndex(0));
            testEachClothing(1,4, 50, "Aritzia", "BOTTOM", "Blue jeans"
                    , outfitList.getOutfit(0).getBottoms().getClothingByIndex(0));
            assertEquals(2, outfitList.getOutfit(0).getShoes());
            testEachClothing(3, 4, 0, "Gift", "ACCESSORY", "Pearl necklace"
                    , outfitList.getOutfit(0).getAccessories().getClothingByIndex(0));
        } catch (IOException e) {
            fail("Needs to read outfitList");
        }
    }
}
