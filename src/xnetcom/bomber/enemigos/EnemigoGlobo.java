package xnetcom.bomber.enemigos;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;

public class EnemigoGlobo extends EnemigoBase {

	public EnemigoGlobo(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = TipoEnemigo.GLOBO;
		tiempoPorCuadro = 0.70f;
		tiempoFotograma = 120;
		TiledTextureRegion principalTR = context.almacenEnemigos.globoTR;
		spritePrincipal = new AnimatedSprite(0, -20, principalTR, context.getVertexBufferObjectManager());
		spritePrincipal.setOffsetCenter(0, 0);
		spritePrincipal.setScale(0.7f);
		// principal.setZIndex(BomberGame.ZINDEX_ENEMIGOS);

		baseTileRectangle.attachChild(spritePrincipal);

		iniciaInteligenciaIA();

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
		if (direccionAnimacion!=Direction.RIGHT){	
			direccionAnimacion=Direction.RIGHT;
			spritePrincipal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}		
	}

	@Override
	public void animarIzquierda() {
		if (direccionAnimacion!=Direction.LEFT){	
			direccionAnimacion=Direction.LEFT;
			spritePrincipal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}		
		
	}

	@Override
	public void animarArriba() {
		if (direccionAnimacion!=Direction.UP){	
			direccionAnimacion=Direction.UP;
			spritePrincipal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{6, 7, 8, 9, 10, 11, 10, 9, 8, 7}, 1000);
		}
		
		
	}

	@Override
	public void animarAbajo() {
		if (direccionAnimacion!=Direction.DOWN){	
			direccionAnimacion=Direction.DOWN;
			spritePrincipal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}
	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 13, 14, 15, 16, 17, 18, 19 }, 0, new ListenerMorir());

	}

}
