/*
 * Created on May 28, 2015
 *
 */
package music;

public class Synchronizer {

	private boolean leadLineFlag;
	private boolean introFlag = true;
	private boolean chordsFlag;
	private int count;
	private boolean chordsWritten;
	public boolean isIntroFlag() {
		return introFlag;
	}

	public void setIntroFlag(boolean introFlag) {
		this.introFlag = introFlag;
	}

	public Synchronizer() {
	}

	public Synchronizer(boolean leadLineFlag) {
		super();
		this.leadLineFlag = leadLineFlag;
	}

	public synchronized void singLeadLine(String leadLine, long delay) {
		while (!leadLineFlag || !chordsFlag) {
			try {
				wait();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}		
		singOneLine(leadLine, delay);
	}

	public synchronized void singBackingLine(String backingLine, long delay) {
		while (leadLineFlag || !chordsFlag) {
			try {
				wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		singOneLine(backingLine, delay);
	}

	private void singOneLine(String line, long delay) {
		try {
			wait(delay);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
			
		if (!introFlag ){
			while(!chordsWritten){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(line);
			chordsWritten=false;
		}
		leadLineFlag = !leadLineFlag;		
		notifyAll();
		chordsFlag=false;
		
		
	}
	public synchronized void playIntro(String line, long introDuration,
			long delay) {

		try {
			count += delay;
			wait(delay);
			if (count >= introDuration)
				introFlag = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(line);
	}

	public synchronized void play(String line, long delay) {
		while(chordsFlag){
			try {
				wait(delay);
				
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		notifyAll();
		chordsFlag=true;		
		try {
			wait(delay);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}		
		
		System.out.println(line);		
		chordsWritten=true;
		notifyAll();
	}
	
}

