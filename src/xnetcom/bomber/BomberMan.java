package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.Constants;
import org.andengine.util.modifier.IModifier;

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
	
	boolean fantasma=false;

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
		bomberArriba.setScaleY(FACTOR_ACHATADO);
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
		bomberAbajo.setScaleY(FACTOR_ACHATADO);

		bomberAbajo.attachChild(bomberArriba);

		// ajustamos el personaje dento de su cuadro
		bomberAbajo.setX(bomberAbajo.getX() - 20);

		bomberAbajo.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO);
		bomberArriba.setZIndex(Constantes.ZINDEX_BOMBERMAN_ARRIBA);

	}

	public void onCreateScene(Scene scene) {

		currentTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth()*Constantes.FARTOR_FORMA, context.escenaJuego.mTMXTiledMap.getTileHeight(), context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
//		currentTileRectangle.setScaleX(Constantes.FARTOR_FORMA);
		currentTileRectangle.setPosition(context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileX(2), context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileY(2));
		currentTileRectangle.setPosition(2 * context.escenaJuego.mTMXTiledMap.getTileWidth() * Constantes.FARTOR_FORMA, currentTileRectangle.getY());

		currentTileRectangle.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO - 2);
		if (!Constantes.DEBUG_CURRENT_RECTANGLE_VISIBLE) {
			currentTileRectangle.setAlpha(0f);
		}

		baseTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth()*Constantes.FARTOR_FORMA, context.escenaJuego.mTMXTiledMap.getTileHeight(), context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		baseTileRectangle.setOffsetCenter(0, 0);
		baseTileRectangle.setColor(50, 50, 50);
		baseTileRectangle.setScaleCenter(0, 0);
