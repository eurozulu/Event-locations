package codetest;

import java.util.*;

/**
 * The Event Index maintaining all the Events available, indexed by their location.
 * <p>
 * Created by rgilham on 11/12/2017.
 */
public class EventIndex {

    private static final int MAX_GRID_SIZE = (Location.MAX_LOCATION_SIZE * 2) + 1;

    private final Map<Location, List<Event>> eventsMap = new HashMap<Location, List<Event>>();


    /**
     * Gets an ordered list of EventIndex found at the location and within the given distance of the location, ordered by the distance from the given location.
     *
     * @param location the location to search from
     * @param distance the distance from the location to search from, zero = limitless distance.
     * @return an ordered list of EventIndex, ordered by the distance from the location, the nearest event being the first in the list.
     */
    public List<Event> findEvents(Location location, int distance) {

        List<Event> orderedEvents = new LinkedList<>();
        orderedEvents.addAll(getAllEvents());

        // Sort found items by distance
        Collections.sort(orderedEvents, new Comparator<Event>() {
            public int compare(Event e1, Event e2) {

                // Compare the distances for each event, from the 'location' and sort by result.
                int d1 = e1.getLocation().getDistance(location);
                int d2 = e2.getLocation().getDistance(location);

                return d1 < d2 ? -1 : d1 > d2 ? 1 : 0;
            }
        });

        // restrict list to specified distance, zero < == no limit
        if (distance > 0) {
            int index = 0;
            for (Event event : orderedEvents) {
                if (event.getLocation().getDistance(location) > distance)
                    break; // Bail out once first event beyond distance as list is ordered.
                index++;
            }
            if (index < orderedEvents.size()) {
                for (int i=orderedEvents.size() - 1; i >= index; i--) {
                    orderedEvents.remove(i);
                }
            }
        }
        return orderedEvents;
    }


    /**
     * Add a new Event to the index
     *
     * @param event the Event to Add
     */
    public void addEvent(Event event) {
        if (event.getID() == 0)
            throw new IllegalArgumentException("event ID can not be zero");

        Location location = event.getLocation();
        if (location == null)
            throw new IllegalArgumentException("Event has no location");

        if (!eventsMap.containsKey(location)) { // If location never seen, create a new, empty list for its events.
            eventsMap.put(location, new ArrayList<Event>());
        }

        if (!eventsMap.get(location).contains(event)) // Only add if it's not already in the list.
            eventsMap.get(location).add(event);
    }

    public void removeEvent(int eventID) {
        Event event = getEventById(eventID);
        while (null != event) {
            List<Event> eventsList = eventsMap.get(event.getLocation());
            if (null != eventsList) {
                eventsList.remove(event);
            }
            event = getEventById(eventID);
        }
    }

    private Event getEventById(int id) {
        Event foundEvent = null;

        for (List<Event> events : eventsMap.values()) {
            for (Event event : events) {
                if (event.getID() == id) {
                    foundEvent = event;
                    break;
                }
            }
            if (null != foundEvent)
                break; // Bail out of out loop if event found.
        }
        return foundEvent;
    }


    /**
     * Gets all of the events, in no specfic order
     *
     * @return
     */
    public Collection<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        for (List<Event> eventList : eventsMap.values()) {
            events.addAll(eventList);
        }
        return events;
    }


    /**
     * Get a list of EventDistances, All events with their distance from the given location.
     * @param fromLocation the location to measure distance to the event from.
     * @return a list of all events and their distance to the given location
     */
    private List<EventDistance> getEventDistances(Location fromLocation) {

        List<EventDistance> eventDistances = new ArrayList<EventDistance>();

        for (Map.Entry<Location, List<Event>> entry : eventsMap.entrySet()) {
            int evDistance = entry.getKey().getDistance(fromLocation);

            for (Event event : entry.getValue()) {
                eventDistances.add(new EventDistance(event, evDistance));
            }
        }
        return eventDistances;
    }



    /**
     * Internal indexing class used to order EventIndex by distance.
     * Not exposed outside of the index.
     */
    private class EventDistance {

        private Event event;
        private int distance;


        public EventDistance(Event event, int distance) {
            this.event = event;
            this.distance = distance;
        }

        public Event getEvent() {
            return event;
        }

        public int getDistance() {
            return distance;
        }

    }

}
