package restaurant;

public class Main {
    public static void main(String[] args) {

        Restaurant restaurant1 = new Restaurant("Kebab u grubego","Warszawska 11, Kraków 31-222",RestaurantType.TURKISH);
        Restaurant restaurant2 = new Restaurant("Milano Pizza","Sobczyka 12/3, Warszawa 00-001",RestaurantType.ITALIAN);
        Restaurant restaurant3 = new Restaurant("Meat Burger","Lipowa 23, Gdańsk 29-545",RestaurantType.AMERICAN);

        System.out.println(restaurant1);
        System.out.println(restaurant2);
        System.out.println(restaurant3);
        CreateRestaurant rest1 = new CreateRestaurant();
    }
}
