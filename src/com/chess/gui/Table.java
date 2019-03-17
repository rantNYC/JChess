package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;


public class Table {

	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private Board chessBoard;
	
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600); 
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	private static String defaultImagesPath = "art/pieces/simple/";
	
	private final Color lightTileColor = Color.decode("#FFFACD");
	private final Color darkTileColor = Color.decode("#593E1A");
	
	public Table() {
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createMenuBar();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardBoard();
		this.boardPanel = new BoardPanel();
		this.gameFrame.add(boardPanel, BorderLayout.CENTER);
		
		this.gameFrame.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		final JMenuBar menu = new JMenuBar();
		menu.add(createFileMenu());
		return menu;
	}

	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open PGN File");
			}
		});
		
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		fileMenu.add(exitMenuItem);
		fileMenu.add(openPGN);
		return fileMenu;
	}
	
	private class BoardPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		final List<TilePanel> boardTiles;
		
		BoardPanel(){
			super(new GridLayout(8,8));
			this.boardTiles = new ArrayList<>();
			for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
				final TilePanel tilePanel = new TilePanel(this, i);
				this.boardTiles.add(tilePanel);
				add(tilePanel);
			}
			setPreferredSize(BOARD_PANEL_DIMENSION);
			validate();
		}

		public void drawBoard(final Board board) {
			removeAll();
			for(final TilePanel tilePanel: boardTiles) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}
	
	private class TilePanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int tileID;
		
		TilePanel(final BoardPanel boardPanel,
				  final int tileID){
			super(new GridBagLayout());
			this.tileID = tileID;
			setPreferredSize(TILE_PANEL_DIMENSION);
			assignTileColor();
			assignPieceIcon(chessBoard);
			
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(final MouseEvent e) {
					if(SwingUtilities.isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					}else if(SwingUtilities.isLeftMouseButton(e)) {
						if(sourceTile == null) {
							//first click
							sourceTile = chessBoard.getTile(tileID);
							humanMovedPiece = sourceTile.getPiece();
							if(humanMovedPiece == null) {
								sourceTile = null;
							}
						}else {
							//second click
							destinationTile =  chessBoard.getTile(tileID);
							final Move move = MoveFactory.createMove(chessBoard, 
																		  sourceTile.getTileCoordinate(), 
																		  destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							if(transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								//TODO: Add the move that was made to the log
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								boardPanel.drawBoard(chessBoard);
							}
						});
					}
				}
				
				@Override
				public void mousePressed(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(final MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			validate();
		}
		
		public void drawTile(final Board board) {
			assignTileColor();
			assignPieceIcon(board);
			validate();
			repaint(); 
		}

		private void assignPieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileID).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(defaultImagesPath + 
					board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0, 1) + 
					board.getTile(this.tileID).getPiece().toString() + ".gif"));
					
					add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

		private void assignTileColor() {
			if(BoardUtils.EIGHTH_RANK[this.tileID] ||
					BoardUtils.SIXTH_RANK[this.tileID] ||
					BoardUtils.FOURTH_RANK[this.tileID] ||
					BoardUtils.SECOND_RANK[this.tileID]) {
				setBackground(this.tileID % 2 == 0 ? lightTileColor : darkTileColor);
			} else if(BoardUtils.SEVENTH_RANK[this.tileID] ||
						BoardUtils.FIFTH_RANK[this.tileID] ||
						BoardUtils.THIRD_RANK[this.tileID] ||
						BoardUtils.FIRST_RANK[this.tileID] )	{
				setBackground(this.tileID % 2 != 0 ? lightTileColor : darkTileColor);
			}
		}
	}
}
