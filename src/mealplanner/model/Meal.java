package mealplanner.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Meal {
    private long id;
    private Category category;
    private String name;
    private List<Ingredient> ingredients;

    public Meal(Category category, String meal, String[] ingredients) {
        this.category = category;
        this.name = meal;
        this.ingredients = Arrays.stream(ingredients)
                .map(Ingredient::new)
                .toList();
    }

    public Meal(long id, Category category, String name) {
        this.category = category;
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}