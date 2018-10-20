package amazon.ood;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

// The Jukebox class represents the body of the problem. 
// Many of the interactions between the components of the system, 
// or between the system and the user, are channeled through here.
public class JukeBox { // 点唱机
	private CDPlayer cdPlayer;
	private Set<CD> cdCollection;
	private SongSelector ts; // physically always point to current song

	private User user;

	public JukeBox(CDPlayer cdPlayer, User user, Set<CD> cdCollection, SongSelector ts) {
		this.cdPlayer = cdPlayer;
		this.user = user;
		this.cdCollection = cdCollection;
		this.ts = ts;
	}

	// selection strategy by user
	public CD selectNext() {
		return cdCollection.iterator().next();
	}

	// user plays the selected CD
	public void play(CD cd) {
		cdPlayer.setCD(cd);
		cdPlayer.playSong(getCurrentSong());
	}

	// user shuffle play list
	public void shuffle() {
		this.cdPlayer.shufflePlaylist();
	}

	public Song getCurrentSong() {
		return ts.getCurrentSong();
	}

	public void setUser(User u) {
		this.user = u;
	}
}

class SongSelector {

	private Song currentSong;

	public SongSelector(Song s) {
		currentSong = s;
	}

	public void setSong(Song s) {
		currentSong = s;
	}

	public Song getCurrentSong() {
		return currentSong;
	}

}

// Like a real CD player,
// the CDP layer class supports storing just one CD at a time.
// The CDs that are not in play are stored in the jukebox.
class CDPlayer {
	private Playlist p;
	private CD c;

	/* Constructors. */
	public CDPlayer(CD c, Playlist p) {
		this.c = c;
		this.p = p;
	}

	public CDPlayer(Playlist p) {
		this.p = p;
	}

	public CDPlayer(CD c) {
		this.c = c;
	}

	/* Playsong */
	public void playSong(Song s) {
		// needs implementation
	}

	public void shufflePlaylist() {
		this.p.shuffle();
	}

	/* Getters and setters */
	public Playlist getPlaylist() {
		return p;
	}

	public void setPlaylist(Playlist p) {
		this.p = p;
	}

	public CD getCD() {
		return c;
	}

	public void setCD(CD c) {
		this.c = c;
	}
}

// navigate history
class Playlist {
	private Queue<Song> songs;

	public Playlist() {
		this.songs = new LinkedList<>();
	}

	public Playlist(Queue<Song> queue) {
		this.songs = queue;
	}

	public Song getNextSongToPlay() {
		return songs.poll();
	}

	public void queueUpSong(Song song) {
		songs.offer(song);
	}

	public void removeSong(Song song) {
		songs.remove(song);
	}

	public void shuffle() {
	}
}

class Playlist2 {
	private List<Song> songs;
	private int next = 0;

	public Playlist2() {
		this.songs = new ArrayList<>();
	}

	public Playlist2(ArrayList<Song> queue) {
		this.songs = queue;
	}

	public Song getNextSongToPlay() {
		if (next == songs.size()) {
			throw new IllegalArgumentException();
		}

		return songs.get(next++);
	}

	public Song getPrevSongToPlay() {
		if (next == 0) {
			throw new IllegalArgumentException();
		}
		next--;
		return songs.get(next);
	}

	public void queueUpSong(Song song) {
		songs.add(song);
	}

	public void removeSong(Song song) {
		songs.remove(song);
	}

	public void shuffle() {
	}
}

class CD {
	/* data for id, artist, songs, etc */
}

class Song {
	/* data for id, CD (could be null), title, length, etc */
}

class User {
	private String name;
	private long ID;

	public User(String name, long iD) {
		this.name = name;
		this.ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public User getUser() {
		return this;
	}

	public static User addUser(String name, long iD) {
		return new User(name, iD);
	}
}