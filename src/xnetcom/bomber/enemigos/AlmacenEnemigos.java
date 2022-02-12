package xnetcom.bomber.enemigos;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.SpritePoolFantasma;
import xnetcom.bomber.util.SpritePoolGlobo;
import xnetcom.bomber.util.SpritePoolGloboAzul;
import xnetcom.bomber.util.SpritePoolMoco;
import xnetcom.bomber.util.SpritePoolMoneda;

public class AlmacenEnemigos {
	
	
	protected Random generator;
	
	public enum TipoEnemigo {
		GLOBO, MOCO, MONEDA, FANTASMA, GOTA_NARANJA, GLOBO_AZUL, MOCO_ROJO, MONEDA_MARRON, GOTA_ROJA,
	}
	
	public ArrayList<TipoEnemigo> enemigosIniciales;
	public ArrayList<EnemigoBase> almacen;
	private BomberGame context;
	
	public SpriteGroup groupGlobo;	
	public SpritePoolGlobo spritePoolGlobo;
	
	public SpriteGroup groupGloboAzul;	
	public SpritePoolGloboAzul spritePoolGloboAzul;
	
	public SpriteGroup groupMoneda;	
	public SpritePoolMoneda spritePoolMoneda;
	
	public SpriteGroup groupFantasmaTransparencia;	
	public SpriteGroup groupFantasma;	
	public SpritePoolFantasma spritePoolFantasma;
	
	public SpriteGroup groupMocoTransparencia;	
	public SpriteGroup groupMoco;	
	public SpritePoolMoco spritePoolMoco;

	
	public TiledTextureRegion globoTR;
	public TiledTextureRegion fantasmaTR;
	
	public BitmapTextureAtlas globo_naranja_ani;
	public BitmapTextureAtlas fantasma_tile90;
	public BitmapTextureAtlas MocoBTA;
	public TiledTextureRegion MocoTR;
	public BitmapTextureAtlas globo_azul_ani;
	public TiledTextureRegion globoAzulTR;
	public BitmapTextureAtlas monedaBTA;
	public TiledTextureRegion monedaTR;
	
	public AlmacenEnemigos(BomberGame context) {
		this.context = context;
		generator= new Random();
		almacen = new ArrayList<EnemigoBase>();
		spritePoolGlobo= new SpritePoolGlobo(context);
		spritePoolGloboAzul= new SpritePoolGloboAzul(context);
		spritePoolFantasma=new SpritePoolFantasma(context);
		spritePoolMoco= new SpritePoolMoco(context);
		spritePoolMoneda= new SpritePoolMoneda(context);
		
	}
	

	

	
	
	public void carga(){
		this.globo_naranja_ani = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR);	
		this.globo_naranja_ani.load();
		this.globoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(globo_naranja_ani, context, "gfx/globo_naranja_ani.png", 0,0,13, 3);
		
