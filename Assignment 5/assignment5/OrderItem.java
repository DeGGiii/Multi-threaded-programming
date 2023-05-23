package assignment5;

import java.util.UUID;

public class OrderItem {
    private String itemID;
    private String name;
    private String description;
    private float cost;

    public OrderItem(String name, String desc, float cost) {
        this.itemID = UUID.randomUUID().toString();
        this.name = name;
        this.description = desc;
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemID() {
        return itemID;
    }

}