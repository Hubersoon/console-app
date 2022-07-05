package restaurant.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Meal {

//    private static int idCounter;
    private String ID = String.valueOf(UUID.randomUUID());
    private final String name;
    private final BigDecimal price;

    public Meal(String name, BigDecimal price) {
//        this.id = idCounter++;
        this.name = name;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Meal ID: " + ID +
                ", name: " + name +
                ", price: " + price
                ;
    }
}
