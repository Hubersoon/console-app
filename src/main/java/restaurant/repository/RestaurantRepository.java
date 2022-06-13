package restaurant.repository;

import restaurant.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    public final List<Restaurant> restaurantList;

    public RestaurantRepository() {
        this.restaurantList = new ArrayList<>();
    }

    public RestaurantRepository(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
