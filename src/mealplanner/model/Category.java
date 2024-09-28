package mealplanner.model;

public enum Category {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}