package xnetcom.bomber.entidades;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.TransparentBitmapTextureAtlasSource;

public class Bomba {

	public BomberGame context;
	public BitmapTextureAtlas bombaBTA;
	public BuildableBitmapTextureAtlas fuegoBBTA;

	private ArrayList<Coordenadas> coordenadas;
	
	
	private TiledTextureRegion mFuegoCentroTR;
	private TiledTextureRegion mFuegoCentroDerechaTR;
	private TiledTextureRegion mFuegoCentroIzquierdaTR;
	private TiledTextureRegion mFuegoCentroArribaTR;
	private TiledTextureRegion mFuegoCentroAbajoTR;
	private TiledTextureRegion mFuegoHorizontalTR;
	private TiledTextureRegion mFuegoVerticalTR;

	private TiledTextureRegion mFuegoFinDerechaTR;
	private TiledTextureRegion mFuegoFinIzquierdaTR;
	private TiledTextureRegion mFuegoFinArribaTR;
	private TiledTextureRegion mFuegoFinAbajoTR;

	private TiledTextureRegion bombaTR;

	public AnimatedSprite sprCentro;
	private AnimatedSprite sprDerecha_0;
	private AnimatedSprite sprIzquierda_0;
	private AnimatedSprite sprArriba_0;
	private AnimatedSprite sprAbajo_0;
	private AnimatedSprite sprArriba_1;
	private AnimatedSprite sprArriba_2;
	private AnimatedSprite sprAbajo_1;
	private AnimatedSprite sprAbajo_2;
	private AnimatedSprite sprDerecha_1;
	private AnimatedSprite sprDerecha_2;
	private AnimatedSprite sprIzquierda_1;
	private AnimatedSprite sprIzquierda_2;
	private AnimatedSprite sprArribaFin;
	private AnimatedSprite sprAbajoFin;
	private AnimatedSprite sprDerechaFin;
	private AnimatedSprite sprIzquierdaFin;

	public AnimatedSprite sprBomba;

	public SpriteGroup batch;

	private static final int TIEMPO = 100;
	private static final long[] ANIMATE_DURATION = new long[] { 50, 50, 50, 50, 50 };

	int fila;
	int columna;
	private int secuencia;
	private int tamExplosion;
	private boolean detonada;
	private boolean detonadorRemoto;

	
	public Rectangle currentTileRectangle;	
	

	public Bomba(BomberGame context) {
		this.context = context;
	}

