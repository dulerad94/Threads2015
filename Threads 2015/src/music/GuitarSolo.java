package music;


public class GuitarSolo extends Thread{
	private String guitarName;
	private Performance performance;
	private Synchronizer synch;
	private boolean running=true;
	
	
	
	public GuitarSolo(String guitarName, Performance performance,Synchronizer synch) {
		super(guitarName);
		this.guitarName = guitarName;
		this.performance = performance;
		this.synch = synch;
	}
	public GuitarSolo() {
		super();
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public String getGuitarName() {
		return guitarName;
	}
	public void setGuitarName(String guitarName) {
		this.guitarName = guitarName;
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
	@Override
	public void run() {
		play();		
	}
	private synchronized void play(){
		Song song=this.performance.getSong();
		long delay=this.performance.getDelay();

		int i = 0;
        String chords = null;
        while(true){
        while (synch.isIntroFlag()) {
        	
                chords = song.pickChordLine( (i % song.getChordsIntro().size()),song.getChordsIntro());            
                synch.playIntro(chords, song.getIntroDuration(),delay,this);
                i ++;
            }
        if(i %2 ==1) i++;
        while (!synch.isIntroFlag()) {
        	if(i%2==0){
                chords = song.pickChordLine( (i % song.getChords().size()),song.getChords());            
                synch.play(chords,delay,this);
        	}else{
                chords = '\t' + song.pickChordLine((i % song.getChords().size()),song.getChords());
                synch.play(chords,delay,this);
            }
                i++;
            }  
        }
	}
}
	
