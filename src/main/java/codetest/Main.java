package codetest;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rgilham on 11/12/2017.
 */
public class Main {

    private static final int TEST_EVENT_COUNT = 50;
    private static final int MAX_OUTPUT_COUNT = 5;


    public static void main(String[] args) {
        Events eventListings = new Events();

        System.out.printf("Generating %d events of test data...", TEST_EVENT_COUNT);
        DataGenerator.makeEvents(TEST_EVENT_COUNT, eventListings);

        Scanner lineIn = new Scanner(System.in);

        Writer out = new OutputStreamWriter(System.out);

        System.out.println("Please Enter your location, x,y where x and y are in the range of -10 to 10");
        System.out.println("Enter 'exit' to end");

        while (lineIn.hasNext()) {

            String line = lineIn.nextLine();
            if (line.trim().length() == 0)
                continue;

            if (line.trim().equalsIgnoreCase("exit"))
                break;

            // look for maximum distance marker '~'
            int distance = Location.MAX_LOCATION_SIZE * 2;
            if (line.contains("~")) {
                int pos = line.indexOf("~");
                distance = Integer.parseInt(line.substring(pos + 1));
                line = line.substring(0, pos);
            }

            Location userLocation;
            try {
                userLocation = Location.parseLocation(line);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                continue;
            }

            List<Event> events = eventListings.findEvents(userLocation, distance);

            if (!events.isEmpty()) {
                int count = MAX_OUTPUT_COUNT;
                for (Event event : events) {
                    //System.out.println(event.toString());

                    if (!event.getTickets().isEmpty()) {
                        int eventDistance = userLocation.getDistance(event.getLocation());
                        System.out.printf("Event %d - $%.2f, Distance %d\n", event.getID(), event.getCheapestTicket().getPrice(), eventDistance);
                        count--;
                        if (count <= 0) { // Limit to five outputs.
                            break;
                        }
                    }
                }

            } else {
                System.out.println("No events were found for that location!");
            }
        }

    }


}
