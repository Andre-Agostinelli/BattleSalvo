package cs3500.pa03.model;

/**
 * Represents a cell on the BattleSalvo board
 */
public class Cell {

  private final Coord coord;

  // Making these modifiable so then can be accessed and changed
  public boolean beenShot;

  public boolean hasShip;


  /**
   * Instantiates a cell of the gameboard with a coordinate assigned
   * @param coord the coordinate to be assigned
   */
  public Cell(Coord coord) {
    this.coord = coord;
    this.beenShot = false;
    this.hasShip = false;
  }

  /**
   * Gets a coord from a cell
   *
   * @return the Coord corresponding to the cell
   */
  public Coord getCoord() {
    return this.coord;
  }

  /**
   * The char value a cell on a board is represented as
   *
   * @return the character to represent the cell by
   */
  public char toChar() {
    char charHolder = '0';
    if (!this.hasShip && this.beenShot) {
      charHolder = '*';
    } else if (this.hasShip && this.beenShot) {
      charHolder = 'X';
    } else if (this.hasShip && !this.beenShot) {
      charHolder = 'B';
    }
    return charHolder;
  }
}
