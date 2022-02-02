package xnetcom.bomber.graficos;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.sprite.AnimatedSprite;
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
	
	
	private Iterator<AnimatedSprite> itr;
	private ArrayList<AnimatedSprite> almacenExplosiones;
	
	public CapaParedes(final BomberGame context){
		this.context=context;
		this.listaMuros=new ArrayList<TrozoPared>();
		
	}

	public void carga() {

		BitmapTextureAtlas explosionBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 512, TextureOptions.NEAREST_PREMULTIPLYALPHA);
		explosionBTA.load();
		
		
		almacenExplosiones = new ArrayList<AnimatedSprite>();
		for (int i = 0; i < 10; i++) {
			AnimatedSprite ans =new AnimatedSprite(0, 0, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionBTA, context, "gfx/pared_explo.png", 0, 0, 2, 2),context.getVertexBufferObjectManager());
			ans.setScale(0.5f);
			ans.setScaleCenter(0, 0);
			almacenExplosiones.add(ans);
		}	
		itr= almacenExplosiones.iterator();

		BitmapTextureAtlas btaParedAbajo = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas btaParedArriba = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion textureArriba = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedArriba, context, "gfx/muroswais74_bigV8_arriba.png", 0, 0, 4, 1);
		TiledTextureRegion textureAbajo = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedAbajo, context, "gfx/muroswais74_bigV8_abajo.png", 0, 0, 4, 1);
		btaParedAbajo.load();
		btaParedArriba.load();

		spritePoolArriba = new SpritePoolParedes(textureArriba, context);
		spriteGroupArriba = new SpriteGroup(btaParedArriba, 220, context.getVertexBufferObjectManager());
		spriteGroupArriba.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA);
		spriteGroupArriba.setScaleCenter(0, 0);
		// no funciona el offset center asi se centra
		spriteGroupArriba.setPosition(-1 * Constantes.TILE_WIDTH, 1 * Constantes.TILE_HEIGHT);

		spritePoolAbajo = new SpritePoolParedes(textureAbajo, context);
		spriteGroupAbajo = new SpriteGroup(btaParedAbajo, 220, context.getVertexBufferObjectManager());
		spriteGroupAbajo.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ABAJO);
		spriteGroupAbajo.setScaleCenter(0, 0);
		// no funciona el offset center asi se centra
		spriteGroupAbajo.setPosition(-1 * Constantes.TILE_WIDTH, 1 * Constantes.TILE_HEIGHT);

	}
	

	
	public void ponPared(int columna,int fila){
		TiledSprite spriteArriba = spritePoolArriba.obtainPoolItem();		
		TiledSprite spriteAbajo = spritePoolAbajo.obtainPoolItem();		
//		spriteArriba.setVisible(false);
		
//		spriteArriba.setScaleX(1.05625f);
//		spriteAbajo.setScaleX(1.05625f);
		Coordenadas coodenadas= new Coordenadas(columna, fila);
		
		spriteArriba.setPosition(coodenadas.getX(), coodenadas.getY()+3);		
		spriteGroupArriba.attachChild(spriteArriba);
		
		spriteAbajo.setPosition(coodenadas.getX(), coodenadas.getY()+3);		
		spriteGroupAbajo.attachChild(spriteAbajo);		
		TrozoPared trozo= new TrozoPared(context,spriteArriba, spriteAbajo, coodenadas);
		listaMuros.add(trozo);
		context.escenaJuego.matriz.setValor(Matriz.PARED, fila, columna, null, trozo);		
		
	}
	
	
	
	public void onSceneCreated(){
//		spriteGroupArriba.setScaleX(Constantes.FARTOR_FORMA);
//		spriteGroupAbajo.setScaleX(Constantes.FARTOR_FORMA);
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
	
	
	public void recalculaTrozosLaterales(Coordenadas coordenada){
		Coordenadas coordenadaDerecha= new Coordenadas(coordenada.getColumna()+1, coordenada.getFila());
		Coordenadas coordenadaIzquierda= new Coordenadas(coordenada.getColumna()-1, coordenada.getFila());		
		recalculaTrozo(coordenadaDerecha);
		recalculaTrozo(coordenadaIzquierda);
	}
	
	public void recalculaTrozo(Coordenadas coordenada){
		Casilla[][] matriz = context.escenaJuego.matriz.getMatrizmuros();
		Casilla casilla = context.escenaJuego.matriz.getValor(coordenada.getFila(), coordenada.getColumna());	
		if (casilla.tipoCasilla==Matriz.PARED){
			// miramos si hay toro a la derecha
			 if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla==Matriz.PARED && matriz[coordenada.getFila()][coordenada.getColumna()-1].tipoCasilla==Matriz.PARED){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(CENTRO);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(CENTRO);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla==Matriz.PARED ){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(IZQUIERDA);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(IZQUIERDA);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()-1].tipoCasilla==Matriz.PARED ){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(DERECHA);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(DERECHA);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla!=Matriz.PARED){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(SOLO);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(SOLO);
			}
		}

		
	}
	
	
	public class TrozoPared{		
		
		public TiledSprite trozoArriba;
		public TiledSprite trozoAbajo;
		public Coordenadas coodenadas;
		public BomberGame context;
		
		
		public TrozoPared(BomberGame context, TiledSprite trozoArriba, TiledSprite trozoAbajo, Coordenadas coodenadas){
			this.trozoArriba=trozoArriba;
			this.trozoAbajo=trozoAbajo;
			this.coodenadas=coodenadas;
			this.context=context;
			
		}
		
		public void explota(){
			context.escenaJuego.matriz.setValor(Matriz.NADA, coodenadas.getFila(), coodenadas.getColumna(), null, null);
			
			trozoArriba.detachSelf();
			trozoAbajo.detachSelf();
			
			spritePoolAbajo.recyclePoolItem(trozoAbajo);
			spritePoolArriba.recyclePoolItem(trozoArriba);
			
			trozoAbajo.setVisible(false);
			trozoArriba.setVisible(false);
			
			
			context.capaParedes.recalculaTrozosLaterales(coodenadas);
		}
	}

	

}
