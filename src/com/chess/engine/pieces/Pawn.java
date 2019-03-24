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
	
	public Pawn(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, true);
	}

	public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}
	
	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();
		
		for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * currentCandidateOffset;
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
				continue;
			
			if(currentCandidateOffset == BoardUtils.NUM_TILES_PER_ROW && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				
				if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
				}
				else {
					legalMoves.add( new PawnMove(board, this, candidateDestinationCoordinate));
				}
			}else if(currentCandidateOffset == BoardUtils.NUM_TILES_PER_ROW * 2 && this.isFirstMove() && 
					((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack()) || 
					(BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
				final int behindCandidateCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * BoardUtils.NUM_TILES_PER_ROW;
				if(!board.getTile(behindCandidateCoordinate).isTileOccupied() && 
				   !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
				}
			}else if(currentCandidateOffset == 7 &&
					 !((BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					 (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						}
						else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}						
					} else if(board.getEnPassant() != null){
						if(board.getEnPassant().getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
							final Piece pieceOnPassant = board.getEnPassant();
							if(this.pieceAlliance != pieceOnPassant.getPieceAlliance()) {
								legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnPassant));
							}
						}
					}
				}
			}else if(currentCandidateOffset == 9 &&
					 !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
					 (BoardUtils.EIGHT_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
						} else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				} else if(board.getEnPassant() != null){
					if(board.getEnPassant().getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
						final Piece pieceOnPassant = board.getEnPassant();
						if(this.pieceAlliance != pieceOnPassant.getPieceAlliance()) {
							legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnPassant));
						}
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}
	
	public Piece getPromotionPiece() {
		//TODO: Check for humans if they want to underpromote
		return new Queen(this.pieceAlliance, this.piecePosition, false);
	}
	
}
