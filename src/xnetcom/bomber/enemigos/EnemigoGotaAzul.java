package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Matriz;
import android.util.Log;

public class EnemigoGotaAzul extends EnemigoBase {

	public EnemigoGotaAzul(BomberGame context) {
		super(context);
	}

	public void inicia(int columna, int fila) {
		super.inicia(columna, fila);

		tipoEnemigo = TipoEnemigo.GOTA_AZUL;
		tiempoPorCuadro = 0.70f;
		tiempoFotograma = 90;

		setPosicionCorreccionTexturaPrincipal(8, 3);
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
		if (spritePrincipal != null) {
			spritePrincipal.setVisible(true);
			return;
		} else {
			spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.gotaAzulTR, context.getVertexBufferObjectManager());
			spritePrincipal.setOffsetCenter(0, 0);
			spritePrincipal.setScale(1f);
			spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
			context.almacenEnemigos.groupGotaAzul.attachChild(spritePrincipal);

		}

	}

	public void inteligencia() {

		Direction dirSeguidor = seguidor();

		Direction direccionActual = this.direccion;
		Direction direccionSalida = this.direccion;
		boolean retardo = false;

		if (dirSeguidor != Direction.NONE && puedoSeguir(dirSeguidor)) {
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida = dirSeguidor;

		} else {

			// no estoy siguendo pudo seguir o no

			// aki hay un fallo

			if (puedoSeguir(direccionActual)) {
				direccionSalida = direccionActual;

				if (tomaDecision(1, 100) < 85) {
					direccionSalida = direccionActual;
				} else {
					direccionSalida = cambiaDireccion(direccionActual);
				}
				// direccionSalida =caminoMuyAndado(direccionSalida); // de
				// momento lo quito que me gusta que vaya mas a su bola
			} else {// si no puedo segui por donde iba
				if (enfadado)
					retardo = true;
				if (tomaDecision(1, 100) < 30) {
					direccionSalida = cambiaDireccion(direccionActual);
				} else {
					direccionSalida = cambioSentido(direccionActual);

				}

				if (direccionSalida.equals(direccionActual)) {
					direccionSalida = cambioSentido(direccionActual);
				}

				// aki podria llegar con direccion NONE
				if (direccionSalida.equals(Direction.NONE)) {
					direccionSalida = EligeDireccion();
				}
			}
			setDireccion(direccionSalida);
			setEnfadado(false);

		}

		if (isCambioSentido(direccionActual, direccionSalida)) {
			retardo(direccionSalida);
		} else {
			if (!retardo) {
				mover(direccionSalida);
			} else {
				retardo(direccionSalida);
			}

		}

	}

	public Direction seguidor() {
		int X = context.bomberman.getColumna();
		int Y = context.bomberman.getFila();
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();

		Direction dir = Direction.NONE;
		int diferencia = 0;
		if ((X == columna - 1 || X == columna - 2 || X == columna - 3) && fila == Y) {
			diferencia = columna - X;
			dir = Direction.LEFT;
		}

		if ((X == columna + 1 || X == columna + 2 || X == columna + 3) && fila == Y) {
			dir = Direction.RIGHT;
			diferencia = X - columna;
		}

		if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3) && columna == X) {
			dir = Direction.UP;
			diferencia = fila - Y;
		}

		if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3) && columna == X) {
			dir = Direction.DOWN;
			diferencia = Y - fila;
		}

		switch (dir) {
		case LEFT:
			try {
				if (diferencia > 1 && context.escenaJuego.matriz.getValor(fila, columna - 1).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 2 && context.escenaJuego.matriz.getValor(fila, columna - 2).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 3 && context.escenaJuego.matriz.getValor(fila, columna - 3).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			break;
		case RIGHT:
			try {
				if (diferencia > 1 && context.escenaJuego.matriz.getValor(fila, columna + 1).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 2 && context.escenaJuego.matriz.getValor(fila, columna + 2).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 3 && context.escenaJuego.matriz.getValor(fila, columna + 3).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			break;

		case UP:

			try {
				if (diferencia > 1 && context.escenaJuego.matriz.getValor(fila - 1, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			try {
				if (diferencia > 2 && context.escenaJuego.matriz.getValor(fila - 2, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			try {
				if (diferencia > 3 && context.escenaJuego.matriz.getValor(fila - 3, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			break;

		case DOWN:
			try {
				if (diferencia > 1 && context.escenaJuego.matriz.getValor(fila + 1, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 2 && context.escenaJuego.matriz.getValor(fila + 2, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			try {
				if (diferencia > 3 && context.escenaJuego.matriz.getValor(fila + 3, columna).tipoCasilla != Matriz.NADA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			break;

		case NONE:
			break;
		}

		return dir;
	}

	protected boolean enfadado = false;

	public void setEnfadado(boolean enfadado) {
		if (enfadado) {
			this.enfadado = true;
			tiempoPorCuadro = 0.40f;
		} else {
			this.enfadado = false;
			tiempoPorCuadro = 0.70f;
		}
	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public void animarDerecha() {
		int tiempo = 120;
		if (enfadado) {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 42, 41, 40, 59, 58, 57, 56 }, true);
		} else {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 49, 48, 47, 46, 45, 44, 43 }, true);
		}

	}

	
	
	
	
	
	
	
	
	
	@Override
	public void animarIzquierda() {
		int tiempo = 120;
		if (enfadado) {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 7, 8, 9, 10, 11, 12, 13 }, true);
		} else {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 0, 1, 2, 3, 4, 5, 6 }, true);
		}

	}

	@Override
	public void animarArriba() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 18, 19, 20, 21, 20, 19 }, true);

	}

	@Override
	public void animarAbajo() {
		int tiempo = 120;
		if (enfadado) {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 22, 23, 24, 25, 26, 27, 28, 29 }, true);
		} else {
			spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo }, new int[] { 14, 15, 16, 17 }, 1000);
		}

	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		spritePrincipal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 30, 31, 32, 33, 34, 35, 36 }, 0, new ListenerMorir());

	}

}
