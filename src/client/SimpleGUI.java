package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import voice.Command;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SimpleGUI extends JFrame implements Runnable{

	private JPanel contentPane;
	private Command command;

	/**
	 * Create the frame.
	 */
	public SimpleGUI(final Command command) {
		this.command = command;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 755, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnOn = new JButton("On");
		btnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.newCommand("lamp enable");
			}
		});
		contentPane.add(btnOn, BorderLayout.WEST);
		
		JButton btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				command.newCommand("lamp disable");
			}
		});
		contentPane.add(btnOff, BorderLayout.EAST);
	}

	@Override
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleGUI frame = new SimpleGUI(command);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
