package xnetcom.bomber.enemigos;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseExponentialOut;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Matriz;

public class EnemigoGotaRoja extends EnemigoBase {

	public EnemigoGotaRoja(BomberGame context) {
		super(context);
	}

	public void inicia(int columna, int fila) {
		super.inicia(columna, fila);

		tipoEnemigo = TipoEnemigo.GOTA_ROJA;
		tiempoPorCuadro = 0.60f;
		tiempoFotograma = 90;

		setPosicionCorreccionTexturaPrincipal(8, 3);
		attachSpriteGroup();

		baseTileRectangle.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {

			}

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// Log.d("UPDATE", "GLOBO baseTileRectangle");
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
			spritePrincipal = new AnimatedSprite(0, 0, context.almacenEnemigos.gotaRojaTR, context.getVertexBufferObjectManager());
			spritePrincipal.setOffsetCenter(0, 0);
			spritePrincipal.setScale(1f);
			spritePrincipal.setPosition(coordenadas.getX() + correccionTexturaPrincipalX, coordenadas.getYCorregido() + correccionTexturaPrincipalY);
			context.almacenEnemigos.groupGotaRoja.attachChild(spritePrincipal);

		}

	}

	public void inteligencia() {
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		try {
			if (context.escenaJuego.matriz.getValor(fila - 1, columna).tipoCasilla == Matriz.BOMBA) {
				System.out.println("bommmm");
			}
		} catch (Exception e) {
			System.out.println();
		}

		Direction dirSeguidor = seguidor();
		Direction dirSeguidorSalto = seguidorSalto();

		if (dirSeguidor == Direction.NONE && dirSeguidorSalto != Direction.NONE) {
			System.out.println("");
		}

		Direction direccionActual = this.direccion;
		Direction direccionSalida = this.direccion;
		boolean retardo = false;

		if (dirSeguidor != Direction.NONE && puedoSeguir(dirSeguidor)) {
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida = dirSeguidor;

		} else if (dirSeguidorSalto != Direction.NONE && puedoSeguirSalto(dirSeguidorSalto)) {
			jump(dirSeguidorSalto);
			setEnfadado(true);
			setDireccion(dirSeguidorSalto);
			direccionSalida = dirSeguidorSalto;
			moverDosCuadros(direccionSalida);
			return;
		} else {

			if (puedoSeguir(direccionActual)) {

				if (tomaDecision(1, 100) < 60) {
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
		if ((X == columna - 1 || X == columna - 2 || X == columna - 3|| X == columna - 4) && fila == Y) {
			diferencia = columna - X;
			dir = Direction.LEFT;
		}

		if ((X == columna + 1 || X == columna + 2 || X == columna + 3|| X == columna + 4) && fila == Y) {
			dir = Direction.RIGHT;
			diferencia = X - columna;
		}

		if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3|| Y == fila - 4) && columna == X) {
			dir = Direction.UP;
			diferencia = fila - Y;
		}

		if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3|| Y == fila + 4) && columna == X) {
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
			try {
				if (diferencia > 4 && context.escenaJuego.matriz.getValor(fila, columna - 4).tipoCasilla != Matriz.NADA) {
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
			try {
				if (diferencia > 4 && context.escenaJuego.matriz.getValor(fila, columna + 4).tipoCasilla != Matriz.NADA) {
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
			try {
				if (diferencia > 4 && context.escenaJuego.matriz.getValor(fila - 4, columna).tipoCasilla != Matriz.NADA) {
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
			try {
				if (diferencia > 4 && context.escenaJuego.matriz.getValor(fila + 4, columna).tipoCasilla != Matriz.NADA) {
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

	private void jump(Direction dirSeguidorSalto) {

		if (dirSeguidorSalto == Direction.DOWN || dirSeguidorSalto == Direction.UP) {
			final float jumpDuration = 0.5f;
			SequenceEntityModifier jumpModifier = new SequenceEntityModifier(new ScaleModifier(jumpDuration, 1, 1.3f, EaseExponentialOut.getInstance()), new ScaleModifier(jumpDuration, 1.3f, 1f,
					EaseBounceOut.getInstance()));
			spritePrincipal.registerEntityModifier(jumpModifier);
		} else {
			final float jumpDuration = 0.5f;
			final int jumpHeight = 50;
			final float startY = spritePrincipal.getY();
			final float peakY = startY + jumpHeight;

			SequenceEntityModifier jumpModifier = new SequenceEntityModifier(new MoveYModifier(jumpDuration, startY, peakY, EaseExponentialOut.getInstance()), new MoveYModifier(jumpDuration, peakY,
					startY, EaseBounceOut.getInstance()));
			spritePrincipal.registerEntityModifier(jumpModifier);
		}

	}

	public boolean puedoSeguirSalto(Direction dir) {
		switch (dir) {
		case DOWN:
			return puedoAbajoSalto();
		case UP:
			return puedoArribaSalto();
		case LEFT:
			return puedoIzquierdaSalto();
		case RIGHT:
			return puedoDerechaSalto();
		}
		return false;

	}

	/**
	 * significa que en la inmediata casilla a su derecha tiene una bomba, pero
	 * en la siguente esta libre si esta enfadado puede saltar esa bomba he ir a
	 * por ti
	 * 
	 * @return
	 */
	public boolean puedoDerechaSalto() {
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		int salida = -1;
		int salida2 = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(fila, columna + 1).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (salida == Matriz.BOMBA) {
			try {
				salida2 = context.escenaJuego.matriz.getValor(fila, columna + 2).tipoCasilla;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (salida2 == Matriz.NADA || salida2 == Matriz.PUERTA || salida == Matriz.MONEDA) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean puedoIzquierdaSalto() {
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		int salida = -1;
		int salida2 = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(fila, columna - 1).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (salida == Matriz.BOMBA) {
			try {
				salida2 = context.escenaJuego.matriz.getValor(fila, columna - 2).tipoCasilla;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (salida2 == Matriz.NADA || salida2 == Matriz.PUERTA || salida == Matriz.MONEDA) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean puedoArribaSalto() {
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		int salida = -1;
		int salida2 = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(fila - 1, columna).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (salida == Matriz.BOMBA) {
			try {
				salida2 = context.escenaJuego.matriz.getValor(fila - 2, columna).tipoCasilla;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (salida2 == Matriz.NADA || salida2 == Matriz.PUERTA || salida == Matriz.MONEDA) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean puedoAbajoSalto() {
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		int salida = -1;
		int salida2 = -1;
		try {
			salida = context.escenaJuego.matriz.getValor(fila + 1, columna).tipoCasilla;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (salida == Matriz.BOMBA) {
			try {
				salida2 = context.escenaJuego.matriz.getValor(fila + 2, columna).tipoCasilla;
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (salida2 == Matriz.NADA || salida2 == Matriz.PUERTA || salida == Matriz.MONEDA) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Direction seguidorSalto() {
		int X = context.bomberman.getColumna();
		int Y = context.bomberman.getFila();
		int columna = coordenadas.getColumna();
		int fila = coordenadas.getFila();
		Direction dir = Direction.NONE;
		int diferencia = 0;
		if ((X == columna - 1 || X == columna - 2 || X == columna - 3 || X == columna - 4 || X == columna - 5 || X == columna - 6) && fila == Y) {
			diferencia = columna - X;
			dir = Direction.LEFT;
		}

		if ((X == columna + 1 || X == columna + 2 || X == columna + 3 || X == columna + 4 || X == columna + 5 || X == columna + 6) && fila == Y) {
			dir = Direction.RIGHT;
			diferencia = X - columna;
		}

		if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3 || Y == fila - 4 || Y == fila - 5 || Y == fila - 6) && columna == X) {
			dir = Direction.UP;
			diferencia = fila - Y;
		}

		if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3 || Y == fila + 4 || Y == fila + 5 || Y == fila + 6) && columna == X) {
			dir = Direction.DOWN;
			diferencia = Y - fila;
		}

		switch (dir) {
		case LEFT:
			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 1).tipoCasilla;
				if (diferencia > 1 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 2).tipoCasilla;
				if (diferencia > 2 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 3).tipoCasilla;
				if (diferencia > 3 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 4).tipoCasilla;
				if (diferencia > 4 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 5).tipoCasilla;
				if (diferencia > 5 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int izq = context.escenaJuego.matriz.getValor(fila, columna - 6).tipoCasilla;
				if (diferencia > 6 && izq != Matriz.NADA && izq != Matriz.BOMBA && izq != Matriz.MONEDA && izq != Matriz.PUERTA) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			break;
		case RIGHT:
			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 1).tipoCasilla;
				if (diferencia > 1 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 2).tipoCasilla;
				if (diferencia > 2 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 3).tipoCasilla;
				if (diferencia > 3 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 4).tipoCasilla;
				if (diferencia > 4 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 5).tipoCasilla;
				if (diferencia > 5 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int der = context.escenaJuego.matriz.getValor(fila, columna + 6).tipoCasilla;
				if (diferencia > 6 && isNoSaltable(der)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			break;

		case UP:

			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 1, columna).tipoCasilla;
				if (diferencia > 1 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 2, columna).tipoCasilla;
				if (diferencia > 2 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 3, columna).tipoCasilla;
				if (diferencia > 3 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 4, columna).tipoCasilla;
				if (diferencia > 4 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}

			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 5, columna).tipoCasilla;
				if (diferencia > 5 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}

			
			try {
				int valor = context.escenaJuego.matriz.getValor(fila - 6, columna).tipoCasilla;
				if (diferencia > 6 && isNoSaltable(valor)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
			}
			break;

		case DOWN:

			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 1, columna).tipoCasilla;
				if (diferencia > 1 && isNoSaltable(down)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 2, columna).tipoCasilla;
				if (diferencia > 2 && isNoSaltable(down)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 3, columna).tipoCasilla;
				if (diferencia > 3 && isNoSaltable(down)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}
			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 4, columna).tipoCasilla;
				if (diferencia > 4 && isNoSaltable(down)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 5, columna).tipoCasilla;
				if (diferencia > 5 && isNoSaltable(down)) {
					dir = Direction.NONE;
					return dir;
				}
			} catch (Exception e) {
				System.out.println("error ");
			}

			
			try {
				int down = context.escenaJuego.matriz.getValor(fila + 6, columna).tipoCasilla;
				if (diferencia > 6 && isNoSaltable(down)) {
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

	public boolean isNoSaltable(int valor) {
		return (valor != Matriz.NADA && valor != Matriz.BOMBA && valor != Matriz.MONEDA && valor != Matriz.PUERTA);
	}

	public void moverDosCuadros(Direction dir) {
		// System.out.println("columna "+columna
		// +"fila "+fila+" mover a "+direccion);

		float x = coordenadas.getX();
		float y = coordenadas.getYCorregido();

		switch (dir) {
		case DOWN:
			// //System.out.println("DOWN");

			baseTileRectangle.registerEntityModifier(new MoveYModifier(2 * tiempoPorCuadro, y, y - 2 * Constantes.TILE_HEIGHT) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarAbajo();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;

		case UP:
			// //System.out.println("UP");
			// toY=this.getY() - Matriz.ALTO;
			baseTileRectangle.registerEntityModifier(new MoveYModifier(2 * tiempoPorCuadro, y, y + 2 * Constantes.TILE_HEIGHT) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarArriba();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case LEFT:
			// //System.out.println("LEFT");
			// toX=this.getX() - Matriz.ANCHO;
			baseTileRectangle.registerEntityModifier(new MoveXModifier(2 * tiempoPorCuadro, x, x - 2 * Constantes.TILE_WIDTH) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					animarIzquierda();
					// playMusica();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case RIGHT:
			// //System.out.println("RIGHT");
			// toX=this.getX() + Matriz.ANCHO;
			baseTileRectangle.registerEntityModifier(new MoveXModifier(2 * tiempoPorCuadro, x, x + 2 * Constantes.TILE_WIDTH) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarDerecha();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;

		case NONE:

		}
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
