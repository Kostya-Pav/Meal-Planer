package mealplanner.handlers;

import mealplanner.interfaces.PlanService;
import mealplanner.model.*;
import mealplanner.service.FileService;
import mealplanner.interfaces.InputService;
import mealplanner.interfaces.OutputService;
import mealplanner.interfaces.MealService;

import java.util.*;
import java.util.stream.Collectors;

public class MainMenu {
    private final InputService inputService;
    private final OutputService outputService;
    private final PlanService planService;
    private final FileService fileService;
    private final MealService mealService;

    public MainMenu(InputService inputService,
                    OutputService outputService,
                    FileService fileService,
                    PlanService planService,
                    MealService mealService) {
        this.inputService = inputService;
        this.outputService = outputService;
        this.planService = planService;
        this.fileService = fileService;
        this.mealService = mealService;
    }

    public void add() {
        outputService.requestToChoseMealCategory();
        Category category = inputService.getMealsCategoryFromConsole();
        outputService.requestToEnterNameOfMeal();
        String mealName = inputService.getCheckedNameFromConsole();
        outputService.requestToEnterIngredients();
        String[] ingredients = inputService.getCheckedNameFromConsole().split(",");
        mealService.save(new Meal(category, mealName, ingredients));
        outputService.respondMealAddedSuccessful();
    }

    public void show() {
        outputService.requestToChoseTheCategoryToPrint();
        Category category = inputService.getMealsCategoryFromConsole();
        Map<Long, Meal> mealsById = mealService.getAllMealsByCategoryMappedById(category);
        outputService.printMealsWithIngredientsByCategory(category, mealsById);
    }

    public void plan() {
        planService.createPlanTable();
        Map<Category, List<Meal>> mealsMap = planService.getSortedMealsByCategory();
        if (mealsMap.isEmpty()) {
            outputService.respondNoMealsFound();
        }
        List<Plan> plansForWeek = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            outputService.printDayOfWeek(dayOfWeek);
            plansForWeek.addAll(getPlansForDay(mealsMap, dayOfWeek));
            outputService.respondSuccessfulFillPlan(dayOfWeek.getName());
        }
        planService.saveAllPlans(plansForWeek);
        printPlan(plansForWeek.stream()
                .collect(Collectors.groupingBy(Plan::getDayOfWeek)));
    }

    public void printPlan() {
        printPlan(planService.planByDay());
    }

    public void save() {
        Map<String, Integer> countByIngredient = planService.getIngredientsWithCount();
        if (countByIngredient.isEmpty()) {
            outputService.respondAnyPlanDoesNotExist();
        }
        outputService.requestToEnterFilename();
        String fileName = inputService.nextLine();
        fileService.saveToFile(countByIngredient, fileName);
        outputService.respondFileSaved();
    }

    private void printPlan(Map<String, List<Plan>> plan) {
        if (plan.isEmpty()) {
            outputService.respondDatabaseIsEmpty();
        }
        outputService.printPlan(plan);
    }
    private List<Plan> getPlansForDay(Map<Category, List<Meal>> mealsMap, DayOfWeek dayOfWeek) {
        List<Plan> plansForDay = new ArrayList<>();
        for (Category category : Category.values()) {
            List<Meal> meals = mealsMap.get(category);
            if (meals == null || meals.isEmpty()) {
                continue;
            }
            outputService.printCategoryMeals(meals);
            Meal chosenMeal = requestMealChoice(meals, category, dayOfWeek);
            plansForDay.add(new Plan(dayOfWeek, chosenMeal));
        }
        return plansForDay;
    }

    private Meal requestMealChoice(List<Meal> meals, Category category, DayOfWeek dayOfWeek) {
        outputService.requestToChoseMealWhichAddToPlan(category, dayOfWeek);
        return inputService.getOptionalMeal(meals)
                .orElseGet(() -> {
                    outputService.respondWrongMealInput();
                    return requestMealChoice(meals, category, dayOfWeek);
                });
    }
}