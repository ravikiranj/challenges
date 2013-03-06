package com.guidewire.wordy.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
	
	public IBoard generateNewBoard(String[] boardStrings) {
		board = new BoardImpl(boardStrings);
		return board;
	}

	@Override
	public int scoreWords(List<String> words) {
		//initialize score
		int score = 0;
		
		//Remove duplicates in the list by using Set
		List<String>uniqWords = new ArrayList<String>(new LinkedHashSet<String>(words));
		
		WordScorerImpl wScorer = new WordScorerImpl();
		
		try {
			WordInBoardValidatorImpl wibValidator = new WordInBoardValidatorImpl();
			WordValidatorImpl wValidator = new WordValidatorImpl(dictionary);
			for (String word : uniqWords) {
				//trim the string
				word = word.trim();
				// Fail fast depending on which of the 2 conditions is faster to check
				// Can switch conditions if the board is much bigger in size (currently 4x4)
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