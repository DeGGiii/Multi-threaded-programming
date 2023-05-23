package assignment5;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        KitchenServer kitchenServer = new KitchenServer();
        GenericRestaurantForm restaurant = new GenericRestaurantForm(kitchenServer);
        restaurant.Start();
    }
}
