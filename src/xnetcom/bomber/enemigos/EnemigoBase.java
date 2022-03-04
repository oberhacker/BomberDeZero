package xnetcom.bomber.enemigos;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.util.Constants;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import android.util.Log;
public abstract class EnemigoBase {

	public enum Direction {
		NONE, UP, DOWN, LEFT, RIGHT
	}

	public enum TipoEnemigo {
		GLOBO, FANTASMA, MOCO, MONEDA, GOTA_AZUL, GOTA_NARANJA, GLOBO_AZUL, GOTA_ROJA, MOCO_ROJO, MONEDA_MARRON
	}
	AtomicBoolean estoyEnPared= new AtomicBoolean(false);
	public static int PIES_X = 32;
	public static int PIES_Y = 32;

	public Direction direccion = Direction.NONE;
	public TipoEnemigo tipoEnemigo;

	protected AnimatedSprite spritePrincipal;
	protected AnimatedSprite spritePrincipalTransparencia;
	public Rectangle currentTileRectangle;
	public Rectangle colidesTileRectangle;
	public Rectangle baseTileRectangle;
	
	protected Random generator;
	
	public Coordenadas coordenadas;
	BomberGame context;
	
	public boolean fantasma;
	public boolean moco;

	public AtomicBoolean muerto;
	
	protected float tiempoPorCuadro=Constantes.TIEMPO_POR_CUADRADO;
	int tiempoFotograma;
	
	protected float tiempoRetardo=0.4f;
	protected Direction direccionAnimacion=Direction.NONE;
	
	
	public EnemigoBase(BomberGame context){
		this.context = context;
		
		baseTileRectangle = new Rectangle(0, 0, Constantes.TILE_WIDTH, Constantes.TILE_HEIGHT, context.getVertexBufferObjectManager());
		baseTileRectangle.setOffsetCenter(0, 0);
		baseTileRectangle.setColor(50, 50, 50);
		baseTileRectangle.setScaleCenter(0, 0);
		
		
		colidesTileRectangle = new Rectangle(0, 0, Constantes.TILE_WIDTH - 20, Constantes.TILE_HEIGHT - 20, context.getVertexBufferObjectManager());
		colidesTileRectangle.setColor(0, 100, 50);
		colidesTileRectangle.setOffsetCenter(0, 0);
		colidesTileRectangle.setScaleCenter(0, 0);
		colidesTileRectangle.setPosition(10, 10);
		baseTileRectangle.attachChild(colidesTileRectangle);		
		baseTileRectangle.setZIndex(Constantes.ZINDEX_ENEMIGOS);
		
		currentTileRectangle = new Rectangle(0, 0, Constantes.TILE_WIDTH, Constantes.TILE_HEIGHT, context.getVertexBufferObjectManager());
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		currentTileRectangle.setZIndex(Constantes.ZINDEX_ENEMIGOS-1);
		
		context.escenaJuego.scene.attachChild(baseTileRectangle);
		context.escenaJuego.scene.attachChild(currentTileRectangle);
		
	}
	
	
	int secuencia = -1;

	public void iniciaMonedaExplotada(int secuencia, int columna, int fila) {
		this.secuencia = secuencia;
		inicia(columna, fila);
	}

	public void inicia (int columna, int fila) {
		
		colidesTileRectangle.setVisible(true);
		baseTileRectangle.setVisible(true);
		currentTileRectangle.setVisible(true);		
		generator= new Random();

		muerto = new AtomicBoolean(false);
		Log.d("COOR", " inicia columna "+columna +"  fila " +fila);
		coordenadas = new Coordenadas(columna, fila);

		baseTileRectangle.setPosition(coordenadas.getX(), coordenadas.getYCorregido());

		if (!Constantes.DEBUG_BASE_RECTANGLE_VISIBLE) {
			colidesTileRectangle.setAlpha(0f);
		}
		
		if (!Constantes.DEBUG_BASE_RECTANGLE_VISIBLE) {
			baseTileRectangle.setAlpha(0f);
		}
		if (!Constantes.DEBUG_CURRENT_RECTANGLE_VISIBLE){
			currentTileRectangle.setAlpha(0f);
		}
		
		
		baseTileRectangle.registerUpdateHandler(new TimerHandler(0.1f, true, new ITimerCallback() {			
			@Override			
			
			public void onTimePassed(final TimerHandler pTimerHandler) {
				updater();
			}
		}));		

	}	

	

	
	public int correccionTexturaPrincipalX=0;
	public int correccionTexturaPrincipalY=0;
	public void setPosicionCorreccionTexturaPrincipal(int x, int y){
		correccionTexturaPrincipalX=x;
		correccionTexturaPrincipalY=y;
	}
	
	
	
