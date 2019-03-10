package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.*;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};
	
	public Pawn(Alliance pieceAlliance, int piecePosition) {
		super(piecePosition, pieceAlliance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();
		
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * currentCandidateOffset;
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
				continue;
			
			if(currentCandidateOffset == BoardUtils.NUM_TILES_PER_ROW && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				//TODO: More work to do when Upgrade
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			}else if(currentCandidateOffset == BoardUtils.NUM_TILES_PER_ROW * 2 && this.isFirstMove() && 
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite())) {
				final int behindCandidateCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * BoardUtils.NUM_TILES_PER_ROW;
				if(!board.getTile(behindCandidateCoordinate).isTileOccupied() && 
				   !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			}else if(currentCandidateOffset == 7 &&
					 !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					 (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece piece = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != piece.getPieceAlliance()) {
						//TODO: More work here, attacking to pawn promotion
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}else if(currentCandidateOffset == 9 &&
					 !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					 (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece piece = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != piece.getPieceAlliance()) {
						//TODO: More work here, attacking to pawn promotion
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
	
}
