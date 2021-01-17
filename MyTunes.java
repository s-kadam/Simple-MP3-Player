package edu.uga.cs1302.mytunes;

import java.awt.*;
import java.awt.event.*;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class MyTunes 
    extends JFrame
{
    private JTable                     table;
    private JTextField                 txtFilter;
    private TableModel                 model;
    private TableRowSorter<TableModel> sorter;
    private MP3Collection music;
    
    // construct the panel and components for this frame
    /**
     * @param title Title of GUI
     * @param pathname path of file
     */
    MyTunes(String title, String pathname )
    {
    	
	// set the frame title
	super (title);

	music = new MP3Collection(pathname);
	
	// setup the exit on window closing
	setDefaultCloseOperation( EXIT_ON_CLOSE );

	// set the starting size
        setSize( 700, 400 );

	// set the minimum size (the window can't be made smaller)
        setMinimumSize( new Dimension( 600, 400 ) );

	// create column names
	String columns[] = { "Title", "Author", "Album", "Year"};

	// get the rows array with JTable cell values;
	// the values in each row must correspond to the column labels
	Object[][] rows = music.getTableData();

	//model = new DefaultTableModel( rows, columns );


	// create a TableModel with the rectangular data and column labels
	model = new DefaultTableModel( rows, columns ) {
		@Override
		public boolean isCellEditable(int row, int column) {
		    return false;
		}
	};


	// create a table attached to the created TableModel
	table = new JTable( model );
	//	table.setEnabled(false);

	// prevent multiple rows from being selected in the table
	table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        // create a right renderer for the Price column
        TableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        ((JLabel) rightRenderer).setHorizontalAlignment( SwingConstants.RIGHT );

        // set the price column width
        table.getColumnModel().getColumn(3).setMaxWidth( 60 );
        table.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );

	// Attach a list selection listener to the table's selection model, to
	// be notified whenever the user selects a row. Use this opportunity to
	// output the view and model indices on the status label.


	// set up a TableRow Sorter for our table
	sorter = new TableRowSorter<TableModel>( model );
	table.setRowSorter( sorter );

	// create a scroll pane including the table
	JScrollPane scrollPane = new JScrollPane( table );

	// it's possible to set its preferred (and minimum/maximum) size
	scrollPane.setPreferredSize( new Dimension (300, 100) );

	// create a panel for buttons and filter text box
	JPanel filterPanel = new JPanel();

	// create a label for the text box
	filterPanel.add( new JLabel( "Search:" ) );
        filterPanel.setMaximumSize( new Dimension( 1000, 350 ) );
        filterPanel.setMinimumSize( new Dimension( 800,  200 ) );

	// create a text box for the filter text
	txtFilter = new JTextField( 20 );
	txtFilter.addActionListener( new TextFieldListener() );
	filterPanel.add( txtFilter );
	
	JButton back = new JButton("Back");
	back.addActionListener(new backListener());
	filterPanel.add(back);

	// create a button to set the filter
	JButton play = new JButton( "Play" );
	play.addActionListener( new playListener() );
	filterPanel.add( play );

	// create a button to discover the row number
	JButton stop = new JButton( "Stop" );
	// Attach a listener to the table's selection model, to
	// be notified whenever the user selects a row. Use this opportunity to
	// output the view and model indices on the status label.
	stop.addActionListener( new stopListener() );
	filterPanel.add(stop);

	// create an empty border around the frame
	Border border = BorderFactory.createEmptyBorder( 20, 20, 20, 20 );
	//Border border = BorderFactory.createEmptyBorder( 5, 5, 5, 5 );
	getRootPane().setBorder( border );

	// get the content pane of the frame
	Container c = getContentPane();

	// set its layout to BoxLayout along the Y-axis
        c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) );
        c.add( filterPanel );
        c.add( scrollPane );

	// display the frame and start processing events
	setVisible(true);
    }

    private class backListener implements ActionListener{
    		public void actionPerformed(ActionEvent e){
    			String expr = "";

    		    // if you'd like a case insensitive filtering, add (?i) in front of the filter text
    		    sorter.setRowFilter( RowFilter.regexFilter( "(?i)" + expr ) );

    		    // Unsort the view.
    		    sorter.setSortKeys( null );
    		}
    }
    
    // this listener illustrates how to set a sorter filter
    private class playListener 
	implements ActionListener
    {
	public void actionPerformed( ActionEvent e )
	{
		
	    int tableRow = table.getSelectedRow();
	    int row = sorter.convertRowIndexToModel( tableRow );
	    music.startPlay(row);
	}
    };

    // this listener illustrates how to set a sorter filter directly with the text field box
    private class TextFieldListener 
	implements ActionListener
    {
	public void actionPerformed( ActionEvent e )
	{
		
		// Install a new row filter.
	    String expr = txtFilter.getText();
	    // if you'd like a case insensitive filtering, add (?i) in front of the filter text
	    sorter.setRowFilter( RowFilter.regexFilter( "(?i)" + expr ) );
	    // Unsort the view.
	    sorter.setSortKeys( null );
	}
    };

    // this listener illustrates how to get an index of the selected row
    private class stopListener 
	implements ActionListener
    {
	public void actionPerformed( ActionEvent e )
	{
	    // get the index of the selected row
	    int tableRow = table.getSelectedRow();
	    music.stopPlay();
	}
    };

    // format a 2-D Object array suitable for a JTable
    private Object[][] getTableData()
    {
		Object[][] data = music.getTableData();
		return data;
    }

    public static void main( String [] args ){
	    if (args.length < 0){
	    	System.err.println("Index Out Of Bounds");
	    	throw new IndexOutOfBoundsException();
	    }
	    
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			    public void run() {
			    MyTunes musicGUI  = new MyTunes( "My Music", args[0]);
			    musicGUI.setVisible(true);
				}
		} );
		
		
	
	
    }
}


