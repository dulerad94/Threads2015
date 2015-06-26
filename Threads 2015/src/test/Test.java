/*
 * Created on May 28, 2015
 *
 */
package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import main.ThreadsGUI;
import music.GuitarSolo;
import music.Performance;
import music.Singer;
import music.Song;
import music.Synchronizer;
import music.Voice;

public class Test {
    
    public static final Scanner IN = new Scanner(System.in);

    private Song song;
    private Performance performance;
    private Singer bbk;
    private Singer bono;
    private GuitarSolo theEdge;
    
    public Singer getBbk() {
		return bbk;
	}

	public void setBbk(Singer bbk) {
		this.bbk = bbk;
	}

	public Singer getBono() {
		return bono;
	}

	public void setBono(Singer bono) {
		this.bono = bono;
	}

	public GuitarSolo getTheEdge() {
		return theEdge;
	}

	public void setTheEdge(GuitarSolo theEdge) {
		this.theEdge = theEdge;
	}

	public Test() {
        initialize();
    }
    
    public void initialize() {
        List<String> lyrics = new ArrayList<String>();
        lyrics.add("When love comes to town I'm gonna jump that train");
        lyrics.add("When love comes to town I'm gonna catch that flame.");
        lyrics.add("Maybe I was wrong to ever let you down");
        lyrics.add("But I did what I did before love came to town.");
        List<String> chords=new ArrayList<String>();
        chords.add("A");
        chords.add("     E                                       A");
        chords.add("E");
        chords.add("      E");
        List<String> chordsIntro=new ArrayList<String>();
        chordsIntro.add("E");
        chordsIntro.add("A");
        chordsIntro.add("E");
        
        
        song = new Song("When Love Comes to Town", lyrics,chords,3000,chordsIntro);
        performance = new Performance(song, 1000);
        Synchronizer synch = new Synchronizer(true);
      
        
//        bbk = new Singer("B.B. King", Voice.LEAD, performance);
//        bono = new Singer("Bono", Voice.BACKING, performance);
        theEdge= new GuitarSolo("David Evans",performance, synch);
        bbk = new Singer("B.B. King", Voice.LEAD, performance, synch);
        bono = new Singer("Bono", Voice.BACKING, performance, synch);
       
    }
    
    public synchronized void simpleWait() {
        System.out.println("Wait 2sec...");
        try {
            wait(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done.");
    }
    
    public void loopWait() {
        for (int i = 0; i < 5; i++) {
            simpleWait();
        }
    }
    
    public void timerWait() {
        Timer timer = new Timer();
        ShoutTimerTask mtt = new ShoutTimerTask(timer);
        timer.schedule(mtt, 4500);
        loopWait();
    }
    
    public void testPickLine() {
        initialize();
        System.out.println(song.allLyrics());
        System.out.println(song.pickLine(Voice.LEAD, 0));
    }
    
    public void testSing() {
        initialize();
        bbk.sing(song, 8);
        System.out.println();
        bono.sing(song, 8);
    }

    public void testSingWithDelay() {
        initialize();
        bbk.singWithDelay(song, 8);
        System.out.println();
        bono.singWithDelay(song, 8);
    }

    public void testSingWithTimer() {
        initialize();
        
        Timer timer = new Timer();
        ShoutTimerTask shout = new ShoutTimerTask(timer);
        timer.schedule(shout, 2500);
        
        bbk.singWithDelay(song, 8);
    }
    
    public void testSingWithThreads() {
     //   initialize();
        
        bbk.start();        
        bono.start();
        theEdge.start();
        
               
    }
    public void testSingOne(Thread performer){
    	performer.start(); 	
    }

}
