package xnetcom.bomber;

import java.util.ArrayList;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.provider.ContactsContract.CommonDataKinds.Contactables;
import xnetcom.bomber.enemigos.EnemigoBase;
import xnetcom.bomber.enemigos.EnemigoGlobo;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;

public class AlmacenEnemigos {
	
	
	public SpriteGroup groupGlobo;
	
	public enum TipoEnemigo {
		GLOBO, MOCO, MONEDA, FANTASMA, GOTA_NARANJA, GLOBO_AZUL, MOCO_ROJO, MONEDA_MARRON, GOTA_ROJA,
	}
	
	public ArrayList<TipoEnemigo> enemigosIniciales;
	public ArrayList<EnemigoBase> almacen;
	private BomberGame context;
	
	
	public TiledTextureRegion globoTR;
	
	public AlmacenEnemigos(BomberGame context) {
		this.context = context;
		almacen = new ArrayList<EnemigoBase>();
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
			context.escenaJuego.scene.sortChildren();
			context.escenaJuego.scene.attachChild(groupGlobo);			
		}
	}
	
	
	/**
	 * crea un enemigo lo mete en el array de enemigos vivos y lo añade a la escena
	 */
	
	public void creaEnemigo( TipoEnemigo tipoEnemigo,int fila, int columna){
		//completar logica
		switch (tipoEnemigo) {
		case GLOBO:
			EnemigoBase enemigo= new EnemigoGlobo(context, columna, fila);
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
	
	
	public void mataEnemigos(final ArrayList<Coordenadas> coordenadas){
		context.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				synchronized (almacen) {
					ArrayList<EnemigoBase> eliminados = new ArrayList<EnemigoBase>();					
					for (EnemigoBase enemigo : almacen) {
						if (enemigo.matarPorCoordenadas(coordenadas)){
							eliminados.add(enemigo);
						}					
					}
					for (EnemigoBase eliminado : eliminados) {
						almacen.remove(eliminado);
					}				
					
				}

			}
		});

		
	}


	public void pararTodosEnemigo(){
		for (EnemigoBase enemigo : almacen) {
			enemigo.detener();
		}
	}
	
	public void eliminaTodosEnemigos(){
		synchronized (almacen) {
			for (EnemigoBase enemigo : almacen) {
				enemigo.detach();
			}
			almacen.clear();			
		}
	}

	public void reiniciaEnemigos(){
		
		
		 for (TipoEnemigo enemigo : enemigosIniciales) {
			 creaEnemigo(enemigo, 2, 15);			
		}
	}

	public void inicializaAlmacen() {
		eliminaTodosEnemigos();		
		enemigosIniciales= new ArrayList<AlmacenEnemigos.TipoEnemigo>();
	}

}
