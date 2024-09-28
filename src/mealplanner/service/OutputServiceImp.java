package mealplanner.service;

import mealplanner.interfaces.OutputService;
import mealplanner.model.*;

import java.util.List;
import java.util.Map;

import static java.lang.System.out;

public class OutputServiceImp implements OutputService {
    @Override
    public void requestToChoseMealCategory() {
        out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
    }

    @Override
    public void requestToEnterNameOfMeal() {
        out.println("Input the meal's name:");
    }

    @Override
    public void requestToEnterIngredients() {
        out.println("Input the ingredients:");
    }

    @Override
    public void requestToChoseMenuOption() {
        out.println("What would you like to do (add, show, plan, list plan, save, exit)?");
    }

    @Override
    public void requestToEnterFilename() {
        out.println("Input a filename:");
    }

    @Override
    public void requestToChoseTheCategoryToPrint() {
        out.println("Which category do you want to print (breakfast, lunch, dinner)?");
    }

    @Override
    public void requestToChoseMealWhichAddToPlan(Category category, DayOfWeek dayOfWeek) {
        out.printf("Choose the %s for %s from the list above:", category.getName(), dayOfWeek.getName());
    }

    @Override
    public void respondMealAddedSuccessful() {
        out.println("The meal has been added!");
    }

    @Override
    public void respondNoMealsSaved() {
        out.println("No meals saved. Add a meal first.");
    }

    @Override
    public void respondWrongMealCategory() {
        out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
    }

    @Override
    public void respondBye() {
        out.println("Bye!");
    }

    @Override
    public void respondWrongInputFormat() {
        out.println("Wrong format. Use letters only!");
    }

    @Override
    public void respondNoMealsFound() {
        out.println("No meals found.");
    }

    @Override
    public void respondSuccessfulFillPlan(String dayOfWeek) {
        out.printf("Yeah! We planned the meals for %s.\n", dayOfWeek);
    }

    @Override
    public void respondFileSaved() {
        out.println("Saved!");
    }

    @Override
    public void respondWrongMealInput() {
        out.println("This meal doesn’t exist. Choose a meal from the list above. \n");
    }

    @Override
    public void respondDatabaseIsEmpty() {
        out.println("Database does not contain any meal plans");
    }

    @Override
    public void respondAnyPlanDoesNotExist() {
        out.println("Unable to save. Plan your meals first.");
    }

    public void printMealsWithIngredientsByCategory(Category category, Map<Long, Meal> filteredMealsByCategory) {
        out.printf("Category: %s%n", category.getName());
        filteredMealsByCategory.forEach((id, meal) -> {
            out.println("Name: " + meal.getName());
            out.println("Ingredients:");
            if (meal.getIngredients() == null || meal.getIngredients().isEmpty()) {
                out.println("No ingredients available.");
            } else {
                meal.getIngredients().forEach(ingredient -> {
                    out.println(ingredient.getName());
                });
            }
        });
    }

    public void printDayOfWeek(DayOfWeek dayOfWeek) {
        out.println(dayOfWeek);
    }

    @Override
    public void printPlan(Map<String, List<Plan>> planByDay) {
        out.println();
        for (Map.Entry<String, List<Plan>> entry : planByDay.entrySet()) {
            out.println(entry.getKey());
            for (Plan plan : entry.getValue()) {
                out.println(plan.getCategory() + ": " + plan.getMealName());
            }
        }
    }

    @Override
    public void printCategoryMeals(List<Meal> meals) {
        meals.forEach(meal -> out.println(meal.getName()));
    }
}