import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

public class Block extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	public int[] id = {-1,-1};
	
	public Block(Rectangle size, int[] id)
	{
		setBounds(size);
		this.id = id;
	}
	
	public void render(Graphics g,int x,int y, int[] id)
	{
		if(id == Tile.blank)
			g.drawImage(Tile.tile_blank,x*Tile.tileSize,y*Tile.tileSize ,null);
		if(id == Tile.player1)
			g.drawImage(Tile.tile_player1,x*Tile.tileSize,y*Tile.tileSize ,null);
		if(id == Tile.player2)
			g.drawImage(Tile.tile_player2,x*Tile.tileSize,y*Tile.tileSize ,null);
	}
}


