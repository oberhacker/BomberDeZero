package xnetcom.bomber;

import java.util.ArrayList;

public class AlmacenEnemigos {
	
	
	public enum TipoEnemigo {
		GLOBO, MOCO, MONEDA, FANTASMA, GOTA_NARANJA, GLOBO_AZUL, MOCO_ROJO, MONEDA_MARRON, GOTA_ROJA,
	}
	
	private ArrayList<EnemigoBase> almacen;
	private BomberGame context;
	
	
	
	
	public AlmacenEnemigos(BomberGame context) {
		this.context = context;
		almacen = new ArrayList<EnemigoBase>();
	}
	
	
	/**
	 * crea un enemigo lo mete en el array de enemigos vivos y lo añade a la escena
	 */
	public EnemigoBase creaEnemigo( TipoEnemigo tipoEnemigo,int fila, int columna){
		//completar logica
		EnemigoBase enemigo= new EnemigoBase();
		almacen.add(enemigo);
		return enemigo;
	}
	
	
	public void inicializaEnemigos(){
		
	}	
	
	public void reciclaEnemigo(EnemigoBase enemigo){
		
	}
	
	public void reseteaEnemigos(){
		
	}
	

}
