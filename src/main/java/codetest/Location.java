package codetest;

/**
 * Location class holds the coordinates of a single location.
 * Each location is referenced by its grid reference, X and Y.
 *
 * Created by rgilham on 11/12/2017.
 */
public class Location {

    public static final int MAX_LOCATION_SIZE = 10;

    private int X;
    private int Y;

    public Location() {
        this(0,0);
    }

    public Location(int x, int y) {
        if (Math.abs(x) > MAX_LOCATION_SIZE)
            throw new IllegalArgumentException(String.format("location %d, %d is outside the bounds of the location grid, size %d", x, y, MAX_LOCATION_SIZE));

        if (Math.abs(y) > MAX_LOCATION_SIZE)
            throw new IllegalArgumentException(String.format("location %d, %d is outside the bounds of the location grid, size %d", x, y, MAX_LOCATION_SIZE));

        X = x;
        Y = y;
    }

    /**
     * Gets the 'X' coordinate for the Location
     * @return the X grid reference
     */
    public int getX() {
        return X;
    }

    /**
     * Gets the 'Y' coordinate for the Location
     * @return the Y grid reference
     */
    public int getY() {
        return Y;
    }

    /**
     * Gets the distance in blocks, of the given location, relative to this location.
     * @param l the location to measure from
     * @return the distance, in blocks, from this location to the given location.
     */
    public int getDistance(Location l) {
        int xd = Math.abs((l.X + 10) - (this.X + 10));
        int yd = Math.abs((l.Y + 10) - (this.Y + 10));
        return xd + yd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (X != location.X) return false;
        return Y == location.Y;
    }

    @Override
    public int hashCode() {
        int result = X;
        result = 31 * result + Y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s-{X=%d,Y=%d}", super.toString(), this.getX(), this.getY());
    }

    /**
     * Parse a string in the format of x,y into a location object.
     * Will strip whitespace/
     * @param location
     * @return
     */
    public static Location parseLocation(String location) {
        if (null == location)
            return null;

        String[] s = location.split(",");
        if (s.length != 2)
                throw new IllegalArgumentException("Failed to parse '" + location + "' into a Location");
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);

        return new Location(x, y);

    }
}


