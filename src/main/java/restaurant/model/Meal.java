package restaurant.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Meal {

    public UUID id;
    public final String name;
    public final BigDecimal price;

    public Meal(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Meal ID: " + id +
                ", name: " + name +
                ", price: " + price
                ;
    }
}
