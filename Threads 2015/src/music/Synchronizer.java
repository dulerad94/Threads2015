/*
 * Created on May 28, 2015
 *
 */
package music;

import javax.swing.JTextArea;

public class Synchronizer {

	private boolean leadLineFlag;
	private boolean introFlag = true;
	private boolean chordsFlag;
	private int count;
	private boolean chordsWritten;
	private JTextArea textArea;
	private String text="";
	private boolean stoped;
	
	public boolean isStoped() {
		return stoped;
	}

	public void setStoped(boolean stoped) {
		this.stoped = stoped;
	}

	public Synchronizer() {
	}

	public Synchronizer(boolean leadLineFlag) {
		super();
		this.leadLineFlag = leadLineFlag;
	}
	

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public boolean isIntroFlag() {
		return introFlag;
	}

	public void setIntroFlag(boolean introFlag) {
		this.introFlag = introFlag;
	}

	

	public synchronized void singLeadLine(String leadLine, long delay, Singer singer) {
		while (!leadLineFlag || !chordsFlag|| stoped) {
			try {
				wait();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}		
		singOneLine(leadLine, delay,singer);
	}

	public synchronized void singBackingLine(String backingLine, long delay, Singer singer) {
		while (leadLineFlag || !chordsFlag|| stoped) {
			try {
				wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		 singOneLine(backingLine, delay, singer );
	}

	private void singOneLine(String line, long delay,Singer singer) {
		if(singer.isRunning()){
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
					
					e.printStackTrace();
				}
			}
			textArea.setText(textArea.getText()+line+'\n');
			chordsWritten=false;
		}
		leadLineFlag = !leadLineFlag;		
		notifyAll();
		chordsFlag=false;
		}
		
		
	}
	public synchronized void playIntro(String line, long introDuration,
			long delay,GuitarSolo guitar) {
		
		while(stoped){
			try {
				wait(delay);
				
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		if (guitar.isRunning()){
		try {
			count += delay;
			wait(delay);
			if (count >= introDuration)
				introFlag = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		textArea.setText(textArea.getText()+line+'\n');
		}
	}

	public synchronized void play(String line, long delay,GuitarSolo guitar) {
		
		while(chordsFlag|| stoped){
			try {
				wait(delay);
				
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		if(guitar.isRunning()){
		notifyAll();
		chordsFlag=true;
		try {
			wait(delay);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}			
		textArea.setText(textArea.getText()+line+'\n');
		chordsWritten=true;
		notifyAll();
		}
		
	}
}

