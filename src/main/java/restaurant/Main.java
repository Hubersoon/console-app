package restaurant;

import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;
import restaurant.repository.RestaurantRepository;
import restaurant.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Restaurant restaurant1 = new Restaurant("Kebab u grubego","Warszawska 11, Kraków 31-222", RestaurantType.TURKISH);
        Restaurant restaurant2 = new Restaurant("Milano Pizza","Sobczyka 12/3, Warszawa 00-001",RestaurantType.ITALIAN);
        Restaurant restaurant3 = new Restaurant("Meat Burger","Lipowa 23, Gdańsk 29-545",RestaurantType.AMERICAN);


        RestaurantRepository restaurantRepository = new RestaurantRepository(new ArrayList<>(List.of(restaurant1,restaurant2,restaurant3)));
        RestaurantService rest1 = new RestaurantService(restaurantRepository);
        rest1.process();
    }
}
