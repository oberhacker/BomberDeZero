package xnetcom.bomber.entidades;

import java.util.ArrayList;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;

public class AlmacenBombas {

	BomberGame context;
	private ArrayList<Bomba> almacen;
	private int secuencia = 0;
	private int nextBomba=0;
	public AlmacenBombas(BomberGame context) {
		this.context = context;
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
	
	public Bomba circulaBomba(){
		if (nextBomba>=almacen.size()){
			nextBomba=0;
		}
		Bomba bomba = almacen.get(nextBomba);
		nextBomba++;
		return bomba;
		
	}
	
	

}
