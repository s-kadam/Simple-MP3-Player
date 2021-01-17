package edu.uga.cs1302.mytunes;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.player.Player;


/**
 * A simple class to play mp3 files
 *
 */
public class MP3Player {

    // player thread
    MP3Thread playerThread = null;

    /**
     *  Create an MP3File object.
     *
     */
    public MP3Player() 
    {
	System.out.println( "MP3Player.MP3Player created" );
	playerThread = null;
    }

    /**
     *  an inner class to play an mp3 file inside a Java thread
     *
     */
    private class MP3Thread
	extends Thread
    {
	private String mp3filename;
	private Player player; 

	/**
	 *  Create a thread instance for playing an MP3 file.
	 *  @param filename is the path to the MP3 file to be played.
	 *
	 */
	public MP3Thread( String filename ) {
	    super( "Playing " + filename );
	    mp3filename = filename;
	}

	/**
	 *  Close the current playbeck.
	 *
	 */
	public void close() {
	    if (player != null) 
		player.close(); 
	}

	/**
	 *  The main thread run method.
	 *
	 */
	public void run() {
	    try {
		FileInputStream     fis = new FileInputStream(mp3filename);
		BufferedInputStream bis = new BufferedInputStream(fis);
		player = new Player(bis);
		player.play(); 
	    }
	    catch (Exception e) {
		System.err.println( "MP3Player: Cannot play: " + mp3filename);
		System.err.println(e);
	    }
	}
    }

    /**
     *  Play an MP3 file.
     *  @param filename is the path to the MP3 file to be played.
     */
    public void play( String filename ) 
    {
	playerThread = new MP3Thread( filename );
	playerThread.start();
    }

    /**
     *  Stop the currently playing file.
     */
    public void stop() {
	if( playerThread != null )
	    playerThread.close();
    }

}

	