//		baseTileRectangle.setScaleX(Constantes.FARTOR_FORMA);
		if (!Constantes.DEBUG_BASE_RECTANGLE_VISIBLE) {
			baseTileRectangle.setAlpha(0f);
		}

		physicsHandler = new PhysicsHandler(baseTileRectangle);
		baseTileRectangle.registerUpdateHandler(physicsHandler);

		baseTileRectangle.attachChild(bomberAbajo);
		baseTileRectangle.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO - 1);

		baseTileRectangle.setPosition(2 * Constantes.TILE_TAM * Constantes.FARTOR_FORMA, context.escenaJuego.mTMXTiledMap.getHeight() - 3 * Constantes.TILE_TAM);

		scene.attachChild(currentTileRectangle);
		scene.attachChild(baseTileRectangle);

		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				updater();
			}

			@Override
			public void reset() {

			}
		});
	}

	int innerColumna = 0;
	int innerFila = 0;

	public void updater() {
		final float[] playerFootCordinates = getSprite().convertLocalCoordinatesToSceneCoordinates(BomberMan.PIES_X, BomberMan.PIES_Y);
		TMXLayer tmxLayer = context.escenaJuego.mTMXTiledMap.getTMXLayers().get(1);
		TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);

		if (tmxTile != null) {
			if (innerColumna != tmxTile.getTileColumn()) {
				innerColumna = tmxTile.getTileColumn();
				setColumna(tmxTile.getTileColumn());
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));
				
			}
			if (innerFila != tmxTile.getTileRow()) {
				innerFila = tmxTile.getTileRow();
				setFila(tmxTile.getTileRow());
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));

			}
			Log.d("POSICION", "FILA: "+tmxTile.getTileRow() +" Columna: "+tmxTile.getTileColumn() +" VALOR: "+context.escenaJuego.matriz.getValor(tmxTile.getTileRow(), tmxTile.getTileColumn()));
			
		}
	}



	public PlayerPosicion getEskinado() {
		// //System.out.println("getEskinado()");
		PlayerPosicion posicionRelativa = PlayerPosicion.CENTRO;

		float difY = (currentTileRectangle.getY()) - baseTileRectangle.getY();
		// float signoY
		// =(context.getGameManager().getCurrentTileRectangle().getY())-cuadrado.getY();

		float difX = (currentTileRectangle.getX()) - baseTileRectangle.getX();
		// float signoX
		// =(context.getGameManager().getCurrentTileRectangle().getX())-cuadrado.getX();

		// habria que sustituir los 26 po un mayor que cero

		Log.d("camino", "getEskinado() difY " + difY + "difX " + difX);
		if (difX == 0 && difY == 0) {
			// centro
			posicionRelativa = PlayerPosicion.CENTRO;
		} else if (difX <= -17) {
			// izquierda
			posicionRelativa = PlayerPosicion.MUY_DERECHA;
		} else if (difX >= 17) {
			// derecha
			posicionRelativa = PlayerPosicion.MUY_IZQUIERDA;
		} else if (difY <= -17) {
			// arriba
			posicionRelativa = PlayerPosicion.MUY_ABAJO;
		} else if (difY >= 17) {
			// abajo
			posicionRelativa = PlayerPosicion.MUY_ARRIBA;
		}
		return posicionRelativa;
	}

	public void cambiaPosicion(int fila, int columna) {

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

//	public void mover(PlayerDirection direccion){
//		baseTileRectangle.clearEntityModifiers();
//		int fila=getFila();
//		int columna=getColumna();
//		switch (direccion) {
//		case DOWN:
//			compro
//			
//			break;
//		case UP:
//			
//			break;
//
//		case RIGHT:
//			
//			break;
//		case LEFT:
//			
//			break;
//		case NONE:
//			
//			break;
//		}
//		
//	}
	
	
	public boolean isLimiteArriba(){context.escenaJuego.matriz.getValor(2, 2);
		int sitio =context.escenaJuego.matriz.getValor(getFila()-1, getColumna());
		if (!fantasma){	
			if (sitio==Matriz.PARED || sitio==Matriz.MURO ||sitio==Matriz.BOMBA){				
				return true;
			}
		}else{
			if (sitio==Matriz.MURO){				
				return true;
			}
		}		
		return false;
	}
	
	public boolean isLimiteAbajo(){
		int sitio =context.escenaJuego.matriz.getValor(getFila()+1,getColumna());
		
		if (!fantasma){			
			if (sitio==Matriz.PARED || sitio==Matriz.MURO ||sitio==Matriz.BOMBA){			
				return true;
			}
		}else{
			if (sitio==Matriz.MURO){				
				return true;
			}
		}		
		return false;
	}
	
	
	public boolean isLimiteDerecha(){
		int sitio =context.escenaJuego.matriz.getValor(getFila(),getColumna()+1);
		if (!fantasma){			
			if (sitio==Matriz.PARED || sitio==Matriz.MURO ||sitio==Matriz.BOMBA){				
				return true;
			}
		}else{
			if (sitio==Matriz.MURO){				
				return true;
			}
		}	
		return false;
	}
	
	public boolean isLimiteIzquierda(){
		int sitio =context.escenaJuego.matriz.getValor(getFila(),getColumna()-1);	
		if (!fantasma){			
			if (sitio==Matriz.PARED || sitio==Matriz.MURO ||sitio==Matriz.BOMBA){				
				return true;
			}
		}else{
			if (sitio==Matriz.MURO){
				return true;
			}
		}
		return false;

	}
	
	
	
	public void centrar() {
		final float xto = currentTileRectangle.getX();
		final float yto = currentTileRectangle.getY();

		float x = baseTileRectangle.getX();
		float y = baseTileRectangle.getY();

		float mover = 0;
		if (xto == x) {
			mover = yto - y;
		} else {
			mover = xto - x;
		}
		
		baseTileRectangle.clearEntityModifiers();
		
		if(xto<x){
			animarIzquierda();
		}else if (xto>x){
			animarDerecha();
		}else if (yto<y){
			animarArriba();
		}else if (yto>y){
			animarAbajo();
		}		
		

		if (mover!=0){
			baseTileRectangle.registerEntityModifier(new MoveModifier(Math.abs(Constantes.TIEMPO_POR_PIXEL * mover), x,y,xto,yto, new IEntityModifierListener() {
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
		}else{
			terminadoCentrar();	
		}


	}
	
	
	public void terminadoCentrar(){
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
		detener();
		playerDirection=PlayerDirection.UP;
		if (!isLimiteArriba()){
			centrar();
		}else{
			animarArriba();
		}
	}
	
	public void moverArribaSinFin() {
		animarArriba();
		physicsHandler.setVelocity(0, VELOCIDAD_RECTO_Y);
	}
	
	
	public void moverAbajo() {
		detener();
		playerDirection=PlayerDirection.DOWN;
		if (!isLimiteAbajo()){
			centrar();
		}else{
			animarAbajo();
		}

	}
	public void moverAbajoSinFin() {
		animarAbajo();
		physicsHandler.setVelocity(0, -VELOCIDAD_RECTO_Y);
	}

	public void moverDerecha() {
		detener();
		playerDirection=PlayerDirection.RIGHT;
		if (!isLimiteDerecha()){
			centrar();
		}else{
			animarDerecha();
		}
	}
	public void moverDerechaSinFin() {
		animarDerecha();
		physicsHandler.setVelocity(VELOCIDAD_RECTO_X, 0);
	}

	public void moverIzquierda() {
		detener();
		playerDirection=PlayerDirection.LEFT;
		if (!isLimiteIzquierda()){
			centrar();
		}else{
			animarIzquierda();
		}
	}
	
	public void moverIzquierdaSinFin() {
		animarIzquierda();
		physicsHandler.setVelocity(-VELOCIDAD_RECTO_X, 0);
	}

	public void detener() {
		physicsHandler.setVelocity(0, 0);
		baseTileRectangle.clearEntityModifiers();
	}

	public void detenerPararAnimacion() {
		stopAnimation();			
		detener();
	}

	public void moverRecto() {
		if (physicsHandler == null) {
			physicsHandler = new PhysicsHandler(bomberAbajo);
			bomberAbajo.registerUpdateHandler(physicsHandler);
		}
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

}
