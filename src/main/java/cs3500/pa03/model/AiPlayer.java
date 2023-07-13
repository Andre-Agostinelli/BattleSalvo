package cs3500.pa03.model;

import cs3500.pa03.controller.GameResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the AiPlayer to be played against
 */
public class AiPlayer implements Player {

  String name;

  BattleSalvoBoard userBoard;

  BattleSalvoBoard enemyBoardCopy;


  /**
   * Instantiates a new player user
   *
   * @param name the name of the player
   */
  public AiPlayer(String name) {
    this.name = name;
    this.userBoard = new BattleSalvoBoard();
    this.enemyBoardCopy = new BattleSalvoBoard();
  }

  /**
   * Instantiates a new player user
   *
   * @param name the name of the player
   * @param userBoard the game to be made with an enemy board placeholder
   */
  public AiPlayer(String name, BattleSalvoBoard userBoard) {
    this.name = name;
    this.userBoard = userBoard;
    this.enemyBoardCopy = new BattleSalvoBoard();
  }

  /**
   * Gets the name of the AiPlayer
   *
   * @return the string name representation
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Setups the game by creating the 2D cell array and placing the ships
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return returns the list of all the ships
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.userBoard.createBoard(height, width, specifications);
    this.enemyBoardCopy.createBlankBoard(height, width);     //creates an empty board to fire upon
    return this.userBoard.fleet;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    //TODO: SEED THIS
    Random rand = new Random(1);
    int height = this.userBoard.cells.length;
    int width = this.userBoard.cells[0].length;
    List<Coord> coords = new ArrayList<>();
    int shotNum = findShotNum(this.userBoard.fleet);
    while (shotNum > 0) {
      int j = rand.nextInt(width);
      int i = rand.nextInt(height);
      while (this.enemyBoardCopy.cells[j][i].beenShot) {
        j = rand.nextInt(width);
        i = rand.nextInt(height);
      }
      Coord coord = new Coord(j,i);
      this.enemyBoardCopy.cells[j][i].beenShot = true;
      coords.add(coord);
      shotNum--;
    }
    return coords;
  }

  /**
   * Determines the shots a user/AI is allowed to take based on alive ships
   *
   * @param fleet the list of ships to determine shot count
   * @return the integer amount of shots to fire
   */
  private int findShotNum(List<Ship> fleet) {
    int output = 0;
    for (Ship ship : fleet) {
      if (ship.isAlive()) {
        output++;
      }
    }
    return output;
  }

  /**
   * Reports the damage to the other player by returning a list of Coord's that hit
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the list of Coord that hit on user board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> output = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      for (int i = 0; i < this.userBoard.fleet.size(); i++) {
        int x = coord.getXLocation();
        int y = coord.getYLocation();
        if (this.userBoard.fleet.get(i).isHit(coord) &&
                !(this.userBoard.fleet.get(i).hits.contains(coord))) {
          //this also adds hit coord to ship metadata
          this.userBoard.cells[x][y].beenShot = true;
          this.userBoard.fleet.get(i).hits.add(coord);
          //check if ship is sunk
          this.userBoard.fleet.get(i).sunkCheck();
          output.add(coord);
        } else {
          this.userBoard.cells[x][y].beenShot = true;
        }
      }
    }
    return output;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      int x = coord.getXLocation();
      int y = coord.getYLocation();
      this.enemyBoardCopy.cells[x][y].hasShip = true;
    }
  }

  /**
   * Not needed for this implementation
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}
