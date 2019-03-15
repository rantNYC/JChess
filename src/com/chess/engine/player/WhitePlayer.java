package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;

import com.google.common.collect.ImmutableList;

public class WhitePlayer extends Player{

	public WhitePlayer(final Board board, 
					   final Collection<Move> whiteStandardLegalMoves,
					   final Collection<Move> blackStandardLegalMoves) {
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.getBlackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegal) {
		
		final List<Move> kingCastle = new ArrayList<>();

		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(!Player.calculateAttacksOnTile(61, opponentsLegal).isEmpty() &&
					   !Player.calculateAttacksOnTile(62, opponentsLegal).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook()) {
					   //rookTile.getPiece().getPieceType() == PieceType.ROOK) 
						kingCastle.add(new KingSideCastleMove(this.board, this.playerKing, 
								   62, (Rook)rookTile.getPiece(), 
								   rookTile.getTileCoordinate(), 61));
					
					}
				}
			}
			if(!this.board.getTile(59).isTileOccupied() && 
			   !this.board.getTile(58).isTileOccupied() && 
			   !this.board.getTile(57).isTileOccupied()) {
				
				final Tile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					kingCastle.add(new QueenSideCastleMove(this.board, this.playerKing, 
							   									58, (Rook)rookTile.getPiece(), 
							   									rookTile.getTileCoordinate(), 59));
				}
			}
		}
		
		return ImmutableList.copyOf(kingCastle);
	}

}