	public void cargaTexturas(Bomba clone) {
		
		if (clone!=null){
			clonarTexturas(clone);			
		}else{
			this.bombaBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
			BitmapTextureAtlasTextureRegionFactory.createFromSource(bombaBTA, new TransparentBitmapTextureAtlasSource(512, 256), 0, 0);
			this.fuegoBBTA = new BuildableBitmapTextureAtlas(context.getTextureManager(), 2048, 256, TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA);
			this.mFuegoCentroTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_r_ani_74.png", 5, 1);
			this.mFuegoCentroAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_abajo_r_ani_74.png", 5, 1);
			this.mFuegoCentroArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_arriba_r_ani_74.png", 5, 1);
			this.mFuegoCentroDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_derecha_r_ani_74.png", 5, 1);
			this.mFuegoCentroIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_izquierda_r_ani_74.png", 5, 1);
			this.mFuegoFinAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_abajo_r_ani_74.png", 5, 1);
			this.mFuegoFinArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_arriba_r_ani_74.png", 5, 1);
			this.mFuegoFinDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_derecha_r_ani_74.png", 5, 1);
			this.mFuegoFinIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_izquierda_r_ani_74.png", 5, 1);
			this.mFuegoHorizontalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_horizontal_r_ani_74.png", 5, 1);
			this.mFuegoVerticalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_vertical_r_ani_74.png", 5, 1);
			this.bombaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bombaBTA, context, "gfx/bomba_ani90.png", 0, 0, 4, 2);

			try {
				fuegoBBTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 2));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.getEngine().getTextureManager().loadTexture(fuegoBBTA);
			context.getEngine().getTextureManager().loadTexture(bombaBTA);
		}	

		creaBatch();
	}

	
	public void clonarTexturas(Bomba clone){
		this.mFuegoCentroAbajoTR=clone.mFuegoCentroAbajoTR.deepCopy();
		this.mFuegoCentroArribaTR=clone.mFuegoCentroArribaTR.deepCopy();
		this.mFuegoCentroDerechaTR=clone.mFuegoCentroDerechaTR.deepCopy();
		this.mFuegoCentroIzquierdaTR=clone.mFuegoCentroIzquierdaTR.deepCopy();
		this.mFuegoCentroTR=clone.mFuegoCentroTR.deepCopy();
		this.mFuegoFinAbajoTR=clone.mFuegoFinAbajoTR.deepCopy();
		this.mFuegoFinArribaTR=clone.mFuegoFinArribaTR.deepCopy();
		this.mFuegoFinDerechaTR=clone.mFuegoFinDerechaTR.deepCopy();
		this.mFuegoFinIzquierdaTR=clone.mFuegoFinIzquierdaTR.deepCopy();
		this.mFuegoHorizontalTR=clone.mFuegoHorizontalTR.deepCopy();
		this.mFuegoVerticalTR=clone.mFuegoVerticalTR.deepCopy();	
		this.bombaTR=clone.bombaTR.deepCopy();
		this.bombaBTA=clone.bombaBTA;
		this.fuegoBBTA=clone.fuegoBBTA;
	}
	
	
	public void normaliza(AnimatedSprite sprite) {
//		sprite.setWidth(Constantes.TILE_WIDTH);
//		sprite.setHeight(Constantes.TILE_HEIGHT);
		sprite.setOffsetCenter(0, 0);
	}
	

	public void creaBatch() {
		// Srpite maestro

		sprCentro = new AnimatedSprite(0,0, mFuegoCentroTR, context.getVertexBufferObjectManager()) {
			public void setAlpha(float pAlpha) {
				sprAbajo_1.setAlpha(pAlpha);
				sprAbajo_2.setAlpha(pAlpha);
				sprArriba_1.setAlpha(pAlpha);
				sprArriba_2.setAlpha(pAlpha);
				sprAbajo_0.setAlpha(pAlpha);
				sprArriba_0.setAlpha(pAlpha);
				sprDerecha_0.setAlpha(pAlpha);
				sprIzquierda_0.setAlpha(pAlpha);
				sprDerecha_1.setAlpha(pAlpha);
				sprDerecha_2.setAlpha(pAlpha);
				sprAbajoFin.setAlpha(pAlpha);
				sprArribaFin.setAlpha(pAlpha);
				sprDerechaFin.setAlpha(pAlpha);
				sprIzquierdaFin.setAlpha(pAlpha);
				sprIzquierda_1.setAlpha(pAlpha);
				sprIzquierda_2.setAlpha(pAlpha);
				super.setAlpha(pAlpha);
			}

			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				sprAbajo_1.setCurrentTileIndex(pTileIndex);
				sprAbajo_2.setCurrentTileIndex(pTileIndex);
				sprArriba_1.setCurrentTileIndex(pTileIndex);
				sprArriba_2.setCurrentTileIndex(pTileIndex);
				sprAbajo_0.setCurrentTileIndex(pTileIndex);
				sprArriba_0.setCurrentTileIndex(pTileIndex);
				sprDerecha_0.setCurrentTileIndex(pTileIndex);
				sprIzquierda_0.setCurrentTileIndex(pTileIndex);
				sprDerecha_1.setCurrentTileIndex(pTileIndex);
				sprDerecha_2.setCurrentTileIndex(pTileIndex);
				sprAbajoFin.setCurrentTileIndex(pTileIndex);
				sprArribaFin.setCurrentTileIndex(pTileIndex);
				sprDerechaFin.setCurrentTileIndex(pTileIndex);
				sprIzquierdaFin.setCurrentTileIndex(pTileIndex);
				sprIzquierda_1.setCurrentTileIndex(pTileIndex);
				sprIzquierda_2.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}

		};

		normaliza(sprCentro);

		sprDerecha_0 = new AnimatedSprite(sprCentro.getX() + sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroDerechaTR, context.getVertexBufferObjectManager());
		normaliza(sprDerecha_0);

		sprIzquierda_0 = new AnimatedSprite(sprCentro.getX() - sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroIzquierdaTR, context.getVertexBufferObjectManager());
		normaliza(sprIzquierda_0);
		sprArriba_0 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + sprCentro.getHeight(), mFuegoCentroArribaTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_0);
		sprAbajo_0 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - sprCentro.getHeight(), mFuegoCentroAbajoTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_0);
		sprArriba_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 2), mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_1);
		sprArriba_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 3), mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_2);
		sprAbajo_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 2), mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_1);
		sprAbajo_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 3), mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_2);
		sprDerecha_1 = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 2), sprCentro.getY(), mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerecha_1);
		sprDerecha_2 = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 3), sprCentro.getY(), mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerecha_2);
		sprIzquierda_1 = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 2), sprCentro.getY(), mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierda_1);
		sprIzquierda_2 = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 3), sprCentro.getY(), mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierda_2);

		sprArribaFin = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 4), mFuegoFinArribaTR, context.getVertexBufferObjectManager());
		normaliza(sprArribaFin);
		sprAbajoFin = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 4), mFuegoFinAbajoTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajoFin);

		sprDerechaFin = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 4), sprCentro.getY(), mFuegoFinDerechaTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerechaFin);
		sprIzquierdaFin = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 4), sprCentro.getY(), mFuegoFinIzquierdaTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierdaFin);

		sprBomba = new AnimatedSprite(sprCentro.getX() - 5, sprCentro.getY() - 10, bombaTR, context.getVertexBufferObjectManager());
		sprBomba.setOffsetCenter(0, 0);

		batch = new SpriteGroup(fuegoBBTA, 21, context.getVertexBufferObjectManager());
		batch.setOffsetCenter(0, 0);
		batch.setScaleCenter(0, 0);
		
