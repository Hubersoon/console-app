package restaurant;

import java.math.BigDecimal;

public class Meal {

    private static int idCounter;
    private final int id;
    private final String name;
    private final BigDecimal price;

    public Meal(String name, BigDecimal price) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal ID: " + id +
                ", name: " + name +
                ", price: " + price + "  "
                ;
    }
}
