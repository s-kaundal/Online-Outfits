package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ClothingTest {

    Clothing top;
    Clothing bottom;
    Clothing shoes;
    Clothing accessory;

    @BeforeEach
    public void setup() {
        top = new Clothing();
        bottom = new Clothing(49.99, "American Eagle", "BOTTOM", "Black Jeans");
        shoes = new Clothing(20, "Ecco", "SHOES", "Pink stilettos");
        accessory = new Clothing(3.45, "Lovisa", "ACCESSORY", "Silver ring");
        Clothing.resetNextId();
    }

    @Test
    public void testClothing() {
        assertEquals(0, top.getClothingId());
        assertEquals(0.0, top.getClothingPrice());
        assertNull(top.getClothingStore());
        assertNull(top.getClothingType());
        assertNull(top.getClothingDesc());
    }

    @Test
    public void testOtherClothing() {
        assertEquals(1, bottom.getClothingId());
        assertEquals(49.99, bottom.getClothingPrice());
        assertEquals("BOTTOM", bottom.getClothingType());
        assertEquals("American Eagle", bottom.getClothingStore());
        assertEquals("Black Jeans", bottom.getClothingDesc());
    }

    @Test
    public void testChangePrice() {
        bottom.setPrice(54.99);
        assertEquals(54.99, bottom.getClothingPrice());
    }

    @Test
    public void testChangeStoreName() {
        bottom.setStoreName("Levi's");
        assertEquals("Levi's", bottom.getClothingStore());
    }

    @Test
    public void testChangeTypeName() {
        bottom.setTypeName("Accessory");
        assertEquals("Accessory", bottom.getClothingType());
    }

    @Test
    public void testChangeDescription() {
        bottom.setDescription("Jeans");
        assertEquals("Jeans", bottom.getClothingDesc());
    }

    @Test
    public void testEmptyClothingToJson() {
        JSONObject json = top.toJson();
        String jsonString = "{\"Price\":0,\"Next ID\":0,\"ID\":0}";
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testNotEmptyClothingToJson() {
        JSONObject json = bottom.toJson();
        String jsonString = "{\"Type\":\"BOTTOM\",\"Description\":\"Black Jeans\"," +
                "\"Price\":49.99,\"Next ID\":0,\"Store\":\"American Eagle\",\"ID\":1}";
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testGetPath() {
        top.setTypeName("TOP");
        assertEquals("./data/TOP/" + top.getClothingId() + ".png", top.getPath());
        assertEquals("./data/BOTTOM/" + bottom.getClothingId() + ".png", bottom.getPath());
        assertEquals("./data/SHOES/" + shoes.getClothingId() + ".png", shoes.getPath());
        assertEquals("./data/ACCESSORY/" + accessory.getClothingId() + ".png", accessory.getPath());
        Clothing clothing = new Clothing();
        clothing.setTypeName("top");
        assertNull(clothing.getPath());
    }
}