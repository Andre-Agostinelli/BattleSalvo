package cs3500.pa03.view;

import cs3500.pa03.model.BattleSalvoBoard;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import java.util.List;
import java.util.Map;

/**
 * The view interface with all associated methods
 */
public interface View {

  void displayBoard(BattleSalvoBoard userBoard, BattleSalvoBoard enemyBoard);

  void write(String phrase);

  String read();

  List<Integer> welcome();

  Map<ShipType, Integer> shipSelection(int maxFleet);

  List<Integer> reWelcome();

  Map<ShipType, Integer> reShipSelection(int maxFleet);

  List<Coord> selectShots(int numShots);

  void showLossScreen();

  void showTieScreen();

  void showWinScreen();


  List<Coord> getUserShots(int shotNum);
}
