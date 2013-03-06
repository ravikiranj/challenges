package com.guidewire.wordy.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.guidewire.wordy.IBoard;
import com.guidewire.wordy.IWordy;

public class WordyImpl implements IWordy {
	IBoard board;
	File dictionary;

	public WordyImpl() {
		dictionary = new File("CROSSWD.TXT");
		//String currentDir = new File(".").getAbsolutePath();
	}

	@Override
	public IBoard generateNewBoard() {
		board = new BoardImpl();
		return board;
	}

	@Override
	public int scoreWords(List<String> words) {
		int score = 0;
		WordScorerImpl wScorer = new WordScorerImpl();
		try {
			WordInBoardValidatorImpl wibValidator = new WordInBoardValidatorImpl();
			WordValidatorImpl wValidator = new WordValidatorImpl(dictionary);
			for (String word : words) {
				// Fail fast depending on which of the 2 conditions is faster to check
				if (wibValidator.isWordInBoard(board, word) && wValidator.isRealWord(word)) {
					score += wScorer.scoreWord(word);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return score;
	}
}