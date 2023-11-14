import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable{
	int x;
	int y;
	
	public Shape(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public abstract void draw(Graphics g);
}

class Elipse extends Shape{
	int width;
	int height;

	public Elipse(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		g.drawOval(x, y, width, height);
	}

}

class Rect extends Shape{
	int width;
	int height;

	public Rect(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
	}
}

class Line extends Shape{
	int endX;
	int endY;

	public Line(int startX, int startY, int endX, int endY) {
		super(startX, startY);
		this.endX = endX;
		this.endY = endY;
	}

	@Override
	public void draw(Graphics g) {
		g.drawLine(x, y, endX, endY);
	}
}