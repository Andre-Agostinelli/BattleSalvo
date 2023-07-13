package cs3500.pa03.controller;

import cs3500.pa03.model.*;
import cs3500.pa03.view.View;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a BattleSalvoController
 */
public class BattleSalvoController implements Controller {

  private final Player player1;

  private final Player player2;

  private final View view;

  private BattleSalvoBoard blankAI;

  private boolean gameStarted;

  /**
   * Instantiates a BattleSalvoController
   *
   * @param player1 the user player
   * @param player2 the AI player
   * @param view the view to display to the console
   */
  public BattleSalvoController(Player player1, Player player2, View view) {
    //ensure game started
    this.view = Objects.requireNonNull(view);
    this.player1 = Objects.requireNonNull(player1);
    this.player2 = Objects.requireNonNull(player2);
    this.gameStarted = true;
    this.blankAI = buildBlankBoard();
  }

  /**
   * Creates a placeholder board to display to user to fire on
   *
   * @return the blank salvo board
   */
  private BattleSalvoBoard buildBlankBoard() {
    return new BattleSalvoBoard();
  }

  /**
   * Runs the entirety of the program
   */
  public void run() {
    //Welcome intro
    List<Integer> dimensions = view.welcome();
    while (invalidDim(dimensions)) {
      dimensions = view.reWelcome();
    }
    //Fleet setting branch
    int maxFleet = Math.min(dimensions.get(0), dimensions.get(1));
    Map<ShipType, Integer> fleetSpec = view.shipSelection(maxFleet);
    while (invalidShips(fleetSpec, maxFleet)) {
      fleetSpec = view.reShipSelection(maxFleet);
    }
    //Setting up boards
    this.player1.setup(dimensions.get(0), dimensions.get(1), fleetSpec);
    this.player2.setup(dimensions.get(0), dimensions.get(1), fleetSpec);
    //Game going
    while(gameStarted) {

      List<Coord> shotsByPlayer1 = player1.takeShots();      //User player shots
      List<Coord> shotsByPlayer2 = player2.takeShots();      //AI player shots

      if (shotsByPlayer1.isEmpty() || shotsByPlayer2.isEmpty()) {
        showEndScreen(gameOver(shotsByPlayer1, shotsByPlayer2));
        gameStarted = false;
      }

      List<Coord> damageToP1 = player1.reportDamage(shotsByPlayer2);
      List<Coord> damageToP2 = player2.reportDamage(shotsByPlayer1);

      player1.successfulHits(damageToP2);
      player2.successfulHits(damageToP1);
    }
  }

  /**
   * Determines which screen to display
   *
   * @param shotsByPlayer1 the shots by the user player
   * @param shotsByPlayer2 the shots by the AI player
   * @return the corresponding game result
   */
  private GameResult gameOver(List<Coord> shotsByPlayer1, List<Coord> shotsByPlayer2) {
    GameResult tempResult;
    if (shotsByPlayer1.isEmpty() && !(shotsByPlayer2.isEmpty())) {
      tempResult = GameResult.LOSE;
    } else if (!(shotsByPlayer1.isEmpty()) && shotsByPlayer2.isEmpty()) {
      tempResult = GameResult.WIN;
    } else if (shotsByPlayer1.isEmpty() && shotsByPlayer2.isEmpty()) {
      tempResult = GameResult.TIE;
    } else {
      tempResult = GameResult.CONTINUE;
    }
    return tempResult;
  }

  /**
   * Tells the view which screen needs to be shown
   *
   * @param tempResult the result to base the view screen on
   */
  public void showEndScreen(GameResult tempResult) {
    if (tempResult == GameResult.LOSE) {
      view.showLossScreen();
    } else if (tempResult == GameResult.TIE) {
      view.showTieScreen();
    } else {
      view.showWinScreen();
    }
  }

  /**
   * Determines if the input dimensions are valid
   *
   * @param dimensions the dimensions to check
   * @return a boolean flag determining if it is valid, true = invalid, false = valid
   */
  public boolean invalidDim(List<Integer> dimensions) {
    return ((dimensions.get(0) > 15 || dimensions.get(1) > 15)
            || (dimensions.get(0) < 6 || dimensions.get(1) < 6));
  }

  /**
   * Checks to ensure the fleets can be randomly placed still on the board
   *
   * @param fleet the number of each ship to place
   * @param maxFleet the number of total ships to place
   * @return if the user correctly input different number of ships
   */
  public boolean invalidShips(Map<ShipType, Integer> fleet, int maxFleet) {
    int sum;
    int carriers;
    int battleships;
    int destroyers;
    int subs;

    carriers = fleet.get(ShipType.CARRIER);
    battleships = fleet.get(ShipType.BATTLESHIP);
    destroyers = fleet.get(ShipType.DESTROYER);
    subs = fleet.get(ShipType.SUB);
    sum = carriers + battleships + destroyers + subs;
    return sum > maxFleet || carriers == 0 || battleships == 0 ||
            destroyers == 0 || subs == 0;
  }

}
