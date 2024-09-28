package mealplanner.service;

import mealplanner.interfaces.MealDao;
import mealplanner.model.Category;
import mealplanner.model.Meal;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class MealDaoImpl implements MealDao {
    private static final String INSERT_INTO_MEALS = "INSERT INTO meals (category, name) VALUES (?, ?)";
    private static final String SELECT_ALL_FROM_MEALS_SORTED_BY_NAME = "SELECT * FROM meals ORDER BY name";
    private static final String SELECT_ALL_FROM_MEALS_BY_CATEGORY = "SELECT * FROM meals WHERE category = ?";

    @Override
    public List<Meal> getAllbyCategory(Category category) {
        List<Meal> mealsByCategoryMap = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statementMeals = connection.prepareStatement(SELECT_ALL_FROM_MEALS_BY_CATEGORY)) {
            statementMeals.setString(1, category.getName());
            ResultSet resultSetMeals = statementMeals.executeQuery();
            while (resultSetMeals.next()) {
                Meal meal = parseResultSetMeal(resultSetMeals);
                mealsByCategoryMap.add(meal);
            }
            return mealsByCategoryMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Meal meal) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statementMeals = connection.prepareStatement(INSERT_INTO_MEALS, Statement.RETURN_GENERATED_KEYS)) {
            statementMeals.setString(1, meal.getCategory().getName());
            statementMeals.setString(2, meal.getName());
            statementMeals.execute();
            ResultSet resultSet = statementMeals.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                meal.setId(id);
                meal.getIngredients()
                        .forEach(ingredient -> ingredient.setMealId(id));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Category, List<Meal>> getAllMealsByCategorySortedByName() {
        List<Meal> meals = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statementMeals = connection.createStatement()) {
            ResultSet resultSetMeals = statementMeals.executeQuery(SELECT_ALL_FROM_MEALS_SORTED_BY_NAME);
            while (resultSetMeals.next()) {
                Meal meal = parseResultSetMeal(resultSetMeals);
                meals.add(meal);
            }
            return meals.stream().collect(Collectors.groupingBy(Meal::getCategory));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Meal parseResultSetMeal(ResultSet resultSetMeals) throws SQLException {
        long id = resultSetMeals.getLong("id");
        Category category = Category.valueOf(resultSetMeals.getString("category").toUpperCase());
        String meal = resultSetMeals.getString("name");
        return new Meal(id, category, meal);
    }
}
