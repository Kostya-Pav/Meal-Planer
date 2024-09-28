package mealplanner.handlers;

import mealplanner.interfaces.InputService;
import mealplanner.interfaces.OutputService;
import mealplanner.interfaces.TableCreatorService;

public class AppRunner {
    private final TableCreatorService tableCreatorService;
    private final OutputService outputService;
    private final InputService inputService;
    private final MainMenu menu;

    public AppRunner(TableCreatorService tableCreatorService, OutputService outputService,
                     InputService inputService, MainMenu menu) {
        this.tableCreatorService = tableCreatorService;
        this.outputService = outputService;
        this.inputService = inputService;
        this.menu = menu;
    }

    public void run() {
        tableCreatorService.createTables();
        while (true) {
            outputService.requestToChoseMenuOption();
            switch (inputService.nextLine()) {
                case "add" -> menu.add();
                case "show" -> menu.show();
                case "plan" -> menu.plan();
                case "list plan" -> menu.printPlan();
                case "save" -> menu.save();
                case "exit" -> {
                    outputService.respondBye();
                    return;
                }
            }
        }
    }
}