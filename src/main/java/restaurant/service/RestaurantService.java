package restaurant.service;

import restaurant.exceptions.NotFoundRestaurantException;
import restaurant.model.Meal;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;
import restaurant.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RestaurantService {

//    5. poczytac o streamach.
//    6. ++refactoring + zmienic for na streams (DELETE MEAL AND DELETE RESTAURANT{atomic reference?} !!)
//    7. poczytać o unit testach.
//    8. poczytać o wyjątkach.
//    9. +++wprowadzić wyjątki do aplikacji
//    10. +++zamienic id na UUID;

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
                final var userChoice = Integer.parseInt(scanner.nextLine());
                a = userChoice;
            } catch (Exception e) {
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
                        throw new Exception("unknown number:");
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + " try again");
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
        } catch (Exception e) {
            System.out.println("invalid parameter, expected number: " + e.getMessage());
            return;
        }
        scanner.nextLine();
        printRestaurants();
        System.out.println("type restaurant ID you want to add meal");
        final var addInfo = scanner.nextLine();
//        do {
//            System.out.println("Type restaurant id to add the meal, or type \"all\" to see all restaurant");
//            addInfo = scanner.nextLine();
//            if ("all".equals(addInfo)) {
//                printRestaurants();
//            }
//        }
//        while ("all".equals(addInfo));

//        final var idRestaurant = Integer.parseInt(addInfo);

//       for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
//            Restaurant restaurant = restaurantRepository.restaurantList.get(i);
//            if (idRestaurant == restaurant.getId()) {
//                restaurant.addMeal(new Meal(mealName, mealPrice));
//                System.out.println("meal added");
//            }
//        }
        restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().equals(addInfo))
                .forEach(restaurant -> restaurant.addMeal(new Meal(mealName, mealPrice)));
    }

    private void printMealsForSpecificRestaurant() {
        printRestaurants();
        System.out.println("type the id you want to see");
        String restaurantID = scanner.nextLine();
        printMeals(restaurantID);
    }

    private void printMeals(String restaurantId) {
//        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
//            if (restaurantId == restaurantRepository.restaurantList.get(i).getId()) {
//                System.out.println("Restaurant: \"" + restaurantRepository.restaurantList.get(i).getName() + "\" "
//                        + restaurantRepository.restaurantList.get(i).getMealsList());
//            }
//        }
        restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .forEach(Restaurant::printMealList);
    }

    private void deleteMeal() {
        printRestaurants();
        System.out.println("type the id of the restaurant you want to change");
        final var restaurantId = scanner.nextLine();
        Restaurant restaurant = null;
        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
            if (restaurantRepository.restaurantList.get(i).getId().equals(restaurantId)) {
                restaurant = restaurantRepository.restaurantList.get(i);
            }
        }
        if (restaurant == null) return;
        printMeals(restaurantId);
        System.out.println("type the id of the meal you want to remove");
        final var mealId = scanner.nextLine();
//        final var mealsList = restaurant.getMealsList();
//        for (int i = 0; i < mealsList.size(); i++) {
//            if (mealId.equals(mealsList.get(i).getID())) {
//                mealsList.remove(i);
//                System.out.println("meal removed");
//            }
//        }
        final var restaurantMeals = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant1 -> restaurant1.getId().equals(restaurantId))
                .flatMap(restaurant1 -> restaurant1.getMealsList().stream())
                .collect(Collectors.toList());

        final var meal1 = restaurantMeals
                .stream()
                .filter(meal -> meal.getID().equals(mealId))
                .findAny()
                .orElseThrow();

        restaurantMeals.remove(meal1);
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

        final var something = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().equals(info))
                .map(restaurant -> restaurant.updateName(newName))
                .findFirst()
                .orElseThrow();

        restaurantRepository.restaurantList.remove(something);
        restaurantRepository.restaurantList.add(something);
    }

    private void deleteRestaurant() {
        printRestaurants();
        System.out.println("type restaurant id you want to delete");
        final var restaurantId = scanner.nextLine();
//        int chosenId = Integer.parseInt(restaurantId);
//        AtomicReference<Restaurant> chosenRestaurant = null;
//        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
//            if (restaurantId.equals(restaurantRepository.restaurantList.get(i).getID())) {
//                chosenRestaurant.set(restaurantRepository.restaurantList.get(i));
//            }
//        }
//        ??? ATOMIC REFERENCE ???
//        restaurantRepository.restaurantList
//                .stream()
//                .filter(restaurant -> restaurant.getID().equals(restaurantId))
//                .forEach(restaurant -> chosenRestaurant.set(restaurant));

        final var foundRestaurant = restaurantRepository.restaurantList
                .stream()
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .findAny()
                .orElseThrow(NotFoundRestaurantException::new);

        restaurantRepository.restaurantList.remove(foundRestaurant);
        System.out.println("Restaurant removed");

//            for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
//                if (restaurantId.equals(restaurantRepository.restaurantList.get(i).getID()))
//                    restaurantRepository.restaurantList.remove(i);
//                System.out.println("restaurant removed");
//

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
