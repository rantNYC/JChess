package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
	
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	private Move(final Board board, final Piece movedPiece, final int coordinate){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = coordinate;
	}
	
	public static final class MajorMove extends Move{
		
		public MajorMove(final Board board,final Piece movedPiece, int destinationCoordinate){
			super(board, movedPiece, destinationCoordinate);
		}

		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}		
	}
	
	public static final class AttackMove extends Move{
		
		final Piece attackedPiece;
		
		public AttackMove(final Board board,final Piece movedPiece, int destinationCoordinate, final Piece attackedPiece){
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}

		@Override
		public Board execute() {
			// TODO Auto-generated method stub
			return null;
		}		
	}

	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}

	public abstract Board execute();
}
