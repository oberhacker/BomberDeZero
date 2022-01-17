package xnetcom.bomber;

import java.io.IOException;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class BomberMan {
	
	public BomberGame context;
	
	public ITexture mPlayerTexture;
	public TiledTextureRegion mPlayerTextureRegion;
	
	public Rectangle currentTileRectangle;
	
	public AnimatedSprite player;

	 
	public BomberMan(BomberGame context){
		this.context=context;
	}
	
	public AnimatedSprite getSprite(){
		return player;
	}
	
	public void carga() throws IOException{
		this.mPlayerTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/player.png", TextureOptions.DEFAULT);
		this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3, 4);
		this.mPlayerTexture.load();		
		this.player = new AnimatedSprite(20, context.CAMERA_HEIGHT-100, this.mPlayerTextureRegion, context.getVertexBufferObjectManager());
		this.player.setOffsetCenter(0, 0);
	}
	
	
	public void onCreateScene(Scene scene){
		
		currentTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth(), context.escenaJuego.mTMXTiledMap.getTileHeight(), context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		currentTileRectangle.setScaleX(context.factorForma);
		currentTileRectangle.setPosition(context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileX(2), context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileY(2));		
		currentTileRectangle.setPosition(2*64*context.factorForma, currentTileRectangle.getY());
		scene.attachChild(currentTileRectangle);
		scene.attachChild(player);
		
	}
	

}
