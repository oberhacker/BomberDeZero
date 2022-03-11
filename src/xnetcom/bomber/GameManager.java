package xnetcom.bomber;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.TimerHandler;

import android.util.Log;
import xnetcom.bomber.enemigos.AlmacenEnemigos.TipoEnemigo;
import xnetcom.bomber.enemigos.EnemigoBase;
import xnetcom.bomber.entidades.AlmacenMonedas.TipoMoneda;
import xnetcom.bomber.preferencias.Preferencias;
import xnetcom.bomber.sql.DatabaseHandler;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.Util;

public class GameManager {

	public int bombaTam = 4;
	public int bombaNum = 5;
	public Boolean detonador = true;
	public int vidas = 0;

	public int muertoVeces = 0;
	public int boostersCogidos = 0;
	public int boostersExplotados = 0;

	BomberGame context;

	public GameManager(BomberGame context) {
		this.context = context;
	}

	int minutos;
	int segundos;

	public void trestreintaminutos() {
		minutos = 3;
		segundos = 30;
	}

	public void cincominutos() {
		minutos = 5;
		segundos = 0;
	}

	public void tiempoInfinito() {
		minutos = 0;
		segundos = 0;
	}

	public void cargaDatos() {
		this.vidas = Preferencias.leerPreferenciasInt("vidas");
		if (vidas == 0) {
			vidas = 1;
			Preferencias.guardarPrefenrenciasInt("vidas", vidas);
		}
		this.detonador = Boolean.valueOf(Preferencias.leerPreferenciasString("detonador"));
		this.bombaNum = Preferencias.leerPreferenciasInt("bombas");
		this.bombaTam = Preferencias.leerPreferenciasInt("explosion");
	}

	public void guardarDatos() {
		Preferencias.guardarPrefenrenciasInt("vidas", vidas);
		Preferencias.guardarPrefenrenciasInt("bombas", bombaNum);
		Preferencias.guardarPrefenrenciasInt("explosion", bombaTam);
		Preferencias.guardarPrefenrenciasString("detonador", detonador.toString());
	}

	boolean partidaTerminada = false;
	IUpdateHandler updater;

	public void inicia() {
		context.escenaJuego.hud.paraCrono();
		context.escenaJuego.hud.settTempo(minutos, segundos);
		context.escenaJuego.hud.update(false);
		context.escenaJuego.hud.ct_tiempo.clearEntityModifiers();
		context.escenaJuego.hud.ct_tiempo.clearEntityModifiers();
		context.escenaJuego.hud.ct_tiempo.setAlpha(1f);
		play();
		muertoVeces = 0;
		boostersCogidos = 0;
		boostersExplotados = 0;
		partidaTerminada = false;
		eligePuerta();

		if (updater == null) {
			updater = new IUpdateHandler() {
				@Override
				public void onUpdate(float pSecondsElapsed) {
					updater();
				}

				@Override
				public void reset() {

				}
			};
			context.escenaJuego.scene.registerUpdateHandler(updater);
		}
		Log.d("MUSICAA", "MUSICAA");
		context.soundManager.playMusicaRandom();
		if (minutos != 0 && segundos != 0) {
			context.escenaJuego.hud.iniciaCuentaAtras();
		}

	}

	Coordenadas coodenadasPuerta;

	public void eligePuerta() {
		context.escenaJuego.spritePuerta.setVisible(false);

		boolean elegido = false;
		int seleccion;
		do {
			seleccion = Util.tomaDecision(1, context.capaParedes.listaMuros.size() - 1);
			ArrayList<Integer> posicionesMonedas = context.almacenMonedas.posiciones;
			if (posicionesMonedas.isEmpty()) {
				elegido = true;
			} else {
				for (Integer integer : posicionesMonedas) {
					if (integer.equals(Integer.valueOf(seleccion))) {
						elegido = false;
						break;
					} else {
						elegido = true;
					}
				}
			}

		} while (!elegido);
		coodenadasPuerta = context.capaParedes.listaMuros.get(seleccion).coodenadas;
	}

