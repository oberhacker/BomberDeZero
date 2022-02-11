package xnetcom.bomber.enemigos;

import java.util.ArrayList;

import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.SpritePoolGlobo;

public class AlmacenEnemigos {
	
	
	
	
	public enum TipoEnemigo {
		GLOBO, MOCO, MONEDA, FANTASMA, GOTA_NARANJA, GLOBO_AZUL, MOCO_ROJO, MONEDA_MARRON, GOTA_ROJA,
	}
	
	public ArrayList<TipoEnemigo> enemigosIniciales;
	public ArrayList<EnemigoBase> almacen;
	private BomberGame context;
	public SpriteGroup groupGlobo;
	
	
	SpritePoolGlobo spritePoolGlobo;
	
	public TiledTextureRegion globoTR;
	
	public AlmacenEnemigos(BomberGame context) {
		this.context = context;
		almacen = new ArrayList<EnemigoBase>();
		spritePoolGlobo= new SpritePoolGlobo(context);
	}
	
	
	BitmapTextureAtlas globo_naranja_ani;
	public void carga(){
		globo_naranja_ani = new BitmapTextureAtlas(context.getTextureManager(),1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		globo_naranja_ani.load();
		this.globoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(globo_naranja_ani, context, "gfx/globo_naranja_ani.png", 0,0,13, 2);
		


	}
	
	
	public boolean ini=false;
	public void inicializaGrupos(){
		if (!ini){
			ini=true;		
			groupGlobo= new SpriteGroup(globo_naranja_ani, 100, context.getVertexBufferObjectManager());
			groupGlobo.setOffsetCenter(0, 0);
			groupGlobo.setPosition(0, 0);
			groupGlobo.setZIndex(Constantes.ZINDEX_ENEMIGO);
			context.escenaJuego.scene.attachChild(groupGlobo);		
			context.escenaJuego.scene.sortChildren();
		}
	}
	
	
	/**
	 * crea un enemigo lo mete en el array de enemigos vivos y lo añade a la escena
	 */
	
	public void creaEnemigo( TipoEnemigo tipoEnemigo,int fila, int columna){
		//completar logica
		switch (tipoEnemigo) {
		case GLOBO:
			EnemigoBase enemigo=spritePoolGlobo.obtainPoolItem();
			enemigo.inicia(columna, fila);
			almacen.add(enemigo);
			break;

		default:
			break;
		}
		
	}
	public void creaEnemigoInicial( TipoEnemigo tipoEnemigo,int fila, int columna){
		inicializaGrupos();
		enemigosIniciales.add(tipoEnemigo);
		creaEnemigo(tipoEnemigo, fila, columna);
	}
	
	
	public void mataEnemigos(final ArrayList<Coordenadas> coordenadas) {

		new Thread() {
			public void run() {
				try {
					for (int i = 0; i < 3; i++) {
						synchronized (almacen) {
							ArrayList<EnemigoBase> eliminados = new ArrayList<EnemigoBase>();
							for (EnemigoBase enemigo : almacen) {
								if (enemigo.matarPorCoordenadas(coordenadas)) {
									eliminados.add(enemigo);
								}
							}
							for (EnemigoBase eliminado : eliminados) {
								almacen.remove(eliminado);
								dettachDePool(eliminado);
							}
						}
						sleep(200);

					}
				} catch (Exception e) {

				}

			};
		}.start();


	}

	
	
	public void dettachDePool(EnemigoBase eliminado){
		switch (eliminado.tipoEnemigo) {
		case GLOBO:
			spritePoolGlobo.recyclePoolItem((EnemigoGlobo)eliminado);					
			break;

		default:
			break;
		}
	}

	public void pararTodosEnemigo(){
		synchronized (almacen) {
			for (EnemigoBase enemigo : almacen) {
				enemigo.detener();
			}
		}

	}
	
	public void eliminaTodosEnemigos(){
		synchronized (almacen) {
			for (EnemigoBase enemigo : almacen) {
				enemigo.detach();
				dettachDePool(enemigo);
			}
			almacen.clear();			
		}
	}

	public void reiniciaEnemigos(){		
		 for (TipoEnemigo enemigo : enemigosIniciales) {
			 creaEnemigo(enemigo, 5, 4);			
		}
	}

	public void inicializaAlmacen() {
		eliminaTodosEnemigos();		
		enemigosIniciales= new ArrayList<AlmacenEnemigos.TipoEnemigo>();
	}

}
