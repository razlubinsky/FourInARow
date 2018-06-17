import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class Board  extends Applet implements Runnable, MouseListener
{
	private int ind_x = 7, ind_y = 6;
	private static final long serialVersionUID = 1L;
	private boolean isFirst = true;
	private static Dimension size = new Dimension(1130,970);
	private static String name = "Four In a Row";
	private Image screen;
	private Block[][] block = new Block[ind_x][ind_y];
	 
	public Board()
	{
		setPreferredSize(size);
		addMouseListener(this);
		
		JFrame frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setTitle(name);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		for (int x=0; x<ind_x;x++)
		{
			for(int y=0;y<ind_y;y++)
			{
				block[x][y] = new Block(new Rectangle(x * Tile.getTileSize(), y * Tile.getTileSize(), Tile.getTileSize(), Tile.getTileSize()), Tile.getId(0));
			}
		}
		generateLevel();
		
	}
	//in case of victory flash the screen
	public void victory()
	{
		int number = 1000;
		generateLevel();
		while(number != 0)
		{
			for (int x = 0 ; x < ind_x ; x++)
			{
				for (int y=0 ; y< ind_y; y++)
				{
					if (block[x][y].getId() == Tile.getId(1))
						block[x][y].setId(Tile.getId(2));
					else if (block[x][y].getId() == Tile.getId(2))
						block[x][y].setId(Tile.getId(0));
					else if (block[x][y].getId() == Tile.getId(0))
						block[x][y].setId(Tile.getId(1));
				}
			}
			this.render();
			number--;
		}
		generateLevel();
		this.render();
	}
	//checking for 4 in a row - left to right angle
	public boolean checkSecondAngle(int x, int y)
	{
		int counter = 0;
		if (x-3 > 0 && y + 3 < ind_y)
		{
				for (int i = 1; i< 4; i++)
				{
					if (block[x-i][y+i].getId() == block[x][y].getId())
						counter++;
				}
				if (counter == 3)
					return true;
				counter = 0;
		}
		
		if(x - 2 > 0 && y + 2 < ind_y && x+1 <ind_x && y-1 > 0)
		{
			for (int i = 1; i< 3; i++)
			{
				if (block[x-i][y+i].getId() == block[x][y].getId())
					counter++;
			}
			if (block[x+1][y-1].getId() == block[x][y].getId())
				counter++;			
			if (counter == 3)
				return true;
			counter = 0;
		}
		if(x - 1 > 0 && y + 1 <ind_y && x+2 <ind_x && y-2 > 0)
		{
			for (int i = 1; i< 3; i++)
			{
				if (block[x+i][y-i].getId() == block[x][y].getId())
					counter++;
			}
			if (block[x-1][y+1].getId() == block[x][y].getId())
				counter++;			
			if (counter == 3)
				return true;
			counter = 0;
		}
		if(x+3 <ind_x && y-3 > 0)
		{
			for (int i = 1; i< 4; i++)
			{
				if (block[x+i][y-i].getId() == block[x][y].getId())
					counter++;
			}
			if (counter == 3)
				return true;
			counter = 0;
		}
		

		return false;
	}
	//checking for 4 in a row - right to left angle
	public boolean checkFirstAngle(int x, int y)
	{
		
		int counter = 0;
		if (x-3 > 0 && y - 3 > 0)
		{
				for (int i = 1; i< 4; i++)
				{
					if (block[x-i][y-i].getId() == block[x][y].getId())
						counter++;
				}
				if (counter == 3)
					return true;
				counter = 0;
		}
		if(x - 2 > 0 && y - 2 > 0 && x+1 <ind_x && y+1 < ind_y)
		{
			for (int i = 1; i< 3; i++)
			{
				if (block[x-i][y-i].getId() == block[x][y].getId())
					counter++;
			}
			if (block[x+1][y+1].getId() == block[x][y].getId())
				counter++;			
			if (counter == 3)
				return true;
			counter = 0;
		}
		if(x - 1 > 0 && y - 1 > 0 && x+2 <ind_x && y+2 < ind_y)
		{
			for (int i = 1; i< 3; i++)
			{
				if (block[x+i][y+i].getId() == block[x][y].getId())
					counter++;
			}
			if (block[x-1][y-1].getId() == block[x][y].getId())
				counter++;			
			if (counter == 3)
				return true;
			counter = 0;
		}
		if(x+3 <ind_x && y+3 < ind_y)
		{
			for (int i = 1; i< 4; i++)
			{
				if (block[x+i][y+i].getId() == block[x][y].getId())
					counter++;
			}
			if (counter == 3)
				return true;
			counter = 0;
		}
				
		return false;

	}
	//checks if there is 4 in a row on one of the angles (left to right and the other way)
	public int checkAngleWinner()
	{
		for (int x = 0; x<ind_x; x++)
		{
			for (int y = 0; y < ind_y; y++)
			{
				if (block[x][y].getId() == Tile.getId(1))
				{
					if (checkFirstAngle(x, y) || checkSecondAngle(x, y))
						return 1;
				}
				else if (block[x][y].getId() == Tile.getId(2))
				{
					if (checkFirstAngle(x, y) || checkSecondAngle(x, y))
						return 2;
				}
			}
		}
		return 0;
	}
	//checks if there is 4 in a row - in a row
	public int checkRowWinner()
	{
		int counter1 = 0, counter2 = 0;
		for (int y = 0; y<ind_y; y++)
		{
			for (int x = 0; x < ind_x; x++)
			{
				if (block[x][y].getId() == Tile.getId(1))
				{
					counter1++;
					if (counter1 == 4)
						return 1;
					counter2 = 0;
				}
				else if (block[x][y].getId() == Tile.getId(2))
				{
					counter2++;
					if (counter2 == 4)
						return 2;
					counter1 = 0;
				}
				else
				{
					counter1 = 0;
					counter2 = 0;
				}
			}
			if (counter1 >= 4)
				return 1;
			if (counter2 >= 4)
				return 2;
		}
		return 0;
	}
	//checks if there is 4 in a row - in a column
	public int checkColWinner()
	{
		int counter1 = 0, counter2 = 0;
		for (int x = 0; x<ind_x; x++)
		{
			for (int y = 0; y < ind_y; y++)
			{
				if (block[x][y].getId() == Tile.getId(1))
				{
					counter1++;
					if (counter1 == 4)
						return 1;
					counter2 = 0;
				}
				else if (block[x][y].getId() == Tile.getId(2))
				{
					counter2++;
					if (counter2 == 4)
						return 2;
					counter1 = 0;
				}
				else
				{
					counter1 = 0;
					counter2 = 0;
				}
			}
			if (counter1 >= 4)
				return 1;
			if (counter2 >= 4)
				return 2;
		}
		
		return 0;
	}
	public int checkWinner()
	{
		int result = 0;
		result = checkRowWinner();
		if (result != 0)
			return result;
		result = checkColWinner();
		if (result != 0)
			return result;
		result = checkAngleWinner();
		if (result != 0)
			return result;
		return result;
	}
	public void addCircle(int x, Graphics g)
	{
		int y = ind_y-1;
		while (y >= 0 && block[x][y].getId() != Tile.getId(0))
			y--;
		if (y >= 0)
		{
			if (isFirst)
				block[x][y].setId(Tile.getId(1));
			else 
				block[x][y].setId(Tile.getId(2));
			this.render();
		}
	}

	//start the board with blank spots
	public void generateLevel()
	{
		for (int x=0; x<block.length;x++)
		{
			for(int y=0;y<block[0].length;y++)
			{
				{
					block[x][y].setId(Tile.getId(0));
				}
			}
		}
	}
	
	

	public void render(Graphics g)
	{
		for (int x=0; x<block.length;x++)
		{
			for(int y=0;y<block[0].length;y++)
			{
				block[x][y].render(g,x,y,block[x][y].getId()); 
			}
		}
	}
	
	public void render()
	{
		Graphics g = screen.getGraphics();
		this.render(g);
		
		g = getGraphics();
		g.drawImage(screen, 5, 5, size.width*2 , size.height *2, 0, 0, size.width , size.height, null);
		//g.dispose();
	}
	public void start()
	{
		//define objects.
		new Tile(); 
		new Thread(this).start();
	}
	public void run() 
	{
		screen = createVolatileImage(size.width, size.height);
		render();
	}

	public static void main(String args[])
	{
		Board board = new Board();
		board.start();
	}
	
	//divide the board for columns by x,y
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		Graphics g = screen.getGraphics();
		int result = 0;
		if (e.getX() >=0 && e.getX()<=159)
			addCircle(0, g);
		if (e.getX() >=160 && e.getX()<=319)
			addCircle(1, g);
		if (e.getX() >=320 && e.getX()<=479)
			addCircle(2, g);
		if (e.getX() >=480 && e.getX()<=639)
			addCircle(3, g);
		if (e.getX() >=640 && e.getX()<=799)
			addCircle(4, g);
		if (e.getX() >=800 && e.getX()<=959)
			addCircle(5, g);
		if (e.getX() >=960 && e.getX()<=1119)
			addCircle(6, g);
		result = checkWinner();
		if (result == 1)
		{
			System.out.println("First player won");
			victory();
		}
		if (result == 2)
		{
			System.out.println("Second player won");
			victory();
		}
		isFirst = !isFirst;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
