package restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateRestaurant {

    private static final List<Restaurant> restaurantAddedList = new ArrayList<>();
    private static int counter;

    public CreateRestaurant() {
        creatingMethod();
    }

    private void creatingMethod() {
        Scanner scan = new Scanner(System.in);
        System.out.println("type \"exit\" to exit, type \"1\" to create a restaurant");
        String info = scan.nextLine();
        if ("exit".equals(info)) {
            return;
        } else if (!"1".equals(info)) {
            System.out.println("wrong type");
            creatingMethod();
        }
        System.out.println("Type restaurant name");
        String name = scan.nextLine();
        System.out.println("Type restaurant address");
        String address = scan.nextLine();
        System.out.println("Type restaurant type[ASIAN, ITALIAN, FRENCH, AMERICAN, TURKISH]");
        String inputType = scan.nextLine();
        RestaurantType restaurantType = restaurantTypeChecker(inputType);
        restaurantAddedList.add(new Restaurant(name, address, restaurantType));
        System.out.println("Restaurant added: " + restaurantAddedList.get(counter++));
        System.out.println("If you want to add another restaurant type \"1\", add a meal to a restaurant type \"2\", exit \"3\"");
        int infoAddMeal = scan.nextInt();
        switch (infoAddMeal) {
            case 1:
                creatingMethod();
                break;
            case 2:
                addMealMethod();
                break;
            case 3:
                return;
        }
    }

    private void addMealMethod() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type meal name");
        String mealName = scan.nextLine();
        System.out.println("Type meal price");
        BigDecimal mealPrice = scan.nextBigDecimal();
        scan.nextLine();
        String addInfo = "";
        do {
            System.out.println("Type restaurant id to add the meal, or type \"all\" to see all restaurant");
            addInfo = scan.nextLine();
            if ("all".equals(addInfo)) {
                System.out.println(Restaurant.getRestaurantList());
            }
        }
        while ("all".equals(addInfo));

        int idRest = Integer.parseInt(addInfo);

        for (int i = 0; i < Restaurant.getRestaurantList().size(); i++) {
            if (idRest == Restaurant.getRestaurantList().get(i).getId()) {
                Restaurant.getRestaurantList().get(i).addMeal(new Meal(mealName, mealPrice));
                System.out.println("meal added");
            }
        }

        whatNextMethod();


    }

    private void whatNextMethod() {
        Scanner scan = new Scanner(System.in);
        System.out.println("type: \"1\" for add next meal, \"2\" for add new restaurant, \"3\" for show all meals, \"4\" for exit, \"5\" for change restaurant name");
        int chooseInfo = scan.nextInt();
        whatNextMealMethod(chooseInfo);
    }

    private void whatNextMealMethod(int chooseInfo) {
        Scanner scan = new Scanner(System.in);

        switch (chooseInfo) {
            case 1: {
                addMealMethod();
                break;
            }
            case 2: {
                creatingMethod();
                break;
            }
            case 3: {
                System.out.println(Restaurant.getRestaurantList());
                System.out.println("type the id you want to see");
                int chosenId = scan.nextInt();
                for (int i = 0; i < Restaurant.getRestaurantList().size(); i++) {
                    if (chosenId == Restaurant.getRestaurantList().get(i).getId()) {
                        System.out.println(Restaurant.getRestaurantList().get(i).getMealsList());
                    }
                }
                whatNextMethod();
                break;
            }
            case 4:
                return;

            case 5: {
                changeRestaurantNameMethod();
                break;
            }
        }

    }

    private void changeRestaurantNameMethod() {
        Scanner scan = new Scanner(System.in);
        String info = " ";
        do {
            System.out.println("Type restaurant id you want to change, or type \"all\" to see all restaurant");
            info = scan.nextLine();
            if ("all".equals(info)) {
                System.out.println(Restaurant.getRestaurantList());
            }
        }
        while ("all".equals(info));
        int chosenId = Integer.parseInt(info);
        System.out.println("type new restaurant name");
        String newName = scan.nextLine();
        for (int i = 0; i < Restaurant.getRestaurantList().size(); i++) {
            if (chosenId == Restaurant.getRestaurantList().get(i).getId()) {
                Restaurant.getRestaurantList().get(i).setName(newName);
                System.out.println("name changed");
            }
        }
        whatNextMethod();


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
