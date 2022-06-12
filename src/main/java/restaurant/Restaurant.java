package restaurant;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private static int idCounter;
    private static final List<Restaurant> restaurantList = new ArrayList<>();
    private String name;
    private final String address;
    private final RestaurantType type;
    private final int id;
    private final List<Meal> mealsList = new ArrayList<>();

    public Restaurant(String name, String address, RestaurantType type) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.type = type;
        restaurantList.add(this);
    }

    public static List<Restaurant> getRestaurantList() {
        return restaurantList;
    }




    public void addMeal(Meal meal) {
        this.mealsList.add(meal);
    }

    public int getId() {
        return id;
    }

    public List<Meal> getMealsList() {
        return mealsList;
    }

    public void setName(String name) {
        this.name = name;
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