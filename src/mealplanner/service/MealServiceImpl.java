package mealplanner.service;

import mealplanner.interfaces.IngredientDao;
import mealplanner.interfaces.MealDao;
import mealplanner.interfaces.MealService;
import mealplanner.interfaces.OutputService;
import mealplanner.model.Category;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MealServiceImpl implements MealService {
    private final MealDao mealDao;
    private final IngredientDao ingredientDao;
    private final OutputService outputService;

    public MealServiceImpl(MealDao mealDao, IngredientDao ingredientDao, OutputService outputService) {
        this.mealDao = mealDao;
        this.ingredientDao = ingredientDao;
        this.outputService = outputService;
    }

    @Override
    public void save(Meal meal) {
        mealDao.save(meal);
        ingredientDao.saveAll(meal.getIngredients());
    }

    @Override
    public Map<Long, Meal> getAllMealsByCategoryMappedById(Category category) {
        List<Meal> meals = mealDao.getAllbyCategory(category);
        Map<Long, Meal> result = meals.stream()
                .collect(Collectors
                        .toMap(Meal::getId, Function.identity()));
        if (meals.isEmpty()) {
            outputService.respondNoMealsSaved();
            return result;
        }
        List<Ingredient> ingredients = ingredientDao.getAllByMealIn(meals);
        for (Ingredient ingredient : ingredients) {
            Meal relatedMeal = result.get(ingredient.getMealId());
            relatedMeal.addIngredient(ingredient);
        }
        return result;
    }
}
