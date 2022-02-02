package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;

public class AlmacenBombas {

	BomberGame context;
	public ArrayList<Bomba> almacen;
	public int secuencia = 0;
	private int nextBomba=0;
	public AtomicInteger bombasPlantadas;
	public AlmacenBombas(BomberGame context) {
		this.context = context;
		bombasPlantadas= new AtomicInteger(0);
	}

	public void carga() {
		almacen = new ArrayList<Bomba>();
		Bomba mBomba = null;
		for (int i = 0; i < Constantes.MAXIMOBOMBAS+5; i++) {
			Bomba bomba = new Bomba(context);
			bomba.cargaTexturas(mBomba);
			mBomba = bomba;
			almacen.add(bomba);
		}
	}

	public void onSceneCreated() {
		for (Bomba bomba : almacen) {
			bomba.onSceneCreated();
		}
	}
	
	private Bomba circulaBomba(){
		if (nextBomba>=almacen.size()){
			nextBomba=0;
		}
		Bomba bomba = almacen.get(nextBomba);
		nextBomba++;
		return bomba;
		
	}
	
	public void detonarBomba(){
		if (context.gameManager.detonador){
			Bomba mBomba=null;
			for (Bomba bomba : almacen) {
				if (!bomba.isDetonada()){
					if (mBomba==null){
						mBomba=bomba;
					}
					if (bomba.secuencia<mBomba.secuencia){
						mBomba=bomba;
					}
				}
			}
			if (mBomba!=null)mBomba.detonar();
		}
	}
	
	public void plantaBomba(){
		if (bombasPlantadas.get()<context.gameManager.bombaNum){
			Bomba bomba = circulaBomba();
			secuencia++;
			bomba.plantarBomba(context.gameManager.bombaTam, secuencia, context.gameManager.detonador);
					
		}
		
	}
	

}
