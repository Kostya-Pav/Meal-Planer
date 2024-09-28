package mealplanner.interfaces;

import mealplanner.model.Category;
import mealplanner.model.Meal;

import java.util.List;
import java.util.Optional;

public interface InputService {
    String nextLine();

    Category getMealsCategoryFromConsole();

    String getCheckedNameFromConsole();

    Optional<Meal> getOptionalMeal(List<Meal> filteredMealsByCategory);
}