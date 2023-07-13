package cs3500.pa03.model;

/**
 * Represents a the ship type and size
 */
public enum ShipType {
  CARRIER(6), BATTLESHIP(5), DESTROYER(4), SUB(3);

  /**
   * Final int to assign value to
   */
  private final int shipSize;

  /**
   * Instantiates a shipType enum
   * @param shipSize the number corresponding to the length of a ship
   */
  ShipType(int shipSize) {
    this.shipSize = shipSize;
  }

  /**
   * Gets the ship size integer
   *
   * @return the integer size of the ship
   */
  public int getShipSize() {
    return this.shipSize;
  }
}




