package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoBase.TipoEnemigo;
import android.util.Log;

public class EnemigoMocoRojo extends EnemigoBase {

	public EnemigoMocoRojo(BomberGame context) {
		super(context);
	}

	float tiempoPorCuadroRapido;
	float tiempoPorCuadroLento;

	public void inicia(int columna, int fila) {
		moco=true;
		super.inicia(columna, fila);

		tipoEnemigo = TipoEnemigo.MOCO_ROJO;
		tiempoPorCuadroRapido = 1.1f;
		tiempoPorCuadroLento = 0.5f;
		tiempoFotograma = 120;
		

		setPosicionCorreccionTexturaPrincipal(-10, 2);
		attachSpriteGroup();
		
		baseTileRectangle.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {

			}
			@Override
			public void onUpdate(float pSecondsElapsed) {
//				Log.d("UPDATE", "GLOBO baseTileRectangle");
				spritePrincipal.setPosition(baseTileRectangle.getX() + correccionTexturaPrincipalX, baseTileRectangle.getY() + correccionTexturaPrincipalY);
//				spritePrincipalTransparencia.setPosition(baseTileRectangle.getX() + correccionTexturaPrincipalX, baseTileRectangle.getY() + correccionTexturaPrincipalY);
			}
		});
		iniciaInteligenciaIA();
	}

	public void attachSpriteGroup() {
		// si no es null ya esta attchado
		if (spritePrincipal != null) {
			spritePrincipal.setVisible(true);
//			spritePrincipalTransparencia.setVisible(true);
			return;
		}

//		spritePrincipalTransparencia = new AnimatedSprite(0, 0, context.almacenEnemigos.mocoTR, context.getVertexBufferObjectManager());
//		spritePrincipalTransparencia.setOffsetCenter(0, 0);
		// spritePrincipalTransparencia.setScale(0.7f);
//		spritePrincipalTransparencia.setAlpha(0.7f);

		spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.mocoRojoTR, context.getVertexBufferObjectManager()) {
			@Override
			public void setCurrentTileIndex(int pCurrentTileIndex) {
//				spritePrincipalTransparencia.setCurrentTileIndex(pCurrentTileIndex);
				super.setCurrentTileIndex(pCurrentTileIndex);
			}
		};
		spritePrincipal.setAlpha(0.8f);
		spritePrincipal.setOffsetCenter(0, 0);
		// spritePrincipal.setScale(0.7f);

		spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
//		spritePrincipalTransparencia.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);

		context.almacenEnemigos.groupMocoRojo.attachChild(spritePrincipal);
//		context.almacenEnemigos.groupMocoTransparencia.attachChild(spritePrincipalTransparencia);

	}

	@Override
	public void mover(Direction dir) {
		if (estoyEnPared.get()) {
			tiempoPorCuadro = tiempoPorCuadroLento;
		} else {
			tiempoPorCuadro = tiempoPorCuadroRapido;
		}
		super.mover(dir);
	}

	@Override
	public void animarDerecha() {
		int tiempo = 120;
		if (direccionAnimacion != Direction.RIGHT) {
			direccionAnimacion = Direction.RIGHT;
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 0, 1, 2, 3, 4, 5, 6 }, true);
		}
	}

	@Override
	public void animarIzquierda() {
		int tiempo = 120;
		if (direccionAnimacion != Direction.LEFT) {
			direccionAnimacion = Direction.LEFT;
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 24, 25, 26, 27, 28, 29 }, true);
		}

	}

	@Override
	public void animarArriba() {
		int tiempo = 120;
		if (direccionAnimacion != Direction.UP) {
			direccionAnimacion = Direction.UP;
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 7, 8, 9, 10, 9, 8 }, true);
		}

	}

	@Override
	public void animarAbajo() {
		int tiempo = 120;
		if (direccionAnimacion != Direction.DOWN) {
			direccionAnimacion = Direction.DOWN;
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 12, 13, 14, 15, 14, 13 }, true);
		}

	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 16, 17, 18, 19, 20, 21, 22 }, 0, new ListenerMorir());

	}
}
