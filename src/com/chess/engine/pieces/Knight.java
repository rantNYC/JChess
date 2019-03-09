package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece{

	public static final int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};
	
	Knight(int piecePosition, Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		int candidateDestinationCoordinate;
		final List<Move> legalMoves = new ArrayList<Move>();
		
		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
							isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
								isEightColumnExclusion(this.piecePosition, currentCandidateOffset))
					continue;
				
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if(!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new Move());
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					
					if(this.getPieceAlliance() != pieceAlliance) {
						legalMoves.add(new Move());
					}
				}
			}
		}
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
				candidateOffset == 6 || candidateOffset == -15);
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);
	}
	
	private static boolean isEightColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHT_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
				candidateOffset == 10 || candidateOffset == 17);
	}
}
