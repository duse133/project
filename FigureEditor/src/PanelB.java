import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelB extends JPanel {
	JButton button1, button2, button3,button4,button5,button6,button7;
	PanelA pa;

	public PanelB(PanelA pa) {
		this.pa = pa;
		setBackground(Color.BLUE);
		setLayout(new GridLayout(7, 1, 5, 5));
		button1 = new JButton("사각");
		button2 = new JButton("타원");
		button3 = new JButton("직선");
		button4 = new JButton("복사");
		button5 = new JButton("삭제");
		button6 = new JButton("저장");
		button7 = new JButton("불러오기");
		add(button1);
		add(button2);
		add(button3);
		add(button4);
		add(button5);
		add(button6);
		add(button7);

		ActionListener listener = new MyActionListener();
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		button3.addActionListener(listener);
		button4.addActionListener(listener);
		button5.addActionListener(listener);
		button6.addMouseListener(new HandleSave());
		button7.addMouseListener(new HandleLoad());
	}

	class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			pa.label.setText(e.getActionCommand());
		}
	}

	class HandleSave extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			try {
				FileOutputStream fileOut = new FileOutputStream("ShapesObjectSave");
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(pa.shapes);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	class HandleLoad extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			try {
				FileInputStream fileIn = new FileInputStream("ShapesObjectSave");
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				pa.shapes = (ArrayList<Shape>) objectIn.readObject();
				pa.repaint();
			} catch (FileNotFoundException ex) {
				throw new RuntimeException(ex);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}