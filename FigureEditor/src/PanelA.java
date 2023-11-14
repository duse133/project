import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelA extends JPanel {
	JLabel label;
	private Point start, end;
	private boolean Dragged = false;
	private boolean Check = false;

	protected ArrayList<Shape> shapes = new ArrayList<Shape>();
	private ArrayList<Shape> littleShapes = new ArrayList<Shape>();
	private Shape CheckShape;

	private String controlPointState = "down";

	public PanelA() {
		label = new JLabel("");
		setBackground(Color.YELLOW);

		addMouseListener(new MyMouseListener());
		addMouseMotionListener(new MyMouseListener());
		setLayout(null);
		label.setSize(50, 20);
		label.setLocation(30, 30);
	}

	class MyMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			controlPointState = "";
			start = e.getPoint();
			if (shapes == null) {
				return;
			}
			for (int i = 0; i < shapes.size(); i++) {
				if (shapes.get(i) instanceof Rect) {
					Rect rect = (Rect) shapes.get(i);
					if ((start.x >= rect.x - 2 && start.x <= rect.x + 2)
							&& (start.y >= rect.y - 2 && start.y <= rect.y + 2)) {
						controlPointState = "up";
					} else if ((start.x >= rect.x + rect.width - 2 && start.x <= rect.x + rect.width + 2)
							&& (start.y >= rect.y + rect.height - 2 && start.y <= rect.y + rect.height + 2)) {
						controlPointState = "down";
					} else if ((start.x >= rect.x && start.x <= rect.x + rect.width)
							&& (start.y >= rect.y && start.y <= rect.y + rect.height)) {
						CheckShape = shapes.get(i);
						ControlBox();
						repaint();
					}
				} else if (shapes.get(i) instanceof Elipse) {
					Elipse elipse = (Elipse) shapes.get(i);
					if ((start.x >= elipse.x - 2 && start.x <= elipse.x + 2)
							&& (start.y >= elipse.y - 2 && start.y <= elipse.y + 2)) {
						controlPointState = "up";
					} else if ((start.x >= elipse.x + elipse.width - 2 && start.x <= elipse.x + elipse.width + 2)
							&& (start.y >= elipse.y + elipse.height - 2 && start.y <= elipse.y + elipse.height + 2)) {
						controlPointState = "down";
					} else if ((start.x >= elipse.x && start.x <= elipse.x + elipse.width)
							&& (start.y > elipse.y && start.y <= elipse.y + elipse.height)) {
						CheckShape = shapes.get(i);
						ControlBox();
						repaint();
					}
				} else if (shapes.get(i) instanceof Line) {
					Line line = (Line) shapes.get(i);
					if ((start.x >= line.x - 2 && start.x <= line.x + 2)
							&& (start.y >= line.y - 2 && start.y <= line.y + 2)) {
						controlPointState = "up";
					} else if ((start.x >= line.endX - 2 && start.x <= line.endX + 2)
							&& (start.y >= line.endY - 2 && start.y <= line.endY + 2)) {
						controlPointState = "down";
					} else if ((start.x >= line.x && start.x <= line.endX)
							&& (start.y > line.y && start.y <= line.endY)) {
						CheckShape = shapes.get(i);
						ControlBox();
						repaint();
					} else if ((start.x >= line.x && start.x <= line.endX)
							&& (start.y <= line.y && start.y >= line.endY)) {
						CheckShape = shapes.get(i);
						ControlBox();
						repaint();
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDragged(e);
			end = e.getPoint();
			Dragged = true;
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseReleased(e);
			end = e.getPoint();
			Dragged = false;

			int x = Math.min(start.x, end.x);
			int y = Math.min(start.y, end.y);
			int width = Math.abs(start.x - end.x);
			int height = Math.abs(start.y - end.y);
			Shape s = null;

			if (littleShapes.size() < 2) {
				Check = false;
			}

			// 버튼의 설정대로 도형 생성, 도형이 안겹치면 false 값이 리턴 되기 때문에 !을 사용하여 true로 만들어서 도형이 안겹칠때 객체 생성
			if (label.getText().equals("사각") && !containXY() && !Check)
				s = new Rect(x, y, width, height);
			else if (label.getText().equals("타원") && !containXY() && !Check)
				s = new Elipse(x, y, width, height);
			else if (label.getText().equals("직선") && !containXY() && !Check)
				s = new Line(start.x, start.y, end.x, end.y);

			// 선택한 도형 복사, CheckShape가 Rect이면 Rect로 객체를 생성하여 shapes 객체 배열에 추가.
			if (label.getText().equals("복사") && CheckShape instanceof Rect) {
				Rect rect = (Rect) CheckShape;
				shapes.add(new Rect(rect.x + 10, rect.y + 10, rect.width, rect.height));
				littleShapes.removeAll(littleShapes);
				repaint();
			} else if (label.getText().equals("복사") && CheckShape instanceof Elipse) {
				Elipse elipse = (Elipse) CheckShape;
				shapes.add(new Elipse(elipse.x + 10, elipse.y + 10, elipse.width, elipse.height));
				littleShapes.removeAll(littleShapes);
				repaint();
			} else if (label.getText().equals("복사") && CheckShape instanceof Line) {
				Line line = (Line) CheckShape;
				shapes.add(new Line(line.x + 10, line.y, line.endX + 10, line.endY));
				littleShapes.removeAll(littleShapes);
				repaint();
			}

			// 도형 삭제, 도형이 포함되어있고 CheckShape와 shapes객체 배열의 어느 위치에 있는것과 같다면 그 위치에 있는 도형을 삭제
			for (int i = 0; i < shapes.size(); i++) {
				if (label.getText().equals("삭제") && CheckShape == shapes.get(i)) {
					shapes.remove(i);
					littleShapes.removeAll(littleShapes);
					repaint();
				}
			}
			if (s == null)
				return;
			shapes.add(s);
		}

	}

	void ControlBox() {
		Shape start, end; // start는 왼쪽위 모서리 부분, end는 오른쪽 아래 모서리 부분
		if (CheckShape instanceof Rect) {
			Rect rect = (Rect) CheckShape;
			start = new Rect(rect.x - 2, rect.y - 2, 4, 4);
			end = new Rect(rect.x - 2 + rect.width, rect.y - 2 + rect.height, 4, 4);
			littleShapes.removeAll(littleShapes);
			littleShapes.add(start);
			littleShapes.add(end);
		} else if (CheckShape instanceof Elipse) {
			Elipse elipse = (Elipse) CheckShape;
			start = new Rect(elipse.x - 2, elipse.y - 2, 4, 4);
			end = new Rect(elipse.x - 2 + elipse.width, elipse.y - 2 + elipse.height, 4, 4);
			littleShapes.removeAll(littleShapes);
			littleShapes.add(start);
			littleShapes.add(end);
		} else if (CheckShape instanceof Line) {
			Line line = (Line) CheckShape;
			start = new Rect(line.x - 2, line.y - 2, 4, 4);
			end = new Rect(line.endX - 2, line.endY - 2, 4, 4);
			littleShapes.removeAll(littleShapes);
			littleShapes.add(start);
			littleShapes.add(end);
		}
	}

	// 도형이 겹치는지 여부 return 값이 true면 겹치는거고 false면 안겹침
	boolean containXY() {
		if (shapes != null) {
			for (int i = 0; i < shapes.size(); i++) {
				if (shapes.get(i) instanceof Rect) {
					Rect rect = (Rect) shapes.get(i);
					if ((start.x >= rect.x && start.x <= rect.x + rect.width)
							&& (start.y > rect.y && start.y <= rect.y + rect.height)) {
						return true;
					}
				} else if (shapes.get(i) instanceof Elipse) {
					Elipse elipse = (Elipse) shapes.get(i);
					if ((start.x >= elipse.x && start.x <= elipse.x + elipse.width)
							&& (start.y > elipse.y && start.y <= elipse.y + elipse.height)) {
						return true;
					}
				} else if (shapes.get(i) instanceof Line) {
					Line line = (Line) shapes.get(i);
					if ((start.x >= line.x && start.x <= line.endX) && (start.y >= line.y && start.y <= line.endY)) {
						return true;
					} else if ((start.x >= line.x && start.x <= line.endX)
							&& (start.y <= line.y && start.y >= line.endY)) {
						return true;
					}
				}
			}
		}
		// 도형이 포함되어 있지 않다면 false를 리턴하고 littleShapes 배열에 있는 ControlBox 객체들을 모두 지운다.
		littleShapes.removeAll(littleShapes);
		repaint();
		return false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (start == null)
			return;
		g.setColor(Color.BLUE);
		int x = Math.min(start.x, end.x);
		int y = Math.min(start.y, end.y);
		int width = Math.abs(start.x - end.x);
		int height = Math.abs(start.y - end.y);

		// 드래그 하면서 도형이 그려지는게 보이는게 하는곳 도형이 체크되어있으면 드래깅하면서 도형이 보여지면 안됨
		if (label.getText().equals("타원") && Dragged && !Check) {
			g.drawOval(x, y, width, height);
		}
		else if (label.getText().equals("사각") && Dragged && !Check) {
			g.drawRect(x, y, width, height);
		}
		else if (label.getText().equals("직선") && Dragged && !Check) {
			g.drawLine(start.x, start.y, end.x, end.y);
		}

		// 드래깅하고 있고 선택한 도형에 타입과 선택한 도형이 있다면 도형이 움직이고 만약 controlPoint를 누르면 그에 따라 도형의 크기 변환
		if (CheckShape instanceof Rect && littleShapes.size() == 2 && Dragged) {
			Rect rect = (Rect) CheckShape;
			if (controlPointState == "") {
				rect.x = end.x;
				rect.y = end.y;
			} else if (controlPointState == "up") {
				rect.width = Math.abs(rect.x + rect.width - end.x);
				rect.height = Math.abs(rect.y + rect.height - end.y);
				rect.x = end.x;
				rect.y = end.y;
			} else if (controlPointState == "down") {
				rect.width = Math.abs(rect.x - end.x);
				rect.height = Math.abs(rect.y - end.y);
			}
			ControlBox();
			Check = true;
		}
		else if (CheckShape instanceof Elipse && littleShapes.size() == 2 && Dragged) {
			Elipse elipse = (Elipse) CheckShape;
			if (controlPointState == "") {
				elipse.x = end.x;
				elipse.y = end.y;
			} else if (controlPointState == "up") {
				elipse.width = Math.abs(elipse.x + elipse.width - end.x);
				elipse.height = Math.abs(elipse.y + elipse.height - end.y);
				elipse.x = end.x;
				elipse.y = end.y;
			} else if (controlPointState == "down") {
				elipse.width = Math.abs(elipse.x - end.x);
				elipse.height = Math.abs(elipse.y - end.y);
			}
			ControlBox();
			Check = true;
		}
		else if (CheckShape instanceof Line && littleShapes.size() == 2 && Dragged) {
			Line line = (Line) CheckShape;
			int xpoint = line.x;
			int ypoint = line.y;
			if (controlPointState == "") {
				line.x = end.x;
				line.y = end.y;
				line.endX += (end.x - xpoint);
				line.endY += (end.y - ypoint);
			} else if (controlPointState == "up") {
				line.x = end.x;
				line.y = end.y;
			} else if (controlPointState == "down") {
				line.endX = end.x;
				line.endY = end.y;
			}
			ControlBox();
			Check = true;
		}

		// shapes 객체에 있는 모든 도형 그리기
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(g);
		}
		// control box 그리기
		g.setColor(Color.BLACK);
		for (int i = 0; i < littleShapes.size(); i++) {
			littleShapes.get(i).draw(g);
		}
	}
}
