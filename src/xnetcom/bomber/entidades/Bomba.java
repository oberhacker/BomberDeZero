package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
import xnetcom.bomber.util.Matriz.Casilla;
import xnetcom.bomber.util.TransparentBitmapTextureAtlasSource;
import android.R.bool;
import android.util.Log;
import android.widget.Toast;

public class Bomba {

	public BomberGame context;

	private ArrayList<Coordenadas> coordenadas;

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
	public int secuencia=0;
	private int tamExplosion;
	private boolean detonada;
	
	public boolean isDetonada() {
		return detonada;
	}

	public void setDetonada(boolean detonada) {
		Log.d("DETONADA", "detonada" +detonada+ " fila"+fila +" columna "+columna);
		this.detonada = detonada;
	}

	private boolean detonadorRemoto;

	
	public Rectangle currentTileRectangle;	
	

	public Bomba(BomberGame context) {
		detonada=true;
		this.context = context;
	}



	
	
	public void normaliza(AnimatedSprite sprite) {
//		sprite.setWidth(Constantes.TILE_WIDTH);
//		sprite.setHeight(Constantes.TILE_HEIGHT);
		sprite.setOffsetCenter(0, 0);
	}
	

	public void creaBatch() {
		// Srpite maestro

		sprCentro = new AnimatedSprite(0,0, context.almacenBombas.mFuegoCentroTR, context.getVertexBufferObjectManager()) {
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

		sprDerecha_0 = new AnimatedSprite(sprCentro.getX() + sprCentro.getWidth(), sprCentro.getY(), context.almacenBombas.mFuegoCentroDerechaTR, context.getVertexBufferObjectManager());
		normaliza(sprDerecha_0);

		sprIzquierda_0 = new AnimatedSprite(sprCentro.getX() - sprCentro.getWidth(), sprCentro.getY(), context.almacenBombas.mFuegoCentroIzquierdaTR, context.getVertexBufferObjectManager());
		normaliza(sprIzquierda_0);
		sprArriba_0 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + sprCentro.getHeight(), context.almacenBombas.mFuegoCentroArribaTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_0);
		sprAbajo_0 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - sprCentro.getHeight(), context.almacenBombas.mFuegoCentroAbajoTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_0);
		sprArriba_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 2), context.almacenBombas.mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_1);
		sprArriba_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 3), context.almacenBombas.mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprArriba_2);
		sprAbajo_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 2), context.almacenBombas.mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_1);
		sprAbajo_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 3), context.almacenBombas.mFuegoVerticalTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajo_2);
		sprDerecha_1 = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 2), sprCentro.getY(), context.almacenBombas.mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerecha_1);
		sprDerecha_2 = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 3), sprCentro.getY(), context.almacenBombas.mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerecha_2);
		sprIzquierda_1 = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 2), sprCentro.getY(), context.almacenBombas.mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierda_1);
		sprIzquierda_2 = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 3), sprCentro.getY(), context.almacenBombas.mFuegoHorizontalTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierda_2);

		sprArribaFin = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() + (sprCentro.getHeight() * 4), context.almacenBombas.mFuegoFinArribaTR, context.getVertexBufferObjectManager());
		normaliza(sprArribaFin);
		sprAbajoFin = new AnimatedSprite(sprCentro.getX(), sprCentro.getY() - (sprCentro.getHeight() * 4), context.almacenBombas.mFuegoFinAbajoTR, context.getVertexBufferObjectManager());
		normaliza(sprAbajoFin);

		sprDerechaFin = new AnimatedSprite(sprCentro.getX() + (sprCentro.getWidth() * 4), sprCentro.getY(), context.almacenBombas.mFuegoFinDerechaTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprDerechaFin);
		sprIzquierdaFin = new AnimatedSprite(sprCentro.getX() - (sprCentro.getWidth() * 4), sprCentro.getY(), context.almacenBombas.mFuegoFinIzquierdaTR, context.getVertexBufferObjectManager());// gles2
		normaliza(sprIzquierdaFin);



		batch = new SpriteGroup(context.almacenBombas.fuegoBBTA, 21, context.getVertexBufferObjectManager());
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
		

		currentTileRectangle.setZIndex(Constantes.ZINDEX_BOMBA);
		currentTileRectangle.setAlpha(0);
		currentTileRectangle.setPosition(0, 0);
