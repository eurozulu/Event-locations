package codetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * DataGenerator generates test data.
 * It has a single, static method, {@link #makeEvents(int, EventIndex)}, which will add the given number of new
 * events, into the given eventIndex.
 * <p>
 * Created by rgilham on 11/12/2017.
 */
public class DataGenerator {

    private static final float MAX_TICKET_PRICE = 100.0f;
    private static final int MAX_TICKET_COUNT = 10;


    /**
     * Makes the given number of events from random data and insert into the given index
     *
     * @param count      the number of events to create
     * @param eventIndex the index to insert them
     */
    public static void makeEvents(int count, EventIndex eventIndex) {

        Random rnd = new Random(System.currentTimeMillis());

        for (int i = 0; i < count; i++) {

            int ticketCount = rnd.nextInt(MAX_TICKET_COUNT);
            List<Ticket> tickets = new ArrayList<Ticket>(ticketCount);
            for (int t = 0; t < ticketCount; t++) {
                tickets.add(new Ticket(rnd.nextFloat() * MAX_TICKET_PRICE));
            }


            Location location = null;
            while (null == location) {
                int x = rnd.nextInt(Location.MAX_LOCATION_SIZE);
                if (rnd.nextBoolean())
                    x = -x;

                int y = rnd.nextInt(Location.MAX_LOCATION_SIZE);
                if (rnd.nextBoolean())
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
