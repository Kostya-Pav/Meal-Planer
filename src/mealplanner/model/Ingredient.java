package mealplanner.model;

public class Ingredient {
    private String name;
    private Long id;
    private Long mealId;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, long mealId) {
        this.mealId = mealId;
        this.name = name;
    }

    public Ingredient(String name, long id, long mealId) {
        this.name = name;
        this.id = id;
        this.mealId = mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getMealId() {
        return mealId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
