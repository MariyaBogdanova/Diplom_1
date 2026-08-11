package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

public class BurgerTest {

    @Mock
    private Bun bun;

    @Mock
    private Ingredient sauce;

    @Mock
    private Ingredient filling;

    private Burger burger;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsSetsBun() {
        burger.setBuns(bun);

        assertSame(bun, burger.bun);
    }

    @Test
    public void addIngredientIncreasesListSize() {
        burger.addIngredient(sauce);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientAddsExactIngredient() {
        burger.addIngredient(sauce);

        assertSame(sauce, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientDecreasesListSize() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientLeavesRemainingIngredient() {
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        burger.removeIngredient(0);

        assertSame(filling, burger.ingredients.get(0));
    }

    @Test
    public void getPriceWithoutIngredientsCountsOnlyBunTwice() {
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        assertEquals(200f, burger.getPrice(), 0.0001);
    }

    @Test
    public void getPriceWithIngredientsSumsBunAndIngredients() {
        when(bun.getPrice()).thenReturn(100f);
        when(sauce.getPrice()).thenReturn(50f);
        when(filling.getPrice()).thenReturn(30f);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        assertEquals(280f, burger.getPrice(), 0.0001);
    }

    @Test
    public void getReceiptWithoutIngredients() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        burger.setBuns(bun);

        String expected = String.format("(==== %s ====)%n", "black bun")
                + String.format("(==== %s ====)%n", "black bun")
                + String.format("%nPrice: %f%n", 200f);

        assertEquals(expected, burger.getReceipt());
    }

    @Test
    public void getReceiptWithIngredients() {
        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100f);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getPrice()).thenReturn(50f);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getPrice()).thenReturn(30f);
        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String expected = String.format("(==== %s ====)%n", "black bun")
                + String.format("= %s %s =%n", "sauce", "hot sauce")
                + String.format("= %s %s =%n", "filling", "cutlet")
                + String.format("(==== %s ====)%n", "black bun")
                + String.format("%nPrice: %f%n", 280f);

        assertEquals(expected, burger.getReceipt());
    }

}
