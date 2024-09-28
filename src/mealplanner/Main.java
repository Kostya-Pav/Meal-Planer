package mealplanner;

import mealplanner.handlers.*;
import mealplanner.interfaces.*;
import mealplanner.service.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final OutputService outputService = new OutputServiceImp();
    private static final InputService inputService = new InputServiceImp(scanner, outputService);
    private static final FileService fileHandler = new FileService();
    private static final MealDao mealDao = new MealDaoImpl();
    private static final IngredientDao ingredientDao = new IngredientDaoImpl();
    private static final PlanDao planDao = new PlanDaoImpl();
    private static final TableCreatorService tableCreatorService = new TableCreatorServiceImpl();
    private static final MealService menuService = new MealServiceImpl(mealDao, ingredientDao, outputService);
    private static final PlanService planService = new PlanServiceImpl(mealDao, planDao,tableCreatorService);
    private static final MainMenu menu = new MainMenu(inputService, outputService, fileHandler, planService, menuService);
    private static final AppRunner appRunner = new AppRunner(tableCreatorService, outputService, inputService, menu);

    public static void main(String[] args) throws SQLException {
        appRunner.run();
    }
}