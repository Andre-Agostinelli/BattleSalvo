package cs3500.pa03;

import cs3500.pa03.controller.BattleSalvoController;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.UserPlayer;
import cs3500.pa03.view.BattleSalvoCommandView;
import cs3500.pa03.view.View;
import java.io.InputStreamReader;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {

    View view = new BattleSalvoCommandView(new InputStreamReader(System.in), System.out);
    // assigning players
    Player player1 = new UserPlayer("Andre", view);
    Player player2 = new AiPlayer("BartAI");

    Controller controlller = new BattleSalvoController(player1, player2, view);
    controlller.run();
  }

}