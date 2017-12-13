package codetest;

import java.util.List;
import java.util.Scanner;

/**
 * Main, entry class.
 *
 * Created by rgilham on 11/12/2017.
 */
public class Main {

    private static final int TEST_EVENT_COUNT = 30; // Number of test events to use
    private static final int MAX_OUTPUT_COUNT = 5; // Maximum output of events. (Ignoring events with no tickets)


    /**
     * Main entry point.
     * No command line arguments are used.
     *
     * Once started commands are:
     * x,y  a location to find the nearest events.
     *      Both x and y must be within the range of #Location.MAX_LOCATION_SIZE, positive or negative.
     *
     * exit Will end the program
     *
     * list will dump all the current events in the index, to the output.
     *
     * @param args command line arguments (Not used)
     */
    public static void main(String[] args) {
        EventIndex eventListings = new EventIndex();

        System.out.printf("Generating %d events of test data...", TEST_EVENT_COUNT);
        DataGenerator.makeEvents(TEST_EVENT_COUNT, eventListings);

        Scanner lineIn = new Scanner(System.in);

        System.out.println("Please Enter your location, x,y where x and y are in the range of -10 to 10");
        System.out.println("Enter 'exit' to end");

        while (lineIn.hasNext()) {

            String line = lineIn.nextLine();
            if (line.trim().length() == 0) {
                continue;
            }

            if (line.trim().equalsIgnoreCase("exit"))
                break;

            if (line.trim().equalsIgnoreCase("list")) {
                dumpEvents(eventListings);
                continue;
            }

            // look for maximum distance marker '~'
            int distance = 0;  // Zero distance = entire grid
            if (line.contains("~")) {
                int pos = line.indexOf("~");
                try {
                    distance = Integer.parseInt(line.substring(pos + 1).trim());
                    line = line.substring(0, pos).trim(); // trim off distance from line
                }catch (Exception ex) {
                    System.err.printf("Failed to parse distance '%s'. %s, %s\n", line, ex.getClass().getSimpleName(), ex.getMessage());
                    continue;
                }
            }
            Location userLocation;
            try {
                userLocation = Location.parseLocation(line);
            } catch (Exception ex) {
                System.err.printf("Failed to parse location '%s'. %s, %s\n", line, ex.getClass().getSimpleName(), ex.getMessage());
                continue;
            }

            System.out.printf("Searching location X:%d , Y:%d over distance of: %s blocks\n", userLocation.getX(), userLocation.getY(),
                    distance > 0 ? Integer.toString(distance) : "limitless");

            // Get an ordered list of events, ordered by distance from user location
            List<Event> events = eventListings.findEvents(userLocation, distance);

            if (!events.isEmpty()) {
                int count = MAX_OUTPUT_COUNT;
                for (Event event : events) {
                    //System.out.println(event.toString());

                    if (!event.getTickets().isEmpty()) {
                        int eventDistance = userLocation.getDistance(event.getLocation());
                        System.out.printf("Event %d - $%.2f, Distance %d\n", event.getID(), event.getCheapestTicket().getPrice(), eventDistance);

                        count--;

                        if (distance == 0 && count <= 0) { // Limit to five outputs when no distance given.
                            break;
                        }
                    }
                }

            } else {
                System.out.println("No events were found for that location!");
            }
        }

    }

    private static void dumpEvents(EventIndex eventListings) {
        for (Event event : eventListings.getAllEvents()) {
            System.out.printf("Event %d, Location: %d,%d\t", event.getID(), event.getLocation().getX(), event.getLocation().getY());
            if (!event.getTickets().isEmpty())
                System.out.printf("\tTicket count: %d.  Cheapest: %.2f\n", event.getTickets().size(), event.getCheapestTicket().getPrice());
            else
                System.out.println("No tickets!");
            System.out.println();
        }
    }

}
