package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.gui.Table.MoveLog;
import com.google.common.primitives.Ints;

public class TakenPiecesPanel extends JPanel{
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	
	private Color PANEL_COLOR = Color.decode("0xFDFE6");
	private final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

	public TakenPiecesPanel() {
		super(new BorderLayout());
		setBackground(PANEL_COLOR);
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8,2));
		this.southPanel = new JPanel(new GridLayout(8,2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		this.add(this.northPanel, BorderLayout.NORTH);
		this.add(this.southPanel, BorderLayout.SOUTH);
		this.setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		southPanel.removeAll();
		northPanel.removeAll();
		
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		for(final Move move: moveLog.getMoves()) {
			if(move.isAttack()) {
				final Piece pieceTaken = move.getAttackedPiece();
				if(pieceTaken.getPieceAlliance().isWhite()) {
					whiteTakenPieces.add(pieceTaken);
				} else if (pieceTaken.getPieceAlliance().isBlack()) {
					blackTakenPieces.add(pieceTaken);
				} else {
					throw new RuntimeException("There is only White and Black Alliances");
				}
			}
		}
		
		Collections.sort(whiteTakenPieces, new Comparator<Piece>() {

			@Override
			public int compare(Piece o1, Piece o2) {
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
			
		});
		
		Collections.sort(blackTakenPieces, new Comparator<Piece>() {

			@Override
			public int compare(Piece o1, Piece o2) {
				return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
			}
			
		});
		
		for(final Piece takenPiece: whiteTakenPieces) {
			try {
				final BufferedImage image = ImageIO.read(new File("art/pieces/plain" + 
						takenPiece.getPieceAlliance().toString().substring(0, 1) + "" + takenPiece.toString()));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(icon);
				this.southPanel.add(imageLabel);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		for(final Piece takenPiece: blackTakenPieces) {
			try {
				final BufferedImage image = ImageIO.read(new File("art/pieces/plain" + 
						takenPiece.getPieceAlliance().toString().substring(0, 1) + "" + takenPiece.toString()));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(icon);
				this.southPanel.add(imageLabel);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		validate();
	}
	
}
