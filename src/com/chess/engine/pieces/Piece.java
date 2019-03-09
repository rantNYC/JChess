package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {

	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	
	Piece(final int piecePosition, final Alliance pieceAlliance){
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
	}
	
	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
}
