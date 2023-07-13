package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents board pieces through their location, type, and sunk indicator
 */
public class Ship {

  private final List<Coord> location;

  //will need to be altered over game
  public List<Coord> hits;

  private final ShipType shipType;

  //helper method to determine when sunk
  private boolean isSunk;


  /**
   * Instantiates a new ship
   *
   * @param coords the coordinate location of the ship
   * @param shipType the ship type
   */
  public Ship(ShipType shipType, List<Coord> coords) {
    this.shipType = shipType;
    this.location = coords;
    this.hits = new ArrayList<>();
    this.isSunk = false;
  }


  public boolean isHit(Coord coord) {
    for (int i = 0; i < shipType.getShipSize(); i++) {
      if (this.location.get(i).equals(coord)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if a ship is alive
   * @return the boolean result of ship's aliveness
   */
  public boolean isAlive() {
    return (!(this.isSunk));
  }

  /**
   * Determines if a ship has been sunk as they have been hit in every location
   */
  public void sunkCheck() {
    if (this.location.size() == (this.hits.size())) {
      this.isSunk = true;
    }
  }

}


