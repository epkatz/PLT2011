import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;


public class GUI {
	private League theLeague;
	private static JFrame frmFloodFantasyLeague;
	private MyTableModel homeTable,draftTable,tradeTable_1,tradeTable_2,dropTable,actionTable;
	private DefaultTableModel homeModel,draftModel,tradeModel_1,tradeModel_2,dropModel,actionModel;
	private JLabel draftLabel;
	private int currentTurn,pick;
	private DecimalFormat twoDForm;
	private final String[] homeHeader={"Rank","Team","Points"},
		playerInfoHeader={"Player","Position","Points All Season"},
		ruleHeader={"Action","Point Value"};
	
	/**Constructor
	 * 
	 * @param League game
	 */
	public GUI(League game){
		this.theLeague=game;
		twoDForm = new DecimalFormat("0.00");
		currentTurn=0;
	}
	
	/**Populates the home table assuming it has already been initialized.
	 * 
	 */
	private void populateHome(){
		User[] rankedTeams= theLeague.getRankedUsers();	//Get all the teams in reverse ranked order
		while(homeModel.getRowCount()>0)	//Remove all the rows from the home table
			homeModel.removeRow(0);
		String[] tempHome=new String[3];	//Initialize a temporary row
		for(int i=rankedTeams.length-1,j=1;i>=0;i--,j++){	//Iterate through the teams
			tempHome[0]=Integer.toString(j);	//Set the rank
			tempHome[1]=rankedTeams[i].getName();	//Set the name
			tempHome[2]=twoDForm.format(rankedTeams[i].getPoints());	//Set the points
			homeModel.addRow(tempHome);	//Add the row
		}
	}
	
	/**Populates the add table assuming it has already been initialized.
	 * 
	 */
	private void populateDraft(){
		pick=FloodProgram.draftFunction(currentTurn);	//Determine who is picking next
		draftLabel.setText(theLeague.getUser(pick).getName()+"'s turn!");	//Figure out which user is picking next
		Player[] rankedPlayers=theLeague.getRankedAvailablePlayers();	//Get all the players in reverse ranked order
		while(draftModel.getRowCount()>0)	//Remove all the rows from the add table
			draftModel.removeRow(0);
		String[] tempDraft=new String[3];	//Initialize a temporary row
		for(int i=rankedPlayers.length-1;i>=0;i--){	//Iterate through the players
			tempDraft[0]=rankedPlayers[i].getName();	//Set the name
			tempDraft[1]=rankedPlayers[i].getPosition();	//Set the position
			tempDraft[2]=twoDForm.format(rankedPlayers[i].getPoints());	//Set the points scored all season
			draftModel.addRow(tempDraft);	//Add the row
		}
	}
	
	/**Populates the add table assuming it has already been initialized.
	 * 
	 */
	private void populateActions(){
		Action[] actions=theLeague.getActions();	//Get all the players in reverse ranked order
		while(actionModel.getRowCount()>0)	//Remove all the rows from the add table
			actionModel.removeRow(0);
		String[] tempAction=new String[2];	//Initialize a temporary row
		for(int i=actions.length-1;i>=0;i--){	//Iterate through the players
			tempAction[0]=actions[i].getAction();	//Set the name
			tempAction[1]=twoDForm.format(actions[i].getPoints());	//Set the points scored all season
			actionModel.addRow(tempAction);	//Add the row
		}
	}
	
	/**Display an error window with the title and message given
	 * as parameters.
	 * 
	 * @param String title
	 * @param String message
	 */
	public static void error(String title,String message){
		JOptionPane.showMessageDialog(frmFloodFantasyLeague, message,title,JOptionPane.ERROR_MESSAGE);
	}
	
