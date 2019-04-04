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

public class BlackPlayer extends Player	{

	public BlackPlayer(final Board board, 
					   final Collection<Move> whiteStandardLegalMoves,
					   final Collection<Move> blackStandardLegalMoves) {
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.getWhitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentsLegal) {
		final List<Move> kingCastle = new ArrayList<>();

		if(this.playerKing.isFirstMove() && !this.isInCheck()) {
			if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(Player.calculateAttacksOnTile(5, opponentsLegal).isEmpty() &&
					   Player.calculateAttacksOnTile(6, opponentsLegal).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook()) {
					   //rookTile.getPiece().getPieceType() == PieceType.ROOK) 
						kingCastle.add(new KingSideCastleMove(this.board, this.playerKing, 
																   6, (Rook)rookTile.getPiece(), 
																   rookTile.getTileCoordinate(), 5));
					}
				}
			}
			if(!this.board.getTile(1).isTileOccupied() && 
			   !this.board.getTile(2).isTileOccupied() && 
			   !this.board.getTile(3).isTileOccupied()) {
				
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
					if(!Player.calculateAttacksOnTile(2, opponentsLegal).isEmpty() &&
					   !Player.calculateAttacksOnTile(3, opponentsLegal).isEmpty() &&
					   rookTile.getPiece().getPieceType().isRook()) {
						kingCastle.add(new QueenSideCastleMove(this.board, this.playerKing, 
							   									2, (Rook)rookTile.getPiece(), 
							   									rookTile.getTileCoordinate(), 3));
					}
				}
			}
		}
		
		return ImmutableList.copyOf(kingCastle);
	}

}
