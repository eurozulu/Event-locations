package codetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rgilham on 11/12/2017.
 */
public class DataGenerator {

    private static final float MAX_TICKET_PRICE = 100.0f;

    /**
     * Makes the given number of events from random data and insert into the given index
     * @param count the number of events to create
     * @param eventIndex the index to insert them
     */
    public static void makeEvents(int count, Events eventIndex) {
        for (int i = 0; i < count; i++) {
            Random r = new Random(System.currentTimeMillis());

            int ticketCount = r.nextInt(10);
            List<Ticket> tickets = new ArrayList<Ticket>(ticketCount);
            for (int t = 0; t < ticketCount; t++) {
                tickets.add(new Ticket(r.nextFloat() * MAX_TICKET_PRICE));
            }


            Location location = null;
            while (null == location) {
                int x = r.nextInt(Location.MAX_LOCATION_SIZE);
                if (r.nextBoolean())
                    x = -x;

                int y = r.nextInt(Location.MAX_LOCATION_SIZE);
                if (r.nextBoolean())
                    y = -y;

                location = new Location(x, y);
                if (eventIndex.getEventAtLocation(location) != null) // If already have event at that location, use another
                    location = null;
            }

            Event event = new Event(i + 1, location, tickets);
            eventIndex.addEvent(event);

            System.out.print(".");
        }
        System.out.println();
    }
}
