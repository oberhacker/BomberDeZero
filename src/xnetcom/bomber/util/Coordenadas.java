package xnetcom.bomber.util;

public class Coordenadas {
	private int fila;
	private int columna;
	
	public  Coordenadas(int columna, int fila){
		this.fila=fila;
		this.columna=columna;
	}
	
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		Coordenadas c =(Coordenadas)o;
		if (c.getColumna()==columna && c.getFila()==fila){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return fila*1250+columna;
	}
	
}