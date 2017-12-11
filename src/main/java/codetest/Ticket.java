package codetest;

import java.util.List;

/**
 * A Ticket is a class containing a price for a single ticket.
 * Created by rgilham on 11/12/2017.
 */
public class Ticket {

    private float price;

    public Ticket(float price) {
        this.price = price;
    }

    /**
     * The price of the ticket.
     * @return the ticket price
     */
    public float getPrice() {
        return price;
    }


}
