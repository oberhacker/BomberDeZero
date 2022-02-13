package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;

public class EnemigoGloboAzul extends EnemigoBase {

	public EnemigoGloboAzul(BomberGame context) {
		super(context);
	}

	public void inicia(int columna, int fila) {
		super.inicia(columna, fila);

		
		tipoEnemigo = TipoEnemigo.GLOBO_AZUL;
		tiempoPorCuadro = 0.60f;
		tiempoFotograma = 100;
		tiempoRetardo=0.1f;

		setPosicionCorreccionTexturaPrincipal(-2, -25);
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
			spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.globoAzulTR, context.getVertexBufferObjectManager());
			spritePrincipal.setOffsetCenter(0, 0);
			spritePrincipal.setScale(0.7f);
			spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
			context.almacenEnemigos.groupGloboAzul.attachChild(spritePrincipal);
//			try {
//				context.escenaJuego.scene.sortChildren();
//			} catch (Exception e) {
//				Log.e("ERROR GLOBO ", "ORDENAR");
//			}
		}


	}


	@Override
	public void animarDerecha() {
		if (direccionAnimacion != Direction.RIGHT) {
			direccionAnimacion = Direction.RIGHT;
			spritePrincipal.animate(new long[] { tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma,
					tiempoFotograma, tiempoFotograma }, new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 }, true);
		}
	}

	@Override
	public void animarIzquierda() {
		if (direccionAnimacion != Direction.LEFT) {
			direccionAnimacion = Direction.LEFT;
			spritePrincipal.animate(new long[] { tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma,
					tiempoFotograma, tiempoFotograma }, new int[] { 24, 23, 22, 21, 20, 19, 20, 21, 22, 23 }, true);
		}

	}

	@Override
	public void animarArriba() {
		if (direccionAnimacion != Direction.UP) {
			direccionAnimacion = Direction.UP;
			spritePrincipal.animate(new long[] { tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma,
					tiempoFotograma, tiempoFotograma }, new int[] { 6, 7, 8, 9, 10, 11, 10, 9, 8, 7 }, true);
		}

	}

	@Override
	public void animarAbajo() {
		if (direccionAnimacion != Direction.DOWN) {
			direccionAnimacion = Direction.DOWN;
			spritePrincipal.animate(new long[] { tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma, tiempoFotograma,
					tiempoFotograma, tiempoFotograma }, new int[] { 26, 27, 28, 29, 30, 31, 30, 29, 28, 27 }, true);
		}
	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo}, new int[] { 12, 13, 14, 14, 16, 17, 18  }, 0, new ListenerMorir());

	}

}
