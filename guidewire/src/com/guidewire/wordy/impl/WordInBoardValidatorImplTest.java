package com.guidewire.wordy.impl;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.guidewire.wordy.IBoard;

public class WordInBoardValidatorImplTest{
	WordyImpl wImpl;
	WordInBoardValidatorImpl wValidator;
	IBoard cBoard;
	String [] boardStrings;
	static int rows, cols, cells;
	
	@BeforeClass
	public static void setupOnce() throws Exception{
		cells = IBoard.BOARD_CELLS;
		rows = IBoard.BOARD_ROWS;
		cols = IBoard.BOARD_COLUMNS;
	}
	
	@Before
	public void setup() throws Exception{
		boardStrings = new String[]{"FXIE",
								 	"AMLO",
								 	"EWBX",
								 	"ASTU"};
		wImpl = new WordyImpl();
		cBoard = wImpl.generateNewBoard(boardStrings);
		wValidator = new WordInBoardValidatorImpl();
		
	}

	@Test
	public void testSingleLettersWords() {
		int bLen = boardStrings.length;
		int sLen = boardStrings[0].length();
		for(int i = 0; i < bLen; i++){
			String s = boardStrings[i];
			for(int j = 0; j < sLen; j++){
				String word = Character.toString(s.charAt(j));
				assertTrue("Failed testSingleLetterWords test", wValidator.isWordInBoard(cBoard, word));
			}
		}
	}
	
	@Test
	public void testLongWords() {
		int bLen = boardStrings.length;
		int sLen = boardStrings[0].length();
		String word1 = "";
		String word2 = "";
		boolean flag = false;
		
		for(int i = 0; i < bLen; i++){
			String s = boardStrings[i];
			for(int j = 0; j < sLen; j++){
				word1 = word1 + Character.toString(s.charAt(j));
				if(flag){
					word2 = word2 + Character.toString(s.charAt(sLen-j-1));
				}else{
					word2 = word2 + Character.toString(s.charAt(j));
				}
			}
			flag = !flag;
		}
		//FXIEAMLOEWBXASTU - wrong order
		assertTrue("Failed longerThanBoardLength test - all board letters", !wValidator.isWordInBoard(cBoard, word1));
		
		//FXIEOLMAEWBXUTSA - correct order
		assertTrue("Failed longerThanBoardLength test - all board letters - reverse", wValidator.isWordInBoard(cBoard, word2));
		
		char[] chars = new char[cells+1];
		Arrays.fill(chars, 'A');
		String word3 = new String(chars);
		//AAAAAAAAAAAAAAAAA > boardCells
		assertTrue("Failed longerThanBoardLength test", !wValidator.isWordInBoard(cBoard, word3));
		
		String word4 = "FMBUTWAESA";
		String word5 = "FMBUXLXIOE";
		//lower diagonal half
		assertTrue("Failed longerThanBoardLength -lower half", wValidator.isWordInBoard(cBoard, word4));
		//upper diagonal half
		assertTrue("Failed longerThanBoardLength -upper half", wValidator.isWordInBoard(cBoard, word5));
	}
	
	@Test
	public void testEmptyWords() {
		String word1 = "";
		String word2 = "  ";
		assertTrue("Failed emptyWords - 1", !wValidator.isWordInBoard(cBoard, word1));
		assertTrue("Failed emptyWords - 2", !wValidator.isWordInBoard(cBoard, word2));
	}

	@Test
	public void testHistogramCheck() {
		//should fail early
		String word1 = "AAA";
		String word2 = "EEE";

		assertTrue("Failed histogramCheck - 1", !wValidator.isWordInBoard(cBoard, word1));
		assertTrue("Failed histogramCheck - 2", !wValidator.isWordInBoard(cBoard, word2));
	}
	
	@Test
	public void testDifferentCases() {
		String word1 = "FXIEOLMAEWBXUTSA";
		String word2 = word1.toLowerCase();
		String word3 = word1 + "BLAHBLAH";
		String word4 = word3.toLowerCase();
		
		assertTrue("Failed testDiffCases - 1", wValidator.isWordInBoard(cBoard, word1));
		assertTrue("Failed testDiffCases - 2", wValidator.isWordInBoard(cBoard, word2));
		assertTrue("Failed testDiffCases - 3", !wValidator.isWordInBoard(cBoard, word3));
		assertTrue("Failed testDiffCases - 4", !wValidator.isWordInBoard(cBoard, word4));
	}
	
	@After
	public void tearDown() throws Exception{
		wImpl = null;
		cBoard = null;
		wValidator = null;
	}
}
