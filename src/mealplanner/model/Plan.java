package mealplanner.model;

public class Plan {
    private DayOfWeek dayOfWeek;
    private Meal meal;

    public Plan(DayOfWeek dayOfWeek, Meal meal) {
        this.dayOfWeek = dayOfWeek;
        this.meal = meal;
    }

    public Plan(String dayOfWeek, String category, Long mealID, String mealName) {
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        this.meal = new Meal(mealID, Category.valueOf(category.toUpperCase()), mealName);
    }

    public String getDayOfWeek() {
        return dayOfWeek.getName();
    }

    public String getCategory() {
        return meal.getCategory().getName();
    }

    public String getMealName() {
        return meal.getName();
    }

    public Long getMealID() {
        return meal.getId();
    }
}