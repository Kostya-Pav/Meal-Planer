package mealplanner.interfaces;

import mealplanner.model.Category;
import mealplanner.model.Meal;
import mealplanner.model.Plan;

import java.util.List;
import java.util.Map;

public interface PlanService {
    void saveAllPlans(List<Plan> plansForWeek);

    void createPlanTable();

    Map<Category, List<Meal>> getSortedMealsByCategory();

    Map<String, List<Plan>> planByDay();

    Map<String, Integer> getIngredientsWithCount();
}
