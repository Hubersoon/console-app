package restaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {


    private static int idCounter;
    private final String address;
    private final RestaurantType type;
    private final int id;
    private final List<Meal> mealsList = new ArrayList<>();
    private String name;

    public Restaurant(String name, String address, RestaurantType type) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    public void addMeal(Meal meal) {
        this.mealsList.add(meal);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Meal> getMealsList() {
        return mealsList;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "id: " + id +
                        ", name: \"" + name + '\"' +
                        ", address: \"" + address + '\"' +
                        ", type: " + type
                ;
    }
}
