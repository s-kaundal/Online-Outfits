package persistence;

import model.Clothing;
import model.ListOfClothing;
import model.Outfit;
import model.OutfitList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {

    protected void testEachClothing(int id, int nextId, double price, String store, String type, String description
            , Clothing clothing) {
        assertEquals(id, clothing.getClothingId());
        assertEquals(nextId, Clothing.getNextId());
        assertEquals(price, clothing.getClothingPrice());
        assertEquals(store, clothing.getClothingStore());
        assertEquals(type, clothing.getClothingType());
        assertEquals(description, clothing.getClothingDesc());
    }

    protected void testEveryClothing(ListOfClothing originalListOfClothing, ListOfClothing newListOfClothing) {
        if (originalListOfClothing.size() != newListOfClothing.size()) {
            fail("Should have the same length since they're the same list.");
        } else {
            for (int i = 0; i < originalListOfClothing.size(); i++) {
                int originalID = originalListOfClothing.getClothingByIndex(i).getClothingId();
                int newId = newListOfClothing.getClothingByIndex(i).getClothingId();
                assertEquals(originalID, newId);
            }
        }
    }

    protected void testEveryOutfit(OutfitList originalOutfitList, OutfitList newOutfitList) {
        if (originalOutfitList.size() != newOutfitList.size()) {
            fail("Should have the same length since they're the same list.");
        } else {
            for (int i = 0; i < originalOutfitList.size(); i++) {
                Outfit originalOutfit = originalOutfitList.getOutfit(i);
                Outfit newOutfit = newOutfitList.getOutfit(i);
                testEveryClothing(originalOutfit.getTops(), newOutfit.getTops());
                testEveryClothing(originalOutfit.getBottoms(), newOutfit.getBottoms());
                assertEquals(originalOutfit.getShoes(), newOutfit.getShoes());
                testEveryClothing(originalOutfit.getAccessories(), newOutfit.getAccessories());
            }
        }
    }
}
