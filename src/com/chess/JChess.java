package com.chess;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

import javafx.application.Application;
import javafx.stage.Stage;

public class JChess  extends Application{

	public static void main(String[] args){

		Board board = Board.createStandardBoard();
		System.out.println(board.toString());
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Table table = new Table();
		primaryStage.setTitle("JChess");
		primaryStage.setScene(table.getScene());
		primaryStage.show();
	}

}
