package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tile {

	protected final int tileCoordinate;
		
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	private Tile(final int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
		
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){
		
		final Map<Integer, EmptyTile> emptyTiles = new HashMap<>();
		
		for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTiles.put(i, new EmptyTile(i));
		}
		
		return ImmutableMap.copyOf(emptyTiles);
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate , piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}

	public abstract boolean isTileOccupied();	
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(int coordinate){
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied(){
			return false;
		}
		
		@Override
		public Piece getPiece(){
			return null;
		}
	}
	
	public static final class OccupiedTile extends Tile{
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int coordinate, Piece pieceOnTile){
			super(coordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() : 
				   getPiece().toString();
		}
		
		@Override
		public boolean isTileOccupied(){
			return true;
		}
		
		@Override
		public Piece getPiece(){
			return this.pieceOnTile;
		}
	}
}