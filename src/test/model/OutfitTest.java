package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OutfitTest {

    private Outfit outfitManyClothing;
    private Outfit outfitOneEach;
    private Clothing blackTop;
    private Clothing anotherTop;
    private Clothing redBottom;
    private Clothing anotherBottom;
    private Clothing accessory;
    private Clothing anotherAccessory;

    @BeforeEach
    public void setup() {
        outfitOneEach = new Outfit();
        outfitManyClothing = new Outfit();
        blackTop = new Clothing(20, "Zara", "TOP", "Black top");
        anotherTop = new Clothing(30, "H&M", "TOP", "Another top");
        redBottom = new Clothing(20, "Zara", "BOTTOM", "Red bottom");
        anotherBottom = new Clothing(30, "H&M", "BOTTOM", "Another bottom");
        accessory = new Clothing(20, "Zara", "ACCESSORY", "A ring");
        anotherAccessory = new Clothing(10, "H&M", "ACCESSORY", "A necklace");
        outfitManyClothing.setTop(blackTop);
        outfitManyClothing.setTop(anotherTop);
        outfitManyClothing.setBottom(redBottom);
        outfitManyClothing.setBottom(anotherBottom);
        outfitManyClothing.setShoes(8);
        outfitManyClothing.setAccessory(accessory);
        outfitManyClothing.setAccessory(anotherAccessory);
        outfitOneEach.setTop(blackTop);
        outfitOneEach.setBottom(redBottom);
        outfitOneEach.setShoes(10);
        outfitOneEach.setAccessory(accessory);
        Clothing.resetNextId();
    }

    @Test
    public void testEmptyOutfit() {
        Outfit outfit = new Outfit();
        assertEquals(0, outfit.getTops().size());
        assertEquals(0, outfit.getBottoms().size());
        assertEquals(-1, outfit.getShoes());
        assertEquals(0, outfit.getAccessories().size());
    }

    @Test
    public void testOneSetTop() {
        assertEquals(blackTop, outfitManyClothing.getTops().getClothing(0));
    }

    @Test
    public void testTwoSetTop() {
        assertEquals(anotherTop, outfitManyClothing.getTops().getClothing(1));
    }

    @Test
    public void testOneSetBottom() {
        assertEquals(redBottom, outfitManyClothing.getBottoms().getClothing(2));
    }

    @Test
    public void testTwoSetBottom() {
        assertEquals(anotherBottom, outfitManyClothing.getBottoms().getClothing(3));
    }

    @Test
    public void testSetShoes() {
        assertEquals(8, outfitManyClothing.getShoes());
    }

    @Test
    public void testOneSetAccessory() {
        assertEquals(accessory, outfitManyClothing.getAccessories().getClothing(4));
    }

    @Test
    public void testTwoSetAccessory() {
        assertEquals(anotherAccessory, outfitManyClothing.getAccessories().getClothing(5));
    }

    @Test
    public void testEmptyOutfitToJson() {
        JSONObject json = new Outfit().toJson();
        String jsonString = "{\"Accessories\":[],\"Bottoms\":[],\"Shoe ID\":-1,\"Tops\":[]}";
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testOneOutfitToJson() {
        String jsonString = "{\"Accessories\":[{\"Type\":\"ACCESSORY\",\"Description\":\"A ring\",\"Price\":20," +
                "\"Next ID\":0,\"Store\":\"Zara\",\"ID\":4},{\"Type\":\"ACCESSORY\",\"Description\":\"A necklace\"," +
                "\"Price\":10,\"Next ID\":0,\"Store\":\"H&M\",\"ID\":5}],\"Bottoms\":[{\"Type\":\"BOTTOM\"," +
                "\"Description\":\"Red bottom\",\"Price\":20,\"Next ID\":0,\"Store\":\"Zara\",\"ID\":2}," +
                "{\"Type\":\"BOTTOM\",\"Description\":\"Another bottom\",\"Price\":30,\"Next ID\":0,\"Store\":\"H&M\"," +
                "\"ID\":3}],\"Shoe ID\":8,\"Tops\":[{\"Type\":\"TOP\",\"Description\":\"Black top\",\"Price\":20," +
                "\"Next ID\":0,\"Store\":\"Zara\",\"ID\":0},{\"Type\":\"TOP\",\"Description\":\"Another top\"," +
                "\"Price\":30,\"Next ID\":0,\"Store\":\"H&M\",\"ID\":1}]}";
        JSONObject json = outfitManyClothing.toJson();
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testGetListIDs(){
        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        assertEquals(intList, outfitManyClothing.getListIDs("TOP"));
        intList = new ArrayList<>();
        intList.add(2);
        intList.add(3);
        assertEquals(intList, outfitManyClothing.getListIDs("BOTTOM"));
        intList = new ArrayList<>();
        intList.add(4);
        intList.add(5);
        assertEquals(intList, outfitManyClothing.getListIDs("ACCESSORY"));
    }
}
