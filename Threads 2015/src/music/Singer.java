/*
 * Created on May 28, 2015
 *
 */
package music;

import java.util.Timer;

import test.ShoutTimerTask;

public class Singer extends Thread{
    
    private String singerName;
    private Voice voice;
    private Performance performance;   
    private Synchronizer synch;
    private boolean running=true;
    
    

	public Singer() {
        super();
        
    }
    
    public Singer(String singerName, Voice voice, Performance performance) {
        super(singerName);
        this.singerName = singerName;
        this.voice = voice;
        this.performance = performance;
       
    }

    public Singer(String singerName, Voice voice, Performance performance,
             Synchronizer synch) {
        super();
        this.singerName = singerName;
        this.voice = voice;
        this.performance = performance;    
        this.synch = synch;
        
    }

    public void sing(Song song, int noOfRepetitions) {
        for (int i = 0; i < noOfRepetitions; i+=2) {
            if (this.voice == Voice.LEAD) {
                System.out.println(song.pickLine(this.voice, (i % song.getLyrics().size())));
            } else {
                System.out.println(song.pickLine(this.voice, (i % song.getLyrics().size()) + 1));
            }
        }
    }

    public synchronized void singWithDelay(Song song, int noOfRepetitions) {
        for (int i = 0; i < noOfRepetitions; i+=2) {
            if (this.voice == Voice.LEAD) {
                System.out.println(song.pickLine(this.voice, (i % song.getLyrics().size())));
                try {
                    wait(performance.getDelay());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                System.out.println('\t' + song.pickLine(this.voice, (i % song.getLyrics().size()) + 1));
                try {
                    wait(performance.getDelay());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void run() {  		
    	sing();		
    }
    
    public synchronized void sing() {
        
        Song song = this.performance.getSong();
        long delay = this.performance.getDelay();
        
        int i = 0;
        String line = null;
        
        while (true) {	
            if (this.voice == Voice.LEAD) {
                line = song.pickLine(this.voice, (i % song.getLyrics().size()));
                synch.singLeadLine(line, delay,this);
            } else {
                line = '\t' + song.pickLine(this.voice, (i % song.getLyrics().size() + 1));
                synch.singBackingLine(line, delay,this);
            	}
            i +=2;
        }
        
    }
    
    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String name) {
        this.singerName = name;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Synchronizer getSynch() {
        return synch;
    }

    public void setSynch(Synchronizer synch) {
        this.synch = synch;
    }
    public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		
	}
}
