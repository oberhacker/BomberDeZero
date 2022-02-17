package xnetcom.bomber.entidades;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.text.Text;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.Constants;
import org.andengine.util.modifier.IModifier;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.Matriz.Casilla;
import android.graphics.Typeface;
import android.util.Log;

public class BomberMan {

	private static long[] ANIMATE_DURATION = new long[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };

	public enum PlayerPosicion {
		ARRIBA, ABAJO, DERECHA, IZQUIERDA, CENTRO, MUY_ARRIBA, MUY_ABAJO, MUY_DERECHA, MUY_IZQUIERDA,
	}

	public enum PlayerDirection {
		NONE, UP, DOWN, LEFT, RIGHT
	}

	public BomberGame context;

	boolean fantasma = false;

	public Rectangle currentTileRectangle;
	// public AnimatedSprite player;
	public AnimatedSprite bomberArriba;
	public AnimatedSprite bomberAbajo;

	private TiledTextureRegion mBombermanTextureRegionAniA;
	private TiledTextureRegion mBombermanTextureRegionAniB;
	private PhysicsHandler physicsHandler;

	public static int PIES_X = 32;
	public static int PIES_Y = 32;

	public static float VELOCIDAD_RECTO_X = 150;
	public static float VELOCIDAD_RECTO_Y = 150;

	public float FACTOR_ACHATADO = 0.96f;

	public Rectangle baseTileRectangle;
	public Rectangle colidesTileRectangle;

	private Integer fila = 0;
	private Integer columna = 0;

	private Boolean muerto = false;

	public PlayerDirection playerDirection = PlayerDirection.NONE;

	public BomberMan(BomberGame context) {
		this.context = context;
	}

	public Rectangle getSprite() {
		return baseTileRectangle;
	}

	
	public void reinicia(){		
		detenerPararAnimacion();
		baseTileRectangle.setPosition(getXinicial(), getYinicial());
		bomberAbajo.setCurrentTileIndex(0);
		bomberAbajo.setCurrentTileIndex(0);		
		setnoMuerto();
	}
	

	float FACTORPEQUEO = 0.90f;

	public void carga() throws IOException {

		BitmapTextureAtlas tiledmaster90A = new BitmapTextureAtlas(context.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBombermanTextureRegionAniA = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90A, context, "gfx/tiledmaster(125x104)ArribaB.png", 0, 0, 12, 5);
		tiledmaster90A.load();

		BitmapTextureAtlas tiledmaster90B = new BitmapTextureAtlas(context.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBombermanTextureRegionAniB = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90B, context, "gfx/tiledmaster(125x104)abajoS6.png", 0, 0, 12, 5);
		tiledmaster90B.load();

		bomberArriba = new AnimatedSprite(0, 0, mBombermanTextureRegionAniA, context.getVertexBufferObjectManager());
		bomberArriba.setOffsetCenter(0, 0);
		bomberArriba.setScaleCenter(0, 0);
		bomberArriba.setScaleY(FACTORPEQUEO*FACTOR_ACHATADO);
		bomberArriba.setScaleX(FACTORPEQUEO);
		
		
		bomberAbajo = new AnimatedSprite(0, 0, mBombermanTextureRegionAniB, context.getVertexBufferObjectManager()) {
			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				bomberArriba.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}
			
			@Override
			protected void onManagedDraw(GLState GLState, Camera pCamera) {
				super.onManagedDraw(GLState, pCamera);
			}
		};
		bomberAbajo.setOffsetCenter(0, 0);
		bomberAbajo.setScaleCenter(0, 0);
		bomberAbajo.setScaleY(FACTORPEQUEO*FACTOR_ACHATADO);
		bomberAbajo.setScaleX(FACTORPEQUEO);
	
//		bomberAbajo.attachChild(bomberAbajo);

		// ajustamos el personaje dento de su cuadro
		bomberAbajo.setX(bomberAbajo.getX() - 10);

