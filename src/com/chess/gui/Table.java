package com.chess.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Table{

	private final int SCREEN_WIDTH   = 600;
	private final int SCREEN_HEIGHT  = 600;
	private Scene scene;
	private Group root;

	
	public Table() {
		//TODO: Use JAVAFXML to create this menu
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(createFileMenu());
	
		root = new Group();
		scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		root.getChildren().add(menuBar);

	}

	private Menu createFileMenu() {
		final Menu fileMenu = new Menu("File");
		
		final MenuItem openPGN = new MenuItem("Load PGN File");
		openPGN.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Open the PGN file");
            }
		});
		fileMenu.getItems().add(openPGN);
		return fileMenu;
	}

	public Scene getScene() {
		return this.scene;
	}

	public Group getRoot() {
		return this.root;
	}
}
