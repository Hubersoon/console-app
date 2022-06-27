package restaurant.service;

import restaurant.model.Meal;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;
import restaurant.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Scanner;

public class RestaurantService {

//    1. refactoring
//    2.++ dodac metode na wypisywanie wszystkich posilkow ze wszystkich restauracji
//    3.++ dodac metode usuwania restauracji
//    4.++ dodac metode usuwania posilku ze specyficznej restauracji
//    5. poczytac o streamach.


    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void process() {

        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    type:\s
                    "1" for add new restaurant, \s
                    "2" for add next meal,\s
                    "3" for show all restaurant,\s
                    "4" for show all meals of all restaurants
                    "5" for show all meals for specific restaurant,\s
                    "6" for delete meal for specific restaurants
                    "7" for change restaurant name
                    "8" for delete restaurant
                    "9" for exit\s""");

            int userChoice = scan.nextInt();

            switch (userChoice) {
                case 1: {
                    createRestaurant();
                    break;
                }
                case 2: {

                    addMealMethod();
                    break;
                }

                case 3:{
                    printRestaurants();
                    break;
                }

                case 4: {
                    for (Restaurant restaurant : restaurantRepository.restaurantList) {
                        System.out.println("id: " + restaurant.getId()
                                + ", restaurant "
                                + restaurant.getName()
                                + " " + restaurant.getMealsList());
                    }
                    break;
                }

                case 5: {
                  printMealsForSpecificRestaurant();
                    break;
                }

                case 6: {
                    deleteMeal();
                    break;

                }

                case 7: {
                    changeRestaurantName();
                    break;
                }

                case 8: {
                    deleteRestaurant();
                    break;
                }
                case 9: {
                    System.out.println("exiting");
                    System.exit(0);
                    break;
                }
            }

        }
    }


    public void printRestaurants() {
        System.out.println("list of restaurants:");
        for (Restaurant restaurant : restaurantRepository.restaurantList) {
            System.out.println(restaurant);
        }
    }

    private void createRestaurant() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("type \"1\" to create a restaurant, type \"back\" to exit, ");
        String info = scanner.nextLine();
        if ("back".equals(info)) {
            return;
        } else if (!"1".equals(info)) {
            System.out.println("wrong type");
            createRestaurant();
        }
        System.out.println("Type restaurant name");
        String name = scanner.nextLine();
        System.out.println("Type restaurant address");
        String address = scanner.nextLine();
        System.out.println("Type restaurant type[ASIAN, ITALIAN, FRENCH, AMERICAN, TURKISH]");
        String inputType = scanner.nextLine();
        RestaurantType restaurantType = restaurantTypeChecker(inputType);
        Restaurant newRestaurant = new Restaurant(name, address, restaurantType);
        restaurantRepository.restaurantList.add(newRestaurant);
        System.out.println("Restaurant added: \"" + newRestaurant.getName() + "\"" + ", id: " + newRestaurant.getId());

    }

    private void addMealMethod() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type meal name");
        String mealName = scan.nextLine();
        System.out.println("Type meal price");
        BigDecimal mealPrice = scan.nextBigDecimal();
        scan.nextLine();
        String addInfo;
        do {
            System.out.println("Type restaurant id to add the meal, or type \"all\" to see all restaurant");
            addInfo = scan.nextLine();
            if ("all".equals(addInfo)) {
                printRestaurants();
            }
        }
        while ("all".equals(addInfo));

        int idRest = Integer.parseInt(addInfo);

        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
            Restaurant restaurant = restaurantRepository.restaurantList.get(i);
            if (idRest == restaurant.getId()) {
                restaurant.addMeal(new Meal(mealName, mealPrice));
                System.out.println("meal added");
            }
        }

    }

    private void printMealsForSpecificRestaurant() {
        Scanner scanner = new Scanner(System.in);
        printRestaurants();
        System.out.println("type the id you want to see");
        int chosenId = scanner.nextInt();
        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
            if (chosenId == restaurantRepository.restaurantList.get(i).getId()) {
                System.out.println("Restaurant: \"" + restaurantRepository.restaurantList.get(i).getName() + "\" "
                        + restaurantRepository.restaurantList.get(i).getMealsList());
            }
        }
    }

    private void deleteMeal() {

        Scanner scanner = new Scanner(System.in);
        String infoRestaurant = "";
        String infoMeal = "";
        do {
            System.out.println("type the id of the restaurant you want to change," +
                    " or type \"all\" to see all restaurants and meals");
            infoRestaurant = scanner.nextLine();
            if ("all".equals(infoRestaurant)) {
                for (Restaurant restaurant : restaurantRepository.restaurantList) {
                    System.out.println("id: " + restaurant.getId() + ", restaurant " + restaurant.getName() +
                            " " + restaurant.getMealsList());
                }
            }
        } while ("all".equals(infoRestaurant));
        int chosenIdRestaurant = Integer.parseInt(infoRestaurant);

        do {
            System.out.println("enter the id of the meal you want to delete," +
                    " or \"all\" to see all meals in chosen restaurant," +
                    " \"back\" to change chosen restaurant");
            infoMeal = scanner.nextLine();
            if ("all".equals(infoMeal)) {
                System.out.println("Restaurant " + restaurantRepository.restaurantList.get(chosenIdRestaurant).getName() +
                        " " + restaurantRepository.restaurantList.get(chosenIdRestaurant).getMealsList());
            } else if ("back".equals(infoMeal)) {
                deleteMeal();
            }
        }
        while ("all".equals(infoMeal));
        int chosenIdMeal = Integer.parseInt(infoMeal);

        for (int i = 0; i < restaurantRepository.restaurantList.get(chosenIdRestaurant).getMealsList().size(); i++) {

            if (chosenIdMeal == restaurantRepository.restaurantList.get(chosenIdRestaurant).getMealsList().get(i).getId()) {
                restaurantRepository.restaurantList.get(chosenIdRestaurant).getMealsList().remove(i);
                System.out.println("meal removed");
            }

        }


    }

    private void changeRestaurantName() {
        Scanner scanner = new Scanner(System.in);
        String info = " ";
        do {
            System.out.println("Type restaurant id you want to change, or type \"all\" to see all restaurant");
            info = scanner.nextLine();
            if ("all".equals(info)) {
                printRestaurants();
            }
        }
        while ("all".equals(info));
        int chosenId = Integer.parseInt(info);
        System.out.println("type new restaurant name");
        String newName = scanner.nextLine();
        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
            Restaurant restaurant = restaurantRepository.restaurantList.get(i);
            if (chosenId == restaurant.getId()) {
                restaurant.setName(newName);
                System.out.println("name changed");
            }
        }


    }

    private void deleteRestaurant() {
        Scanner scanner = new Scanner(System.in);
        String userChoice = "";
        String confirmChoice = "";
        do {
            System.out.println("type restaurant id you want to delete, or \"all\" to see all restaurant");
            userChoice = scanner.nextLine();
            if ("all".equals(userChoice)) {
                printRestaurants();
            }
        } while ("all".equals(userChoice));

        int chosenId = Integer.parseInt(userChoice);
        Restaurant chosenRestaurant = null;
        for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
            if (chosenId == restaurantRepository.restaurantList.get(i).getId()) {
                chosenRestaurant = restaurantRepository.restaurantList.get(i);
            }

        }

        System.out.println("You chose restaurant \"" + chosenRestaurant.getName() + "\"" +
                "\nIf you sure to delete restaurant type \"yes\"");
        confirmChoice = scanner.nextLine();
        if ("yes".equals(confirmChoice)) {
            for (int i = 0; i < restaurantRepository.restaurantList.size(); i++) {
                if (chosenId == restaurantRepository.restaurantList.get(i).getId())
                    restaurantRepository.restaurantList.remove(i);
                System.out.println("restaurant removed");
            }
        }

    }

    private RestaurantType restaurantTypeChecker(String inputType) {

        return switch (inputType.toUpperCase()) {
            case "ASIAN" -> RestaurantType.ASIAN;
            case "ITALIAN" -> RestaurantType.ITALIAN;
            case "FRENCH" -> RestaurantType.FRENCH;
            case "TURKISH" -> RestaurantType.TURKISH;
            case "AMERICAN" -> RestaurantType.AMERICAN;
            default -> null;
        };
    }

}
