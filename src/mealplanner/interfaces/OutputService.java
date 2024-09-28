package mealplanner.interfaces;

import mealplanner.model.*;

import java.util.List;
import java.util.Map;


public interface OutputService {

    void printDayOfWeek(DayOfWeek dayOfWeek);

    void requestToChoseMealCategory();

    void requestToEnterNameOfMeal();

    void requestToEnterIngredients();

    void requestToChoseMenuOption();

    void respondMealAddedSuccessful();

    void respondNoMealsSaved();

    void respondWrongMealCategory();

    void respondBye();

    void respondWrongInputFormat();

    void requestToChoseTheCategoryToPrint();

    void respondNoMealsFound();

    void requestToChoseMealWhichAddToPlan(Category meal, DayOfWeek dayOfWeek);

    void respondSuccessfulFillPlan(String dayOfWeek);

    void respondWrongMealInput();

    void respondDatabaseIsEmpty();

    void respondAnyPlanDoesNotExist();

    void respondFileSaved();

    void requestToEnterFilename();

    void printMealsWithIngredientsByCategory(Category category, Map<Long, Meal> filteredMealsByCategory);

    void printPlan(Map<String, List<Plan>> planByDay);

    void printCategoryMeals(List<Meal> meals);
}