package com.guidewire.wordy.impl;

import java.util.HashMap;
import java.util.Map;

import com.guidewire.wordy.IBoard;
import com.guidewire.wordy.IWordInBoardValidator;

public class WordInBoardValidatorImpl implements IWordInBoardValidator {
	@Override
	public boolean isWordInBoard(IBoard board, String word) {
		// Try to fail early if possible with multiple checks and finally make
		// sure it passes the snake test
		
		// Get board properties
		int rows = IBoard.BOARD_ROWS;
		int cols = IBoard.BOARD_COLUMNS;
		int cells = IBoard.BOARD_CELLS;
		
		// Convert to lower case
		word = word.toLowerCase();
		
		// Check if word length >= 3 or length > cells
		if (word.length() < 3 || word.length() > cells) {
			return false;
		}

		// map to store character count
		Map<Character, Integer> charCount = new HashMap<Character, Integer>();
		// char array to store all board characters
		char[] boardChars = new char[cells];
		int row, col, k = 0;
		
		//Get character count of the board
		for (row = 0; row < rows; row++) {
			for (col = 0; col < cols; col++) {
				Character c = Character.toLowerCase(board.getCell(row, col));
				boardChars[k++] = c;
				if (charCount.containsKey(c)) {
					charCount.put(c, charCount.get(c) + 1);
				} else {
					charCount.put(c, 1);
				}
			}
		}

		// Iterate through the string and check if all characters are available
		// in the board
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			// if character is not present in map or has less no. characters than
			// required, return false
			if (!charCount.containsKey(c) || charCount.get(c) == 0) {
				return false;
			}
			charCount.put(c, charCount.get(c) - 1);
		}

		// Snake test - check if the word is possible from each possible starting location
		for (row = 0; row < rows; row++) {
			for (col = 0; col < cols; col++) {
				if (wordInBoard(boardChars, word, row, col, rows, cols)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean wordInBoard(char[] letters, String word, int x, int y, int rows, int cols) {
		//Empty word, all letters found
		if(word.equals("")){
			return true;
		}
		//Outside of the board
		if(x < 0 || x > rows-1 || y < 0 || y > cols-1){
			return false;
		}
		//Wrong position
		if(letters[x*rows+y] != word.charAt(0)){
			return false;
		}
		//a letter can be reused only once, so send a clone with the modified change
		char[] newLetters = letters.clone();
		newLetters[x*rows+y] = '\0'; 
		
		//so far the word matches, check the next letter
		for(int dx = -1; dx <= 1; dx++){
			for(int dy = -1; dy <= 1; dy++){
				if(wordInBoard(newLetters, word.substring(1), x+dx, y+dy, rows, cols)){
					return true;
				}
			}
		}
		return false;
	}
}
