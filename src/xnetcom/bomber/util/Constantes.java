package xnetcom.bomber.util;

public class Constantes {
	
	
	public static boolean DEBUG_BASE_RECTANGLE_VISIBLE=false;
	public static boolean DEBUG_CURRENT_RECTANGLE_VISIBLE=false;
	public static boolean DEBUG_TEXT=false; 
	
	public static int TILE_TAM=64;
	public static float FARTOR_FORMA = 1.15625f;
	
	public static float TILE_HEIGHT =64;
	public static float TILE_WIDTH =FARTOR_FORMA*TILE_HEIGHT;
	
		
	
	public static int ZINDEX_CAPA_SUELO=0;
	public static int ZINDEX_CAPA_PIEDRAS_SOMBRA=100;
	
	public static int ZINDEX_CAPA_PAREDES_ABAJO=99;
	
	public static int ZINDEX_BOMBA=180;
	
	public static int ZINDEX_CAPA_PAREDES_ARRIBA=200;
	
	public static int ZINDEX_CAPA_BORDE_ABAJO=300;
	public static int ZINDEX_CAPA_TECHO_PIEDRAS=600;
	
	public static int ZINDEX_BOMBERMAN_ARRIBA=560;
	public static int ZINDEX_BOMBERMAN_ABAJO=195;	
	
	public static int ZINDEX_ENEMIGO=460;
	public static int ZINDEX_PUERTA=401;
	
	public static int ZINDEX_FUEGO=550;
	
	
	
	
	public static float TIEMPO_POR_CUADRADO=0.40f;
	public static float TIEMPO_POR_PIXEL=TIEMPO_POR_CUADRADO/TILE_TAM;
	
	
	
	public static int TIEMPO_TEMPORIZADOR_BOMBA;
	
	
	public static int MAXIMOBOMBAS=9;
	public static int MAXIMOEXPLOSION=4;
	
	
	

}
