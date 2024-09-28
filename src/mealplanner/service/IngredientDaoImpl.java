package mealplanner.service;

import mealplanner.interfaces.IngredientDao;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDaoImpl implements IngredientDao {
    private static final String INSERT_INTO_INGREDIENTS = "INSERT INTO ingredients (name, meal_id) VALUES (?, ?)";
    private static final String SELECT_FROM_INGREDIENTS_BY_MEAL_ID = "SELECT * FROM ingredients WHERE meal_id = ?";

    @Override
    public void saveAll(List<Ingredient> ingredients) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statementIngredients = connection.prepareStatement(INSERT_INTO_INGREDIENTS)) {
            for (Ingredient ingredient : ingredients) {
                ResultSet resultSet = statementIngredients.getGeneratedKeys();
                statementIngredients.setString(1, ingredient.getName());
                statementIngredients.setLong(2, ingredient.getMealId());
                statementIngredients.executeUpdate();
                if (resultSet.next()) {
                    ingredient.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Ingredient> getAllByMealIn(List<Meal> meals) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Meal meal : meals) {
            ingredients.addAll(getIngredientsByMealId(meal.getId()));
        }
        return ingredients;
    }


    @Override
    public List<Ingredient> getIngredientsByMealId(Long mealId) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statementIngredients = connection.prepareStatement(SELECT_FROM_INGREDIENTS_BY_MEAL_ID)) {
            statementIngredients.setLong(1, mealId);
            ResultSet resultSetIngredient = statementIngredients.executeQuery();
            return parseResultSetToIngredients(resultSetIngredient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Ingredient> parseResultSetToIngredients(ResultSet resultSetIngredient) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        while (resultSetIngredient.next()) {
            long mealId = resultSetIngredient.getLong("meal_id");
            String ingredientName = resultSetIngredient.getString("name");
            long id = resultSetIngredient.getLong("id");
            ingredients.add(new Ingredient(ingredientName, id, mealId));
        }
        return ingredients;
    }
}
