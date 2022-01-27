package xnetcom.bomber.util;

import android.util.Log;

public class Matriz{
	
	
	// posicion Inicial 2 - 2
	
	public static int NADA=0;
	public static int MURO=1;
	public static int PARED=2;
	public static int MONEDA=3;
	public static int PUERTA=4;
	public static int BOMBA=5;
	
	
	
	public int matrizmuros[][]={			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
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
		return this.matrizmuros;
	}
	
	
	public synchronized void setMatriz(int [][] matriz){

		//this.matrizmuros=matriz.clone();
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				this.matrizmuros[i][j]=matriz[i][j];
			}		
		}
		
	}
	
	public void reiniciaMatriz(){
		for (int i = 0; i < matrizmuros.length; i++) {
			for (int j = 0; j < matrizmuros.length; j++) {
				matrizmuros[i][j]=NADA;
			}				
		}
		
	}
	public int getNumFilas(){
		return this.matrizmuros.length;
	}
	public int getNumColumnas(){
		return this.matrizmuros[0].length;
	}
	public void pintaMatriz(){
		
		for (int i = 0; i < 13; i++) {
			String cadena="";
			for (int j = 0; j < matrizmuros.length; j++) {
				cadena+="["+matrizmuros[i][j]+"]";
			}	
			Log.d("puerta",cadena);
		}
		
	}
	public  void setValor(int valor, int fila, int columna){
		synchronized (matrizmuros) {
			this.matrizmuros[fila][columna]=valor;
		}
		
	}
	public  int getValor( int fila, int columna){			
		synchronized (matrizmuros) {
			int valor=MURO;
			try{
				if (fila==-1 || columna==-1){
					valor=MURO;
				}else{
					valor =this.matrizmuros[fila][columna];
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
