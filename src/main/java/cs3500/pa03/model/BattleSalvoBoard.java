package cs3500.pa03.model;

import java.util.*;

/**
 * Represents a BattleSalvoBoard
 */
public class BattleSalvoBoard {

  Cell[][] cells;

  Map<ShipType, Integer> specifications;

  List<Ship> fleet;

  /**
   * Instantiates a board with cells a number of ships
   *
   * @param board the cells on a given board
   * @param specs the specification mapping the number of ships for each type
   */
  public BattleSalvoBoard(Cell[][] board, Map<ShipType, Integer> specs) {
    this.cells = board;
    this.specifications = specs;
    this.fleet = new ArrayList<>();
  }

  /**
   * Instantiates a board with cells a number of ships
   */
  public BattleSalvoBoard() {
  }

  /**
   * Creates a random board for the AiPlayer
   *
   * @param height         the height of the board
   * @param width          the width of the board
   * @param specifications the specification mapping ship type to the number of ships appearing on
   *                       board
   */
  public void createBoard(int height, int width, Map<ShipType, Integer> specifications) {
    this.cells = createCells(height, width);
    this.specifications = specifications;
    this.fleet = new ArrayList<>();
    placeAllShips(this.cells, this.specifications, fleet);
  }

  /**
   * Creates the cells initially based on the height and dimensions
   *
   * @param height the height
   * @param width  the width
   * @return the Cells
   */
  private Cell[][] createCells(int height, int width) {
    Cell[][] outputCells = new Cell[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        outputCells[i][j] = new Cell(new Coord(j, i));
      }
    }
    return outputCells;
  }


  /**
   * Places the correct number of the ships on the board
   *
   * @param cells          the given game board to place the ships on
   * @param specifications the specification of number of ship
   */
  private void placeAllShips(Cell[][] cells, Map<ShipType, Integer> specifications,
                             List<Ship> fleet) {
    int carrierNum = specifications.get(ShipType.CARRIER);
    int bShipNum = specifications.get(ShipType.BATTLESHIP);
    int destroyerNum = specifications.get(ShipType.DESTROYER);
    int subNum = specifications.get(ShipType.SUB);
    for (int i = 0; i < carrierNum; i++) {
      placeShips(cells, ShipType.CARRIER, fleet);
    }
    for (int i = 0; i < bShipNum; i++) {
      placeShips(cells, ShipType.BATTLESHIP, fleet);
    }
    for (int i = 0; i < destroyerNum; i++) {
      placeShips(cells, ShipType.DESTROYER, fleet);
    }
    for (int i = 0; i < subNum; i++) {
      placeShips(cells, ShipType.SUB, fleet);
    }
  }


  /**
   * Places ships on different spaces on the board
   *
   * @param cells the board to place the ship on
   * @param shipType the ship type enum
   * @param fleet the list of ships to place
   */
  private void placeShips(Cell[][] cells, ShipType shipType, List<Ship> fleet) {
    //TODO: SEED THIS FOR TESTING
    Random rand = new Random(1);
    boolean beenCalled = false;
    while(!beenCalled) {
      int x = rand.nextInt(cells[0].length - 1); // width
      int y = rand.nextInt(cells.length - 1); // height
      if (canBePlacedVertically(cells, cells[y][x], shipType)) {
        placeShipVertically(cells, x, y, true, shipType, fleet);
        beenCalled = true;
      } else if (canBePlacedHorizontally(cells, cells[y][x], shipType)) {
        placeShipVertically(cells, x, y, false, shipType, fleet);
        beenCalled = true;
      }
    }
  }

  /**
   * Determines if a ship can be placed vertically
   * @param cells the board to place the ship on
   * @param shipPosition the ship location
   * @param shipType the ship type
   * @return a boolean result of which way something can be placed
   */
  private boolean canBePlacedVertically(Cell[][] cells, Cell shipPosition,
                                        ShipType shipType) {
    Coord coord = shipPosition.getCoord();
    boolean flag = true;
    //can place vertically?
    if (coord.getYLocation() + shipType.getShipSize() > cells.length) {
      return false;
    } else {
      for (int i = 0; i < shipType.getShipSize(); i++) {
        if (cells[coord.getYLocation()][coord.getXLocation()].hasShip)  {
          flag = false;
        }
        coord = new Coord(coord.getXLocation(), coord.getYLocation() + 1);
      }
      return flag;
    }
  }

  /**
   * Determines if a ship can be placed horizontally
   * @param cells the board to place the ship on
   * @param shipPosition the ship location
   * @param shipType the ship type
   * @return a boolean result of which way something can be placed
   */
  private boolean canBePlacedHorizontally(Cell[][] cells, Cell shipPosition,
                                          ShipType shipType) {
    Coord coord = shipPosition.getCoord();
    boolean flag = true;
    //can place horizontally?
    if (coord.getXLocation() + shipType.getShipSize() > cells[0].length) {
      return false;
    } else {
      for (int i = 0; i < shipType.getShipSize(); i++) {
        if (cells[coord.getYLocation()][coord.getXLocation()].hasShip)  {
          flag = false;
        }
        coord = new Coord(coord.getXLocation() + 1, coord.getYLocation());
      }
      return flag;
    }
  }

  /**
   * Places ships depending on their shipSize and direction
   *
   * @param cells the game board
   * @param i i index of place to set ship in Cell[i][j] array
   * @param j j index of place to set ship
   * @param vertical flag to determine if ship should be placed vertically or horizontally
   * @param shipType the ShipType enum and size corresponding to the name
   */
  private void placeShipVertically(Cell[][] cells, int i, int j, boolean vertical,
                                   ShipType shipType, List<Ship> fleet) {
    List<Coord> coords = new ArrayList<>();
    if (vertical) {
      for (int x = 0; x < shipType.getShipSize(); x++) {
        Coord coord = new Coord(i, j);
        coords.add(coord);
        cells[j][i].hasShip = true;
        j++;
      }
    } else {
      for (int x = 0; x < shipType.getShipSize(); x++) {
        Coord coord = new Coord(i, j);
        coords.add(coord);
        cells[j][i].hasShip = true;
        i++;
      }
    }
    Ship ship = new Ship(shipType, coords);
    fleet.add(ship);
  }


  /**
   * Always will output board in formatted version
   *
   * @return the properly formatted string of the board
   */
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    int height = this.cells.length;
    int width = this.cells[0].length;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        output.append(this.cells[i][j].toChar()).append(" ");
      }
      output.append("\n");
    }
    return output.toString();
  }


  /**
   * Creates a blank board placeholder
   *
   * @param height the height length
   * @param width the width length
   */
  public void createBlankBoard(int height, int width) {
    this.cells = createCells(height, width);
    this.fleet = Collections.emptyList();
    this.specifications = Collections.emptyMap();
  }
}