	/**Show a warning window with the title and message given
	 * as parameters
	 * 
	 * @param String title
	 * @param String message
	 */
	public static void alert(String title, String message) {
		JOptionPane.showMessageDialog(frmFloodFantasyLeague, message,title,JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public void drawBoard() {
		//Set up the frame
		frmFloodFantasyLeague = new JFrame();
		frmFloodFantasyLeague.setBackground(new Color(0, 0, 205));
		frmFloodFantasyLeague.setTitle("FLOOD Fantasy League: "+theLeague.getName());
		frmFloodFantasyLeague.setBounds(100, 100, 450, 300);
		frmFloodFantasyLeague.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFloodFantasyLeague.setSize(new Dimension(700, 400));
		frmFloodFantasyLeague.setVisible(true);
		
		//Initialize and add the tabbed pane
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmFloodFantasyLeague.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		//Initialize the home tab
		JSplitPane homeSplitPane = new JSplitPane();
		homeSplitPane.setResizeWeight(0.99);
		homeSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		//Initialize the home tab toolbar
		JToolBar homeToolBar = new JToolBar();
		homeToolBar.setFloatable(false);
		homeSplitPane.setRightComponent(homeToolBar);
		
		//Add the upload stats button to the toolbar
		JButton uploadStatsButton = new JButton("Upload Stat File");
		uploadStatsButton.setMaximumSize(new Dimension(32767, 32767));
		homeToolBar.add(uploadStatsButton);
		
		//Add the create dump button to the toolbar
		JButton createDumpButton = new JButton("Create Dump File");
		createDumpButton.setMaximumSize(new Dimension(32767, 32767));
		homeToolBar.add(createDumpButton);
		
		//Add the import dump button to the toolbar
		JButton importDumpButton = new JButton("Import Dump File");
		importDumpButton.setMaximumSize(new Dimension(32767, 32767));
		homeToolBar.add(importDumpButton);
		
		//Initialize the home tab scrollpane
		JScrollPane homePane = new JScrollPane();
		homeSplitPane.setLeftComponent(homePane);
		
		//Add the home tab to the tabbed pane
		tabbedPane.addTab("Home", null, homeSplitPane, null);
		
		//Initialize, format and add the home table
		homeModel = new DefaultTableModel(homeHeader,0);	//Add the header but no rows
		homeTable = new MyTableModel(homeModel);
		homeTable.setEnabled(false);	//Make the rows unselectable
		homeTable.setAutoCreateRowSorter(true);	//Allow sorting
		homePane.setViewportView(homeTable);	//Put the table into the scroll pane
		
		populateHome();	//Populate the home table
		
		JSplitPane draftSplitPane = new JSplitPane();	//Add the draft split pane
		draftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	//Split vertically
		tabbedPane.addTab("Draft", null, draftSplitPane, null);	//Add the split pane to the tabbed pane
		
		//Initialize and add the draft scroll pane
		JScrollPane draftScrollPane = new JScrollPane();
		draftSplitPane.setRightComponent(draftScrollPane);
		
		//Initialize, format and add the draft table
		draftModel = new DefaultTableModel(playerInfoHeader,0);	//Add the header but no rows
		draftTable = new MyTableModel(draftModel);
		draftTable.setRowSelectionAllowed(true);	//Allow row selection
		draftTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//Allow only one row selection at a time
		draftTable.setAutoCreateRowSorter(true);	//Allow sorting
		draftScrollPane.setViewportView(draftTable);	//Put the table into the scroll pane
		
		//Initialize, format and add the draft tool bar
		JToolBar draftToolBar = new JToolBar();
		draftToolBar.setFloatable(false);
		draftSplitPane.setLeftComponent(draftToolBar);
		
		//Initialize, format and add the draft label
		draftLabel = new JLabel();
		draftLabel.setMinimumSize(new Dimension(600, 15));
		draftLabel.setMaximumSize(new Dimension(32767, 15));
		draftToolBar.add(draftLabel);
		
		//Initialize,format and add the draft button
		JButton btnDraft = new JButton("Draft");
		btnDraft.setPreferredSize(new Dimension(100, 25));
		btnDraft.setMaximumSize(new Dimension(100, 25));
		btnDraft.setMinimumSize(new Dimension(100, 25));
		draftToolBar.add(btnDraft);
		
		//Initialize, format and add the trade split pane
		JSplitPane tradeSplitPane = new JSplitPane();
		tradeSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	//Split vertically
		tabbedPane.addTab("Trade", null, tradeSplitPane, null);
		
		//Initialize, format and add the trade tool bar
		JToolBar tradeToolBar = new JToolBar();
		tradeToolBar.setFloatable(false);
		tradeSplitPane.setLeftComponent(tradeToolBar);
		
		//Initialize, format and add the first trade combo box
		final JComboBox tradeComboBox_1 = new JComboBox();
		tradeToolBar.add(tradeComboBox_1);
		
		//Initialize, format and add the second trade combo bos
		final JComboBox tradeComboBox_2 = new JComboBox();
		tradeToolBar.add(tradeComboBox_2);
		
		//Initialize and add the trade button
		JButton btnTrade = new JButton("Trade");
		tradeToolBar.add(btnTrade);
		
		//Initialize, format and add the second trade split pane
		JSplitPane tradeSplitPane_2 = new JSplitPane();
		tradeSplitPane_2.setResizeWeight(0.5);
		tradeSplitPane.setRightComponent(tradeSplitPane_2);
		
		//Initialize and add the left trade scroll pane
		JScrollPane tradeScrollPane_1 = new JScrollPane();
		tradeSplitPane_2.setLeftComponent(tradeScrollPane_1);
		
		//Initialize, format and add the left trade table
		tradeModel_1 = new DefaultTableModel(playerInfoHeader,0);	//Add the header but no rows
		tradeTable_1 = new MyTableModel(tradeModel_1);
		tradeTable_1.setRowSelectionAllowed(true);	//Set selection of entire rows
		tradeTable_1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//Allow multiple row selection
		tradeTable_1.setAutoCreateRowSorter(true);	//Allow sorting
		tradeScrollPane_1.setViewportView(tradeTable_1);	//Add table to scroll pane
		
		//Initialize and add the right trade scroll pane
		JScrollPane tradeScrollPane_2 = new JScrollPane();
		tradeSplitPane_2.setRightComponent(tradeScrollPane_2);
		
		//Initialize, format and add the right trade table
		tradeModel_2 = new DefaultTableModel(playerInfoHeader,0);	//Add the header but no rows
		tradeTable_2 = new MyTableModel(tradeModel_2);
		tradeTable_2.setRowSelectionAllowed(true);	//Set selection of entire rows
		tradeTable_2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);	//Allow multiple row selection
		tradeTable_2.setAutoCreateRowSorter(true);	//Allow sorting
		tradeScrollPane_2.setViewportView(tradeTable_2);	//Add table to scroll pane
		
		//Initialize, format and add the drop split pane
		JSplitPane dropSplitPane = new JSplitPane();
		dropSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);	//Split vertically
		tabbedPane.addTab("Drop", null, dropSplitPane, null);
		
		//Initialize, format and add the drop toolbar
		JToolBar dropToolBar = new JToolBar();
		dropToolBar.setFloatable(false);
		dropSplitPane.setLeftComponent(dropToolBar);
		
		//Initialize and add the drop combo box
		final JComboBox dropComboBox = new JComboBox();
		dropToolBar.add(dropComboBox);
		
		//Initialize and add the drop button
		JButton btnDrop = new JButton("Drop");
		dropToolBar.add(btnDrop);
		
		//Initialize and add the drop scroll pane
		JScrollPane dropScrollPane = new JScrollPane();
		dropSplitPane.setRightComponent(dropScrollPane);
		
		//Initialize, format and add the drop table
		dropModel = new DefaultTableModel(playerInfoHeader,0);	//Add the header but no rows
		dropTable = new MyTableModel(dropModel);
		dropTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	//Alow single selection
		dropTable.setAutoCreateRowSorter(true);	//Allow sorting
		dropScrollPane.setViewportView(dropTable);	//Add the table to the scroll pane
		
		//Initialize the home tab scrollpane
		JScrollPane actionPane = new JScrollPane();
		
		//Add the home tab to the tabbed pane
		tabbedPane.addTab("Actions", null, actionPane, null);
		
		//Initialize, format and add the home table
		actionModel = new DefaultTableModel(ruleHeader,0);	//Add the header but no rows
		actionTable = new MyTableModel(actionModel);
		actionTable.setEnabled(false);	//Make the rows unselectable
		actionTable.setAutoCreateRowSorter(true);	//Allow sorting
		actionPane.setViewportView(actionTable);	//Put the table into the scroll pane
		
		populateActions();	//Populate the home table
		
		
		//Initialize the file chooser
		final JFileChooser chooser=new JFileChooser();
		
		//Populate both trade and the drop combo boxes
		User[] rankedTeams= theLeague.getRankedUsers();
		for(int i=0;i<rankedTeams.length;i++){
			tradeComboBox_1.insertItemAt(rankedTeams[i].getName(),i);
			tradeComboBox_2.insertItemAt(rankedTeams[i].getName(),i);
			dropComboBox.insertItemAt(rankedTeams[i].getName(),i);
		}
		
		//************************************************************************
		//****************************Action Listeners****************************
		//************************************************************************
		
		//Action listener for changing tabs
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int selection = tabbedPane.getSelectedIndex();	//Get selected tab
				switch(selection){
				case 0:	//Populate home table
					populateHome();
					break;
				case 1:	//Populate draft table
					populateDraft();
					break;
				case 4:
					populateActions();
					break;
				}
			}
		});
		
		//Stats upload action listener
		uploadStatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = chooser.showOpenDialog(null);	//Determine what the user pressed
				switch (result) {
				case JFileChooser.APPROVE_OPTION:	//Opened file
					File file=chooser.getSelectedFile();	//Get the chosen file
					IOManager.uploadStats(theLeague,file.getAbsolutePath());	//Pass the file path to the parser method
					populateHome();
					break;
				case JFileChooser.CANCEL_OPTION:	//Canceled
					break;
				case JFileChooser.ERROR_OPTION:	//Generated an error
					GUI.error("Upload Error!","Sorry, there was an error opening the stat file.");
					break;
				}
			}
		});
		
		//Dump generator action listener
		createDumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.setSelectedFile(new File("flooddmp.txt"));
				int result = chooser.showSaveDialog(null);
				switch (result) {
				case JFileChooser.APPROVE_OPTION:	//Opened file
					File file=chooser.getSelectedFile();	//Get the chosen file
					if(file.exists()) {
		                int overwrite = JOptionPane.showConfirmDialog(frmFloodFantasyLeague, "Do you want to overwrite " + file.getName());
		                if(overwrite == JOptionPane.YES_OPTION) {
		                	IOManager.writeState(theLeague,file.getAbsolutePath(),currentTurn);	//Pass the file path to the parser method
		                	tabbedPane.setSelectedIndex(0);
		                }
					}
					else{
						IOManager.writeState(theLeague,file.getAbsolutePath(),currentTurn);	//Pass the file path to the parser method
						tabbedPane.setSelectedIndex(0);
					}
					break;
				case JFileChooser.CANCEL_OPTION:	//Canceled
					break;
				case JFileChooser.ERROR_OPTION:	//Generated an error
					GUI.error("Upload Error!","Sorry, error creating the dump file.");
					break;
				}
			}
		});
		
		//Dump importer action listener
		importDumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = chooser.showOpenDialog(null);	//Determine what the user pressed
				switch (result) {
				case JFileChooser.APPROVE_OPTION:	//Opened file
					File file=chooser.getSelectedFile();	//Get the chosen file
					int temp=IOManager.importState(theLeague,file.getAbsolutePath());	//Pass the file path to the parser method
					if(temp!=-1){
						currentTurn=temp;
						populateDraft();
					}
					populateHome();
					break;
				case JFileChooser.CANCEL_OPTION:	//Canceled
					break;
				case JFileChooser.ERROR_OPTION:	//Generated an error
					GUI.error("Upload Error!","Sorry, there was an error opening the stat file.");
					break;
				}
			}
		});
		
		//Determine which user is picking first
		pick=FloodProgram.draftFunction(currentTurn);	//Gets the number representing the user's turn
		draftLabel.setText(theLeague.getUser(pick).getName()+"'s turn!");	//Puts the user's name in the label 
		
		//Draft action listener
		btnDraft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<draftModel.getRowCount();i++){	//Iterate through table entries
					if(draftTable.isCellSelected(i,0)){	//If it's selected
						int overwrite = JOptionPane.showConfirmDialog(frmFloodFantasyLeague, "Are you sure you want to draft: " + League.athletes.get(draftModel.getValueAt(i,0)).getName());
		                if(overwrite == JOptionPane.YES_OPTION) {
							if(!FloodProgram.draftPlayer(theLeague.getUser(pick),League.athletes.get(draftModel.getValueAt(i,0)))){	//If the draft isn't successful
								GUI.error("Invalid draft!","Sorry, your draft violates rules of the league.");
								return;
							}
							currentTurn++;	//Increment the turn
							draftModel.removeRow(i);	//Remove that row from the draft table
							populateDraft();
							//Make all the combo boxes not select anything
							tradeComboBox_1.setSelectedIndex(-1);
							tradeComboBox_2.setSelectedIndex(-1);
							dropComboBox.setSelectedIndex(-1);
							//Remove the current tables in all the other tabs so that they are up to date
							while(tradeModel_1.getRowCount()>0){
					    		tradeModel_1.removeRow(0);
					    	}
							while(tradeModel_2.getRowCount()>0){
					    		tradeModel_2.removeRow(0);
					    	}
							while(dropModel.getRowCount()>0){
					    		dropModel.removeRow(0);
					    	}
							return;
		                }
		                return;
					}
				}
				//If no selection is found
				GUI.error("Add error!","Sorry, no player was selected!");
			}
		});
		
		//Action listener for left trade combo box
		tradeComboBox_1.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(tradeComboBox_1.getSelectedIndex()!=-1){	//If a user is selected
			    	Player[] teamPlayers=League.teams.get(tradeComboBox_1.getSelectedItem()).getPlayers();	//Get the players the user has
			    	while(tradeModel_1.getRowCount()>0){	//Clear the current table
			    		tradeModel_1.removeRow(0);
			    	}
			    	for(int i=0;i<teamPlayers.length;i++){	//Populate the table with the new data
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Float.toString(teamPlayers[i].getPoints())};	//Initialize the row
			    		tradeModel_1.addRow(temp);
			    	}
		    	}
		    }
		});
		
		//Action listener for the right trade combo box
		tradeComboBox_2.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(tradeComboBox_2.getSelectedIndex()!=-1){	//If a user is selected
			    	Player[] teamPlayers=League.teams.get(tradeComboBox_2.getSelectedItem()).getPlayers();	//Get the players the user has
			    	while(tradeModel_2.getRowCount()>0){	//Clear the current table
			    		tradeModel_2.removeRow(0);
			    	}
			    	for(int i=0;i<teamPlayers.length;i++){	//Populate the table with the new data
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Float.toString(teamPlayers[i].getPoints())};	//Initialize the row
			    		tradeModel_2.addRow(temp);
			    	}
		    	}
		    }
		});
		
		//Action listener for the trade button
		btnTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tradeComboBox_1.getSelectedIndex()==-1 || tradeComboBox_2.getSelectedIndex()==-1) {	//If either combo box doesn't have a user selected
					GUI.error("Trade error!","Must select two teams to trade between.");
					return;
				}
				else if(tradeComboBox_1.getSelectedIndex()==tradeComboBox_2.getSelectedIndex()){	//If the same user is selected in each combo box
					GUI.error("Trade error!","Must select two different teams to trade between.");
					return;
				}
				int rows1[] = tradeTable_1.getSelectedRows();	//Get selected rows in the left table
				int rows2[] = tradeTable_2.getSelectedRows();	//Get selected rows in the right table
				if(rows1.length==0 && rows2.length==0){	//If no players are selected
					GUI.error("Trade error!","Must select at least one player to trade.");
					return;
				}
				int overwrite = JOptionPane.showConfirmDialog(frmFloodFantasyLeague, "Are you sure you want to trade?");
                if(overwrite == JOptionPane.YES_OPTION) {
					Player[] p1 = new Player[rows1.length];	//Initialize player array for the left table selection
					Player[] p2 = new Player[rows2.length];	//Initialize player array for the right table selection
					//Populate the player arrays
					for (int i = 0; i < p1.length; i++) {
						p1[i] = League.athletes.get(tradeModel_1.getValueAt(rows1[i],0));
					}
					for (int i = 0; i < p2.length; i++) {
						p2[i] = League.athletes.get(tradeModel_2.getValueAt(rows2[i],0));
					}
					boolean success=FloodProgram.trade(League.teams.get(tradeComboBox_1	//Determine if it's a successful trade
							.getSelectedItem()), p1, League.teams
							.get(tradeComboBox_2.getSelectedItem()), p2);
					if(!success){	//If unsuccessful
						GUI.error("Invalid trade!","Sorry, your trade violates rules of the league.");
						return;
					}
					//Repopulate the tables
					Player[] teamPlayers = League.teams.get(tradeComboBox_1.getSelectedItem()).getPlayers();
					while (tradeModel_1.getRowCount() > 0) {	//Clear the left trade table
						tradeModel_1.removeRow(0);
					}
					for (int i = 0; i < teamPlayers.length; i++) {	//Repopulate the left trade table
						String[] temp = { teamPlayers[i].getName(),
								teamPlayers[i].getPosition(),
								Float.toString(teamPlayers[i].getPoints()) };
						tradeModel_1.addRow(temp);	//Add the row
					}
					teamPlayers = League.teams.get(tradeComboBox_2.getSelectedItem()).getPlayers();
					while (tradeModel_2.getRowCount() > 0) {	//Clear the left trade table
						tradeModel_2.removeRow(0);
					}
					for (int i = 0; i < teamPlayers.length; i++) {	//Repopulate the left trade table
						String[] temp = { teamPlayers[i].getName(),
								teamPlayers[i].getPosition(),
								Float.toString(teamPlayers[i].getPoints()) };
						tradeModel_2.addRow(temp);	//Add the row
					}
                }
			}
		});
		
		//Action listener for drop combo box
		dropComboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	if(dropComboBox.getSelectedIndex()!=-1){	//If a user is selected
			    	Player[] teamPlayers=League.teams.get(dropComboBox.getSelectedItem()).getPlayers();	//Get the user's players
			    	while(dropModel.getRowCount()>0){	//Clear the table
			    		dropModel.removeRow(0);
			    	}
			    	for(int i=0;i<teamPlayers.length;i++){	//Repopulate the table
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Float.toString(teamPlayers[i].getPoints())};
			    		dropModel.addRow(temp);	//Add the row
			    	}
		    	}
		    }
		});
		
		//Action listener for drop button
		btnDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index=dropTable.getSelectedRow();
				if(index==-1){	//If no player is selected to drop
					GUI.error("Drop error!","Must select a team to drop a player from.");
					return;
				}
				int overwrite = JOptionPane.showConfirmDialog(frmFloodFantasyLeague, "Are you sure you want to drop: " + League.athletes.get(dropModel.getValueAt(index,0)).getName());
                if(overwrite == JOptionPane.YES_OPTION) {
					Player drop=League.athletes.get(dropModel.getValueAt(index,0));	//Get player
					boolean success=FloodProgram.dropPlayer(League.teams.get(dropComboBox.getSelectedItem()),drop);	//Determine if drop is successful
					if(!success){
						GUI.error("Invalid drop!","Sorry, your drop violates rules of the league.");
						return;
					}
					dropModel.removeRow(index);	//Delete that row
					//Make all the combo boxes not select anything
					tradeComboBox_1.setSelectedIndex(-1);
					tradeComboBox_2.setSelectedIndex(-1);
					while(tradeModel_1.getRowCount()>0){
						tradeModel_1.removeRow(0);
					}
					while(tradeModel_2.getRowCount()>0){
						tradeModel_2.removeRow(0);
					}
                }
			}
		});
		
		tabbedPane.setSelectedIndex(0);
	}
}
class MyTableModel extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyTableModel(){
		super();
	}
	public MyTableModel(DefaultTableModel model){
		super(model);
	}
	public MyTableModel(Object[][] data,Object[] columnNames){
		super(data,columnNames);
	}
    public boolean isCellEditable(int row, int col) {
    	return false;
    }
}
