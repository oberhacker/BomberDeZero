package xnetcom.bomber;

import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class CapaParedes {
	
	public BomberGame context;
	
	private TiledTextureRegion texturePared;	
	private SpritePool spritePoolParedes;
	
	
	public CapaParedes(final BomberGame context){
		this.context=context;
	}
	
	
	
	public void carga(){
		BitmapTextureAtlas btaPared = new BitmapTextureAtlas(context.getTextureManager(),512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		this.texturePared=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaPared, context, "gfx/muros64_tiled.png",0,0,4, 0);
		btaPared.load();		
//		spritePool= new SpritePool(texturePared, context);
//		spritePool.
		
		SpriteGroup spriteGroup = new SpriteGroup(btaPared,220,context.getVertexBufferObjectManager());
//		spriteGroup.
		
		
	}
	

}
