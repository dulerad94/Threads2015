/*
 * Created on May 28, 2015
 *
 */
package music;

import javax.swing.JTextArea;

public class Synchronizer {

	private boolean leadLineFlag;
	private boolean introFlag=true;
	private boolean chordsFlag;
	private int count;
	private boolean chordsWritten;
	private JTextArea textArea;
	private boolean oneActive;
	

	public Synchronizer() {
		reset();
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
	public boolean isOneActive() {
		return oneActive;
	}

	public void setOneActive(boolean oneActive) {
		this.oneActive = oneActive;
	}
	

	public boolean isLeadLineFlag() {
		return leadLineFlag;
	}

	public void setLeadLineFlag(boolean leadLineFlag) {
		this.leadLineFlag = leadLineFlag;
	}

	public boolean isChordsFlag() {
		return chordsFlag;
	}

	public void setChordsFlag(boolean chordsFlag) {
		this.chordsFlag = chordsFlag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isChordsWritten() {
		return chordsWritten;
	}

	public void setChordsWritten(boolean chordsWritten) {
		this.chordsWritten = chordsWritten;
	}

	public synchronized void singLeadLine(String leadLine, long delay, Singer singer) {
		while (!leadLineFlag || !chordsFlag|| !singer.isRunning()) {
			try {
				wait();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}		
		singOneLine(leadLine, delay,singer);
	}

	public synchronized void singBackingLine(String backingLine, long delay, Singer singer) {
		while (leadLineFlag || !chordsFlag|| !singer.isRunning()) {
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
			if(!oneActive) chordsWritten=false;
		}
		if(!oneActive)	leadLineFlag = !leadLineFlag;		
		notifyAll();
		if(!oneActive)	chordsFlag=false;
		}
		
		
	}
	public synchronized void playIntro(String line, long introDuration,
			long delay,GuitarSolo guitar) {
		
		while(!guitar.isRunning()){
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
			if (count >= introDuration){
					introFlag = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		textArea.setText(textArea.getText()+line+'\n');
		}
	}

	public synchronized void play(String line, long delay,GuitarSolo guitar) {
		
		while(chordsFlag|| !guitar.isRunning()){
			try {				
				wait(delay);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		if(guitar.isRunning()){
		notifyAll();
		if(!oneActive)	chordsFlag=true;
		try {
			wait(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}			
		textArea.setText(textArea.getText()+line+'\n');
		if(!oneActive) chordsWritten=true;
		notifyAll();
		}
	}
	public synchronized void reset(){	
		introFlag=true;
		count=0;
		chordsFlag=false;
		chordsWritten=false;
		oneActive=false;
		notifyAll();
	}
}

