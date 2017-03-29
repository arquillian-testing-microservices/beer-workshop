package org.ingredients.workshop.boundary;

import org.ingredients.workshop.entity.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Ingredients {

    private static Map<String, List<Ingredient>> ingredientsByBeerName = new HashMap<>();

    static {

        Ingredient malt = new Ingredient("MALT", 20);
        Ingredient water = new Ingredient("WATER", 50);
        Ingredient hop = new Ingredient("HOP", 15);
        Ingredient yeast = new Ingredient("YEAST", 15);

        final List<Ingredient> ingredients = Arrays.asList(malt, water, hop, yeast);

        ingredientsByBeerName.put("Voll Damm", ingredients);

    }

    public List<Ingredient> findIngredientsByBeerName(String beerName) {
        return ingredientsByBeerName.get(beerName);
    }

}
