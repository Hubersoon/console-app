package restaurant.service;

import restaurant.exceptions.NotFoundRestaurantException;
import restaurant.exceptions.UnknownNumberException;
import restaurant.model.Meal;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;
import restaurant.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class RestaurantService {

//    5. poczytac o streamach.
//    6. ++refactoring + zmienic for na streams (DELETE MEAL AND DELETE RESTAURANT{atomic reference?} !!)
//    7. poczytać o unit testach.
//    8. poczytać o wyjątkach.


// Problem z builderem w Restaurant. (Update name)
// Konieczne dodanie update MealList po usunięciu posiłku z konkretnej restauracji.

    private final RestaurantRepository restaurantRepository;
    private final Scanner scanner = new Scanner(System.in);

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void process() {
        while (true) {
            System.out.println("""
                    type:\s
                    "1" for add new restaurant,
                    "2" for add next meal,
                    "3" for show all restaurant,
                    "4" for show all meals of all restaurants
                    "5" for show all meals for specific restaurant,
                    "6" for delete meal for specific restaurants
                    "7" for change restaurant name
                    "8" for delete restaurant
                    "9" for exit""");
            int a;
            try {
                a = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("invalid parameter, try again. " + e.getMessage());
                continue;
            }
            switch (a) {
                case 1 -> {
                    createRestaurant();
                }
                case 2 -> {
                    addMeal();
                }
                case 3 -> {
                    printRestaurants();
                }
                case 4 -> {
                    printAllMealsForAllRestaurants();
                }
                case 5 -> {
                    printMealsForSpecificRestaurant();
                }
                case 6 -> {
                    deleteMeal();
                }
                case 7 -> {
                    changeRestaurantName();
                }
                case 8 -> {
                    deleteRestaurant();
                }
                case 9 -> {
                    System.out.println("exiting");
                    System.exit(0);
                }
                default -> {
                    try {
                        throw new UnknownNumberException();
                    } catch (UnknownNumberException e) {
                        System.out.println("try again");
                    }
                }
            }
        }
    }

    private void printAllMealsForAllRestaurants() {
//        for (Restaurant restaurant : restaurantRepository.restaurantList) {
//            System.out.println("id: " + restaurant.getId()
//                    + ", restaurant "
//                    + restaurant.getName()
//                    + " " + restaurant.getMealsList());
//        }
        restaurantRepository.restaurantList
                .stream()
                .forEach(restaurant -> {
                    System.out.println("Restaurant: " + restaurant.getName() + ", Meals: ");
                    restaurant.printMealList();
                });
    }

    public void printRestaurants() {
//        System.out.println("list of restaurants:");
//        for (Restaurant restaurant : restaurantRepository.restaurantList) {
//            System.out.println(restaurant);
//        }
        restaurantRepository.restaurantList
                .stream()
                .forEach(restaurant -> System.out.println("Restaurant: " + restaurant.getName()
                        + ", ID:" + restaurant.getId()));
    }

    public void createRestaurant() {
        System.out.println("Type restaurant name");
        String name = scanner.nextLine();
        System.out.println("Type restaurant address");
        String address = scanner.nextLine();
        System.out.println("Type restaurant type[ASIAN, ITALIAN, FRENCH, AMERICAN, TURKISH]");
        String inputType = scanner.nextLine();
        RestaurantType restaurantType = null;
        try {
            restaurantType = restaurantTypeChecker(inputType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        Restaurant newRestaurant = new Restaurant(name, address, restaurantType);
        restaurantRepository.restaurantList.add(newRestaurant);
        System.out.println("Restaurant added: \"" + newRestaurant.getName() + "\"" + ", id: " + newRestaurant.getId());
    }

    private void addMeal() {
        System.out.println("Type meal name");
        final var mealName = scanner.nextLine();
        System.out.println("Type meal price");
        BigDecimal mealPrice;
        try {
            mealPrice = scanner.nextBigDecimal();
        } catch (NumberFormatException e) {
            System.out.println("invalid parameter, expected number: " + e.getMessage());
            return;
        }
        scanner.nextLine();
        printRestaurants();
        System.out.println("type restaurant ID you want to add meal");
        final var addInfo = scanner.nextLine();
        final var restaurantId = UUID.fromString(addInfo);

        restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> (restaurant.getId()).compareTo(restaurantId) == 0)
                .forEach(restaurant -> {
                    restaurant.addMeal(new Meal(mealName, mealPrice));
                    System.out.println("Meal added");
                });
    }

    private void printMealsForSpecificRestaurant() {
        printRestaurants();
        System.out.println("type the id you want to see");
        String restaurantID = scanner.nextLine();
        printMeals(restaurantID);
    }

    private void printMeals(String restaurantId) {
        final var restaurantUUID = UUID.fromString(restaurantId);
        restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().compareTo(restaurantUUID) == 0)
                .forEach(Restaurant::printMealList);
    }

    private void deleteMeal() {
        printRestaurants();
        System.out.println("type the id of the restaurant you want to change");
        final var restaurantId = scanner.nextLine();
        printMeals(restaurantId);
        System.out.println("type the id of the meal you want to remove");
        final var mealId = scanner.nextLine();
        final var restaurantUUID = UUID.fromString(restaurantId);
        final var mealUUID = UUID.fromString(mealId);

        final var restaurant = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant1 -> restaurant1.getId().compareTo(restaurantUUID) == 0)
                .findAny()
                .orElseThrow();

        final var restaurantMeals = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant1 -> restaurant1.getId().compareTo(restaurantUUID) == 0)
                .flatMap(restaurant1 -> restaurant1.getMealsList().stream())
                .collect(Collectors.toList());

        final var meal = restaurantMeals
                .stream()
                .filter(m -> m.getId().compareTo(mealUUID) == 0)
                .findAny()
                .orElseThrow();


        restaurantMeals.remove(meal);
