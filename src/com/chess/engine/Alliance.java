package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.board.BoardUtils;

public enum Alliance {
	WHITE{
		@Override
		public int getDirection() {
			return -1;
		}
		
		@Override
		public int getOppositeDirection() {
			return 1;
		}
		
		@Override
		public boolean isWhite() {
			return true;
		}
		
		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return whitePlayer;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) {
			return BoardUtils.EIGHTH_RANK[position];
		}
	},
	BLACK{
		@Override
		public int getDirection() {
			return 1;
		}
		
		@Override
		public int getOppositeDirection() {
			return -1;
		}
		
		@Override
		public boolean isWhite() {
			return false;
		}
		
		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return blackPlayer;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) {
			return BoardUtils.FIRST_RANK[position];
		}
	};
	
	public abstract int getDirection();
	public abstract int getOppositeDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract boolean isPawnPromotionSquare(int position);
	
	public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
