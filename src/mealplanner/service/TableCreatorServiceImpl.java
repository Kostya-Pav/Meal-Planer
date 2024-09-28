package mealplanner.service;

import mealplanner.interfaces.TableCreatorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreatorServiceImpl implements TableCreatorService {
    private static final String CREATE_TABLE_MEALS = """
            CREATE TABLE IF NOT EXISTS meals (
            id SERIAL PRIMARY KEY,
            category VARCHAR,
            name VARCHAR)""";
    private static final String CREATE_TABLE_INGREDIENTS = """
            CREATE TABLE IF NOT EXISTS ingredients (
            id SERIAL PRIMARY KEY,
            name VARCHAR,
            meal_id INTEGER,
            FOREIGN KEY (meal_id) REFERENCES meals(id)
            )""";
    private static final String CREATE_TABLE_PLAN = """
            CREATE TABLE IF NOT EXISTS plan (
            day_of_week VARCHAR,
            meal_category VARCHAR,
            meal_id INTEGER,
            FOREIGN KEY (meal_id) REFERENCES meals(id)
            )""";
    private static final String DROP_TABLE_IF_EXISTS_PLAN = "DROP TABLE IF EXISTS plan";

    @Override
    public void createTables() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_MEALS);
            statement.execute(CREATE_TABLE_INGREDIENTS);
            statement.execute(CREATE_TABLE_PLAN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTableIfExistsPlan() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(DROP_TABLE_IF_EXISTS_PLAN);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
