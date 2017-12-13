package codetest;

/**
 * A Ticket is a class containing a price for a single ticket.
 * It is Immutable.
 *
 * Created by rgilham on 11/12/2017.
 */
public class Ticket {

    private final float price;

    /**
     * Create a new Ticket, with the given price.
     *
     * @param price the price of the ticket.
     */
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