		this.fantasma_tile90 = new BitmapTextureAtlas(context.getTextureManager(),512, 512, TextureOptions.BILINEAR);
		this.fantasma_tile90.load();
		this.fantasmaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fantasma_tile90, context, "gfx/fantasma_tile90.png",0,0,  5, 5);
		
		this.MocoBTA = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR);
		this.MocoBTA.load();
		this.MocoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(MocoBTA, context, "gfx/moco_tiled90.png",0,0,  6, 5);
		
		this.globo_azul_ani = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR);	
		this.globo_azul_ani.load();
		this.globoAzulTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(globo_azul_ani, context, "gfx/globo_azul_ani.png", 0,0,13, 3);
		
		
		this.monedaBTA = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.BILINEAR);
		this.monedaBTA.load();
		this.monedaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(monedaBTA, context, "gfx/moneda_tiled.png",0,0,  6, 3);


	}
	
	
	public boolean ini=false;
	public void inicializaGrupos(){
		if (!ini){
			ini=true;		
			groupGlobo= new SpriteGroup(globo_naranja_ani, 100, context.getVertexBufferObjectManager());
			groupGlobo.setOffsetCenter(0, 0);
			groupGlobo.setPosition(0, 0);
			groupGlobo.setZIndex(Constantes.ZINDEX_ENEMIGO);
			
			
			groupGloboAzul= new SpriteGroup(globo_azul_ani, 100, context.getVertexBufferObjectManager());
			groupGloboAzul.setOffsetCenter(0, 0);
			groupGloboAzul.setPosition(0, 0);
			groupGloboAzul.setZIndex(Constantes.ZINDEX_ENEMIGO);
			
			groupMoneda= new SpriteGroup(monedaBTA, 100, context.getVertexBufferObjectManager());
			groupMoneda.setOffsetCenter(0, 0);
			groupMoneda.setPosition(0, 0);
			groupMoneda.setZIndex(Constantes.ZINDEX_ENEMIGO);
			
			
			groupFantasma= new SpriteGroup(fantasma_tile90, 100, context.getVertexBufferObjectManager());
			groupFantasma.setOffsetCenter(0, 0);
			groupFantasma.setPosition(0, 0);
			groupFantasma.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ABAJO-1);

			
			groupFantasmaTransparencia= new SpriteGroup(fantasma_tile90, 100, context.getVertexBufferObjectManager());
			groupFantasmaTransparencia.setOffsetCenter(0, 0);
			groupFantasmaTransparencia.setPosition(0, 0);
			groupFantasmaTransparencia.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA+1);

			
			
			
			groupMoco= new SpriteGroup(MocoBTA, 100, context.getVertexBufferObjectManager());
			groupMoco.setOffsetCenter(0, 0);
			groupMoco.setPosition(0, 0);
			groupMoco.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ABAJO-1);

			
			groupMocoTransparencia= new SpriteGroup(MocoBTA, 100, context.getVertexBufferObjectManager());
			groupMocoTransparencia.setOffsetCenter(0, 0);
			groupMocoTransparencia.setPosition(0, 0);
			groupMocoTransparencia.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA+1);

			
			
			
			context.escenaJuego.scene.attachChild(groupMoneda);
			context.escenaJuego.scene.attachChild(groupGlobo);	
			context.escenaJuego.scene.attachChild(groupGloboAzul);	
			context.escenaJuego.scene.attachChild(groupFantasma);	
			context.escenaJuego.scene.attachChild(groupFantasmaTransparencia);		
			
			context.escenaJuego.scene.attachChild(groupMoco);	
			context.escenaJuego.scene.attachChild(groupMocoTransparencia);	
			
			context.escenaJuego.scene.sortChildren();
			
			
			
			
			
		}
	}
	
	
	/**
	 * crea un enemigo lo mete en el array de enemigos vivos y lo añade a la escena
	 */
	
	
	public void creaEnemigo( TipoEnemigo tipoEnemigo,int fila, int columna){
		//completar logica
		EnemigoBase enemigo;
//		Coordenadas coordenada=eligePosiciones();
		Coordenadas coordenada= new Coordenadas(columna, fila);
		switch (tipoEnemigo) {
		case MONEDA:
			enemigo = spritePoolMoneda.obtainPoolItem();	
			if (coordenada != null) {
				enemigo.inicia(coordenada.getColumna(), coordenada.getFila());
				almacen.add(enemigo);
			}
			break;
		case GLOBO:
			enemigo = spritePoolGlobo.obtainPoolItem();
			if (coordenada != null) {
				enemigo.inicia(coordenada.getColumna(), coordenada.getFila());
				almacen.add(enemigo);
			}
			break;
			
		case GLOBO_AZUL:
			enemigo = spritePoolGloboAzul.obtainPoolItem();
			if (coordenada != null) {
				enemigo.inicia(coordenada.getColumna(), coordenada.getFila());
				almacen.add(enemigo);
			}
			break;
			
		case FANTASMA:
			enemigo =spritePoolFantasma.obtainPoolItem();
				if (coordenada != null) {
				enemigo.inicia(coordenada.getColumna(), coordenada.getFila());
				almacen.add(enemigo);
			}
			break;
			
		case MOCO:
			enemigo =spritePoolMoco.obtainPoolItem();
			if (coordenada != null) {
				enemigo.inicia(coordenada.getColumna(), coordenada.getFila());
				almacen.add(enemigo);
			}
			break;
		default:
			break;
		}
		
	}
	
	public Coordenadas eligePosiciones() {
		// columna

		int i=0;
		do {
			int columna = tomaDecision(5, 22);
			int fila = tomaDecision(5, 12);
			if (context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla == Matriz.NADA) {
				return new Coordenadas(columna, fila);
			}
			i++;
		} while (i<200);
		return null;
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
		case FANTASMA:
			spritePoolFantasma.recyclePoolItem((EnemigoFantasma)eliminado);					
			break;
		case MOCO:
			spritePoolMoco.recyclePoolItem((EnemigoMoco)eliminado);					
			break;
		case GLOBO_AZUL:
			spritePoolGloboAzul.recyclePoolItem((EnemigoGloboAzul)eliminado);					
			break;
		case MONEDA:
			spritePoolMoneda.recyclePoolItem((EnemigoMoneda)eliminado);					
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

	protected int tomaDecision(int aStart, int aEnd) {
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + aStart);
		return aleatorio;

	}
	
	
}
