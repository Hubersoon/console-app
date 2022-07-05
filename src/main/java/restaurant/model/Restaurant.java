package restaurant.model;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public class Restaurant {


//    private static int idCounter;
    public final String address;
    public final RestaurantType type;
    public final UUID id;
    public final List<Meal> mealsList;
    public final String name;

    public Restaurant(String name, String address, RestaurantType type) {
//        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.type = type;
        this.id = UUID.randomUUID();
        this.mealsList = new ArrayList<>();
    }

    public void addMeal(Meal meal) {
        this.mealsList.add(meal);

    }

    public Restaurant updateName(String name) {
        return Restaurant.builder()
                .name(name)
                .id(id)
                .address(address)
                .type(type)
                .mealsList(mealsList)
                .build();
    }

    public UUID getId() {
        return id;
    }

    public List<Meal> getMealsList() {
        return mealsList;
    }

    public void printMealList(){
        for (int i = 0; i < mealsList.size(); i++) {
            System.out.println(mealsList.get(i));
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;

        Restaurant that = (Restaurant) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}