	public void descubrePuerta(ArrayList<Coordenadas> coodenadas) {
		if (!context.escenaJuego.spritePuerta.isVisible()) {
			for (Coordenadas coordenadas : coodenadas) {
				if (coordenadas.getColumna() == coodenadasPuerta.getColumna() && coordenadas.getFila() == coodenadasPuerta.getFila()) {
					context.escenaJuego.spritePuerta.setVisible(true);
					context.escenaJuego.spritePuerta.setPosition(coodenadasPuerta.getX() - 2f, coodenadasPuerta.getYCorregido());
					return;
				}
			}
		}
	}

	public boolean pausa = false;

	public void pausa() {
		pausa = true;
		context.bomberman.pausa();
		context.almacenBombas.pausa();
		context.almacenEnemigos.pausa();
	}

	public void play() {
		pausa = false;
		context.almacenBombas.play();
		context.bomberman.play();
		context.almacenEnemigos.play();
	}

	public void terminaPartida(boolean ganado) {
		if (!partidaTerminada) {
			partidaTerminada = true;
			Log.d("GANAR", "GANAR");
			pausa();

			int estrellas = 1;
			if (ganado) {
				if (muertoVeces == 0) {
					estrellas++;
				}
				if (boostersCogidos == context.escenaJuego.datosMapa.getBoosterTotales()) {
					estrellas++;
				}
				guardaEstrellas(estrellas, context.escenaJuego.datosMapa.getNumeroMapa());
			} else {
				estrellas = 0;
				if (context.gameManager.vidas == 0) {
					pierdeBoosters();
				}
			}
			context.tarjeta.muestraTarjeta(estrellas);
		}
	}

	public void pierdeBoosters() {
		this.detonador = false;
		this.bombaNum = Constantes.INICIO_BOMBAS;
		this.bombaTam = Constantes.INICIO_EXPLOSION;
		guardarDatos();
	}

	public void checkpuerta() {
		if (context.escenaJuego.spritePuerta.isVisible()) {
			if (context.bomberman.getColumna() == coodenadasPuerta.getColumna() && context.bomberman.getFila() == coodenadasPuerta.getFila()) {
				if (context.almacenEnemigos.almacen.isEmpty()) {
					terminaPartida(true);
				}
			}
		}
	}

	public void updater() {
		checkpuerta();
		context.escenaJuego.hud.debugText.setText(context.almacenEnemigos.almacen.size() + "  ymax" + context.miengine.getCamaraJuego().getYMax());

		// comprobamos matar a bomberman
		synchronized (context.almacenEnemigos.almacen) {

			if (Constantes.DEBUG_IMMORTAL) {
				return;
			}
			try {
				for (EnemigoBase enemigoBase : context.almacenEnemigos.almacen) {
					if (enemigoBase.colidesTileRectangle.collidesWith(context.bomberman.colidesTileRectangle)) {
						matarBomberman(false);
						return;
					}
				}
			} catch (Exception e) {
				Log.e("Almacen updater", "error");
			}

		}

	}

	public void matarBomberman(boolean fuego) {
		if (context.bomberman.morir(fuego)) {
			context.almacenEnemigos.pararTodosEnemigo();
			vidas--;
			guardarDatos();
		}
	}

	public void matarPorCoordenadas(ArrayList<Coordenadas> coordenadas) {
		descubrePuerta(coordenadas);
		boolean matado = context.bomberman.matarPorCoordenadas(coordenadas);
		if (matado) {
			context.almacenEnemigos.pararTodosEnemigo();
			vidas--;
			guardarDatos();
		}
	}

