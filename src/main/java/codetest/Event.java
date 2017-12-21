package codetest;

import java.util.List;
import java.util.Objects;

/**
 * An Event class contains the data relating to a single event.Each event has a unique (integer) ID,
 * A Location of where it is and a list of zero or more tickets at random prices.
 * Created by rgilham on 11/12/2017.
 */
public class Event {

    private final int id;
    private final Location location;
    private final List<Ticket> tickets;

    public Event(int id, Location location, List<Ticket> tickets) {
        this.id = id;
        this.location = location;
        this.tickets = tickets;
    }

    /**
     * Get the EventIndex unique ID
     *
     * @return the event ID
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the EventIndex Location
     *
     * @return the event location
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Gets the events Ticket list.
     *
     * @return the tickets available for the event
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * Gets the cheapest ticket available for the event.
     *
     * @return the cheapest ticket or null if no tickets are available.
     */
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

    @Override
    public int hashCode() {
        return Objects.hashCode(String.format("%d%s%s", getID(), getLocation().toString(), getTickets().toString()));
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj || !(obj instanceof Event))
            return false;

        Event eObj = (Event)obj;
        return eObj.getID() == this.getID() &&
                eObj.getLocation().equals(this.getLocation()) &&
                eObj.getTickets().equals(this.getTickets());
    }
}
