package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;

public class EnemigoGlobo extends EnemigoBase {

	public EnemigoGlobo(BomberGame context) {
		super(context);
	}

	public void inicia(int columna, int fila) {
		super.inicia(columna, fila);

		
		tipoEnemigo = TipoEnemigo.GLOBO;
		tiempoPorCuadro = 0.70f;
		tiempoFotograma = 120;

		setPosicionCorreccionTexturaPrincipal(-2, -20);
		attachSpriteGroup();		
	
		baseTileRectangle.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {

			}
			@Override
			public void onUpdate(float pSecondsElapsed) {
				Log.d("UPDATE", "GLOBO baseTileRectangle");
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
			spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.globoTR, context.getVertexBufferObjectManager());
			spritePrincipal.setOffsetCenter(0, 0);
			spritePrincipal.setScale(0.7f);
			spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
			context.almacenEnemigos.groupGlobo.attachChild(spritePrincipal);
			try {
				context.escenaJuego.scene.sortChildren();
			} catch (Exception e) {
				Log.e("ERROR GLOBO ", "ORDENAR");
			}
		}


	}

	@Override
	public void mover(Direction dir) {
		if (dir == Direction.LEFT) {
			spritePrincipal.setFlippedHorizontal(true);
		} else if (dir == Direction.RIGHT) {
			spritePrincipal.setFlippedHorizontal(false);
		}
		super.mover(dir);
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
					tiempoFotograma, tiempoFotograma }, new int[] { 25, 24, 23, 22, 21, 20, 21, 22, 23, 24 }, true);
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
					tiempoFotograma, tiempoFotograma }, new int[] { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1 }, true);
		}
	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo}, new int[] { 13, 14, 15, 16, 17, 18, 19 }, 0, new ListenerMorir());

	}

}