		bomberAbajo.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO);
		bomberArriba.setZIndex(Constantes.ZINDEX_BOMBERMAN_ARRIBA);

	}

	
	
	public float getXinicial(){
		return (2 * Constantes.TILE_TAM * Constantes.FARTOR_FORMA);
	}
	public float getYinicial(){
		return (context.escenaJuego.mTMXTiledMap.getHeight() - 3 * Constantes.TILE_TAM);
	}
	
	
	IUpdateHandler updater;
	public void onCreateScene(Scene scene) {

		currentTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth() * Constantes.FARTOR_FORMA, context.escenaJuego.mTMXTiledMap.getTileHeight(),
				context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		currentTileRectangle.setPosition(2 * context.escenaJuego.mTMXTiledMap.getTileWidth() * Constantes.FARTOR_FORMA, currentTileRectangle.getY());

		currentTileRectangle.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO - 2);
		if (!Constantes.DEBUG_CURRENT_RECTANGLE_VISIBLE) {
			currentTileRectangle.setAlpha(0f);
		}

		baseTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth() * Constantes.FARTOR_FORMA, context.escenaJuego.mTMXTiledMap.getTileHeight(),
				context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		baseTileRectangle.setOffsetCenter(0, 0);
		baseTileRectangle.setColor(50, 50, 50);
		baseTileRectangle.setScaleCenter(0, 0);
		// baseTileRectangle.setScaleX(Constantes.FARTOR_FORMA);
		if (!Constantes.DEBUG_BASE_RECTANGLE_VISIBLE) {
			baseTileRectangle.setAlpha(0f);
		}

		physicsHandler = new PhysicsHandler(baseTileRectangle);
		baseTileRectangle.registerUpdateHandler(physicsHandler);

		baseTileRectangle.attachChild(bomberAbajo);
		baseTileRectangle.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO - 1);

		baseTileRectangle.setPosition(getXinicial(), getYinicial());
		
		colidesTileRectangle= new Rectangle(0, 0, Constantes.TILE_WIDTH-20, Constantes.TILE_HEIGHT-20,	context.getVertexBufferObjectManager());	
		colidesTileRectangle.setColor(0, 100, 50);
		colidesTileRectangle.setOffsetCenter(0, 0);
		colidesTileRectangle.setScaleCenter(0, 0);
		colidesTileRectangle.setPosition(10,10);
		baseTileRectangle.attachChild(colidesTileRectangle);
		if (!Constantes.DEBUG_BASE_RECTANGLE_VISIBLE) {
			colidesTileRectangle.setAlpha(0f);
		}

		scene.attachChild(currentTileRectangle);
		scene.attachChild(bomberArriba);
		scene.attachChild(baseTileRectangle);

		if (updater==null){
			 updater=new IUpdateHandler() {
					@Override
					public void onUpdate(float pSecondsElapsed) {
						updater();
					}

					@Override
					public void reset() {

					}
				};
				scene.registerUpdateHandler(updater);
		}	
	}

	int innerColumna = 0;
	int innerFila = 0;

	public void updater() {
		
		
		//////////////
		//context.escenaJuego.hud.debugText.setText("Hijos "+context.escenaJuego.scene.getChildCount());
		
		bomberArriba.setX(baseTileRectangle.getX()- 10);
		bomberArriba.setY(baseTileRectangle.getY());
		final float[] playerFootCordinates = getSprite().convertLocalCoordinatesToSceneCoordinates(BomberMan.PIES_X, BomberMan.PIES_Y);
		TMXLayer tmxLayer = context.escenaJuego.mTMXTiledMap.getTMXLayers().get(1);
		TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);


		
		if (tmxTile != null) {
			if (innerColumna != tmxTile.getTileColumn()) {
				innerColumna = tmxTile.getTileColumn();
				setColumna(tmxTile.getTileColumn());
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));
				cambiaPosicion();
			}
			if (innerFila != tmxTile.getTileRow()) {
				innerFila = tmxTile.getTileRow();
				setFila(tmxTile.getTileRow());
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));
				cambiaPosicion();
			}			
		}
	}

	public PlayerPosicion getEskinado() {
		PlayerPosicion posicionRelativa = PlayerPosicion.CENTRO;

		float difY = (currentTileRectangle.getY()) - baseTileRectangle.getY();

		float difX = (currentTileRectangle.getX()) - baseTileRectangle.getX();

		// habria que sustituir los 26 po un mayor que cero

		if (difX == 0 && difY == 0) {
			posicionRelativa = PlayerPosicion.CENTRO;
		} else if (difY == 0) {
			if (difX <= -17) {
				// izquierda
				posicionRelativa = PlayerPosicion.MUY_DERECHA;
			} else if (difX >= 17) {
				// derecha
				posicionRelativa = PlayerPosicion.MUY_IZQUIERDA;
			}
		} else if (difX == 0) {

			if (difY <= -17) {
				// arriba
				posicionRelativa = PlayerPosicion.MUY_ARRIBA;
			} else if (difY >= 17) {
				// abajo
				posicionRelativa = PlayerPosicion.MUY_ABAJO;
			}

		}

		return posicionRelativa;
	}

	public void cambiaPosicion() {
		

		if (estaMovientoSinLimite()) {
			switch (playerDirection) {

			case DOWN:
				if (isLimiteAbajo()){
					detener();
					centrar(PlayerDirection.NONE);
				}
				break;
			case UP:
				if (isLimiteArriba()){
					detener();
					centrar(PlayerDirection.NONE);
				}
				break;
			case RIGHT:
				if (isLimiteDerecha()){
					detener();
					centrar(PlayerDirection.NONE);
				}
				break;

			case LEFT:
				if (isLimiteIzquierda()){
					detener();
					centrar(PlayerDirection.NONE);
				}
				break;

			}
		}

	}

	public void setFila(int fila) {
		synchronized (this.fila) {
			this.fila = fila;
		}
	}

	public void setColumna(int columna) {
		synchronized (this.columna) {
			this.columna = columna;
		}
	}

	public int getFila() {
		synchronized (this.fila) {
			return this.fila;
		}
	}

	public int getColumna() {
		synchronized (this.columna) {
			return this.columna;
		}
	}

	public boolean isLimiteArriba() {
		context.escenaJuego.matriz.getValor(2, 2);
		int sitio = context.escenaJuego.matriz.getValor(getFila() - 1, getColumna()).tipoCasilla;
		if (!fantasma) {
			if (sitio == Matriz.PARED || sitio == Matriz.MURO || sitio == Matriz.BOMBA) {
				return true;
			}
		} else {
			if (sitio == Matriz.MURO) {
				return true;
			}
		}
		return false;
	}

	public boolean isLimiteAbajo() {
		int sitio = context.escenaJuego.matriz.getValor(getFila() + 1, getColumna()).tipoCasilla;

		if (!fantasma) {
			if (sitio == Matriz.PARED || sitio == Matriz.MURO || sitio == Matriz.BOMBA) {
				return true;
			}
		} else {
			if (sitio == Matriz.MURO) {
				return true;
			}
		}
		return false;
	}

	public boolean isLimiteDerecha() {
		int sitio = context.escenaJuego.matriz.getValor(getFila(), getColumna() + 1).tipoCasilla;
		if (!fantasma) {
			if (sitio == Matriz.PARED || sitio == Matriz.MURO || sitio == Matriz.BOMBA) {
				return true;
			}
		} else {
			if (sitio == Matriz.MURO) {
				return true;
			}
		}
		return false;
	}

	public boolean isLimiteIzquierda() {
		int sitio = context.escenaJuego.matriz.getValor(getFila(), getColumna() - 1).tipoCasilla;
		if (!fantasma) {
			if (sitio == Matriz.PARED || sitio == Matriz.MURO || sitio == Matriz.BOMBA) {
				return true;
			}
		} else {
			if (sitio == Matriz.MURO) {
				return true;
			}
		}
		return false;

	}

	public void centrar(PlayerDirection direccion) {
		float xto = currentTileRectangle.getX();
		float yto = currentTileRectangle.getY();

		float x = baseTileRectangle.getX();
		float y = baseTileRectangle.getY();

		float mover = 0;

		switch (direccion) {
		case NONE:
			if (xto == x) {
				mover = yto - y;
			} else {
				mover = xto - x;
			}

			if (xto < x) {
				animarIzquierda();
			} else if (xto > x) {
				animarDerecha();
			} else if (yto < y) {				
				animarAbajo();
			} else if (yto > y) {
				animarArriba();
			}

			break;
		case RIGHT:
			x = baseTileRectangle.getX();
			xto = currentTileRectangle.getX() + currentTileRectangle.getWidth();
			mover = xto - x;
			animarDerecha();
			break;
		case LEFT:
			x = baseTileRectangle.getX();
			xto = currentTileRectangle.getX() - currentTileRectangle.getWidth();
			mover = xto - x;
			animarIzquierda();
			break;
		case UP:
			y = baseTileRectangle.getY();
			yto = currentTileRectangle.getY() + currentTileRectangle.getHeight();
			mover = yto - y;
			animarArriba();
			break;
		case DOWN:
			y = baseTileRectangle.getY();
			yto = currentTileRectangle.getY() - currentTileRectangle.getHeight();
			mover = yto - y;
			animarAbajo();
			break;

		default:
			break;
		}

		baseTileRectangle.clearEntityModifiers();

		if (mover != 0) {
			baseTileRectangle.registerEntityModifier(new MoveModifier(Math.abs(Constantes.TIEMPO_POR_PIXEL * mover), x, y, xto, yto, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					terminadoCentrar();
				}
			}));
		} else {
			terminadoCentrar();
		}

	}

	public void terminadoCentrar() {
		switch (playerDirection) {
		case UP:
			moverArribaSinFin();
			break;

		case DOWN:
			moverAbajoSinFin();
			break;

		case LEFT:
			moverIzquierdaSinFin();
			break;

		case RIGHT:
			moverDerechaSinFin();
			break;

		case NONE:
			break;
		}

	}

	public void moverArriba() {
		if(isMuerto()){
			return;
		}
		context.soundManager.sonarPasos();
		detener();
		playerDirection = PlayerDirection.UP;
		if (!isLimiteArriba()) {
			if (!compruebaCentradoHorizontal()) {
				centrar(PlayerDirection.NONE);
			} else {
				moverArribaSinFin();
			}
		} else {
			// comprobamos si estamos muy esquinados y podemos hacer una L
			PlayerPosicion esquinado = getEskinado();
			switch (esquinado) {
			case MUY_ABAJO:
			case CENTRO:// no movemos solo animamos
				if (currentTileRectangle.getY()!=baseTileRectangle.getY()){
					centrar(PlayerDirection.NONE);
				}else{
					animarArriba();
				}	
				break;
			case MUY_DERECHA:// no movemos solo animamos
				// centramos a la derecha
				centrar(PlayerDirection.RIGHT);
				break;
			case MUY_IZQUIERDA:// no movemos solo animamos
				// centramos a la iquierda
				centrar(PlayerDirection.LEFT);
				break;
			default:
				break;
			}
		}
	}

	public void moverArribaSinFin() {
		animarArriba();
		if (!isLimiteArriba()) {
			physicsHandler.setVelocity(0, VELOCIDAD_RECTO_Y);
		}

	}

	public void moverAbajo() {
		if(isMuerto()){
			return;
		}
		context.soundManager.sonarPasos();
		detener();
		playerDirection = PlayerDirection.DOWN;
		if (!isLimiteAbajo()) {
			if (!compruebaCentradoHorizontal()) {
				centrar(PlayerDirection.NONE);
			} else {
				moverAbajoSinFin();
			}
		} else {
			// comprobamos si estamos muy esquinados y podemos hacer una L
			PlayerPosicion esquinado = getEskinado();
			switch (esquinado) {
			case MUY_ARRIBA:
			case CENTRO:// no movemos solo animamos
				if (currentTileRectangle.getY()!=baseTileRectangle.getY()){
					centrar(PlayerDirection.NONE);
				}else{
					animarAbajo();
				}				
				break;
			case MUY_DERECHA:// no movemos solo animamos
				// centramos a la derecha
				centrar(PlayerDirection.RIGHT);
				break;
			case MUY_IZQUIERDA:// no movemos solo animamos
				// centramos a la iquierda
				centrar(PlayerDirection.LEFT);
				break;
			default:
				break;
			}

		}

	}

	public void moverAbajoSinFin() {
		animarAbajo();
		if (!isLimiteAbajo()) {
			physicsHandler.setVelocity(0, -VELOCIDAD_RECTO_Y);
		}

	}

	public void moverDerecha() {
		if(isMuerto()){
			return;
		}
		context.soundManager.sonarPasos();
		detener();
		playerDirection = PlayerDirection.RIGHT;
		if (!isLimiteDerecha()) {
			if (!compruebaCentradoVertical()) {
				centrar(PlayerDirection.NONE);
			} else {
				moverDerechaSinFin();
			}
		} else {
			// comprobamos si estamos muy esquinados y podemos hacer una L
			PlayerPosicion esquinado = getEskinado();
			switch (esquinado) {
			case MUY_IZQUIERDA:
			case CENTRO:// no movemos solo animamos
				if (currentTileRectangle.getX()!=baseTileRectangle.getX()){
					centrar(PlayerDirection.NONE);
				}else{
					animarDerecha();
				}				
				break;
			case MUY_ARRIBA:// no movemos solo animamos
				// centramos a la derecha
				centrar(PlayerDirection.UP);
				break;
			case MUY_ABAJO:// no movemos solo animamos
				// centramos a la iquierda
				centrar(PlayerDirection.DOWN);
				break;
			default:
				break;
			}
		}
	}

	public void moverDerechaSinFin() {
		animarDerecha();
		if (!isLimiteDerecha()) {
			physicsHandler.setVelocity(VELOCIDAD_RECTO_X, 0);
		}

	}

	public void moverIzquierda() {
		if(isMuerto()){
			return;
		}
		context.soundManager.sonarPasos();
		detener();
		playerDirection = PlayerDirection.LEFT;
		if (!isLimiteIzquierda()) {
			if (!compruebaCentradoVertical()) {
				centrar(PlayerDirection.NONE);
			} else {
				moverIzquierdaSinFin();
			}

		} else {
			// comprobamos si estamos muy esquinados y podemos hacer una L
			PlayerPosicion esquinado = getEskinado();
			switch (esquinado) {
			case MUY_DERECHA:
			case CENTRO:// no movemos solo animamos
				if (currentTileRectangle.getX()!=baseTileRectangle.getX()){
					centrar(PlayerDirection.NONE);
				}else{
					animarIzquierda();
				}
				break;
			case MUY_ARRIBA:// no movemos solo animamos
				// centramos a la derecha
				centrar(PlayerDirection.UP);
				break;
			case MUY_ABAJO:// no movemos solo animamos
				// centramos a la iquierda
				centrar(PlayerDirection.DOWN);
				break;
			default:
				break;
			}
		}
	}

	public void moverIzquierdaSinFin() {
		animarIzquierda();
		if (!isLimiteIzquierda()) {
			physicsHandler.setVelocity(-VELOCIDAD_RECTO_X, 0);
		}

	}

	public boolean estaMovientoSinLimite() {
		return (physicsHandler.getVelocityY() != 0 || physicsHandler.getVelocityX() != 0);
	}

	public void detener() {
		physicsHandler.setVelocity(0, 0);
		baseTileRectangle.clearEntityModifiers();
	}

	public void detenerPararAnimacion() {
		stopAnimation();
		detener();
		context.soundManager.pararPasos();
	}

	public void moverRecto() {
		if (physicsHandler == null) {
			physicsHandler = new PhysicsHandler(bomberAbajo);
			bomberAbajo.registerUpdateHandler(physicsHandler);
		}
	}

	// centra vertical cuando queremos movernos en horizontal dentro de mismo
	// cuadro
	private boolean compruebaCentradoVertical() {
		final float yto = currentTileRectangle.getY();
		float y = baseTileRectangle.getY();
		return (yto == y);

	}

	private boolean compruebaCentradoHorizontal() {
		final float xto = currentTileRectangle.getX();
		float x = baseTileRectangle.getX();
		return (xto == x);

	}

	public void animarIzquierda() {
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION, 36, 47, true);
	}

	public void animarDerecha() {
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION, 24, 35, true);
	}

	public void animarArriba() {
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION, 12, 23, true);
	}

	public void animarAbajo() {
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION, 0, 11, true);
	}

	public void stopAnimation() {
		bomberAbajo.stopAnimation();
	}

	public boolean isMuerto() {
		synchronized (this.muerto) {
			return this.muerto;
		}

	}

	public void setMuerto() {
		synchronized (this.muerto) {
			muerto = true;
		}
	}

	public void setnoMuerto() {
		synchronized (this.muerto) {
			muerto = false;
		}
	}
	
	
	
	
	
	
	
	
	int [] array;
	public void morir(boolean fuego) {
		
		if (isMuerto()){
			return;		
		}
		setMuerto();
		
		Log.d("MORIR", "MORIR");
		
		int currentTile= bomberAbajo.getCurrentTileIndex();
		
		array= new int[]{48,49,48,49};
		if (fuego){
			if (0<=currentTile && currentTile<=11){
				////System.out.println("0-11");
				array = new int[]{48,49,48,49};
			}
			if (12<=currentTile && currentTile<=23){
				////System.out.println("12- 23");
				array = new int[]{50,51,50, 51};
			}			
			if (24<=currentTile && currentTile<=35){
				////System.out.println("2 -35");
				array = new int[]{52,53,52, 53};
			}
			if (36<=currentTile && currentTile<=47){
				////System.out.println("36-47");
				array = new int[]{54,55,54, 55};
			}
		
		}else {
			if (0<=currentTile && currentTile<=11){
				////System.out.println("0-11");
				array = new int[]{11,56,11, 56};
			}
			if (12<=currentTile && currentTile<=23){
				////System.out.println("12- 23");
				array = new int[]{23,23,23, 23};
			}
			if (24<=currentTile && currentTile<=35){
				////System.out.println("2 -35");
				array = new int[]{24,57,24, 57};
			}
			if (36<=currentTile && currentTile<=47){
				////System.out.println("36-47");
				array = new int[]{47,58,47, 58};
			}
			
		}		
		
		
		// bomber.stopAnimation();
		detenerPararAnimacion();
		final long tiempo = 500;
		Log.d("INICIO ANIMACION", "INICIO ANIMACION");
		
		new Thread(){
			public void run() {
				try {
					sleep(2500);
					context.gameManager.reiniciarPartida();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				

			};
		}.start();	

		bomberAbajo.animate(new long[] { tiempo, tiempo, tiempo, tiempo }, array, 0, new IAnimationListener() {

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
				Log.d("ANIMATION STARTED", "ANIMATION STARTED");

			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
				Log.d("onAnimationFrameChanged", "frame " + pNewFrameIndex);

			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
				Log.d("onAnimationLoopFinished", "onAnimationLoopFinished");

			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				Log.d("onAnimationFinished", "onAnimationFinished");
//				context.gameManager.reiniciarBomberMan();

			}
		});

	}

	public boolean matarPorCoordenadas(ArrayList<Coordenadas> coordenadas) {
		if (Constantes.DEBUG_IMMORTAL){
			return false;
		}
		for (Coordenadas coordenada : coordenadas) {
			if(coordenada.getColumna()==getColumna() && coordenada.getFila()==getFila()){
				if (isMuerto()){
					return false;
				}else{
					morir(true);
					return true;
				}

			}
		}
		return false;			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
