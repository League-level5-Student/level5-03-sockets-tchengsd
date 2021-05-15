package _02_Chat_Application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	public static void main(String [] args) {
		String ip = "localhost";
		int portNum = 8080;
		JFrame frame;
		JPanel panel;
		ArrayList<JLabel> labels = new ArrayList<JLabel>();
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(200,500));
		panel = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.pack();
		try {
			Socket socket = new Socket(ip, portNum);
			DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
			//stream.writeUTF(JOptionPane.showInputDialog("Message to send:"));
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			
			while(socket.isConnected()) {
				String utf = inStream.readUTF();
				if(utf != "") {
					JLabel newLabel = new JLabel();
					newLabel.setPreferredSize(new Dimension(170, 30));
					labels.add(newLabel);
					labels.get(labels.size() - 1).setText(utf);
					panel.add(labels.get(labels.size() - 1));
					frame.pack();
				}
			}
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
