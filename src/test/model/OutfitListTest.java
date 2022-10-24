package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutfitListTest {
    private OutfitList noOutfitList;
    private OutfitList outfitBlackTest;
    private OutfitList outfitRedTest;
    private Outfit outfitBlack;
    private Outfit outfitRed;
    private Clothing blackTop;
    private Clothing blackBottom;
    private Clothing blackShoes;
    private Clothing blackAccessories;
    private Clothing redTop;
    private Clothing redBottom;
    private Clothing redShoes;
    private Clothing redAccessories;

    @BeforeEach
    public void setup() {
        outfitBlack = new Outfit();
        outfitRed = new Outfit();
        noOutfitList = new OutfitList();
        outfitBlackTest = new OutfitList();
        outfitRedTest = new OutfitList();
        blackTop = new Clothing(20, "Zara", "TOP", "Black top");
        blackBottom = new Clothing(20, "Zara", "BOTTOM", "Black bottom");
        blackShoes = new Clothing(20, "Zara", "TOP", "Black shoes");
        blackAccessories = new Clothing(20, "Zara", "ACCESSORY", "Black accessory");
        redTop = new Clothing(20, "Zara", "TOP", "Red top");
        redBottom = new Clothing(20, "Zara", "BOTTOM", "Red bottom");
        redShoes = new Clothing(20, "Zara", "TOP", "Red shoes");
        redAccessories = new Clothing(20, "Zara", "ACCESSORY", "Red accessory");
        outfitBlack.setTop(blackTop);
        outfitBlack.setBottom(blackBottom);
        outfitBlack.setShoes(blackShoes.getClothingId());
        outfitBlack.setAccessory(blackAccessories);
        outfitRed.setTop(redTop);
        outfitRed.setTop(blackTop);
        outfitRed.setBottom(redBottom);
        outfitRed.setShoes(redShoes.getClothingId());
        outfitRed.setAccessory(redAccessories);
        outfitBlackTest.add(outfitBlack);
        outfitRedTest.add(outfitRed);
        Clothing.resetNextId();
    }

    @Test
    public void testOutfitList() {
        assertEquals(0, noOutfitList.size());
        assertEquals(1, outfitBlackTest.size());
        assertEquals(1, outfitRedTest.size());
        assertEquals(outfitBlack, outfitBlackTest.getOutfit(0));
        assertEquals(outfitRed, outfitRedTest.getOutfit(0));
    }

    @Test
    public void testOneOutfitAdd() {
        noOutfitList.add(outfitBlack);
        assertEquals(outfitBlack, noOutfitList.getOutfit(0));
    }

    @Test
    public void testTwoOutfitAdd() {
        noOutfitList.add(outfitBlack);
        noOutfitList.add(outfitRed);
        assertEquals(outfitBlack, noOutfitList.getOutfit(0));
        assertEquals(outfitRed, noOutfitList.getOutfit(1));
    }

    @Test
    public void testRemoveOutfit() {
        outfitBlackTest.remove(0);
        assertEquals(0, outfitBlackTest.size());
    }

    @Test
    public void testEmptyListOfClothingToJson() {
        JSONArray json = noOutfitList.outfitListToJson();
        String jsonString = "[]";
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testBlackOutfitListToJson() {
        String jsonString = "[{\"Accessories\":[{\"Type\":\"ACCESSORY\"," +
                "\"Description\":\"Black accessory\",\"Price\":20,\"Next ID\":0," +
                "\"Store\":\"Zara\",\"ID\":3}],\"Bottoms\":[{\"Type\":\"BOTTOM\"," +
                "\"Description\":\"Black bottom\",\"Price\":20,\"Next ID\":0," +
                "\"Store\":\"Zara\",\"ID\":1}],\"Shoe ID\":2,\"Tops\":[{\"Type\":\"TOP\"," +
                "\"Description\":\"Black top\",\"Price\":20,\"Next ID\":0,\"Store\":\"Zara\",\"ID\":0}]}]";
        JSONArray json = outfitBlackTest.outfitListToJson();
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testRedOutfitListToJson() {
        String jsonString = "[{\"Accessories\":[{\"Type\":\"ACCESSORY\",\"Description\":\"Red accessory\"," +
                "\"Price\":20,\"Next ID\":0,\"Store\":\"Zara\",\"ID\":7}]," +
                "\"Bottoms\":[{\"Type\":\"BOTTOM\",\"Description\":\"Red bottom\",\"Price\":20,\"Next ID\":0," +
                "\"Store\":\"Zara\",\"ID\":5}],\"Shoe ID\":6,\"Tops\":[{\"Type\":\"TOP\"," +
                "\"Description\":\"Red top\",\"Price\":20,\"Next ID\":0,\"Store\":\"Zara\",\"ID\":4}," +
                "{\"Type\":\"TOP\",\"Description\":\"Black top\",\"Price\":20,\"Next ID\":0," +
                "\"Store\":\"Zara\",\"ID\":0}]}]";
        JSONArray json = outfitRedTest.outfitListToJson();
        assertEquals(jsonString, json.toString());
    }
}
