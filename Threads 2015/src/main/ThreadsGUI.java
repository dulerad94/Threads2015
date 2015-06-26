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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 583, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanelEast(), BorderLayout.EAST);
		contentPane.add(getScrollPane(), BorderLayout.CENTER);
		test = new Test();
		test.getBbk().getSynch().setTextArea(textArea);
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
					switch ((String) comboBox.getSelectedItem()) {
					case "Bono":
//						test.getBono().setRunning(true);
						test.testSingOne(test.getBono());
						break;
					case "B. B. King":
//						test.getBbk().setRunning(true);
						test.testSingOne(test.getBbk());
						break;
					case "TheEdge":
//						test.getTheEdge().setRunning(true);
						test.testSingOne(test.getTheEdge());
						break;
					}

				}
			});
		}
		return btnStart;
	}

	private JButton getBtnStop() {
		if (btnStop == null) {
			btnStop = new JButton("STOP");
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
					setRunning(false);					
				}
			});
		}
		return btnStopSvi;
	}

	private void setRunning(boolean running) {
		test.getBbk().setRunning(running);
		test.getBono().setRunning(running);
		test.getTheEdge().setRunning(running);	
		test.getBbk().getSynch().setStoped(!running);
	}
	
}
