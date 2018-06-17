import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Tile extends JComponent
{

	private static final long serialVersionUID = 1L;
	private static int tileSize = 80;
	private static int[] id = {0,1,2};
	private static BufferedImage[] type = new BufferedImage[3];

	public Tile()
	{
		try 
		{
			SpriteSheet sheet = new SpriteSheet(ImageIO.read(new File("res/tile_set.png")));
			
			for (int i = 0; i<3 ; i++)
				Tile.type[i] = sheet.crop(id[i],tileSize,tileSize);
		}
		catch(Exception e)
		{
			
		}
	}
	public static BufferedImage getType(int index)
	{
		return type[index];
	}
	
	public static int getTileSize()
	{
		return tileSize;
	}
	public static void setId(int index, int value)
	{
		id[index] = value;
	}
	public static int getId(int index)
	{
		return id[index];
	}



	
	
}
