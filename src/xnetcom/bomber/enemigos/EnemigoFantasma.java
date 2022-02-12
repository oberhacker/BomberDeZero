package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import xnetcom.bomber.BomberGame;
import android.util.Log;

public class EnemigoFantasma extends EnemigoBase{	
	
	
	
	public EnemigoFantasma(BomberGame context) {
		super(context);
	}
	
	
	public void inicia(int columna, int fila) {
		fantasma=true;
		super.inicia(columna, fila);
		
		tipoEnemigo = TipoEnemigo.FANTASMA;
		tiempoPorCuadro = 0.70f;
		tiempoFotograma = 120;
		
		setPosicionCorreccionTexturaPrincipal(-15, -15);
		attachSpriteGroup();
		
		baseTileRectangle.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {

			}
			@Override
			public void onUpdate(float pSecondsElapsed) {
				Log.d("UPDATE", "GLOBO baseTileRectangle");
				spritePrincipal.setPosition(baseTileRectangle.getX() + correccionTexturaPrincipalX, baseTileRectangle.getY() + correccionTexturaPrincipalY);
				spritePrincipalTransparencia.setPosition(baseTileRectangle.getX() + correccionTexturaPrincipalX, baseTileRectangle.getY() + correccionTexturaPrincipalY);
			}
		});
		iniciaInteligenciaIA();
	}

	
	public void attachSpriteGroup() {
		// si no es null ya esta attchado
		if (spritePrincipal!=null){
			spritePrincipal.setVisible(true);	
			spritePrincipalTransparencia.setVisible(true);	
			return;
		}
	
		
		spritePrincipalTransparencia = new AnimatedSprite(0, 0, context.almacenEnemigos.fantasmaTR, context.getVertexBufferObjectManager());
		spritePrincipalTransparencia.setOffsetCenter(0, 0);
//		spritePrincipalTransparencia.setScale(0.7f);
		spritePrincipalTransparencia.setAlpha(0.7f);	
		
		
		spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.fantasmaTR, context.getVertexBufferObjectManager()){
			@Override
			public void setCurrentTileIndex(int pCurrentTileIndex) {
				spritePrincipalTransparencia.setCurrentTileIndex(pCurrentTileIndex);
				super.setCurrentTileIndex(pCurrentTileIndex);
			}
		};
		spritePrincipal.setAlpha(0.9f);
		spritePrincipal.setOffsetCenter(0, 0);
//		spritePrincipal.setScale(0.7f);	
		
		
		spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);		
		spritePrincipalTransparencia.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
		
		context.almacenEnemigos.groupFantasma.attachChild(spritePrincipal);		
		context.almacenEnemigos.groupFantasmaTransparencia.attachChild(spritePrincipalTransparencia);
		
		


	}
	
	
	
	@Override
	public void animarDerecha() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=Direction.RIGHT){	
			direccionAnimacion=Direction.RIGHT;					
			spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo},new int[]{22,23,24,23}, true);
		}
		
	}

	@Override
	public void animarIzquierda() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=Direction.LEFT){	
			direccionAnimacion=Direction.LEFT;
			spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo},new int[]{5,6,7, 6,}, true);
		}
		
	}

	@Override
	public void animarArriba() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=Direction.UP){	
			direccionAnimacion=Direction.UP;
			spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo,tiempo},new int[]{10, 11, 12, 11, 10, 13, 14,13}, true);
		}
		
	}

	@Override
	public void animarAbajo() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=Direction.DOWN){	
			direccionAnimacion=Direction.DOWN;
			spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo,tiempo},new int[]{0, 1, 2, 1, 0, 3, 4,3}, true);
		}
		
	}

	@Override
	public void animarMuerte() {
		int tiempo=120;	
		spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{15,15,15, 16, 17, 18,19}, 0, new ListenerMorir());
		
	}

}
