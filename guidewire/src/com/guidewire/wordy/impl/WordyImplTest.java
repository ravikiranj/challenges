package com.guidewire.wordy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.guidewire.wordy.IBoard;

public class WordyImplTest{
	WordyImpl wImpl;
	IBoard cBoard;
	String [] boardStrings;
	
	@Before
	public void setup() throws Exception{
		boardStrings = new String[]{"FXIE",
								 	"AMLO",
								 	"EWBX",
								 	"ASTU"};
		wImpl = new WordyImpl();
		cBoard = wImpl.generateNewBoard(boardStrings);
	}

	@Test
	public void testScoreWordsCountsWordsThatAreBothValidAndInBoard() {
		List<String> words = Arrays.asList("stub", "swami", "amble", "axile","wames");
		assertEquals("Failed to validate words that are both valid and on board", wImpl.scoreWords(words), 9);
	}

	@Test
	public void testScoreWordsCountsDuplicatesOnlyOnce() {
		List<String> words = Arrays.asList("stub", "stub", "amble", "amble","blahblahblahblah");
		assertEquals("Failed to validate duplicate valid words (count only once)", wImpl.scoreWords(words), 3);
	}

	@Test
	public void testScoreWordsWithLeadingAndTrailingSpaces() {
		List<String> words = Arrays.asList("  stub", "wames  ", "  swami  ", "amble", "axile", " a ", "blahblahblahblah");
		assertEquals("Failed to validate words with leading or trailing spaces", wImpl.scoreWords(words), 9);
	}

	@Test
	public void testScoreWordsDoesNotCountWordsThatAreValidButNotInBoard() {
		List<String> words = Arrays.asList("aa", "aah", "aahed", "aahing", "aahs", "aal", "aalii", "aaliis");
		assertEquals("Failed to validate words that are valid but not in board", wImpl.scoreWords(words), 0);
	}
	
	@Test
	public void testGenNewBoardIsOfTypeIBoard(){
		IBoard newBoard = wImpl.generateNewBoard();
		assertTrue("Failed GenNewBoardIsOfTypeIBoard", newBoard instanceof IBoard);
	}
	
	@Test
	public void testValidBoard(){
		IBoard board = wImpl.generateNewBoard();
		int rows = IBoard.BOARD_ROWS;
		int cols = IBoard.BOARD_COLUMNS;
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				assertNotNull("Failed testValidBoard", board.getCell(i, j));
			}
		}
	}
	
	@Test
	public void testCustomBoard(){
		int rows = IBoard.BOARD_ROWS;
		int cols = IBoard.BOARD_COLUMNS;
		
		for(int i = 0; i < rows; i++){
			String s = boardStrings[i];
			for(int j = 0; j < cols; j++){
				assertEquals("Failed to validate custom board", cBoard.getCell(i, j), s.charAt(j));
			}
		}
	}

	@After
	public void tearDown() throws Exception{
		wImpl = null;
	}
}