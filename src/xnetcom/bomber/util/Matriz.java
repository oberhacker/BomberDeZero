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
	
	public Casilla matriz2[][]={			    {new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla(),new Casilla()},
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
	
	public int matriz[][]={			        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
											{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}	};
	
	
	public synchronized int[][] getMatrizmuros() {			
		return this.matriz;
	}
	
	
	public synchronized void setMatriz(int [][] matriz){

		//this.matrizmuros=matriz.clone();
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				this.matriz[i][j]=matriz[i][j];
			}		
		}
		
	}
	
	public void reiniciaMatriz(){
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				matriz[i][j]=NADA;
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
				cadena+="["+matriz[i][j]+"]";
			}	
			Log.d("puerta",cadena);
		}
		
	}
	public  void setValor(int valor, int fila, int columna){
		synchronized (matriz) {
			this.matriz[fila][columna]=valor;
		}
		
	}
	public  int getValor( int fila, int columna){			
		synchronized (matriz) {
			int valor=MURO;
			try{
				if (fila==-1 || columna==-1){
					valor=MURO;
				}else{
					valor =this.matriz[fila][columna];
				}
				
			}catch (Exception e) {
				////System.out.println("matrizmuros[fila][columna]"+fila+" "+columna);
				System.out.println("error  intentamos miarar fila"+fila+ " columna"+columna);
				e.printStackTrace();
				//System.out.println("mierrroorrr");
			}				
			return valor;
		}

	}

}
