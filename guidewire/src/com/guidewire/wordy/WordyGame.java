package com.guidewire.wordy;

import com.guidewire.wordy.impl.WordyImpl;

/**
 * The client application that uses the Wordy implementation.
 */
public class WordyGame {

  public static void main(String[] args) {
    IWordy wordy = new WordyImpl(); // Instantiate your Wordy implementation here
    new WordyFrame(wordy).setVisible(true);
  }

}
