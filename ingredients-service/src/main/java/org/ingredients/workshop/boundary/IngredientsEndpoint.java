package org.ingredients.workshop.boundary;

import org.ingredients.workshop.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientsEndpoint {

    @Autowired
    Ingredients ingredients;

    @RequestMapping(value = "/{beerName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ingredient>> getIngredients(@PathVariable("beerName") String beerName) {
        return new ResponseEntity<>(ingredients.findIngredientsByBeerName(beerName), HttpStatus.OK);
    }

}
