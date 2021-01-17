package edu.uga.cs1302.mytunes;

import java.io.File;
import java.util.LinkedList;
import java.lang.IndexOutOfBoundsException;
import java.util.List;

public class MP3Collection{
    
	List<MP3File> tunes = new LinkedList<MP3File>();
	MP3Player player = new MP3Player();

 
    /**
     * MP3 Collection constructor
     */
    public MP3Collection(){
		this.tunes = new LinkedList<MP3File>();
	}

    /**
     * @param directoryPathname String for directory path 
     */
    public MP3Collection(String directoryPathname){
		this.tunes= new LinkedList<MP3File>();
		File musicFolder = new File(directoryPathname);
	    File[] musicFiles = musicFolder.listFiles();
	
		for(int i = 0; i < musicFiles.length; i++){
		    if(musicFiles[i].getName().endsWith(".mp3")){
			MP3File song = new MP3File(musicFiles[i].getPath());
			tunes.add(song);
		    }
        }
    }

    /**
     * @return Object[][] tableData
     */
    public Object[][] getTableData(){
		Object[][] tuneData = new Object[tunes.size()][4];
			for (int i = 0; i < tunes.size(); i++){
			    tuneData[i][0] = tunes.get(i).getTitle();
			    tuneData[i][1] = tunes.get(i).getAuthor();
			    tuneData[i][2] = tunes.get(i).getAlbum();
			    tuneData[i][3] = tunes.get(i).getYear();
			}
			return tuneData;
    }

	    public int size(){
		return tunes.size();
    }
    
    /**
     * @param index index#
     * @return MP3 File
     * @throws IndexOutOfBoundsException
     */
    public MP3File getFile(int index) throws IndexOutOfBoundsException{
		if (index < 0 || index > tunes.size()){
		    throw new IndexOutOfBoundsException();
		}
		return tunes.get(index);
    }

    /**
     * plays the music
     * @param index index number
     * @throws IndexOutOfBoundsException
     */
    public void startPlay(int index) throws IndexOutOfBoundsException{
		if (index < 0 || index > tunes.size()){
				throw new IndexOutOfBoundsException();
		}
		player.stop();
		MP3File file = this.getFile(index);
		String path = file.getPath();
		player.play(path);
    }

    /**
     * stops the play
     */
    public void stopPlay(){
        player.stop();
    }
}




