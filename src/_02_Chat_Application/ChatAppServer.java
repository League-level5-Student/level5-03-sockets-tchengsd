package _02_Chat_Application;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import _01_Intro_To_Sockets.server.ServerGreeter;

public class ChatAppServer extends Thread implements ActionListener {
	JFrame frame;
	JPanel panel;
	JTextField text;
	JButton button;
	ServerSocket sSocket;
	DataOutputStream outStream;
	ArrayList<JLabel> labels = new ArrayList<JLabel>();

	public ChatAppServer() throws IOException {
		sSocket = new ServerSocket(8080);
	}

	public void run() {
		buildFrame();
		boolean forWhile = true;
		while (forWhile) {
			try {
				System.out.println("waiting to connect...");
				Socket socket = sSocket.accept();
				System.out.println("client has connected!");
				DataInputStream inStream = new DataInputStream(socket.getInputStream());
				outStream = new DataOutputStream(socket.getOutputStream());
				while(socket.isConnected()) {
					String utf = inStream.readUTF();
					if(utf != "") {
						addLabel(utf);
					}
				}
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				forWhile = false;
			} catch (IOException e) {
				e.printStackTrace();
				forWhile = false;
			}
		}
	}

	void buildFrame() {
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(400, 500));
		panel = new JPanel();
		text = new JTextField(25);
		button = new JButton();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.add(text);
		panel.add(button);
		button.setText("Send");
		button.addActionListener(this);
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
	public static void main(String[] args) {
		Thread t = new Thread(() -> {
			try {
				ChatAppServer greet = new ChatAppServer();
				greet.run();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			String write = text.getText();
			outStream.writeUTF(write);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
