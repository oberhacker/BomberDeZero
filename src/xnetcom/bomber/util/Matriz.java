package xnetcom.bomber.util;

import xnetcom.bomber.entidades.Bomba;
import xnetcom.bomber.graficos.CapaParedes.TrozoPared;
import android.util.Log;

public class Matriz{
	
	
	// posicion Inicial 2 - 2
	
	public static int NADA=0;
	public static int MURO=1;
	public static int PARED=2;
	public static int MONEDA=3;
	public static int PUERTA=4;
	public static int BOMBA=5;
	
	
	public class Casilla{
		public int tipoCasilla=0;
		public Bomba bomba;
		public TrozoPared trozoPared;		
	}
	
	public Casilla matriz[][]={			    {new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
												{new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()}	};
	

	
	
	public synchronized Casilla[][] getMatrizmuros() {			
		return this.matriz;
	}
	
	
	public synchronized void setMatriz(Casilla [][] matriz){		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				this.matriz[i][j]=matriz[i][j];
			}		
		}		
	}
	
	public void reiniciaMatriz(){
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				matriz[i][j].tipoCasilla=NADA;
				matriz[i][j].bomba=null;
				matriz[i][j].trozoPared=null;
			}				
		}
		
	}
	public int getNumFilas(){
		return this.matriz.length;
	}
	public int getNumColumnas(){
		return this.matriz[0].length;
	}
	
	public void pintaMatriz(){		
		for (int i = 0; i < 13; i++) {
			String cadena="";
			for (int j = 0; j < matriz.length; j++) {
				cadena+="["+matriz[i][j].tipoCasilla+"]";
			}	
			Log.d("puerta",cadena);
		}
		
	}
	public  void setValor(int valor, int fila, int columna, Bomba bomba, TrozoPared trozoPared){
		synchronized (matriz) {
			this.matriz[fila][columna].tipoCasilla=valor;
			this.matriz[fila][columna].bomba=bomba;
			this.matriz[fila][columna].trozoPared=trozoPared;
		}
		
	}

	public Casilla getValor(int fila, int columna) {
		synchronized (matriz) {
			return this.matriz[fila][columna];
		}

	}

}
