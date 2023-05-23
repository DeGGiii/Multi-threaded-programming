package assignment5;

public enum OrderStatus {
    NotSent("not sent to the server"),
    Received("received"),
    BeingPrepared("preparing"),
    Ready("ready"),
    Served("served"),
    NotFound("not found");

    public final String text;

    private OrderStatus(String name){
        this.text = name;
    }
}
