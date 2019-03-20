package com.chess.engine.board;

import java.util.Map;

public class BoardUtils {
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGHT_COLUMN = initColumn(7);
	
	public static final boolean[] EIGHTH_RANK = initRow(0);
	public static final boolean[] SEVENTH_RANK = initRow(8);
	public static final boolean[] SIXTH_RANK = initRow(16);
	public static final boolean[] FIFTH_RANK  = initRow(24);
	public static final boolean[] FOURTH_RANK = initRow(32);
	public static final boolean[] THIRD_RANK = initRow(40);
	public static final boolean[] SECOND_RANK = initRow(48);
	public static final boolean[] FIRST_RANK = initRow(56);
	
	
	private static final String[] ALGEBREIC_NOTATION = initializeAlgebreicNotation();
	private static final Map<String, Integer> POSITION_TO_COORDINATE  = initializePositionToCoordinateMap();
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW = 8;
	
	private BoardUtils() {
		throw new RuntimeException("Cannot instantiate Utils Class");
	}
	
	private static Map<String, Integer> initializePositionToCoordinateMap() {
		// TODO Auto-generated method stub
		return null;
	}

	private static String[] initializeAlgebreicNotation() {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean[] initRow(int rowNumber) {
		final boolean[] row = new boolean[NUM_TILES];
		do {
			row[rowNumber] = true;
			rowNumber++;		
		}while(rowNumber % NUM_TILES_PER_ROW != 0);
		return row;
	}

	private static boolean[] initColumn(int columnNumber) {
		
		final boolean[] column = new boolean[NUM_TILES];
		while(columnNumber < NUM_TILES) {
			column[columnNumber] = true;
			columnNumber += NUM_TILES_PER_ROW;	
		}
		return column;
	}

	public static boolean isValidTileCoordinate(final int coordinate) {		
		return coordinate >= 0 && coordinate < NUM_TILES;
	}

	public static int getCoordinateAtPosition(final String destinationCoordinate) {
		return POSITION_TO_COORDINATE.get(destinationCoordinate);
	}
	
	public static String getPositionAtCoordinate(final int destinationCoordinate) {
		return ALGEBREIC_NOTATION[destinationCoordinate];
	}
}
