import java.awt.*;
import java.awt.event.*;
import java.awt.image.DirectColorModel;
import java.util.ArrayList;

import javax.swing.*;

public class FigureEditorFrame extends JFrame {
	PanelA pa;
	public FigureEditorFrame() {
		setTitle("Figure Editor");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pa = new PanelA();
		add(pa, BorderLayout.CENTER);
		add(new PanelC(pa), BorderLayout.LINE_START);
		
		setVisible(true);
	}
}

class PanelC extends JPanel {
	public PanelC(PanelA pa) {
		add(new PanelB(pa));
	}
}

