package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListOfClothingTest {

    private ListOfClothing emptyList;
    private ListOfClothing oneTop;
    private ListOfClothing oneBottom;
    private ListOfClothing twoTopsTwoShoes;
    private ListOfClothing threeAccessories;
    private Clothing blackTop;
    private Clothing redTop;
    private Clothing khakis;
    private Clothing sneakers;
    private Clothing boots;
    private Clothing pearlNecklace;
    private Clothing ring;
    private Clothing whiteSocks;

    @BeforeEach
    public void setup() {
        Clothing.resetNextId();
        emptyList = new ListOfClothing();
        oneTop = new ListOfClothing();
        oneBottom = new ListOfClothing();
        twoTopsTwoShoes = new ListOfClothing();
        threeAccessories = new ListOfClothing();
        blackTop = new Clothing(10.50, "Zara", "top", "Black Top");
        redTop = new Clothing(20.55, "H&M", "top", "Red Top");
        khakis = new Clothing(10.50, "Zara", "bottom", "Khakis");
        sneakers = new Clothing(20.55, "Sketchers", "shoes", "Sneakers");
        boots = new Clothing(40, "DocMartin's", "shoes", "Boots");
        pearlNecklace = new Clothing(5.45, "Accessorize", "accessory",
                "Pearl Necklace");
        ring = new Clothing(10, "Accessorize", "accessory", "Ring");
        whiteSocks = new Clothing(5.45, "Ecco", "accessory", "White Socks");
        oneTop.addClothing(blackTop);
        oneBottom.addClothing(khakis);
        twoTopsTwoShoes.addClothing(blackTop);
        twoTopsTwoShoes.addClothing(redTop);
        twoTopsTwoShoes.addClothing(sneakers);
        twoTopsTwoShoes.addClothing(boots);
        threeAccessories.addClothing(pearlNecklace);
        threeAccessories.addClothing(ring);
        threeAccessories.addClothing(whiteSocks);
        Clothing.resetNextId();
    }

    @Test
    public void testListOfClothing() {
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testOneAddClothing() {
        emptyList.addClothing(whiteSocks);
        assertEquals(whiteSocks, emptyList.getClothing(7));
        assertEquals(1, emptyList.size());
    }

    @Test
    public void testTwiceAddClothing() {
        emptyList.addClothing(whiteSocks);
        emptyList.addClothing(khakis);
        assertEquals(khakis, emptyList.getClothing(2));
        assertEquals(whiteSocks, emptyList.getClothing(7));
        assertEquals(2, emptyList.size());
    }

    @Test
    public void testNotEmptyListAddClothing() {
        oneBottom.addClothing(redTop);
        assertEquals(redTop, oneBottom.getClothing(1));
        assertEquals(2, oneBottom.size());
    }

    @Test
    public void testOneLastClothingRemoveClothing() {
        oneTop.removeClothing(0);
        assertEquals(0, oneTop.size());
    }

    @Test
    public void testOneFirstClothingRemoveClothing() {
        twoTopsTwoShoes.removeClothing(0);
        assertEquals(3, twoTopsTwoShoes.size());
        assertEquals(redTop, twoTopsTwoShoes.getClothing(1));
    }

    @Test
    public void testMoreThanOneClothingRemoveClothing() {
        threeAccessories.removeClothing(7);
        assertEquals(2, threeAccessories.size());
        assertEquals(ring, threeAccessories.getClothing(6));
        assertEquals(pearlNecklace, threeAccessories.getClothing(5));
        threeAccessories.removeClothing(5);
        assertEquals(ring, threeAccessories.getClothing(6));
    }

    @Test
    public void testNotRemoveClothing() {
        threeAccessories.removeClothing(0);
        assertEquals(3, threeAccessories.size());
    }

    @Test
    public void testNotFindIndexGetClothing() {
        assertNull(threeAccessories.getClothing(0));
    }

    @Test
    public void testOneClothingListReturnSamePrice() {
        ListOfClothing samePrice = oneTop.returnSamePrice(10.50);
        Clothing clothing = samePrice.getClothing(0);
        assertEquals(0, clothing.getClothingId());
    }

    @Test
    public void testOneClothingReturnEmptyReturnSamePrice() {
        ListOfClothing samePrice = oneTop.returnSamePrice(10);
        assertEquals(0, samePrice.size());
    }

    @Test
    public void testManyClothingReturnManyReturnSamePrice() {
        ListOfClothing samePrice = twoTopsTwoShoes.returnSamePrice(20.55);
        Clothing clothing = samePrice.getClothing(1);
        assertEquals(1, clothing.getClothingId());
        clothing = samePrice.getClothing(3);
        assertEquals(3, clothing.getClothingId());
    }

    @Test
    public void testOneClothingListReturnSameType() {
        ListOfClothing sameType = oneTop.returnSameType("top");
        Clothing clothing = sameType.getClothing(0);
        assertEquals(0, clothing.getClothingId());
    }

    @Test
    public void testOneClothingReturnEmptyReturnSameType() {
        ListOfClothing sameType = oneBottom.returnSameType("accessory");
        assertEquals(0, sameType.size());
    }

    @Test
    public void testManyClothingReturnManyReturnSameType() {
        ListOfClothing sameType = twoTopsTwoShoes.returnSameType("shoes");
        Clothing clothing = sameType.getClothing(3);
        assertEquals(3, clothing.getClothingId());
        clothing = sameType.getClothing(4);
        assertEquals(4, clothing.getClothingId());
    }

    @Test
    public void testOneClothingListReturnSameStore() {
        ListOfClothing sameStore = oneTop.returnSameStore("Zara");
        Clothing clothing = sameStore.getClothing(0);
        assertEquals(0, clothing.getClothingId());
    }

    @Test
    public void testOneClothingReturnEmptyReturnSameStore() {
        ListOfClothing sameStore = threeAccessories.returnSameStore("Lovisa");
        assertEquals(0, sameStore.size());
    }

    @Test
    public void testManyClothingReturnManyReturnSameStore() {
        ListOfClothing sameStore = threeAccessories.returnSameStore("Accessorize");
        Clothing clothing = sameStore.getClothing(5);
        assertEquals(5, clothing.getClothingId());
        clothing = sameStore.getClothing(6);
        assertEquals(6, clothing.getClothingId());
    }

    @Test
    public void testEmptyListOfClothingToJson() {
        JSONArray json = emptyList.listOfClothingToJson();
        String jsonString = "[]";
        assertEquals(jsonString, json.toString());
    }

    @Test
    public void testThreeAccessoriesListOfClothingToJson() {
        JSONArray json = threeAccessories.listOfClothingToJson();
        String jsonString = "[{\"Type\":\"accessory\",\"Description\":\"Pearl Necklace\",\"Price\":5.45," +
                "\"Next ID\":0,\"Store\":\"Accessorize\",\"ID\":5},{\"Type\":\"accessory\"," +
                "\"Description\":\"Ring\",\"Price\":10,\"Next ID\":0,\"Store\":\"Accessorize\",\"ID\":6}," +
                "{\"Type\":\"accessory\",\"Description\":\"White Socks\",\"Price\":5.45,\"Next ID\":0," +
                "\"Store\":\"Ecco\",\"ID\":7}]";
        assertEquals(jsonString, json.toString());
    }
}