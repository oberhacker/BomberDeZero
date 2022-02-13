package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import xnetcom.bomber.BomberGame;
import android.util.Log;

public class EnemigoMonedaMarron extends EnemigoBase {

	public EnemigoMonedaMarron(BomberGame context) {
		super(context);
	}

	public void inicia(int columna, int fila) {
		super.inicia(columna, fila);

		
		tipoEnemigo = TipoEnemigo.MONEDA_MARRON;
		tiempoPorCuadro = 0.25f;
		tiempoFotograma = 80;
		tiempoRetardo=0;
		setPosicionCorreccionTexturaPrincipal(-3, -3);		
		attachSpriteGroup();		
	
		baseTileRectangle.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {

			}
			@Override
			public void onUpdate(float pSecondsElapsed) {
//				Log.d("UPDATE", "GLOBO baseTileRectangle");
				spritePrincipal.setPosition(baseTileRectangle.getX() + correccionTexturaPrincipalX, baseTileRectangle.getY() + correccionTexturaPrincipalY);

			}
		});
		iniciaInteligenciaIA();

	}	


	public void attachSpriteGroup() {
		// si no es null ya esta attchado
		if (spritePrincipal!=null){
			spritePrincipal.setVisible(true);	
			return;
		}else{
			spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.monedaMarronTR, context.getVertexBufferObjectManager());
			spritePrincipal.setOffsetCenter(0, 0);
			spritePrincipal.setScale(0.9f);
			spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
			context.almacenEnemigos.groupMonedaMarron.attachChild(spritePrincipal);

		}


	}

	public void animar(){
		if (!spritePrincipal.isAnimationRunning()){
			int tiempo=80;
			spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2, 3,4,5,6,7,8,3,9,10}, 1000);
		}		
	}
	
	@Override
	public void animarDerecha() {
		animar();
	}

	@Override
	public void animarIzquierda() {
		animar();		
	}

	@Override
	public void animarArriba() {
		animar();
		
	}

	@Override
	public void animarAbajo() {
		animar();		
	}
	@Override
	public void animarMuerte() {
		int tiempo=120;

		spritePrincipal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{11,12,13, 14, 15, 16,17}, 0,new ListenerMorir());
	}
	
	
}
