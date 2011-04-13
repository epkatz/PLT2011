package backend;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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


public class GUI {
	private League theLeague;
	private JFrame frmFloodFantasyLeague;
	private JTable homeTable;
	private MyTableModel addTable;
	private JTable tradeTable1;
	private JTable tradeTable2;
	private JTable dropTable;
	private int currentTeam;
	
	public GUI(League game){
		this.theLeague=game;
		currentTeam=0;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void drawBoard() {
		frmFloodFantasyLeague = new JFrame();
		frmFloodFantasyLeague.setBackground(new Color(0, 0, 205));
		frmFloodFantasyLeague.setTitle("FLOOD Fantasy League");
		frmFloodFantasyLeague.setBounds(100, 100, 450, 300);
		frmFloodFantasyLeague.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFloodFantasyLeague.setSize(new Dimension(700, 400));
		frmFloodFantasyLeague.setVisible(true);
		
		//Populate the home table
		String[] homeHeader={"Rank","Team","Points"};
		User[] rankedTeam=theLeague.getRankedUsers();
		String[][] homeData=new String[rankedTeam.length][3];
		for(int i=0;i<rankedTeam.length;i++){
			homeData[i][0]=Integer.toString(i+1);
			homeData[i][1]=rankedTeam[i].getName();
			homeData[i][2]=Double.toString(rankedTeam[i].getPoints());
		}
		
		//Populate the add table
		String[] addHeader={"Player","Position","Points All Season"};
		final Player[] rankedPlayers=theLeague.getRankedPlayers();
		final String[][] addData=new String[rankedPlayers.length][3];
		for(int i=0;i<rankedPlayers.length;i++){
			addData[i][0]=rankedPlayers[i].getName();
			addData[i][1]=rankedPlayers[i].getPosition();
			addData[i][2]=Double.toString(rankedPlayers[i].getPoints());
		}

		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmFloodFantasyLeague.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JScrollPane homePane = new JScrollPane();
		tabbedPane.addTab("Home", null, homePane, null);
		
		homeTable = new JTable(homeData,homeHeader);
		homeTable.setEnabled(false);
		homePane.setViewportView(homeTable);
		
		JSplitPane addSplitPane = new JSplitPane();
		addSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("Add", null, addSplitPane, null);
		
		JScrollPane addScrollPane = new JScrollPane();
		addSplitPane.setRightComponent(addScrollPane);
		
		addTable = new MyTableModel(addData,addHeader);
		addTable.setCellSelectionEnabled(true);
		addTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addScrollPane.setViewportView(addTable);
		
		JToolBar addToolBar = new JToolBar();
		addSplitPane.setLeftComponent(addToolBar);
		
		final JLabel addLabel = new JLabel("New label");
		addLabel.setMinimumSize(new Dimension(600, 15));
		addLabel.setMaximumSize(new Dimension(32767, 15));
		addToolBar.add(addLabel);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setPreferredSize(new Dimension(100, 25));
		btnAdd.setMaximumSize(new Dimension(100, 25));
		btnAdd.setMinimumSize(new Dimension(100, 25));
		addToolBar.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<addData.length;i++){
					if(addTable.isCellSelected(i,0)){
						if(!Test.draftPlayer(theLeague.getUser(i),rankedPlayers[i]))
							addLabel.setText("Invalid pick!");
					}
				}
			}
		});
		
		JSplitPane tradeSplitPane = new JSplitPane();
		tradeSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("Trade", null, tradeSplitPane, null);
		
		JToolBar tradeToolBar = new JToolBar();
		tradeSplitPane.setLeftComponent(tradeToolBar);
		
		JComboBox tradeComboBox_1 = new JComboBox();
		tradeToolBar.add(tradeComboBox_1);
		
		JComboBox tradeComboBox_2 = new JComboBox();
		tradeToolBar.add(tradeComboBox_2);
		
		JButton btnTrade = new JButton("Trade");
		tradeToolBar.add(btnTrade);
		
		btnTrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Trade players using selection of tables
			}
		});
		
		JSplitPane tradeSplitPane_2 = new JSplitPane();
		tradeSplitPane_2.setResizeWeight(0.5);
		tradeSplitPane.setRightComponent(tradeSplitPane_2);
		
		JScrollPane tradeScrollPane_1 = new JScrollPane();
		tradeSplitPane_2.setRightComponent(tradeScrollPane_1);
		
		tradeTable2 = new JTable();
		tradeTable2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tradeScrollPane_1.setViewportView(tradeTable2);
		
		JScrollPane tradeScrollPane_2 = new JScrollPane();
		tradeSplitPane_2.setLeftComponent(tradeScrollPane_2);
		
		tradeTable1 = new JTable();
		tradeTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tradeScrollPane_2.setViewportView(tradeTable1);
		
		JSplitPane dropSplitPane = new JSplitPane();
		dropSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("Drop", null, dropSplitPane, null);
		
		JToolBar dropToolBar = new JToolBar();
		dropSplitPane.setLeftComponent(dropToolBar);
		
		JComboBox dropComboBox = new JComboBox();
		dropToolBar.add(dropComboBox);
		
		JButton btnDrop = new JButton("Drop");
		dropToolBar.add(btnDrop);
		
		btnDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Drop players using selection of table
			}
		});
		
		JScrollPane dropScrollPane = new JScrollPane();
		dropSplitPane.setRightComponent(dropScrollPane);
		
		dropTable = new JTable();
		dropTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		dropScrollPane.setViewportView(dropTable);
		
		JPanel uploadPanel = new JPanel();
		tabbedPane.addTab("Upload Stat File", null, uploadPanel, null);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JFileChooser chooser=new JFileChooser();
				int selection = tabbedPane.getSelectedIndex();
				if (selection == 4) {
					int result = chooser.showOpenDialog(null);
					switch (result) {
					case JFileChooser.APPROVE_OPTION:
						System.out.println("Approve (Open or Save) was clicked");
						break;
					case JFileChooser.CANCEL_OPTION:
						tabbedPane.setSelectedIndex(0);
						System.out.println("Cancel or the close-dialog icon was clicked");
						break;
					case JFileChooser.ERROR_OPTION:
						System.out.println("Error");
						break;
					}
				}
			}
		});
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
	public MyTableModel(Object[][] data,Object[] columnNames){
		super(data,columnNames);
	}
    public boolean isCellEditable(int row, int col) {
    	return false;
    }
}
