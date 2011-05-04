package backend;
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
import javax.swing.JPanel;
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
	private MyTableModel homeTable,draftTable,tradeTable_1,tradeTable_2,dropTable;
	private DefaultTableModel homeModel,draftModel,tradeModel_1,tradeModel_2,dropModel;
	private int currentTurn,pick,previousIndex;
	private DecimalFormat twoDForm;
	private final String[] homeHeader={"Rank","Team","Points"},
		playerInfoHeader={"Player","Position","Points All Season"};
	
	/**Constructor
	 * 
	 * @param League game
	 */
	public GUI(League game){
		this.theLeague=game;
		twoDForm = new DecimalFormat("0.00");
		currentTurn=0;
		previousIndex=0;
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
	
	/**Upload the statistics from a file.
	 * 
	 * @param String fileName
	 */
	private void uploadStats(String fileName){
		String[][] stats=IOManager.getStats(fileName);	//Parse the file
		if(stats==null){	//If the stat array is empty, there was an error opening/reading/parsing the file
			displayError("Upload error!","Sorry, there was an error opening the stat file.");
			return;
		}
		boolean valid=true;
		for(int i=0;i<stats.length;i++){	//Iterate through the stat array
			valid=valid && League.athletes.containsKey(stats[i][0]);	//Check if the athlete exists
			if(!valid){	//If the athlete doesn't exist
				displayError("Athelete doesn't exist!",stats[i][0]+" is not a valid athlete.");
				return;
			}
			valid=valid && League.ptsDist.containsKey(stats[i][1]);	//Check if the action exists
			if(!valid){	//If the athlete doesn't exist
				displayError("Action doesn't exist!",stats[i][1]+" Is not a valid action.");
				return;
			}
			valid=valid && Integer.parseInt(stats[i][2])>0;	//Check if the quantity is greater than zero
			if(!valid){	//If the quantity is less than or equal to zero
				displayError("Quantity must be positive!",stats[i][2]+" is not a positive number greater than zero.");
				return;
			}
		}
		for(int i=0;i<stats.length;i++){	//Iterate through the stats
			double pts=League.ptsDist.get(stats[i][1]).getPoints() * Integer.parseInt(stats[i][2]);	//Compute the points
			Player temp=League.athletes.get(stats[i][0]);	//Get the player
			temp.addPoints(pts);	//Add the points to the player and thereby the team they're one
		}
	}
	
	/**Display an error window with the title and message given
	 * as parameters.
	 * 
	 * @param String title
	 * @param String message
	 */
	private void displayError(String title,String message){
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
		frmFloodFantasyLeague.setTitle("FLOOD Fantasy League");
		frmFloodFantasyLeague.setBounds(100, 100, 450, 300);
		frmFloodFantasyLeague.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFloodFantasyLeague.setSize(new Dimension(700, 400));
		frmFloodFantasyLeague.setVisible(true);
		
		//Initialize and add the tabbed pane
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmFloodFantasyLeague.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		//Initialize and add the home pane
		JScrollPane homePane = new JScrollPane();
		tabbedPane.addTab("Home", null, homePane, null);
		
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
		final JLabel draftLabel = new JLabel();
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
		
		//Initialize and add the upload panel
		JPanel uploadPanel = new JPanel();
		tabbedPane.addTab("Upload Stat File", null, uploadPanel, null);
		
		//Initialize and add the dump panel
		JPanel dumpPanel = new JPanel();
		tabbedPane.addTab("Upload Stat File", null, uploadPanel, null);
		
		//Populate both trade and the drop combo boxes
		User[] rankedTeams= theLeague.getRankedUsers();
		for(int i=0;i<rankedTeams.length;i++){
			tradeComboBox_1.insertItemAt(rankedTeams[i].getName(),i);
			tradeComboBox_2.insertItemAt(rankedTeams[i].getName(),i);
			dropComboBox.insertItemAt(rankedTeams[i].getName(),i);
		}
		
		//*************************************************************************
		//**********************Action Listener Portion****************************
		//*************************************************************************
		
		//Action listener for changing tabs
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int selection = tabbedPane.getSelectedIndex();	//Get selected tab
				switch(selection){
				case 0:	//Populate home table
					populateHome();
					previousIndex=selection;
					break;
				case 1:	//Populate draft table
					populateDraft();
					previousIndex=selection;
					break;
				case 4:	//Prompt user to browse for file
					JFileChooser chooser=new JFileChooser();
					int result = chooser.showOpenDialog(null);	//Determine what the user pressed
					switch (result) {
					case JFileChooser.APPROVE_OPTION:	//Opened file
						File file=chooser.getSelectedFile();	//Get the chosen file
						uploadStats(file.getAbsolutePath());	//Pass the file path to the parser method
						break;
					case JFileChooser.CANCEL_OPTION:	//Cancelled
						break;
					case JFileChooser.ERROR_OPTION:	//Generated an error
						displayError("Upload Error!","Sorry, there was an error opening the stat file.");
						break;
					}
					tabbedPane.setSelectedIndex(previousIndex);	//Select the previously selected tab
					break;
				default:
					previousIndex=selection;
				}
			}
		});
		
		//Determine which user is picking first
		pick=Test.draftFunction(currentTurn);	//Gets the number representing the user's turn
		draftLabel.setText(theLeague.getUser(pick).getName()+"'s turn!");	//Puts the user's name in the label 
		
		//Draft action listener
		btnDraft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<draftTable.getRowCount();i++){	//Iterate through table entries
					if(draftTable.isCellSelected(i,0)){	//If it's selected
						if(!Test.draftPlayer(theLeague.getUser(pick),League.athletes.get(draftModel.getValueAt(i,0)))){	//If the draft isn't successful
							displayError("Invalid draft!","Sorry, your draft violates rules of the league.");
							return;
						}
						currentTurn++;	//Increment the turn
						draftModel.removeRow(i);	//Remove that row from the draft table
						pick=Test.draftFunction(currentTurn);	//Determine who is picking next
						draftLabel.setText(theLeague.getUser(pick).getName()+"'s turn!");	//Figure out which user is picking next
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
				}
				//If no selection is found
				displayError("Add error!","Sorry, no player was selected!");
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
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Double.toString(teamPlayers[i].getPoints())};	//Initialize the row
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
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Double.toString(teamPlayers[i].getPoints())};	//Initialize the row
			    		tradeModel_2.addRow(temp);
			    	}
		    	}
		    }
		});
		
		//Action listener for the trade button
		btnTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tradeComboBox_1.getSelectedIndex()==-1 || tradeComboBox_2.getSelectedIndex()==-1) {	//If either combo box doesn't have a user selected
					displayError("Trade error!","Must select two teams to trade between.");
					return;
				}
				else if(tradeComboBox_1.getSelectedIndex()==tradeComboBox_2.getSelectedIndex()){	//If the same user is selected in each combo box
					displayError("Trade error!","Must select two different teams to trade between.");
					return;
				}
				int rows1[] = tradeTable_1.getSelectedRows();	//Get selected rows in the left table
				int rows2[] = tradeTable_2.getSelectedRows();	//Get selected rows in the right table
				if(rows1.length==0 && rows2.length==0){	//If no players are selected
					displayError("Trade error!","Must select at least one player to trade.");
					return;
				}
				Player[] p1 = new Player[rows1.length];	//Initialize player array for the left table selection
				Player[] p2 = new Player[rows2.length];	//Initialize player array for the right table selection
				//Populate the player arrays
				for (int i = 0; i < p1.length; i++) {
					p1[i] = League.athletes.get(tradeModel_1.getValueAt(rows1[i],0));
				}
				for (int i = 0; i < p2.length; i++) {
					p2[i] = League.athletes.get(tradeModel_2.getValueAt(rows2[i],0));
				}
				boolean success=Test.trade(League.teams.get(tradeComboBox_1	//Determine if it's a successful trade
						.getSelectedItem()), p1, League.teams
						.get(tradeComboBox_2.getSelectedItem()), p2);
				if(!success){	//If unsuccessful
					displayError("Invalid trade!","Sorry, your trade violates rules of the league.");
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
							Double.toString(teamPlayers[i].getPoints()) };
					tradeModel_1.addRow(temp);	//Add the row
				}
				teamPlayers = League.teams.get(tradeComboBox_2.getSelectedItem()).getPlayers();
				while (tradeModel_2.getRowCount() > 0) {	//Clear the left trade table
					tradeModel_2.removeRow(0);
				}
				for (int i = 0; i < teamPlayers.length; i++) {	//Repopulate the left trade table
					String[] temp = { teamPlayers[i].getName(),
							teamPlayers[i].getPosition(),
							Double.toString(teamPlayers[i].getPoints()) };
					tradeModel_2.addRow(temp);	//Add the row
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
			    		String[] temp={teamPlayers[i].getName(),teamPlayers[i].getPosition(),Double.toString(teamPlayers[i].getPoints())};
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
					displayError("Drop error!","Must select a team to drop a player from.");
					return;
				}
				Player drop=League.athletes.get(dropModel.getValueAt(index,0));	//Get player
				boolean success=Test.dropPlayer(League.teams.get(dropComboBox.getSelectedItem()),drop);	//Determine if drop is successful
				if(!success){
					displayError("Invalid drop!","Sorry, your drop violates rules of the league.");
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
