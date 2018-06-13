import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Tile extends JComponent
{

	private static final long serialVersionUID = 1L;

	public static int tileSize = 80;
	
	public static int[] blank = {0,0};
	public static int[] player1 = {1,0};
	public static int[] player2 = {4,0};
	
	public static BufferedImage tile_blank;
	public static BufferedImage tile_player1;
	public static BufferedImage tile_player2;
	

	public Tile()
	{
		try 
		{
			SpriteSheet sheet = new SpriteSheet(ImageIO.read(new File("res/tile_set.png")));
			
			Tile.tile_blank = sheet.crop(blank,tileSize,tileSize);
			Tile.tile_player1 = sheet.crop(player1,tileSize,tileSize);
			Tile.tile_player2 = sheet.crop(player2,tileSize,tileSize);			
		}
		catch(Exception e)
		{
			
		}
	}



	
	
}
