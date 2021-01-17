package edu.uga.cs1302.mytunes;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFileFormat;


public class MP3File{
    private String path;
    private String author;
    private String album;
    private String title;
    private int date;
    /**
     * default constructor for MP3File
     */
    public MP3File(){
	this.path = "";
	this.author = "";
	this.album = "";
	this.title = "";
	this.date = 1900;
    }
    
    /**
     * @param pathname the path of the MP3file
     * Constructor for MP3File
     */
    public MP3File(String pathname){
	FileInputStream fis = null;
	try {
		fis = new FileInputStream(pathname);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	BufferedInputStream bis    = new BufferedInputStream(fis);
	AudioFileFormat mpegFormat = null;
	try {
		mpegFormat = AudioSystem.getAudioFileFormat(bis);
	
		Map properties  = mpegFormat.properties();
		
		this.path = pathname;
		this.author = (String) properties.get("author");
		this.album = (String) properties.get("album");
		this.title = (String) properties.get("title");
		String year = (String) properties.get("date");
		this.date = Integer.parseInt(year);
		if (properties.get("author").equals(null)){
		    this.author = "";
		}  
		if (properties.get("album").equals(null)){
		    this.album = "";
		}
		
		if (properties.get("date").equals(null)){
		    this.date = 1900;
		}
	
		if (properties.get("title").equals(null)){
		    this.title = "";
		}
	} catch (UnsupportedAudioFileException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }

    /**
     * @return pathname
     */
    public String getPath(){
	return this.path;
    }
    
    /**
     * @param pathname the path to the MP3 file
     * setter for this.path
     */
    public void setPath(String pathname){
	this.path = pathname;
    }
    
    /**
     * @return author name
     * getter for this.author
     */
    public String getAuthor(){
	return this.author;
    }
    
    /**
     * @param author the name of the author
     * setter for this.author
     */
    public void setAuthor(String author){
	this.author = author;
    }

    /**
     * @return the name of the album
     * getter for album
     */
    public String getAlbum(){
	return this.album;
    }

    /**
     * @param album the name of the album
     * setter for this.album
     */
    public void setAlbum(String album){
	this.album = album;
    }
    
    /**
     * @return the title of the track
     * getter for title
     */
    public String getTitle(){
	return this.title;
    }
   
    /**
     * @param title the title of the track
     * setter for title
     */
    public void setTitle(String title){
	this.title = title;
    }
     
    /**
     * @return the (int) release date
     */
    public int getYear(){
	return this.date;
    }

    public void setYear(int year){
	this.date = year;
    }

		
    /**
     * @return a string representation of the MP3 attributes
     */
    public String toString(){
	String s = "";
	s += "\nTitle:\t\t" + getTitle();
	s += "\nAuthor:\t\t" + getAuthor();
	s += "\nAlbum:\t\t" + getAlbum();
	s += "\nRelease Date:\t" + getYear();
	s += "\nFile Path:\t" + getPath() + "\n";
	return s;
    }
    /**
     * @param o Any object
     * @return true if author, title, album, and date are equal, else returns false
     */
    public boolean equals (Object o){
	if (o instanceof MP3File){ 
	    MP3File mf = (MP3File) o;
	    if (mf.author.equals(this.author) && mf.title.equals(this.title) && mf.album.equals(this.album) && mf.date == (this.date)){
		return true;
	    }else{
		return false;
	    }
	}
	return false;
    }
    /**
     * play method to play the MP3 file
     */
    public void play(){
	MP3Player player = new MP3Player();
	try {
	    player.play(getPath());
	    
        }
        catch( Exception e ) {
	    System.err.println( "Usage: java TestMP3Player file.mp3" );
        }
    }
}