//		batch.attachChild(sprBomba);
		batch.attachChild(sprAbajo_1);
		batch.attachChild(sprAbajo_2);

		batch.attachChild(sprArriba_1);
		batch.attachChild(sprArriba_2);

		batch.attachChild(sprDerecha_1);
		batch.attachChild(sprDerecha_2);

		batch.attachChild(sprIzquierda_1);
		batch.attachChild(sprIzquierda_2);

		batch.attachChild(sprCentro);

		batch.attachChild(sprAbajo_0);
		batch.attachChild(sprArriba_0);
		batch.attachChild(sprDerecha_0);
		batch.attachChild(sprIzquierda_0);

		batch.attachChild(sprAbajoFin);
		batch.attachChild(sprArribaFin);
		batch.attachChild(sprDerechaFin);
		batch.attachChild(sprIzquierdaFin);

		// batch.setVisible(false);
		// sprBomba.setVisible(false);
		batch.setZIndex(Constantes.ZINDEX_FUEGO);
		// batch.setIgnoreUpdate(true);

		
		currentTileRectangle = new Rectangle(0, 0, Constantes.TILE_WIDTH, Constantes.TILE_HEIGHT,	context.getVertexBufferObjectManager());
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		
		
		reiniciaBatch();
		
		currentTileRectangle.attachChild(sprBomba);
		currentTileRectangle.setZIndex(Constantes.ZINDEX_BOMBA);
		currentTileRectangle.setAlpha(0);
		currentTileRectangle.setPosition(0, 0);
