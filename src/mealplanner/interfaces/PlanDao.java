package mealplanner.interfaces;

import mealplanner.model.Plan;

import java.util.List;
import java.util.Map;

public interface PlanDao {
    void saveAllPlans(List<Plan> list);

    List<Plan> getAllPlansWithMealName();

    Map<String, Integer> getIngredientCountFromPlan();
}
