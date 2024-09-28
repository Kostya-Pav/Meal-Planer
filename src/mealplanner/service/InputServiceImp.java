package mealplanner.service;

import mealplanner.interfaces.InputService;
import mealplanner.interfaces.OutputService;
import mealplanner.model.Category;
import mealplanner.model.Meal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class InputServiceImp implements InputService {
    private static final String REGEX = "^[a-zA-Z]+(\\s+[a-zA-Z]+)*(\\s*,\\s*[a-zA-Z]+(\\s+[a-zA-Z]+)*)*$";
    private final Scanner scanner;
    private final OutputService outputService;

    public InputServiceImp(Scanner scanner, OutputService outputService) {
        this.scanner = scanner;
        this.outputService = outputService;
    }

    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

    @Override
    public Category getMealsCategoryFromConsole() {
        while (true) {
            try {
                return Category.valueOf(nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                outputService.respondWrongMealCategory();
            }
        }
    }

    @Override
    public String getCheckedNameFromConsole() {
        while (true) {
            String line = nextLine();
            if (line != null && line.matches(REGEX)) {
                return line;
            }
            outputService.respondWrongInputFormat();
        }
    }

    @Override
    public Optional<Meal> getOptionalMeal(List<Meal> filteredMealsByCategory) {
        String inputDish = nextLine();
        return filteredMealsByCategory.stream()
                .filter(meal -> meal.getName().equals(inputDish))
                .findFirst();
    }
}
