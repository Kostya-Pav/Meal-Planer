package mealplanner.interfaces;

import mealplanner.model.Category;
import mealplanner.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealDao {

    void save(Meal meal);

    Map<Category, List<Meal>> getAllMealsByCategorySortedByName();

    List<Meal> getAllbyCategory(Category category);
}