//		currentTileRectangle.setZIndex(900);
//		sprCentro.animate(200, false);
		}
	
	public void onSceneCreated(){
		context.escenaJuego.scene.attachChild(currentTileRectangle);
		context.escenaJuego.scene.attachChild(batch);
		context.escenaJuego.scene.sortChildren();
	}
	
	

	public void reiniciaBatch() {
//		batch.setVisible(false);
		sprCentro.setVisible(false);
		sprAbajo_1.setVisible(false);
		sprAbajo_2.setVisible(false);
		sprArriba_1.setVisible(false);
		sprArriba_2.setVisible(false);
		sprAbajo_0.setVisible(false);
		sprArriba_0.setVisible(false);
		sprDerecha_0.setVisible(false);
		sprIzquierda_0.setVisible(false);
		sprDerecha_1.setVisible(false);
		sprDerecha_2.setVisible(false);
		sprAbajoFin.setVisible(false);
		sprArribaFin.setVisible(false);
		sprDerechaFin.setVisible(false);
		sprIzquierdaFin.setVisible(false);
		sprIzquierda_1.setVisible(false);
		sprIzquierda_2.setVisible(false);
		sprBomba.setVisible(false);
	}

	public boolean plantarBomba(int tamExplosion, int secuencia, boolean detonadorRemoto) {
		coordenadas= new ArrayList<Coordenadas>();
		int columna = context.bomberman.getColumna();
		int fila = context.bomberman.getFila();

		// si no se puede plantar no hacemos nada
		if (!sePuedePlantarBomba(fila, columna)) {
			return false;
		}
		context.escenaJuego.matriz.setValor(Matriz.BOMBA, fila, columna);

		this.fila = fila;
		this.columna = columna;
		this.secuencia = secuencia;
		this.tamExplosion = tamExplosion;
		this.detonada = false;
		this.detonadorRemoto = detonadorRemoto;

		batch.setPosition(context.bomberman.currentTileRectangle.getX(), context.bomberman.currentTileRectangle.getY());
		currentTileRectangle.setPosition(context.bomberman.currentTileRectangle.getX(), context.bomberman.currentTileRectangle.getY());
		context.soundManager.plantaBomba();
		context.soundManager.sonarMecha();
		
		sprBomba.setVisible(true);
		if (!detonadorRemoto){
			sprBomba.animate(TIEMPO, 2,new ListenerDetonador());
		}else{
			sprBomba.animate(TIEMPO, 1000);
			
		}		
		return true;

	}

	
	public synchronized void detonar(){	
		batch.setIgnoreUpdate(false);		
//		batch.setVisible(true);
		creaFragmentoExplosiones();
		context.soundManager.sonidoExplosion();
		sprBomba.setVisible(false);
		context.escenaJuego.matriz.setValor(Matriz.NADA, fila, columna);
		context.vibrar(300);
		detonada=true;
		sprCentro.animate(ANIMATE_DURATION, false, new ListenerExplotar());
	}	
	
	public void detonarContiempo(float secs){
		
		batch.registerEntityModifier(new DelayModifier(secs){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				// TODO Auto-generated method stub
				detonar();
			}
		});		
		
	}
	
	
	
	public boolean sePuedePlantarBomba(int fila, int columna) {
		return (context.escenaJuego.matriz.getValor(fila, columna) == Matriz.NADA);
	}
	
	
	
	public void creaFragmentoExplosiones(){
		Coordenadas coordenadasCentro = new Coordenadas(columna, fila);
		coordenadas.add(coordenadasCentro);
		int [][] matriz = context.escenaJuego.matriz.getMatrizmuros();	
		
		centro();
		cruzArriba(matriz);
		cruzAbajo(matriz);
		cruzIzquierda(matriz);
		cruzDerecha(matriz);
	}
	
	public float getXFragmento(int posX){
		return posX*Constantes.TILE_WIDTH;
		
	}
	
	public float getYFragmento(int posY){
		return posY*Constantes.TILE_HEIGHT;
	}
	
	
	
	public void centro(){	
		Coordenadas coodenadas= new Coordenadas(columna, fila);
		sprCentro.setVisible(true);
		this.coordenadas.add(coodenadas);
		
	}
	
	public void cruzArriba(int [][]matriz){
		Sprite [] array = new Sprite[] {sprCentro, sprArriba_0, sprArriba_1,sprArriba_2, sprArribaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i < 5; i++,iAnterior++) {
			int mColumna=columna;
			int mFila = fila-i;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna());
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				array[i].setVisible(true);
				array[i].setPosition(getXFragmento(0), getYFragmento(i));
				this.coordenadas.add(coodenadas);
			}else if (valor==Matriz.PARED|| valor==Matriz.BOMBA){
				array[4].setPosition(getXFragmento(0), getYFragmento(i));
				array[4].setVisible(true);
				this.coordenadas.add(coodenadas);
				break;
			}else if (valor==Matriz.MURO){
				// si es muro poner el final en el anterior, a no ser que sea i=1
				if (i!=1){
					array[iAnterior].setVisible(false);
					array[4].setPosition(getXFragmento(0), getYFragmento(iAnterior));
					array[4].setVisible(true);
				}
				break;				
			}
		}		
	}
	public void cruzIzquierda(int [][]matriz){
		Sprite [] array = new Sprite[] {sprCentro, sprIzquierda_0, sprIzquierda_1,sprIzquierda_2, sprIzquierdaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i < 5; i++,iAnterior++) {
			int mColumna=columna-i;
			int mFila = fila;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna());
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				array[i].setVisible(true);
				array[i].setPosition(getXFragmento(-i), getYFragmento(0));
				this.coordenadas.add(coodenadas);
			}else if (valor==Matriz.PARED|| valor==Matriz.BOMBA){
				array[4].setPosition(getXFragmento(-i), getYFragmento(0));
				array[4].setVisible(true);
				this.coordenadas.add(coodenadas);
				break;
			}else if (valor==Matriz.MURO){
				// si es muro poner el final en el anterior, a no ser que sea i=1
				if (i!=1){
					array[iAnterior].setVisible(false);
					array[4].setPosition(getXFragmento(-iAnterior), getYFragmento(0));
					array[4].setVisible(true);
				}
				break;				
			}
		}		
	}
	
	public void cruzDerecha(int [][]matriz){
		Sprite [] array = new Sprite[] {sprCentro, sprDerecha_0, sprDerecha_1,sprDerecha_2, sprDerechaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i < 5; i++,iAnterior++) {
			int mColumna=columna+i;
			int mFila = fila;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna());
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				array[i].setVisible(true);
				array[i].setPosition(getXFragmento(i), getYFragmento(0));
				this.coordenadas.add(coodenadas);
			}else if (valor==Matriz.PARED|| valor==Matriz.BOMBA){
				array[4].setPosition(getXFragmento(i), getYFragmento(0));
				array[4].setVisible(true);
				this.coordenadas.add(coodenadas);
				break;
			}else if (valor==Matriz.MURO){
				// si es muro poner el final en el anterior, a no ser que sea i=1
				if (i!=1){
					array[iAnterior].setVisible(false);
					array[4].setPosition(getXFragmento(iAnterior), getYFragmento(0));
					array[4].setVisible(true);
				}
				break;				
			}
		}		
	}
	
	public void cruzAbajo(int [][]matriz){
		Sprite [] array = new Sprite[] {sprCentro, sprAbajo_0, sprAbajo_1,sprAbajo_2, sprAbajoFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i < 5; i++,iAnterior++) {
			int mColumna=columna;
			int mFila = fila+i;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna());
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				array[i].setVisible(true);
				array[i].setPosition(getXFragmento(0), getYFragmento(-i));
				this.coordenadas.add(coodenadas);
			}else if (valor==Matriz.PARED || valor==Matriz.BOMBA){
				array[4].setPosition(getXFragmento(0), getYFragmento(-i));
				array[4].setVisible(true);
				this.coordenadas.add(coodenadas);
				break;
			}else if (valor==Matriz.MURO){
				// si es muro poner el final en el anterior, a no ser que sea i=1
				if (i!=1){
					array[iAnterior].setVisible(false);
					array[4].setPosition(getXFragmento(0), getYFragmento(-iAnterior));
					array[4].setVisible(true);
				}
				break;				
			}
		}		
	}
	
	
	
	
	
	
	public class ListenerExplotar implements IAnimationListener{

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			sprCentro.animate(ANIMATE_DURATION,new int[]{4, 3, 2, 1, 0}, 0,new ListenerDesvanecer());
			
		}


		
	}
	public class ListenerDesvanecer implements IAnimationListener{

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
//			batch.setVisible(false);
			reiniciaBatch();
			batch.setIgnoreUpdate(true);			
		}


		
	}
	
	public class ListenerDetonador implements IAnimationListener{

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			context.soundManager.pararMecha();
			detonar();
			
		}

		
	}

}