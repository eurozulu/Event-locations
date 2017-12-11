package codetest;

import java.util.*;

/**
 * Events is the Event Index maintaining the Events available indexed by their location.
 *
 * Events have their id indexed in a two dimentionsal index.
 * Created by rgilham on 11/12/2017.
 */
public class Events {

    private static final int MAX_GRID_SIZE = (Location.MAX_LOCATION_SIZE * 2) + 1;

    private final Map<Integer, Event> eventMap = new HashMap<Integer, Event>();
    private final int[][] index = new int[MAX_GRID_SIZE][MAX_GRID_SIZE];


    /**
     * Gets an ordered list of Events found at the location and within the given distance of the location, ordered by the distance from the given location.
     * @param location the location to search from
     * @param distance the distance from the location to search from, zero = limitless distance.
     * @return an ordered list of Events, ordered by the distance from the location, the nearest event being the first in the list.
     */
    public List<Event> findEvents(Location location, int distance) {

        int dist = Math.abs(distance);
        if (dist == 0)
            dist = MAX_GRID_SIZE;

        int startX = location.getX() - dist;
        if (startX < -Location.MAX_LOCATION_SIZE) {
            startX = -Location.MAX_LOCATION_SIZE;
        }
        int endX = location.getX() + dist;
        if (endX > Location.MAX_LOCATION_SIZE) {
            endX = Location.MAX_LOCATION_SIZE;
        }
        int startY = location.getY() - dist;
        if (startY < -Location.MAX_LOCATION_SIZE) {
            startY = -Location.MAX_LOCATION_SIZE;
        }
        int endY = location.getY() + dist;
        if (endY > Location.MAX_LOCATION_SIZE) {
            endY = Location.MAX_LOCATION_SIZE;
        }

        // Use found items to calculate the order by distance,
        List<FoundItem> foundItems = new ArrayList<FoundItem>();

        // Collect all items in range of the offset location
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                Location l = new Location(x, y);
                int d = location.getDistance(l);

                if (d <= dist) { // check location is inside distance range. (exteams of iterations may not be)
                    Event event = getEventAtLocation(l);
                    if (null != event) {
                        foundItems.add(new FoundItem(event, d));
                    }
                }
            }
        }

        Collections.sort(foundItems, new Comparator<FoundItem>() {
            public int compare(FoundItem o1, FoundItem o2) {
                return o1.getDistance() < o2.getDistance() ? -1 : o1.getDistance() == o2.getDistance() ? 0 : 1;
            }
        });

        List<Event> orderedEvents = new LinkedList<Event>();
        for (FoundItem item : foundItems) {
            orderedEvents.add(item.event);
        }
        return orderedEvents;
    }


    /**
     * Add a new Event to the index
     * @param event the Event to Add
     */
    public void addEvent(Event event) {
        if (event.getID() == 0)
            throw new IllegalArgumentException("event ID can not be zero");

        if (eventMap.containsKey(event.getID())) // Remove any existing event with same id.
            removeEvent(event.getID());

        eventMap.put(event.getID(), event);
        addEventID(event.getID(), event.getLocation());

    }

    /**
     * Remove the event with the given ID from the index
     * @param id
     */
    public void removeEvent(int id) {
        Event event = getEvent(id);
        if (null != event) {
            clearLocation(event.getLocation());
        }
    }

    /**
     * Gets the Event by the event ID
     * @param id the ID of the event
     * @return the Event with that id or null if no event with that id exists.
     */
    public Event getEvent(int id) {
        return eventMap.get(id);
    }

    /**
     * Gets the event at the given location.
     * If an event is located at the given location it is returned, otherwise null is returned.
     * @param location the location of the event
     * @return the event at that location or null if no event exists at the given location
     */
    public Event getEventAtLocation(Location location) {
        int id = getEventID(location);
        return id > 0 ? getEvent(id) : null;
    }


    /**
     * Adds the given event ID to the grid index, at the location index.
     * @param id the id of the event
     * @param location the location of the event
     */
    private void addEventID(int id, Location location) {
        int offsetX = location.getX() + Location.MAX_LOCATION_SIZE;
        int offsetY = location.getY() + Location.MAX_LOCATION_SIZE;

        index[offsetX][offsetY] = id;
    }

    /**
     * Clears the grid index of any event at the given location
     * @param location
     */
    private void clearLocation(Location location) {
        addEventID(0, location);
    }

    /**
     * Gets the event id at the given location
     * @param location the location to get the id
     * @return the event id at theat location or zero of no event exists at the location
     */
    private int getEventID(Location location) {
        int offsetX = location.getX() + Location.MAX_LOCATION_SIZE;
        int offsetY = location.getY() + Location.MAX_LOCATION_SIZE;
        return index[offsetX][offsetY];
    }


    /**
     * Internal indexing class used to order Events by distance.
     * Not exposed outside of the index.
     */
    private class FoundItem {

        private Event event;
        private int distance;


        public FoundItem(Event event, int distance) {
            this.event = event;
            this.distance = distance;
        }

        public Event getEvent() { return event;}

        public int getDistance() { return distance;}

    }

}
