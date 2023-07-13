package cs3500.pa03.controller;

/**
 * Added Controller interface for extensibility
 */
public interface Controller {

  /**
   * Main method that will run all of our problems
   */
   void run();

  /**
   * Displays end screen
   * @param tempResult result to base off of
   */
   void showEndScreen(GameResult tempResult);

  }
