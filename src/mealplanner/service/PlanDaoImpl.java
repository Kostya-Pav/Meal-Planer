package mealplanner.service;

import mealplanner.interfaces.PlanDao;
import mealplanner.model.Plan;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanDaoImpl implements PlanDao {
    private static final String INSERT_INTO_PLAN =
            "INSERT INTO plan (day_of_week, meal_category, meal_id) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_INGREDIENTS_FROM_PLAN = """
            SELECT i.name, COUNT(i.name) AS ingredient_count
            FROM plan p
            JOIN ingredients i ON p.meal_id = i.meal_id
            GROUP BY i.name;""";
    private static final String SELECT_ALL_FROM_PLAN_WITH_MEAL_NAME = """
            SELECT p.meal_id, p.meal_category, p.day_of_week, m.name
                                                   FROM plan p
                                                   LEFT JOIN meals m ON p.meal_id = m.id
                                                   ORDER BY p.meal_id""";


    @Override
    public void saveAllPlans(List<Plan> list) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statementPlane = connection.prepareStatement(INSERT_INTO_PLAN)) {
            for (int i = 0; i < list.size(); i++) {
                Plan plan = list.get(i);
                statementPlane.setString(1, plan.getDayOfWeek());
                statementPlane.setString(2, plan.getCategory());
                statementPlane.setLong(3, plan.getMealID());
                statementPlane.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Plan> getAllPlansWithMealName() {
        List<Plan> planMeal = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statementPlan = connection.createStatement()) {
            ResultSet resultSetPlan = statementPlan.executeQuery(SELECT_ALL_FROM_PLAN_WITH_MEAL_NAME);
            while (resultSetPlan.next()) {
                Plan plan = parseResultSetPlan(resultSetPlan);
                planMeal.add(plan);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return planMeal;
    }

    @Override
    public Map<String, Integer> getIngredientCountFromPlan() {
        Map<String, Integer> ingredientCountMap = new HashMap<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_INGREDIENTS_FROM_PLAN)) {
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("name");
                int count = resultSet.getInt("ingredient_count");
                ingredientCountMap.put(ingredientName, count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredientCountMap;
    }

    private Plan parseResultSetPlan(ResultSet resultSet) throws SQLException {
        String dayOfWeek = resultSet.getString("day_of_week");
        String category = resultSet.getString("meal_category");
        Long mealId = resultSet.getLong("meal_id");
        String mealName = resultSet.getString("name");
        return new Plan(dayOfWeek, category, mealId, mealName);
    }
}