	public void reiniciarPartida() {
		context.runOnUpdateThread(new Runnable() {
			public void run() {
				if (context.gameManager.vidas == 0) {
					pausa();
					terminaPartida(false);
				} else {
					context.bomberman.reinicia();
					context.almacenBombas.reinicia();
					context.almacenEnemigos.eliminaTodosEnemigos();
					context.almacenEnemigos.reiniciaEnemigos();
					context.capaParedes.restauraInicial();
					context.almacenMonedas.barajeaMonedas();
					eligePuerta();
				}

			}
		});
	}

	public void iniciaGuardaMapas(int mapaNum) {
		DatosMapa mapa = context.databaseHandler.getMapa(mapaNum);
		DatosMapa mapaActulal = context.escenaJuego.datosMapa;
		mapaActulal.setEstrellas(mapa.getEstrellas());
		context.databaseHandler.actualizaDatosMapa(mapaActulal);
	}

	public void guardaEstrellas(int estrellas, int numMap) {
		DatosMapa mapa = context.databaseHandler.getMapa(numMap);
		if (mapa.getEstrellas() < estrellas) {
			mapa.setEstrellas(estrellas);
			context.databaseHandler.actualizaEstrellasMapa(mapa);
		}
		if ((numMap + 1) <= Constantes.ULTIMO_MAPA) {
			desbloqueaSiguienteMapa(numMap + 1);
		}

	}

	public void desbloqueaSiguienteMapa(int nextMap) {
		DatosMapa mapa = context.databaseHandler.getMapa(nextMap);
		if (mapa.getEstrellas() == -1) {
			mapa = new DatosMapa();
			mapa.setNumeroMapa(nextMap);
			mapa.setEstrellas(0);
			context.databaseHandler.addMapa(mapa);
		}
	}

	public void cogerMoneda(TipoMoneda tipoMonena) {
		context.soundManager.sonarCogerMoneda();
		boostersCogidos++;
		switch (tipoMonena) {
		case MBOMBA:
			cogeMonedaBomba();
			break;
		case MCORAZON:
			cogeMonedaCorazon();
			break;
		case MDETONADOR:
			cogeMonedaDetonador();
			break;
		case MEXPLOSION:
			cogeMonedaPotenciador();
			break;
		case MFANTASMA:
			cogeMonedaFantasma();
			break;
		case MFUERZA:
			cogeMonedaFuerza();
			break;
		case MVELOCIDAD:
			cogeMonedaCorrer();
			break;

		default:
			break;
		}

		guardarDatos();
		context.escenaJuego.hud.update(true);
	}

	public void cogeMonedaCorazon() {
		vidas++;
	}

	public void cogeMonedaDetonador() {
		this.detonador = true;
	}

	public void cogeMonedaBomba() {
		if (bombaNum < Constantes.MAXIMOBOMBAS) {
			this.bombaNum++;
		}
	}

	public void cogeMonedaPotenciador() {
		if (bombaTam < Constantes.MAXIMOEXPLOSION) {
			this.bombaTam++;
		}
	}

	public void cogeMonedaFantasma() {
		context.bomberman.boosterFantasma();
	}

	public void cogeMonedaFuerza() {
		context.bomberman.boosterSayan();
	}

	public void cogeMonedaCorrer() {
		context.bomberman.boosterrapido();
	}

	DatosMapa datosMapaAcumilados;

