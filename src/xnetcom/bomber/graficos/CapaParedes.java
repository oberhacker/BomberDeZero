package xnetcom.bomber.graficos;

import java.util.ArrayList;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.SpritePoolParedes;

public class CapaParedes {
	
	public BomberGame context;
	public SpritePoolParedes spritePoolArriba;
	public SpriteGroup spriteGroupArriba;
	
	public SpritePoolParedes spritePoolAbajo;
	public SpriteGroup spriteGroupAbajo;
	
	public ArrayList<TrozoPared> listaMuros;
	
	public CapaParedes(final BomberGame context){
		this.context=context;
		this.listaMuros=new ArrayList<TrozoPared>();
	}
	
	
	
	public void carga(){
			BitmapTextureAtlas btaParedAbajo = new BitmapTextureAtlas(context.getTextureManager(),1024, 265, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
			BitmapTextureAtlas btaParedArriba = new BitmapTextureAtlas(context.getTextureManager(),1024, 265, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
			TiledTextureRegion textureArriba=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedArriba, context, "gfx/muros74_v2_arriba.png",0,0,4, 1);
			TiledTextureRegion textureAbajo=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedAbajo, context, "gfx/muros74_v2_abajo.png",0,0,4, 1);
			btaParedAbajo.load();		
			btaParedArriba.load();			
			
			
			spritePoolArriba = new SpritePoolParedes(textureArriba, context);		
			spriteGroupArriba = new SpriteGroup(btaParedArriba,220,context.getVertexBufferObjectManager());		
			spriteGroupArriba.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA);
			spriteGroupArriba.setScaleCenter(0, 0);
			// no funciona el offset center asi se centra
			spriteGroupArriba.setPosition(-1*Constantes.TILE_WIDTH, 1*Constantes.TILE_HEIGHT);		
			
			
			
			
			spritePoolAbajo = new SpritePoolParedes(textureAbajo, context);		
			spriteGroupAbajo = new SpriteGroup(btaParedAbajo,220,context.getVertexBufferObjectManager());		
			spriteGroupAbajo.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ABAJO);
			spriteGroupAbajo.setScaleCenter(0, 0);
			// no funciona el offset center asi se centra
			spriteGroupAbajo.setPosition(-1*Constantes.TILE_WIDTH, 1*Constantes.TILE_HEIGHT);	
			

	}
	
	
	
	public void ponPared(int columna,int fila){
		TiledSprite spriteArriba = spritePoolArriba.obtainPoolItem();		
		TiledSprite spriteAbajo = spritePoolAbajo.obtainPoolItem();		
		Coordenadas coodenadas= new Coordenadas(columna, fila);
		
		spriteArriba.setPosition(coodenadas.getX(), coodenadas.getY());		
		spriteGroupArriba.attachChild(spriteArriba);
		
		spriteAbajo.setPosition(coodenadas.getX(), coodenadas.getY());		
		spriteGroupAbajo.attachChild(spriteAbajo);		
		TrozoPared trozo= new TrozoPared(spriteArriba, spriteAbajo, coodenadas);
		listaMuros.add(trozo);
		
	}
	

	
	
	public void onSceneCreated(){
		context.escenaJuego.scene.attachChild(spriteGroupAbajo);
		context.escenaJuego.scene.attachChild(spriteGroupArriba);
	}
	
	
	
	public void recalculaPared(){
		
		
	}
	
	public class TrozoPared{
		
		public TiledSprite trozoArriba;
		public TiledSprite trozoAbajo;
		public Coordenadas coodenadas;
		
		public TrozoPared(TiledSprite trozoArriba, TiledSprite trozoAbajo, Coordenadas coodenadas){
			this.trozoArriba=trozoArriba;
			this.trozoAbajo=trozoAbajo;
			this.coodenadas=coodenadas;
			
		}
		
	}

	

}
