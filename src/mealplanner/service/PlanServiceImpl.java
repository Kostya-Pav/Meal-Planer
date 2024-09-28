package mealplanner.service;

import mealplanner.interfaces.*;
import mealplanner.model.Category;
import mealplanner.model.Meal;
import mealplanner.model.Plan;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlanServiceImpl implements PlanService {
    private final MealDao mealDao;
    private final PlanDao planDao;
    private final TableCreatorService tableCreatorService;

    public PlanServiceImpl(MealDao mealDao, PlanDao planDao, TableCreatorService tableCreatorService) {
        this.mealDao = mealDao;
        this.planDao = planDao;
        this.tableCreatorService = tableCreatorService;
    }

    @Override
    public void saveAllPlans(List<Plan> plansForWeek) {
        planDao.saveAllPlans(plansForWeek);
    }

    @Override
    public void createPlanTable() {
        tableCreatorService.dropTableIfExistsPlan();
        tableCreatorService.createTables();
    }

    @Override
    public Map<Category, List<Meal>> getSortedMealsByCategory() {
        return mealDao.getAllMealsByCategorySortedByName();
    }

    @Override
    public Map<String, List<Plan>> planByDay() {
        List<Plan> planWithMealName = planDao.getAllPlansWithMealName();
        return planWithMealName.stream()
                .collect(Collectors.groupingBy(Plan::getDayOfWeek));
    }

    @Override
    public Map<String, Integer> getIngredientsWithCount() {
        return  planDao.getIngredientCountFromPlan();
    }
}
