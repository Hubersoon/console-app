package restaurant;

import jdk.swing.interop.SwingInterOpUtils;
import restaurant.model.Meal;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;
import restaurant.repository.RestaurantRepository;
import restaurant.service.RestaurantService;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        final var restaurant1 = new Restaurant("Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH);
        final var restaurant2 = new Restaurant("Milano Pizza", "Sobczyka 12/3, Warszawa 00-001", RestaurantType.ITALIAN);
        final var restaurant3 = new Restaurant("Meat Burger", "Lipowa 23, Gdańsk 29-545", RestaurantType.AMERICAN);

        restaurant1.addMeal(new Meal("Burger",BigDecimal.valueOf(255)));


        final var restaurantRepository = new RestaurantRepository(new ArrayList<>(List.of(restaurant1, restaurant2, restaurant3)));
        final var restaurantService = new RestaurantService(restaurantRepository);
        restaurantService.process();


    }
}
