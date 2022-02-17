package xnetcom.bomber;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;
import xnetcom.bomber.enemigos.EnemigoBase;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Util;

public class GameManager {

	public int bombaTam = 4;
	public int bombaNum = 5;
	public boolean detonador =true;

	BomberGame context;

	public GameManager(BomberGame context) {
		this.context = context;
	}

	boolean ganado=false;
	IUpdateHandler updater;
	public void inicia() {
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
		int seleccion=Util.tomaDecision(1, context.capaParedes.listaMuros.size());
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

	
	public void ganarPartida(){
		Log.d("GANAR", "GANAR");
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
				eligePuerta();
			}});



	}

}
