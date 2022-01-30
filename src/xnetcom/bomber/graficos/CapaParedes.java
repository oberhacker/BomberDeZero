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
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.Matriz.Casilla;
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
		context.escenaJuego.matriz.setValor(Matriz.PARED, fila, columna, null, trozo);		
		
	}
	

	
	
	public void onSceneCreated(){
		context.escenaJuego.scene.attachChild(spriteGroupAbajo);
		context.escenaJuego.scene.attachChild(spriteGroupArriba);
	}
	
	
	public static int SOLO=0;
	public static int IZQUIERDA=1;
	public static int CENTRO=2;
	public static int DERECHA=3;
	
	public void recalculaPared(){
		Casilla[][] matriz = context.escenaJuego.matriz.getMatrizmuros();		
		
		
		for (int y = 2; y < matriz.length; y++) {
			for (int x = 2; x < matriz.length; x++) {
				Casilla casilla = matriz[y][x];
				int trozo = matriz[y][x].tipoCasilla;
				if (trozo==Matriz.PARED){
					// miramos si hay toro a la derecha
					 if (matriz[y][x+1].tipoCasilla==Matriz.PARED && matriz[y][x-1].tipoCasilla==Matriz.PARED){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(CENTRO);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(CENTRO);
					}else if (matriz[y][x+1].tipoCasilla==Matriz.PARED ){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(IZQUIERDA);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(IZQUIERDA);
					}else if (matriz[y][x-1].tipoCasilla==Matriz.PARED ){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(DERECHA);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(DERECHA);
					}else if (matriz[y][x+1].tipoCasilla!=Matriz.PARED){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(SOLO);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(SOLO);
					}
				}

			}				
		}
		
		
		
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
