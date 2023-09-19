/*
 * Copyright 2023 The Bank of New York Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bnymellon.codekatas.coffeeshopkata;

import bnymellon.codekatas.coffeeshopkata.food.Bagel;
import bnymellon.codekatas.coffeeshopkata.food.Cookie;
import bnymellon.codekatas.coffeeshopkata.food.Donut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bnymellon.codekatas.coffeeshopkata.food.BagelType.EVERYTHING;
import static bnymellon.codekatas.coffeeshopkata.food.CookieType.CHOCOLATE_CHIP;
import static bnymellon.codekatas.coffeeshopkata.food.DonutType.GLAZED;
import static bnymellon.codekatas.coffeeshopkata.food.SpreadType.HERB_GARLIC_CREAM_CHEESE;
import static org.junit.jupiter.api.Assertions.*;

class CoffeeShopTest
{
    private List<Item> itemList;
    private CoffeeShopOrder coffeeShopOrder;
    private Bagel bagel1;
    private Cookie cookie1;
    private Donut donut1;

    @BeforeEach
    public void setUp()
    {
        bagel1 = new Bagel(EVERYTHING, HERB_GARLIC_CREAM_CHEESE, true);
        cookie1 = new Cookie(CHOCOLATE_CHIP, true);
        donut1 = new Donut(GLAZED);
        itemList = new ArrayList<>();
        itemList.add(bagel1);
        itemList.add(cookie1);
        itemList.add(donut1);
        coffeeShopOrder = new CoffeeShopOrder("Emilie", itemList);
    }

    @Test
    public void getFoodItemsForOrderTest()
    {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("CHOCOLATE_CHIP cookie");
        expected.add("EVERYTHING bagel with HERB_GARLIC_CREAM_CHEESE");
        expected.add("GLAZED donut");
        List<String> actual = coffeeShopOrder.getFoodItemsForOrder();
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void generateReceiptTest()
    {
        String expectedReceipt =
                "Bagel: EVERYTHING $2.5\n" +
                        "Cookie: CHOCOLATE_CHIP $1.25\n" +
                        "Donut: GLAZED $1.75\n" +
                        "Total: $5.5";
        assertEquals(expectedReceipt, coffeeShopOrder.generateReceipt());
    }

    @Test
    public void testBagelClass()
    {
        Bagel bagel2 = new Bagel(EVERYTHING, HERB_GARLIC_CREAM_CHEESE, true);
        assertEquals(bagel1, bagel2);
        assertEquals("Bagel[bagelType=EVERYTHING, spreadType=HERB_GARLIC_CREAM_CHEESE, toasted=true]", bagel1.toString());
    }

    @Test
    public void testBagelGetters()
    {
        assertTrue(bagel1.isToasted());
        assertEquals(bagel1.getBagelType(), EVERYTHING);
        assertEquals(bagel1.getSpreadType(), HERB_GARLIC_CREAM_CHEESE);
    }

    @Test
    public void getDrinkItems()
    {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("HOT Americano");
        expected.add("HOT CARAMEL Latte with ALMOND_MILK");
        expected.add("HOT VANILLA Macchiato with WHOLE_MILK");
        expected.add("MATCHA Tea");
        assertEquals(expected, coffeeShopOrder.getDrinkForOrder());
    }
}
