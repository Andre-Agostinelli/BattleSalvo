package cs3500.pa03.view;

import cs3500.pa03.model.BattleSalvoBoard;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents the command View shown to the user.
 */
public class BattleSalvoCommandView implements View {

  private final Scanner scanner;

  private final Appendable appendTo;

  /**
   * Initiates a view object.
   *
   * @param read the readable to read from for input data.
   * @param appendTo the appendable to output to for output data.
   */
  public BattleSalvoCommandView(Readable read, Appendable appendTo) {
    this.scanner = new Scanner(read);
    this.appendTo = Objects.requireNonNull(appendTo);
  }

  /**
   * Welcomes the user and returns the board dimensions
   *
   * @return a list with the dimensions at index 0 and 1
   */
  public List<Integer> welcome() {
    try {
      this.appendTo.append("Hello! Welcome to the OOD BattleSalvo Game!\n")
              .append("Please enter a valid height and width below:\n")
              .append("------------------------------------------------------\n");
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
    List<Integer> intList;
    intList = readInDimensions();
    return intList;
  }

  /**
   * reWelcome's the user if needed and gets valid dimensions
   *
   * @return the integer's for width and height
   */
  public List<Integer> reWelcome() {
    String ErrorMessage = "------------------------------------------------------\n" +
            "Uh Oh! You've entered invalid dimensions. Please remember that the height and width\n" +
            "of the game must be in the range (6, 15), inclusive. Try again!\n" +
            "------------------------------------------------------\n";
    try {
      this.appendTo.append(ErrorMessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
    List<Integer> intList;
    intList = readInDimensions();
    return intList;
  }

  /**
   * Parses in 2 numbers to board dimensions (height, width)
   *
   * @return the dimensions of the game board
   */
  public List<Integer> readInDimensions() {
    List<Integer> intList = new ArrayList<>();
    String s = this.scanner.nextLine();
    String[] str = s.split(" ");
    if (str.length != 2) {
      throw new IllegalArgumentException("Invalid number of arguments");
    } else {
      intList.add(Integer.parseInt(str[0]));
      intList.add(Integer.parseInt(str[1]));
      return intList;
    }
  }

  /**
   * Gets the mapping back of the type of ship to the number of occurrences
   *
   * @param maxFleet the max number of ships that can be placed
   * @return Map of (ShipTye, Integer) the specifications for each number of ship
   */
  @Override
  public Map<ShipType, Integer> shipSelection(int maxFleet) {
    String shipSelectMsg = "Please enter your fleet in the order [Carrier, Battleship, Destroyer," +
            " Submarine].\n" +
            "Remember, your fleet may not exceed size " + maxFleet + ".\n" +
            "--------------------------------------------------------------------------------\n";
    try {
      this.appendTo.append(shipSelectMsg);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
    Map<ShipType, Integer> map = new HashMap<>();
    return map = readInShips();
  }

  /**
   * Gets the mapping back of the type of ship to the number of occurrences
   *
   * @param maxFleet the max number of ships that can be placed
   * @return Map of (ShipTye, Integer) the specifications for each number of ship
   */
  @Override
  public Map<ShipType, Integer> reShipSelection(int maxFleet) {
    String reSelectMsg = "------------------------------------------------------------------" +
            "--------------\n" + "Uh Oh! You've entered invalid fleet sizes.\n" +
            "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n" +
            "Remember, your fleet may not exceed size " + maxFleet + ".\n" +
            "--------------------------------------------------------------------------------\n";
    try {
      this.appendTo.append(reSelectMsg);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
    Map<ShipType, Integer> map = new HashMap<>();
    return map = readInShips();
  }

  /**
   * Shows the coordinates collection screen to the user
   *
   * @param numShots the number of shots allowed
   * @return the List(Coord) representing a volley of shots
   */
  @Override
  public List<Coord> selectShots(int numShots) {
    String select = "Please Enter" + numShots + "Shots:\n" +
            "------------------------------------------------------------------\n";
    try {
      this.appendTo.append(select);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }

    List<Coord> output = new ArrayList<>();
    for (int i = 0; i < numShots; i++) {
      int a = this.scanner.nextInt();
      int b = this.scanner.nextInt();
      Coord coord = new Coord(a,b);
      output.add(coord);
    }
    return output;
  }

  /**
   * Displays the game state
   * @param userBoard the userBoard to be displayed
   * @param enemyBoard the enemyBoard with shots fired on it to be displayed
   */
  @Override
  public void displayBoard(BattleSalvoBoard userBoard, BattleSalvoBoard enemyBoard) {
    String output = "\nOpponent Board Data:\n" +
            enemyBoard.toString() + "\nYour Board: \n" + userBoard.toString() + "\n";
    try {
      this.appendTo.append(output);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
  }

  /**
   * Parses the specifications of ships into a map to be returned
   *
   * @return Map<ShipType, Integer>
   */
  private Map<ShipType, Integer> readInShips() {
    Map<ShipType, Integer> map = new HashMap<>();
    List<Integer> intList = new ArrayList<>();
    String s = this.scanner.nextLine();
    String[] str = s.split(" ");
    if (str.length != 4) {
      throw new IllegalArgumentException("Invalid number of arguments");
    } else {
      intList.add(Integer.parseInt(str[0]));
      intList.add(Integer.parseInt(str[1]));
      intList.add(Integer.parseInt(str[2]));
      intList.add(Integer.parseInt(str[3]));
      map.put(ShipType.CARRIER, intList.get(0));
      map.put(ShipType.BATTLESHIP, intList.get(1));
      map.put(ShipType.DESTROYER, intList.get(2));
      map.put(ShipType.SUB, intList.get(3));
      return map;
    }
  }

  /**
   * Write out to a given stream
   *
   * @param phrase the String phrase to be written
   */
  @Override
  public void write(String phrase) {
    try {
      appendTo.append(phrase);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Reads from an input source
   *
   * @return the String contents from reading in
   */
  @Override
  public String read() {
    return null;
  }

  /**
   * Displays a loss screen to the user
   */
  @Override
  public void showLossScreen() {
    String loseMessage = "------------------------------------------------------------------" +
            "--------------\n" + "Unfortunately, you have lost the BattleSalvo game!\n" +
            "--------------------------------------------------------------------------------\n";
    try {
      this.appendTo.append(loseMessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
  }

  /**
   * Displays a tie screen to the user
   */
  @Override
  public void showTieScreen() {
    String tieMessage = "------------------------------------------------------------------" +
            "--------------\n" + "You tied the opposing player!\n" +
            "--------------------------------------------------------------------------------\n";
    try {
      this.appendTo.append(tieMessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
  }

  /**
   * Displays a win screen to the user
   */
  @Override
  public void showWinScreen() {
    String winMessage = "------------------------------------------------------------------" +
            "--------------\n" + "Congratulations, you have won the BattleSalvo game!\n" +
            "--------------------------------------------------------------------------------\n";
    try {
      this.appendTo.append(winMessage);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
  }

  /**
   * Gets the shot coordinates from the user
   * @param shotNum the number of shots to fire
   * @return the List of Coord with locations of shots
   */
  @Override
  public List<Coord> getUserShots(int shotNum) {
    String getShotsMsg = "Please Enter " + shotNum + " Shots: \n" +
    "------------------------------------------------------------------\n";
    try {
      this.appendTo.append(getShotsMsg);
    } catch (IOException e) {
      throw new IllegalArgumentException("invalid appendable");
    }
    List<Coord> shots = new ArrayList<>();

    for (int i = 0; i < shotNum; i++) {
      String s = this.scanner.nextLine();
      String[] str = s.split(" ");
      if (str.length != 2) {
        throw new IllegalArgumentException("Invalid number of arguments");
      } else {
        Coord coord = new Coord(Integer.parseInt(str[1]), Integer.parseInt(str[0]));
        shots.add(coord);
      }
    }
    return shots;
  }

}