//        restaurant.updateMealsList(restaurantMeals);
        System.out.println("Meal removed");

    }

    private void changeRestaurantName() {
        printRestaurants();
        System.out.println("Type restaurant id you want to change");
        final var info = scanner.nextLine();
        System.out.println("type new restaurant name");
        final var newName = scanner.nextLine();
//        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
//            Restaurant restaurant = restaurantRepository.restaurantList.get(i);
//            if (chosenId == restaurant.getId()) {
//                restaurant.setName(newName);
//                System.out.println("name changed");
//            }
//        }
//
//        final var something = restaurantRepository.restaurantList
//                .stream()
//                .filter(restaurant -> restaurant.getId().equals(info))
//                .map(restaurant -> restaurant.updateName(newName))
//                .findFirst()
//                .orElseThrow();
//
//        restaurantRepository.restaurantList.remove(something);
//        restaurantRepository.restaurantList.add(something);
    }

    private void deleteRestaurant() {
        printRestaurants();
        System.out.println("type restaurant id you want to delete");
        final var restaurantId = scanner.nextLine();
        final var restaurantUUID = UUID.fromString(restaurantId);
        final var foundRestaurant = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().compareTo(restaurantUUID) == 0)
                .findAny()
                .orElseThrow(NotFoundRestaurantException::new);
        restaurantRepository.restaurantList.remove(foundRestaurant);
        System.out.println("Restaurant removed");

    }

    private RestaurantType restaurantTypeChecker(String inputType) throws Exception {
        switch (inputType.toUpperCase()) {
            case "ASIAN" -> {
                return RestaurantType.ASIAN;
            }
            case "ITALIAN" -> {
                return RestaurantType.ITALIAN;
            }
            case "FRENCH" -> {
                return RestaurantType.FRENCH;
            }
            case "TURKISH" -> {
                return RestaurantType.TURKISH;
            }
            case "AMERICAN" -> {
                return RestaurantType.AMERICAN;
            }
            default -> {
                throw new Exception("unknow restaurant type: " + inputType);
            }
        }
    }

}
