package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
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
	
	public abstract Board execute();
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public static final class MajorMove extends Move{
		
		public MajorMove(final Board board,final Piece movedPiece, int destinationCoordinate){
			super(board, movedPiece, destinationCoordinate);
		}

		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for(final Piece piece: this.board.currentPlayer().getActivePiece()) {
				//TODO: hashCode and equals for class Pieces
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			
			for(final Piece piece: this.board.currentPlayer().getActivePiece()) {
				builder.setPiece(piece);
			}
			
			//move the moved Piece
			builder.setPiece(this.movedPiece.movePiece(this));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			
			return builder.build();
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
}
