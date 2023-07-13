package cs3500.pa03.model;

/**
 * Represents coordinates on the battleship board
 */
public class Coord {

  private final int x;

  private final int y;

  /**
   * Instantiates a coordinate object
   *
   * @param xLoc integer representing the x location
   * @param yLoc integer representing the y location
   */
  public Coord(int xLoc, int yLoc) {
    this.x = xLoc;
    this.y = yLoc;
  }


  /**
   * Returns the given objects xLocation
   *
   * @return integer x location
   */
  public int getXLocation() {
    return this.x;
  }

  /**
   * Returns the given objects yLocation
   *
   * @return integer y location
   */
  public int getYLocation() {
    return this.y;
  }

  /**
   * Equality checker for coordinates
   * @param coord the coord to check against
   * @return the boolean result of the comparison
   */
  public boolean equals(Coord coord) {
      return this.x == coord.getXLocation() && this.y == coord.getYLocation();
    }

}
