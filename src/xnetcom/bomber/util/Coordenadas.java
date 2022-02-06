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
	
	
	public float getX(){
		return columna*Constantes.TILE_WIDTH;
	}
	public float getY(){
		return (12*Constantes.TILE_HEIGHT) - fila*Constantes.TILE_HEIGHT;
	}
	public float getYCorregido(){
		return (14*Constantes.TILE_HEIGHT) - fila*Constantes.TILE_HEIGHT;
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