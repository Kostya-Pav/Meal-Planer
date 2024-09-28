package mealplanner.interfaces;

import mealplanner.model.Ingredient;
import mealplanner.model.Meal;

import java.util.List;

public interface IngredientDao {
     List<Ingredient> getAllByMealIn(List<Meal> meals);

    List<Ingredient> getIngredientsByMealId(Long mealId);

    void saveAll(List<Ingredient> ingredients);
}
