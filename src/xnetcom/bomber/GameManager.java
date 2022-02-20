package xnetcom.bomber;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;
import xnetcom.bomber.enemigos.EnemigoBase;
import xnetcom.bomber.entidades.AlmacenMonedas.TipoMoneda;
import xnetcom.bomber.sql.DatabaseHandler;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Util;

public class GameManager {

	public int bombaTam = 4;
	public int bombaNum = 5;
	public boolean detonador =true;
	
	public int muertoVeces=0;
	public int boostersCogidos=0;

	BomberGame context;

	public GameManager(BomberGame context) {
		this.context = context;
	}

	boolean ganado=false;
	IUpdateHandler updater;
	public void inicia() {
		play();
		muertoVeces=0;
		boostersCogidos=0;
		ganado=false;
		eligePuerta();
		
		if (updater==null){
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
	
		
		
	}
	
	Coordenadas coodenadasPuerta;
	
	
	public void eligePuerta(){
		context.escenaJuego.spritePuerta.setVisible(false);

		boolean elegido=false;
		int seleccion;
		do{
			seleccion=Util.tomaDecision(1, context.capaParedes.listaMuros.size()-1);
			ArrayList<Integer> posicionesMonedas = context.almacenMonedas.posiciones;
			for (Integer integer : posicionesMonedas) {				
				if(integer.equals(Integer.valueOf(seleccion))){	
					elegido=false;
					break;					
				}else{
					elegido=true;
				}
			}
			
		}while (!elegido);		
		coodenadasPuerta = context.capaParedes.listaMuros.get(seleccion).coodenadas;		
	}
	
	
	public void descubrePuerta(ArrayList<Coordenadas> coodenadas){
		if (!context.escenaJuego.spritePuerta.isVisible()){
			for (Coordenadas coordenadas : coodenadas) {
				if (coordenadas.getColumna()==coodenadasPuerta.getColumna() && coordenadas.getFila()==coodenadasPuerta.getFila()){
					context.escenaJuego.spritePuerta.setVisible(true);					
					context.escenaJuego.spritePuerta.setPosition(coodenadasPuerta.getX()-4f, coodenadasPuerta.getYCorregido()-4);	
					return;
				}
			}
		}
	}

	public boolean pausa=false;
	public void pausa(){
		pausa=true;
		context.bomberman.pausa();
		context.almacenBombas.pausa();
		context.almacenEnemigos.pausa();
	}
	
	public void play(){
		pausa=false;
		context.almacenBombas.play();
		context.bomberman.play();
		context.almacenEnemigos.play();
	}
	
	
	public void ganarPartida(){
		if (!ganado){
			ganado=true;
			Log.d("GANAR", "GANAR");
			pausa();
			
			int estrellas=1;
			if (muertoVeces==0){
				estrellas++;
			}
			if (boostersCogidos==context.escenaJuego.datosMapa.getBoosterTotales()){
				estrellas++;
			}					
			guardaEstrellas(estrellas, context.escenaJuego.datosMapa.getNumeroMapa());			
			context.tarjeta.muestraTarjeta(estrellas);
		}		
	}
	
	
	public void checkpuerta(){
		if (context.escenaJuego.spritePuerta.isVisible()){
			if (context.bomberman.getColumna()==coodenadasPuerta.getColumna() && context.bomberman.getFila()==coodenadasPuerta.getFila()){
				if (context.almacenEnemigos.almacen.isEmpty()){
					ganarPartida();
				}
			}
		}
	}
	
	
	public void updater() {	
		checkpuerta();
		context.escenaJuego.hud.debugText.setText(context.almacenEnemigos.almacen.size() + "  ymax"+context.miengine.getCamaraJuego().getYMax() );
		
		// comprobamos matar a bomberman
		synchronized (context.almacenEnemigos.almacen) {
			
			if (Constantes.DEBUG_IMMORTAL){
				return;
			}
			try{
				for (EnemigoBase enemigoBase : context.almacenEnemigos.almacen) {
					if (enemigoBase.colidesTileRectangle.collidesWith(context.bomberman.colidesTileRectangle)) {
						matarBomberman(false);
						return;
					}
				}
			}catch(Exception e){
				Log.e("Almacen updater", "error");
			}		

		}

	}

	public void matarBomberman(boolean fuego) {
		context.bomberman.morir(fuego);
		context.almacenEnemigos.pararTodosEnemigo();
	}

	
	public void matarPorCoordenadas(ArrayList<Coordenadas> coordenadas) {
		descubrePuerta(coordenadas);		
		boolean matado = context.bomberman.matarPorCoordenadas(coordenadas);
		if (matado) {
			context.almacenEnemigos.pararTodosEnemigo();
		}
	}

	public void reiniciarPartida() {
		context.runOnUpdateThread(new Runnable() {
			public void run() {
				context.bomberman.reinicia();
				context.almacenBombas.reinicia();
				context.almacenEnemigos.eliminaTodosEnemigos();		
				context.almacenEnemigos.reiniciaEnemigos();
				context.capaParedes.restauraInicial();
				context.almacenMonedas.barajeaMonedas();
				eligePuerta();
			}});
	}
	
	
	public void iniciaGuardaMapas(int mapaNum){
		DatosMapa mapa = context.databaseHandler.getMapa(mapaNum);
		DatosMapa mapaActulal =context.escenaJuego.datosMapa;		
		mapaActulal.setEstrellas(mapa.getEstrellas());
		context.databaseHandler.actualizaDatosMapa(mapaActulal);		
	}
	
	
	public void  guardaEstrellas(int estrellas, int numMap){
		DatosMapa mapa = context.databaseHandler.getMapa(numMap);		
		if (mapa.getEstrellas()<estrellas){
			mapa.setEstrellas(estrellas);
			context.databaseHandler.actualizaEstrellasMapa(mapa);
		}
		desbloqueaSiguienteMapa(numMap+1);		
	}
	
	
	public void desbloqueaSiguienteMapa(int nextMap){		
		DatosMapa mapa = context.databaseHandler.getMapa(nextMap);
		if (mapa.getEstrellas()==-1){
			mapa=new DatosMapa();
			mapa.setNumeroMapa(nextMap);	
			mapa.setEstrellas(0);
			context.databaseHandler.addMapa(mapa);
		}		
	}


	public void cogerMoneda(TipoMoneda tipoMonena) {		
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
				
	}
	
	
	public void cogeMonedaCorazon(){
		
	}	
	public void cogeMonedaDetonador(){
		
	}
	public void cogeMonedaBomba(){
		
	}
	public void cogeMonedaPotenciador(){
		
	}
	public void cogeMonedaFantasma(){
		
	}
	public void cogeMonedaFuerza(){
		
	}
	public void cogeMonedaCorrer(){
		
	}
	

}
