import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class Block extends JComponent
{
	private static final long serialVersionUID = 1L;
	private int id = -1;
	
	public Block(Rectangle size, int id)
	{
		setBounds(size);
		this.id = id;
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void render(Graphics g,int x,int y, int id)
	{
			g.drawImage(Tile.getType(id),x*Tile.getTileSize(),y*Tile.getTileSize() ,null);
	}
}