//		currentTileRectangle.setZIndex(900);
//		sprCentro.animate(200, false);
		}
	
	public void onSceneCreated(){
		context.escenaJuego.scene.attachChild(currentTileRectangle);
		context.escenaJuego.scene.attachChild(batch);
//		context.escenaJuego.scene.sortChildren();
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
//		sprBomba.setVisible(false);
	}

	public boolean plantarBomba(int tamExplosion, int secuencia, boolean detonadorRemoto) {
		
		
		
		coordenadas= new ArrayList<Coordenadas>();
		int columna = context.bomberman.getColumna();
		int fila = context.bomberman.getFila();
		this.fila = fila;
		this.columna = columna;
		
		

		// si no se puede plantar no hacemos nada
		if (!sePuedePlantarBomba(fila, columna)) {
			return false;
		}
		
		
		context.almacenBombas.bombasPlantadas.incrementAndGet();
		setDetonada(false);
		
		
		context.escenaJuego.matriz.setValor(Matriz.BOMBA, fila, columna,this,null);
		this.secuencia = secuencia;
		this.tamExplosion = tamExplosion;
		this.detonadorRemoto = detonadorRemoto;
		

		batch.setPosition(context.bomberman.currentTileRectangle.getX(), context.bomberman.currentTileRectangle.getY());
		
		
		sprBomba = new AnimatedSprite(sprCentro.getX() - 5, sprCentro.getY() - 10, context.almacenBombas.bombaTR, context.getVertexBufferObjectManager());
		sprBomba.setOffsetCenter(0, 0);
		
		currentTileRectangle.attachChild(sprBomba);
		
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

	
	public void reinicia(){
		context.soundManager.pararMecha();
//		sprBomba.setVisible(false);
		batch.clearEntityModifiers();
		sprCentro.clearEntityModifiers();
		this.setDetonada(true);		
		
	}
	public void detonarConDelay(){
		if (isDetonada()){
			return;
		}		
		Log.d("DETONAR", "ME LLAMAN A DETONAR CON TIEMPO Fila:"+fila+" columna "+columna);
		batch.clearEntityModifiers();
		new Thread(){
			public void run() {
				try {
					sleep(100);
					detonar();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				

			};
		}.start();		
	}
	
	public synchronized void detonar(){	
		Log.d("DETONAR", "fila"+fila +" Columna "+columna);
		if (isDetonada()){
			return;
		}

//		batch.resetEntityModifiers();
		setDetonada(true);
		context.soundManager.pararMecha();
		batch.setIgnoreUpdate(false);		
//		batch.setVisible(true);
		creaFragmentoExplosiones();
		context.soundManager.sonidoExplosion();
//		sprBomba.setVisible(false);
		sprBomba.clearEntityModifiers();
		sprBomba.clearUpdateHandlers();
		sprBomba.setIgnoreUpdate(true);
		sprBomba.detachSelf();
		context.escenaJuego.matriz.setValor(Matriz.NADA, fila, columna,null,null);
		context.vibrar(300);
		
		sprCentro.animate(ANIMATE_DURATION, false, new ListenerExplotar());
		// detonamos las demas bombas
				
		context.almacenBombas.bombasPlantadas.decrementAndGet();
		context.escenaJuego.matriz.explota(coordenadas);
		context.almacenEnemigos.mataEnemigos(coordenadas);
		context.gameManager.matarPorCoordenadas(coordenadas);
	}	
	
	

	
	
	
	public boolean sePuedePlantarBomba(int fila, int columna) {
		return (context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla == Matriz.NADA);
	}
	
	
	
	public void creaFragmentoExplosiones(){
		centro();
		cruzArriba();
		cruzAbajo();
		cruzIzquierda();
		cruzDerecha();
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
	
	public void cruzArriba(){
		Sprite [] array = new Sprite[] {sprCentro, sprArriba_0, sprArriba_1,sprArriba_2, sprArribaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i <=tamExplosion; i++,iAnterior++) {
			int mColumna=columna;
			int mFila = fila-i;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna()).tipoCasilla;
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				if (i==tamExplosion){
					array[4].setPosition(getXFragmento(0), getYFragmento(i));
					array[4].setVisible(true);
				}else{
					array[i].setVisible(true);
					array[i].setPosition(getXFragmento(0), getYFragmento(i));
				}

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
	public void cruzIzquierda(){
		Sprite [] array = new Sprite[] {sprCentro, sprIzquierda_0, sprIzquierda_1,sprIzquierda_2, sprIzquierdaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i <= tamExplosion; i++,iAnterior++) {
			int mColumna=columna-i;
			int mFila = fila;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna()).tipoCasilla;
			
			if (valor==Matriz.NADA){
				// poner fagmento		
				if (i==tamExplosion){
					array[4].setPosition(getXFragmento(-i), getYFragmento(0));
					array[4].setVisible(true);
				}else{
					array[i].setVisible(true);
					array[i].setPosition(getXFragmento(-i), getYFragmento(0));	
				}

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
	
	public void cruzDerecha(){
		Sprite [] array = new Sprite[] {sprCentro, sprDerecha_0, sprDerecha_1,sprDerecha_2, sprDerechaFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i <=tamExplosion; i++,iAnterior++) {
			int mColumna=columna+i;
			int mFila = fila;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna()).tipoCasilla;
			
			if (valor==Matriz.NADA){
				// poner fagmento			
				if (i==tamExplosion){
					array[4].setPosition(getXFragmento(i), getYFragmento(0));
					array[4].setVisible(true);
				}else{
					array[i].setVisible(true);
					array[i].setPosition(getXFragmento(i), getYFragmento(0));
				}

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
	
	public void cruzAbajo(){
		Sprite [] array = new Sprite[] {sprCentro, sprAbajo_0, sprAbajo_1,sprAbajo_2, sprAbajoFin};
		//centro+1	
		
		int iAnterior=0;	
		for (int i = 1; i <=tamExplosion; i++,iAnterior++) {
			int mColumna=columna;
			int mFila = fila+i;			
			Coordenadas coodenadas= new Coordenadas(mColumna, mFila);
			int valor=context.escenaJuego.matriz.getValor(coodenadas.getFila(), coodenadas.getColumna()).tipoCasilla;
			
			if (valor==Matriz.NADA){
				// poner fagmento		
				if (i==tamExplosion){
					array[4].setPosition(getXFragmento(0), getYFragmento(-i));
					array[4].setVisible(true);
				}else{
					array[i].setVisible(true);
					array[i].setPosition(getXFragmento(0), getYFragmento(-i));
				}

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
			
//			detonar();
			
		}

		
	}

}