	public void detach() {
				direccion=Direction.NONE;
				direccionAnimacion=Direction.NONE;
				spritePrincipal.clearEntityModifiers();
				spritePrincipal.clearUpdateHandlers();
				spritePrincipal.setIgnoreUpdate(true);
				spritePrincipal.setVisible(false);
//				context.escenaJuego.scene.detachChild(spritePrincipal) ;	
				
				if (spritePrincipalTransparencia!=null){
					spritePrincipalTransparencia.clearEntityModifiers();
					spritePrincipalTransparencia.clearUpdateHandlers();
					spritePrincipalTransparencia.setIgnoreUpdate(true);
					spritePrincipalTransparencia.setVisible(false);					
				}
				
				
				currentTileRectangle.clearEntityModifiers();
				currentTileRectangle.clearUpdateHandlers();
				currentTileRectangle.setIgnoreUpdate(true);
				currentTileRectangle.setVisible(false);
//				context.escenaJuego.scene.detachChild(currentTileRectangle) ;				
				
				colidesTileRectangle.clearEntityModifiers();
				colidesTileRectangle.clearUpdateHandlers();
				colidesTileRectangle.setIgnoreUpdate(true);
				colidesTileRectangle.setVisible(false);
//				context.escenaJuego.scene.detachChild(colidesTileRectangle) ;
				
				baseTileRectangle.clearEntityModifiers();
				baseTileRectangle.clearUpdateHandlers();
				baseTileRectangle.setIgnoreUpdate(true);
				baseTileRectangle.setVisible(false);
//				context.escenaJuego.scene.detachChild(baseTileRectangle) ;

		
		
	}
	
	
	

	int innerColumna = 0;
	int innerFila = 0;

	protected void updater() {
		
//		Log.d("ENEMIGO", "UPDATE");
		final float[] playerFootCordinates = baseTileRectangle.convertLocalCoordinatesToSceneCoordinates(EnemigoBase.PIES_X, EnemigoBase.PIES_Y);
		TMXLayer tmxLayer = context.escenaJuego.mTMXTiledMap.getTMXLayers().get(1);
		TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);