	public void iniciaTraining() {

		// cargar datos ded los mapas anteriores
		datosMapaAcumilados = new DatosMapa();
		boolean terminado = false;
		int numMapa = 1;
		do {

			DatosMapa datosMapa = context.databaseHandler.getMapa(numMapa);
			numMapa++;
			if (datosMapa.getEstrellas() < 1) {
				terminado = true;
			} else {
				datosMapaAcumilados.setEnemigo_fantasma(datosMapaAcumilados.getEnemigo_fantasma() + datosMapa.getEnemigo_fantasma());
				datosMapaAcumilados.setEnemigo_globo(datosMapaAcumilados.getEnemigo_globo() + datosMapa.getEnemigo_globo());
				datosMapaAcumilados.setEnemigo_moco(datosMapaAcumilados.getEnemigo_moco() + datosMapa.getEnemigo_moco());
				datosMapaAcumilados.setEnemigo_moneda(datosMapaAcumilados.getEnemigo_moneda() + datosMapa.getEnemigo_moneda());
				datosMapaAcumilados.setEnemigo_gota(datosMapaAcumilados.getEnemigo_gota() + datosMapa.getEnemigo_gota());
				datosMapaAcumilados.setEnemigo_gotaNaranja(datosMapaAcumilados.getEnemigo_gotaNaranja() + datosMapa.getEnemigo_gotaNaranja());
				datosMapaAcumilados.setEnemigo_globoAzul(datosMapaAcumilados.getEnemigo_globoAzul() + datosMapa.getEnemigo_globoAzul());
				datosMapaAcumilados.setEnemigo_mocoRojo(datosMapaAcumilados.getEnemigo_mocoRojo() + datosMapa.getEnemigo_mocoRojo());
				datosMapaAcumilados.setEnemigo_monedaMarron(datosMapaAcumilados.getEnemigo_monedaMarron() + datosMapa.getEnemigo_monedaMarron());
				datosMapaAcumilados.setEnemigo_gotaRoja(datosMapaAcumilados.getEnemigo_gotaRoja() + datosMapa.getEnemigo_gotaRoja());
				datosMapaAcumilados.setM_bomba(datosMapaAcumilados.getM_bomba() + datosMapa.getM_bomba());
				datosMapaAcumilados.setM_corazon(datosMapaAcumilados.getM_corazon() + datosMapa.getM_corazon());
				datosMapaAcumilados.setM_correr(datosMapaAcumilados.getM_correr() + datosMapa.getM_correr());
				datosMapaAcumilados.setM_detonador(datosMapaAcumilados.getM_detonador() + datosMapa.getM_detonador());
				datosMapaAcumilados.setM_fantasma(datosMapaAcumilados.getM_fantasma() + datosMapa.getM_fantasma());
				datosMapaAcumilados.setM_fuerza(datosMapaAcumilados.getM_fuerza() + datosMapa.getM_fuerza());
				datosMapaAcumilados.setM_potenciador(datosMapaAcumilados.getM_potenciador() + datosMapa.getM_potenciador());
			}
		} while (!terminado);

		datosMapaAcumilados.setM_bomba(datosMapaAcumilados.getM_bomba() - bombaNum + 1);
		if (datosMapaAcumilados.getM_bomba() > Constantes.MAXIMOBOMBAS) {
			datosMapaAcumilados.setM_bomba(Constantes.MAXIMOBOMBAS);
		}

		datosMapaAcumilados.setM_corazon(datosMapaAcumilados.getM_corazon() - vidas + 1);
		if (datosMapaAcumilados.getM_corazon() > Constantes.MAXIMO_VIDAS) {
			datosMapaAcumilados.setM_corazon(Constantes.MAXIMO_VIDAS);
		}

		if (datosMapaAcumilados.getM_detonador() > 0) {
			datosMapaAcumilados.setM_detonador(1);
		}

		datosMapaAcumilados.setM_potenciador(datosMapaAcumilados.getM_potenciador() - bombaTam + 1);
		if (datosMapaAcumilados.getM_potenciador() > Constantes.MAXIMOEXPLOSION) {
			datosMapaAcumilados.setM_potenciador(Constantes.MAXIMOEXPLOSION);
		}

		// crea tres monedas
		int monedasPuestas = 0;
		int monedasMaximas = 0;
		// crea tres monedas
		if (datosMapaAcumilados.getBoosterTotales() >= 3) {
			monedasMaximas = 3;
		} else {
			monedasMaximas = datosMapaAcumilados.getBoosterTotales();
		}

		if (monedasMaximas > 0) {
			do {
				if (trainingCreaMonedasIniciales(null)) {
					monedasPuestas++;
				}
			} while (monedasPuestas < monedasMaximas);

			context.almacenMonedas.barajeaMonedas();
		}
		trainingPlaceInicialEnemigos();
	}

