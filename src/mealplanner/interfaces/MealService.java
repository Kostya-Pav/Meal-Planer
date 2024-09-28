package mealplanner.interfaces;

import mealplanner.model.Category;
import mealplanner.model.Meal;

import java.util.Map;


public interface MealService {

    public void save(Meal meal);

    public Map<Long, Meal> getAllMealsByCategoryMappedById(Category category);
}