		if (tmxTile != null) {
			if (innerColumna != tmxTile.getTileColumn()) {
				innerColumna = tmxTile.getTileColumn();
				innerFila = tmxTile.getTileRow();
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));
				if (innerColumna==0 ||innerFila==0 ){
					System.out.println();
				}
				cambiaPosicion(innerColumna, innerFila);
			}
			if (innerFila != tmxTile.getTileRow()) {
				innerFila = tmxTile.getTileRow();
				innerColumna = tmxTile.getTileColumn();
				currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn()) * Constantes.FARTOR_FORMA, tmxLayer.getTileY(tmxTile.getTileRow()));
				if (innerColumna==0 ||innerFila==0 ){
					System.out.println();
				}
				cambiaPosicion(innerColumna, innerFila);
			}
		}

	}
	
	private void cambiaPosicion(final int innerColumna, final int innerFila) {
		new Thread() {
			public void run() {
				try {
					synchronized (coordenadas) {
						Log.d("COOR", "cambiaPosicion Columna"+innerColumna +" Fila"+innerFila);
						coordenadas.setColumna(innerColumna);
						coordenadas.setFila(innerFila);
					}
					if(context.escenaJuego.matriz.getValor(innerFila, innerColumna).tipoCasilla==Matriz.PARED){
						estoyEnPared.set(true);
					}else{
						estoyEnPared.set(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

	}

	public Coordenadas getCoordenadas() {
//		synchronized (coordenadas) {
			return coordenadas;
//		}
	}

	public boolean puedoDerecha() {
		int salida = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(getCoordenadas().getFila(), getCoordenadas().getColumna() + 1).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (moco && salida == Matriz.PARED){
			return true;
		}else 	if (fantasma && (salida == Matriz.PARED || salida == Matriz.BOMBA)){
			return true;
		}else if (salida == Matriz.NADA || salida == Matriz.PUERTA || salida == Matriz.MONEDA) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedoIzquierda() {
		int salida = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(getCoordenadas().getFila(), getCoordenadas().getColumna() - 1).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (moco && salida == Matriz.PARED){
			return true;
		}else 	if (fantasma && (salida == Matriz.PARED || salida == Matriz.BOMBA)){
			return true;
		}else if (salida == Matriz.NADA || salida == Matriz.PUERTA || salida == Matriz.MONEDA) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedoArriba() {
		int salida = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(getCoordenadas().getFila() - 1, getCoordenadas().getColumna()).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (moco && salida == Matriz.PARED){
			return true;
		}else 	if (fantasma && (salida == Matriz.PARED || salida == Matriz.BOMBA)){
			return true;
		}else if (salida == Matriz.NADA || salida == Matriz.PUERTA || salida == Matriz.MONEDA) {
			return true;
		} else {
			return false;
		}
	}

	public boolean puedoAbajo() {
		int salida = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(getCoordenadas().getFila() + 1, getCoordenadas().getColumna()).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (moco && salida == Matriz.PARED){
			return true;
		}else 	if (fantasma && (salida == Matriz.PARED || salida == Matriz.BOMBA)){
			return true;
		}else if (salida == Matriz.NADA || salida == Matriz.PUERTA || salida == Matriz.MONEDA) {
			return true;
		} else {
			return false;
		}
	}

	
	public int numeroSalidas(){
		int salidas=0;
		if(puedoAbajo())salidas++;
		if(puedoArriba())salidas++;
		if(puedoDerecha())salidas++;
		if(puedoIzquierda())salidas++;
		return salidas;
	}
	
	public void matarBicho() {
		clearEntityModifiers();
		animarMuerte();
	}
	

	private void clearEntityModifiers() {
		spritePrincipal.clearEntityModifiers();
		if (spritePrincipalTransparencia!=null){
			spritePrincipalTransparencia.clearEntityModifiers();
		}
		currentTileRectangle.clearEntityModifiers();
		colidesTileRectangle.clearEntityModifiers();
		baseTileRectangle.clearEntityModifiers();
	}

	public TipoEnemigo getTipoEnemigo() {
		// TODO Auto-generated method stub
		return tipoEnemigo;
	}

	public class ListenerMorir implements IAnimationListener {

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
			

		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			detach();

		}
	}
	

	
	public void play(){
		baseTileRectangle.setIgnoreUpdate(false);
		spritePrincipal.setIgnoreUpdate(false);
	}	
	public void pausa(){
		baseTileRectangle.setIgnoreUpdate(true);
		spritePrincipal.setIgnoreUpdate(true);
	}
	
	public void iniciaInteligenciaIA() {
		direccion = EligeDireccion();
		baseTileRectangle.setIgnoreUpdate(false);
		currentTileRectangle.setIgnoreUpdate(false);
		spritePrincipal.setIgnoreUpdate(false);
		if (spritePrincipalTransparencia != null) {
			spritePrincipalTransparencia.setIgnoreUpdate(false);
		}
		new Thread() {
			public void run() {
				try {
					sleep(300);
					Log.d("INICIO INTELIGENCIAAA", "INICIO INTELIGENCIAAA");
					inteligencia();
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();
	}
	
	public void inteligencia(){			
		
		
		if (puedoSeguir(direccion)){// siguooooo
			
			if(tomaDecision(1, 100)<70){
				setDireccion(direccion);
				mover(direccion);				
				return;
			}else{// cambio de sentido
				Direction dir = cambiaDireccion(direccion);
				setDireccion(dir);
				mover(dir);
				return;

			}

		}else{// cambio	direccion	
			
			
			if(numeroSalidas()==0){// me paro
				tiempoMuerto();
				return;
			}else if (numeroSalidas()==1){//cambio	direccion  solo una salida
				Direction cambio =EligeDireccion();
				boolean cam = isCambioSentido(direccion, cambio);
				if(cam){
					setDireccion(cambio);
					retardo(cambio);
					return;
				}else{
					setDireccion(cambio);
					mover(cambio);
					return;
				}				
			}else{// cambio	direccion tengo multiples salidas
				
				// doy la vuelta  o cambio de direccion??
				if(tomaDecision(1, 100)<70){// doy la vuelta
					Direction cambio = cambioSentido(direccion);
					setDireccion(cambio);
					retardo(cambio);	
					return;
				}else{
					Direction cambio = cambiaDireccion(direccion);
					setDireccion(cambio);
					mover(cambio);
					return;
					
				}				
			}
			
		}
	}
	
	
	public void llegado(){
		inteligencia();
	}

	
	
	public void retardo(final Direction dir){		
		baseTileRectangle.registerEntityModifier(new DelayModifier(tiempoRetardo){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				mover(dir);
			}
		});	
	}
	
	public void tiempoMuerto(){			
		//revisar si este delaymodifier funciona bien
		baseTileRectangle.registerEntityModifier(new DelayModifier(tiempoPorCuadro){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				llegado();
			}
		});	
	}
	
	
	
	public Direction cambioSentido(Direction direccion){
		Direction direccionSalida=Direction.NONE;
		if(direccion==Direction.RIGHT)direccionSalida=Direction.LEFT;
		else if(direccion==Direction.LEFT)direccionSalida=Direction.RIGHT;
		else if(direccion==Direction.UP)direccionSalida=Direction.DOWN;
		else if(direccion==Direction.DOWN)direccionSalida=Direction.UP;
		return direccionSalida;
		
	}
	
	public boolean isCambioSentido(Direction anterior, Direction actual){
		boolean cambio=true;
		if(anterior==Direction.RIGHT && actual==Direction.LEFT);
		else if(anterior==Direction.LEFT && actual==Direction.RIGHT);
		else if(anterior==Direction.UP && actual==Direction.DOWN);
		else if(anterior==Direction.DOWN && actual==Direction.UP);
		else{
			cambio = false;
		}
		return cambio;
	}
	
	
	
	public Direction cambiaDireccion(Direction dir) {

		Direction dirSalida = Direction.NONE;

		int valorDerecha = tomaDecision(1, 1000);
		int valorIzquierda = tomaDecision(1, 1000);
		int valorArriba = tomaDecision(1, 1000);
		int valorAbajo = tomaDecision(1, 1000);
		int valorMaximo = -1;

		// con esto evitamos el cambio de sentido
		switch (dir) {
		case DOWN:
			valorArriba = -10;
			valorAbajo=1;
			break;
		case UP:
			valorAbajo = -10;
			valorArriba=1;
			break;

		case LEFT:
			valorDerecha = -10;
			valorIzquierda=1;
			break;

		case RIGHT:
			valorIzquierda = -10;
			valorDerecha=1;
			break;

		default:
			break;
		}

		if (valorDerecha >= valorMaximo && puedoDerecha()) {
			valorMaximo = valorDerecha;
			dirSalida = Direction.RIGHT;
		}
		if (valorIzquierda >= valorMaximo && puedoIzquierda()) {
			valorMaximo = valorIzquierda;
			dirSalida = Direction.LEFT;
		}
		if (valorArriba >= valorMaximo && puedoArriba()) {
			valorMaximo = valorArriba;
			dirSalida = Direction.UP;
		}
		if (valorAbajo >= valorMaximo && puedoAbajo()) {
			valorMaximo = valorAbajo;
			dirSalida = Direction.DOWN;
		}

		return dirSalida;

	}
	
	
	
	
	public void mover(Direction dir) {
		Log.d("muevo", "muevo");
		float fromX = getCoordenadas().getX();
		float fromY = getCoordenadas().getYCorregido();		
		float toX=fromX;
		float toY=fromY;
		
		switch (dir) {
		case DOWN:
			toY=fromY-Constantes.TILE_HEIGHT;
			baseTileRectangle.registerEntityModifier(new MoveYModifier(tiempoPorCuadro, fromY, toY) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					Log.d("muevo", "muevo");
					animarAbajo();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
					Log.d("llegado", "llegado");
				}
			});

			break;

		case UP:
			toY=fromY+Constantes.TILE_HEIGHT;
			baseTileRectangle.registerEntityModifier(new MoveYModifier(tiempoPorCuadro, fromY, toY) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					Log.d("muevo", "muevo");
					animarArriba();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
					Log.d("llegado", "llegado");
				}
			});
			break;
		case LEFT:
			toX=fromX-Constantes.TILE_WIDTH;
			baseTileRectangle.registerEntityModifier(new MoveXModifier(tiempoPorCuadro, fromX, toX) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					animarIzquierda();
					Log.d("muevo", "muevo");
					// playMusica();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
					Log.d("llegado", "llegado");
				}
			});
			break;
		case RIGHT:
			toX=fromX+Constantes.TILE_WIDTH;
			baseTileRectangle.registerEntityModifier(new MoveXModifier(tiempoPorCuadro, fromX, toX) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarDerecha();
					Log.d("muevo", "muevo");
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
					Log.d("llegado", "llegado");
				}
			});

			break;
			
		case NONE:		
			
			baseTileRectangle.registerEntityModifier(new MoveXModifier(tiempoPorCuadro, fromX, fromX) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					//animarDerecha();
					Log.d("moverNonNONE", "llegado");
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
					Log.d("llegadoNONE", "llegado");
				}
			});
			
		}
	}	
	
	public boolean matarPorCoordenadas(ArrayList<Coordenadas> coordenadas, int secuencia){
		// que no lo mate la bomba que lo creo
		if (this.secuencia==secuencia){
			return false;
		}
		for (Coordenadas coordenada : coordenadas) {
			if (!muerto.get()){
				if (getCoordenadas().getFila()==coordenada.getFila() && getCoordenadas().getColumna()==coordenada.getColumna()){				
					matarBicho();
					return true;
				}
			}		
		}
		return false;
	}
	
	
	
	public void setDireccion(Direction direccion){
		this.direccion=direccion;
	}
	
	
	public boolean puedoSeguir(Direction dir){
		switch (dir) {
		case DOWN:
			return puedoAbajo();			
		case UP:
			return puedoArriba();			
		case LEFT:
			return puedoIzquierda();			
		case RIGHT:
			return puedoDerecha();			
		}
		return false;
		
	}
	
	public Direction EligeDireccion(){

		
		Direction direccion = Direction.NONE;
		
		if(numeroSalidas()==0){
			return Direction.NONE;
		}		
		
		while (direccion == Direction.NONE) {
			
			int dir = tomaDecision(1, 4);
			
			switch (dir) {
			case 1:
				if (puedoAbajo()) {
					direccion = Direction.DOWN;
				}
				break;
			case 2:
				if (puedoArriba()) {
					direccion = Direction.UP;
				} 
				break;
			case 3:
				if (puedoIzquierda()) {
					direccion = Direction.LEFT;
				} 
				break;
			case 4:
				if (puedoDerecha()) {
					direccion = Direction.RIGHT;
				} 
				break;
			}
		}		
	
		return direccion;	
	}
	
	
	protected int tomaDecision(int aStart, int aEnd) {
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + aStart);
		return aleatorio;

	}
	
	
	public void detener(){
		baseTileRectangle.setIgnoreUpdate(true);
		spritePrincipal.stopAnimation();	
		if (spritePrincipalTransparencia!=null){
			spritePrincipalTransparencia.stopAnimation();	
		}
	}
	
	public abstract void animarDerecha();

	public abstract void animarIzquierda();

	public abstract void animarArriba();

	public abstract void animarAbajo();

	public abstract void animarMuerte();

}
