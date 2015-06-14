/*
 * Created on May 28, 2015
 *
 */
package music;

import java.util.Iterator;
import java.util.List;

public class Song {

	private String title;
	private List<String> lyrics;
	private List<String> chords;
	private int introDuration;
	private List<String> chordsIntro;

	
	public Song() {
	}

	public Song(String title, List<String> lyrics) {
		super();
		this.title = title;
		this.lyrics = lyrics;
	}

	public Song(String title, List<String> lyrics, List<String> chords,
			int introDuration, List<String> chordsIntro) {
		super();
		this.title = title;
		this.lyrics = lyrics;
		this.chords = chords;
		this.introDuration = introDuration;
		this.chordsIntro = chordsIntro;
	}

	public String pickLine(Voice voice, int linePointer) {
		if (voice == Voice.ALL) {
			return allLyrics();
		}
		if ((voice == Voice.LEAD) || (voice == Voice.BACKING)) {
			return lyrics.get(linePointer);
		}
		return null;
	}

	public String pickChordLine(int linePointer, List<String> chords) {
		return chords.get(linePointer);

	}

	public String allLyrics() {
		StringBuffer lyrics = new StringBuffer();
		for (Iterator iterator = this.lyrics.iterator(); iterator.hasNext();) {
			// String string = (String) iterator.next();
			lyrics = lyrics.append((String) iterator.next() + '\n');
		}
		return lyrics.toString();
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getLyrics() {
		return lyrics;
	}

	public void setLyrics(List<String> lyrics) {
		this.lyrics = lyrics;
	}

	public List<String> getChords() {
		return chords;
	}

	public void setChords(List<String> chords) {
		this.chords = chords;
	}

	public int getIntroDuration() {
		return introDuration;
	}

	public void setIntroDuration(int introDuration) {
		this.introDuration = introDuration;
	}
	public List<String> getChordsIntro() {
		return chordsIntro;
	}

	public void setChordsIntro(List<String> chordsIntro) {
		this.chordsIntro = chordsIntro;
	}

}
