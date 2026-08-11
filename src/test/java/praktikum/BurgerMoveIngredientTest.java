package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BurgerMoveIngredientTest {

    private final int from;
    private final int to;
    private final List<String> expectedOrder;

    public BurgerMoveIngredientTest(int from, int to, List<String> expectedOrder) {
        this.from = from;
        this.to = to;
        this.expectedOrder = expectedOrder;
    }

    @Parameterized.Parameters(name = "move from {0} to {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 2, Arrays.asList("sour cream", "cutlet", "hot sauce", "cheese")},
                {2, 0, Arrays.asList("cutlet", "hot sauce", "sour cream", "cheese")},
                {1, 1, Arrays.asList("hot sauce", "sour cream", "cutlet", "cheese")},
                {3, 0, Arrays.asList("cheese", "hot sauce", "sour cream", "cutlet")},
                {0, 3, Arrays.asList("sour cream", "cutlet", "cheese", "hot sauce")},
        });
    }

    @Test
    public void moveIngredientReordersListCorrectly() {
        Burger burger = new Burger();
        Ingredient hotSauce = new Ingredient(IngredientType.SAUCE, "hot sauce", 10);
        Ingredient sourCream = new Ingredient(IngredientType.SAUCE, "sour cream", 20);
        Ingredient cutlet = new Ingredient(IngredientType.FILLING, "cutlet", 30);
        Ingredient cheese = new Ingredient(IngredientType.FILLING, "cheese", 40);
        burger.addIngredient(hotSauce);
        burger.addIngredient(sourCream);
        burger.addIngredient(cutlet);
        burger.addIngredient(cheese);

        burger.moveIngredient(from, to);

        List<String> actualOrder = burger.ingredients.stream()
                .map(Ingredient::getName)
                .collect(java.util.stream.Collectors.toList());
        assertEquals(expectedOrder, actualOrder);
    }

}
