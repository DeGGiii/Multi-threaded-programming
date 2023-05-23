package assignment5;

public enum KitchenStatus {
    Received("order received"),
    Rejected("order rejected"),
    NotFound("order not found"),
    Served("order served"),
    Cooking("order is being cooked");

    public final String text;

    private KitchenStatus(String name){
        this.text = name;
    }

    public String getText() {
        return text;
    }
}
