package codetest;

import java.util.List;

/**
 * Created by rgilham on 11/12/2017.
 */
public class Event {

    private final int id;
    private Location location;
    private List<Ticket> tickets;

    public Event(int id, Location location, List<Ticket> tickets) {
        this.id = id;
        this.location = location;
        this.tickets = tickets;
    }

    public int getID() {
        return this.id;
    }

    public Location getLocation() {
        return this.location;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Ticket getCheapestTicket() {
        Ticket ticket = null;
        for (Ticket t : getTickets()) {
            if (null == ticket || ticket.getPrice() > t.getPrice())
                ticket = t;
        }
        return ticket;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("{id: %d, location: %s, tickets: %d", this.getID(), this.getLocation().toString(), this.getTickets().size());
    }
}
