package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import test.Test;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import music.Synchronizer;

import java.awt.Dimension;
public class ThreadsGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panelEast;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnStartSvi;
	private Test test;
	private JComboBox comboBox;
	private JButton btnStart;
	private JButton btnStop;
	private JButton btnStopSvi;
	private Thread start;
	private Synchronizer synch;
	private JButton btnRestart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ThreadsGUI frame = new ThreadsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ThreadsGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelEast(), BorderLayout.EAST);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		setUp();
	}

	private JPanel getPanelEast() {
		if (panelEast == null) {
			panelEast = new JPanel();
			panelEast.setPreferredSize(new Dimension(114, 10));
			panelEast.setLayout(null);
			panelEast.add(getBtnStartSvi());
			panelEast.add(getBtnStopSvi());
			panelEast.add(getBtnStart());
			panelEast.add(getBtnStop());
			panelEast.add(getComboBox());
			panelEast.add(getBtnRestart());
		}
		return panelEast;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setEditable(false);
		}
		return textArea;
	}

	private JButton getBtnStartSvi() {
		if (btnStartSvi == null) {
			btnStartSvi = new JButton("START SVI");
			btnStartSvi.setBounds(7, 7, 103, 23);
			btnStartSvi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnStartSvi.setEnabled(false);
					btnStopSvi.setEnabled(true);
					btnStart.setEnabled(false);
					btnStop.setEnabled(false);
					comboBox.setEnabled(false);
					setRunning(true);
					start = new Thread("Start dugme") {
						public void run() {
							test.testSingWithThreads();
						}

					};
					start.start();
				}
			});
		}
		return btnStartSvi;
	}

	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
			comboBox.setBounds(7, 171, 103, 20);
			comboBox.setModel(new DefaultComboBoxModel(new String[] { "Bono",
					"B. B. King", "TheEdge" }));

		}
		return comboBox;
	}

	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("START");
			btnStart.setBounds(7, 195, 103, 23);
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnStartSvi.setEnabled(false);
					btnStopSvi.setEnabled(false);
					btnStart.setEnabled(false);
					btnStop.setEnabled(true);
					comboBox.setEnabled(false);
					synch.reset();
					setRunning(false);
					switch ((String) comboBox.getSelectedItem()) {
					case "Bono":{
						synch.setLeadLineFlag(false);
						setSingerSing(true);
						test.getBono().setRunning(true);					
						break;
					}
					case "B. B. King":{
						synch.setLeadLineFlag(true);
						setSingerSing(true);
						test.getBbk().setRunning(true);			
						break;
					}
					case "TheEdge":{
						textArea.setText(textArea.getText()+"Without Intro\n");
						setGuitarPlay(true);
						test.getTheEdge().setRunning(true);
						break;
					}
					}				
					start = new Thread("Start dugme") {
						public void run() {
							test.testSingWithThreads();
						}
					};
					start.start();
					

				}
			});
		}
		return btnStart;
	}

	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton("STOP");
			btnStop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnStartSvi.setEnabled(false);
					btnStopSvi.setEnabled(false);			
					btnStart.setEnabled(true);
					btnStop.setEnabled(false);
					comboBox.setEnabled(true);
					setRunning(false);	
					synch.setOneActive(false);
					
				}
			});
			btnStop.setBounds(7, 222, 103, 23);
		}
		return btnStop;
	}

	private JButton getBtnStopSvi() {
		if (btnStopSvi == null) {
			btnStopSvi = new JButton("STOP SVI");
			btnStopSvi.setBounds(7, 34, 103, 23);
			btnStopSvi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					btnStartSvi.setEnabled(true);
					btnStopSvi.setEnabled(false);			
					btnStart.setEnabled(false);
					btnStop.setEnabled(false);
					comboBox.setEnabled(false);
					setRunning(false);	
					synch.setOneActive(false);
				}
			});
		}
		return btnStopSvi;
	}
	private JButton getBtnRestart() {
		if (btnRestart == null) {
			btnRestart = new JButton("RESTART");
			btnRestart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setUp();
				}
			});
			btnRestart.setBounds(7, 256, 103, 23);
		}
		return btnRestart;
	}
	private void setRunning(boolean running) {
		test.getBbk().setRunning(running);
		test.getBono().setRunning(running);
		test.getTheEdge().setRunning(running);	
	}
	private void setSingerSing(boolean sing){
		synch.setIntroFlag(!sing);				
		synch.setChordsWritten(sing);
		synch.setChordsFlag(sing);
		synch.setOneActive(sing);
	}
	private void setGuitarPlay(boolean play){
		synch.setIntroFlag(!play);
		synch.setChordsWritten(!play);
		synch.setChordsFlag(!play);
		synch.setOneActive(play);
	}
	private synchronized void setUp(){
		if(test!=null) setRunning(false);
		test = new Test();
		synch=test.getBbk().getSynch();
		try {
			wait(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		textArea.setText("");
		synch.setTextArea(textArea);
		btnStop.setEnabled(false);
		btnStopSvi.setEnabled(false);
		btnStartSvi.setEnabled(true);
		btnStart.setEnabled(true);
		comboBox.setEnabled(true);
		setRunning(false);
		synch.setOneActive(false);
	}
	
}
