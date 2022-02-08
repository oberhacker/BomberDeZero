package xnetcom.bomber;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;

import android.util.Log;
import xnetcom.bomber.enemigos.EnemigoBase;
import xnetcom.bomber.util.Coordenadas;

public class GameManager {
	
	public int bombaTam=4;
	public int bombaNum=5;
	public boolean detonador=true;
	

	BomberGame context;
	
	public GameManager (BomberGame context){
		this.context=context;
	}

	
	public void inicia(){
		context.escenaJuego.scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				updater();
			}

			@Override
			public void reset() {

			}
		});
	}
	
	public void updater(){
		
		// comprobamos matar a bomberman
		 ArrayList<EnemigoBase> almacen = context.almacenEnemigos.almacen;
		 for (EnemigoBase enemigoBase : almacen) {
			if ( enemigoBase.colidesTileRectangle.collidesWith(context.bomberman.colidesTileRectangle)){
				matarBomberman(false);
				return;
			 }			
		}
		
	}
	
	
	public void matarBomberman(boolean fuego ){
		 context.bomberman.morir(fuego);
		 context.almacenEnemigos.pararTodosEnemigo();
	}
	
	public void matarPorCoordenadas(ArrayList<Coordenadas> coordenadas) {
		context.bomberman.matarPorCoordenadas(coordenadas);
		context.almacenEnemigos.pararTodosEnemigo();
	}
		
	
	public void reiniciarBomberMan(){
		context.bomberman.reinicia();
		
	}
	

}