	public void trainingPlaceInicialEnemigos() {

		boolean puesto = false;
		int fila = 2;
		int columna = 2;
		do {
			boolean salida = false;

			do {
				fila = Util.tomaDecision(2, 12);
				columna = Util.tomaDecision(2, 22);
				if (columna <= 5 && fila <= 5) {
					salida = false;
				} else {
					salida = true;
				}
			} while (!salida);
			context.escenaJuego.matriz.pintaMatriz();
			if (context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla != Matriz.MURO && context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla != Matriz.PARED) {
				puesto = true;
			}
		} while (!puesto);

		context.almacenEnemigos.creaEnemigo(TipoEnemigo.GLOBO, fila, columna);

	}

	int tiksParaEnemigo=0;
	int tikPared=0;
	public void trainingUpdater(TimerHandler pTimerHandler) {
		Log.d("trainingUpdater", "getTimerSeconds " + pTimerHandler.getTimerSeconds() + " TimerSecondsElapsed " + pTimerHandler.getTimerSecondsElapsed());
		if (pausa) {
			return;
		}

		new Thread() {
			public void run() {		
				
				if (tikPared>5){
					tikPared=0;
					ponParedAleatoria();	
					context.escenaJuego.matriz.pintaMatriz();
				}else{
					tikPared++;
				}
				
				
				if (context.almacenEnemigos.almacen.size() < 5&& tiksParaEnemigo>10) {
					// crear enemigo aleatorio
					crearEnemigoAleatorio();
					tiksParaEnemigo=0;
				}else{
					tiksParaEnemigo++;
				}				
			};
		}.start();
		


	}

	public void ponParedAleatoria(){
		
			boolean puesto=false;
			do{
				boolean salida=false;
				int fila=2;
				int columna=2;
				do{
					fila = Util.tomaDecision(2, 12);
					columna = Util.tomaDecision(2, 22);	
					int bomberColumna=context.bomberman.getColumna();
					int bomberFila=context.bomberman.getFila();
					if ( Math.abs(fila-bomberFila)<2 &&  Math.abs(columna-bomberColumna)<2){
						salida =false;
					}else{
						salida=true;
					}

				}while(!salida);

				if(context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla==Matriz.NADA){
					context.capaParedes.ponParedInicial(columna, fila);
					puesto=true;					
				}	
				
			}while(!puesto);
			context.capaParedes.recalculaPared();
			
	}
	
	

	public int calculaPotenciaFuego(){		
		
//		minimo  2 + 3 =6
//		maximo 10+ 16+10=36
		float potencia=(bombaNum*2)+(bombaTam*4);
		if (detonador){
			potencia=potencia+10;
		}
		
		potencia=potencia/3.6f;
		int salida= Float.valueOf(potencia).intValue();
		return salida;
	}
	
	
	
