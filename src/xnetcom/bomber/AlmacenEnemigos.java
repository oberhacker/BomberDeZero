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
	
	
	public SpriteGroup group;
	
	public enum TipoEnemigo {
		GLOBO, MOCO, MONEDA, FANTASMA, GOTA_NARANJA, GLOBO_AZUL, MOCO_ROJO, MONEDA_MARRON, GOTA_ROJA,
	}
	
	private ArrayList<EnemigoBase> almacen;
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
	
	
	
	/**
	 * crea un enemigo lo mete en el array de enemigos vivos y lo a�ade a la escena
	 */
	public void creaEnemigo( TipoEnemigo tipoEnemigo,int fila, int columna){
		
		if (group==null){
			group= new SpriteGroup(globo_naranja_ani, 100, context.getVertexBufferObjectManager());
			group.setOffsetCenter(0, 0);
			group.setPosition(0, 0);
			group.setZIndex(Constantes.ZINDEX_CAPA_TECHO_PIEDRAS);
			context.escenaJuego.scene.sortChildren();
			context.escenaJuego.scene.attachChild(group);
			

		}

		
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


	
	public void reseteaEnemigos(){
		
	}

}
