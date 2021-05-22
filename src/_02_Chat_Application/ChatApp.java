package _02_Chat_Application;

import java.awt.Dimension;
import java.awt.HeadlessException;
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

public class ChatApp implements ActionListener {
	JFrame frame;
	JPanel panel;
	JTextField text;
	JButton button;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	DataOutputStream stream;
	static ChatApp app;
	public static void main(String [] args) {
		String ip = "localhost";
		int portNum = 8080;
		app = new ChatApp();
		app.buildFrame();
		try {
			Socket socket = new Socket(ip, portNum);
			app.stream = new DataOutputStream(socket.getOutputStream());
			//stream.writeUTF(JOptionPane.showInputDialog("Message to send:"));
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			while(socket.isConnected()) {
				String utf = inStream.readUTF();
				if(utf != "") {
					app.addLabel(utf);
				}
			}
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	void buildFrame() {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(400,500));
		panel = new JPanel();
		text = new JTextField(25);
		button = new JButton();
		button.setText("Send");
		button.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		panel.add(text);
		panel.add(button);
		frame.pack();
	}
	void addLabel(String newString) {
		JLabel newLabel = new JLabel();
		newLabel.setPreferredSize(new Dimension(370, 30));
		labels.add(newLabel);
		labels.get(labels.size() - 1).setText(newString);
		panel.add(labels.get(labels.size() - 1));
		frame.pack();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			String write = text.getText();
			app.stream.writeUTF(write);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