	public void crearEnemigoAleatorio() {
	
			TipoEnemigo tipoEnemigo=null;
			do {
				int enemigoMaximo =calculaPotenciaFuego();
				int numeroEnemigo = Util.tomaDecision(1, enemigoMaximo);				
				switch (numeroEnemigo) {
				case 1:					
					if(datosMapaAcumilados.getEnemigo_globo()>0){
						tipoEnemigo=TipoEnemigo.GLOBO;		
					}
					break;
				case 2:
					if(datosMapaAcumilados.getEnemigo_globoAzul()>0){
						tipoEnemigo=TipoEnemigo.GLOBO_AZUL;	
					}								
					break;
				case 3:
					if(datosMapaAcumilados.getEnemigo_moco()>0){
					tipoEnemigo=TipoEnemigo.MOCO;				
					}
					break;
				case 4:
					if(datosMapaAcumilados.getEnemigo_mocoRojo()>0){
						tipoEnemigo=TipoEnemigo.MOCO_ROJO;	
					}
					break;
				case 5:
					if(datosMapaAcumilados.getEnemigo_fantasma()>0){
						tipoEnemigo=TipoEnemigo.FANTASMA;
					}
					break;
				case 6:
					if(datosMapaAcumilados.getEnemigo_globoAzul()>0){
					tipoEnemigo=TipoEnemigo.GOTA_AZUL;				
					}
					break;
				case 7:
					if(datosMapaAcumilados.getEnemigo_moneda()>0){
						tipoEnemigo=TipoEnemigo.MONEDA;	
					}								
					break;
				case 8:
					if(datosMapaAcumilados.getEnemigo_monedaMarron()>0){
						tipoEnemigo=TipoEnemigo.MONEDA_MARRON;
					}									
					break;	

				case 9:
					if(datosMapaAcumilados.getEnemigo_gotaNaranja()>0){
					tipoEnemigo=TipoEnemigo.GOTA_NARANJA;				
					}
					break;
				case 10:
					if(datosMapaAcumilados.getEnemigo_gotaNaranja()>0){
					tipoEnemigo=TipoEnemigo.GOTA_ROJA;				
					}
					break;		
				}				
				
			} while (tipoEnemigo==null);		
			
			
			
			boolean salida = false;
			int fila;
			int columna;				

	
			do {
				fila = Util.tomaDecision(2, 12);
				columna = Util.tomaDecision(2, 22);
				int bomberColumna=context.bomberman.getColumna();
				int bomberFila=context.bomberman.getFila();
				if ( Math.abs(fila-bomberFila)<2 &&  Math.abs(columna-bomberColumna)<2){
					salida =false;
				}else{
					if (context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla != Matriz.MURO && context.escenaJuego.matriz.getValor(fila, columna).tipoCasilla != Matriz.PARED) {
						salida=true;
					}else{
						salida=false;
					}
				}				

			} while (!salida);			

		context.almacenEnemigos.creaEnemigo(tipoEnemigo, fila, columna);

	}

	public boolean trainingCreaMonedasIniciales(Coordenadas coordenadas) {

		if (coordenadas == null) {
			coordenadas = new Coordenadas(0, 0);
		}
		int numero = Util.tomaDecision(1, 7);

		if (numero == 1 && datosMapaAcumilados.getM_bomba() >= 1) {
			datosMapaAcumilados.setM_bomba(datosMapaAcumilados.getM_bomba() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MBOMBA, coordenadas);
			return true;
		} else if (numero == 2 && datosMapaAcumilados.getM_corazon() >= 1) {
			datosMapaAcumilados.setM_corazon(datosMapaAcumilados.getM_corazon() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MCORAZON, coordenadas);
			return true;
		} else if (numero == 3 && datosMapaAcumilados.getM_correr() >= 1) {
			datosMapaAcumilados.setM_correr(datosMapaAcumilados.getM_correr() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MVELOCIDAD, coordenadas);
			return true;
		} else if (numero == 4 && datosMapaAcumilados.getM_detonador() >= 1) {
			datosMapaAcumilados.setM_detonador(datosMapaAcumilados.getM_detonador() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MDETONADOR, coordenadas);
			return true;
		} else if (numero == 5 && datosMapaAcumilados.getM_fantasma() >= 1) {
			datosMapaAcumilados.setM_fantasma(datosMapaAcumilados.getM_fantasma() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MFANTASMA, coordenadas);
			return true;
		} else if (numero == 6 && datosMapaAcumilados.getM_fuerza() >= 1) {
			datosMapaAcumilados.setM_fuerza(datosMapaAcumilados.getM_fuerza() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MFUERZA, coordenadas);
			return true;
		} else if (numero == 7 && datosMapaAcumilados.getM_potenciador() >= 1) {
			datosMapaAcumilados.setM_potenciador(datosMapaAcumilados.getM_potenciador() - 1);
			context.almacenMonedas.creaMoneda(TipoMoneda.MEXPLOSION, coordenadas);
			return true;
		}

		return false;

	}

}
