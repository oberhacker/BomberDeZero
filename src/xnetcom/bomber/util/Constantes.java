package xnetcom.bomber.util;

import org.andengine.entity.IEntity;
import org.andengine.util.modifier.IModifier.IModifierListener;

public class Constantes {
	
	
	public static boolean DEBUG_BASE_RECTANGLE_VISIBLE=false;
	public static boolean DEBUG_CURRENT_RECTANGLE_VISIBLE=false;
	public static boolean DEBUG_IMMORTAL=false;
	
	
	
	public static boolean DEBUG_TEXT=false; 
	
	public static int TILE_TAM=64;
	public static float FARTOR_FORMA = 1.15625f;
	
	public static float TILE_HEIGHT =64;
	public static float TILE_WIDTH =FARTOR_FORMA*TILE_HEIGHT;
	
			
	public static int ZINDEX_CAPA_SUELO=0;
	public static int ZINDEX_CAPA_PIEDRAS_SOMBRA=100;
	
	public static int ZINDEX_CAPA_PAREDES_ABAJO=99;
	
	public static int ZINDEX_BOMBA=180;
	
	public static int ZINDEX_CAPA_PAREDES_ARRIBA=800;
	
	public static int ZINDEX_CAPA_BORDE_ABAJO=810;
	public static int ZINDEX_CAPA_TECHO_PIEDRAS=880;
	
	public static int ZINDEX_BOMBERMAN_ARRIBA=850;
	public static int ZINDEX_BOMBERMAN_ABAJO=195;	
	
	public static int ZINDEX_ENEMIGO=460;
	public static int ZINDEX_PUERTA=401;
	
	public static int ZINDEX_FUEGO=550;
	
	
	public static final int FONT_BOMBA_MAPA = 60;
	public static final int ZINDEX_MUROFRAGMENTOS = 900;
	public static final int ZINDEX_ENEMIGOS = 199;
	
	public static final float ZOOM_MAX=2F;
	public static final float ZOOM_MIN=0.8F;
	
	
	
	public static float TIEMPO_POR_CUADRADO=0.40f;
	public static float TIEMPO_POR_PIXEL=TIEMPO_POR_CUADRADO/TILE_TAM;
	
	
	
	public static int TIEMPO_TEMPORIZADOR_BOMBA;
	
	
	public static int MAXIMOBOMBAS=9;
	public static int MAXIMOEXPLOSION=4;
	
	public static final int INICIO_BOMBAS = 1;
	public static final int INICIO_EXPLOSION = 1;
	public static final String INICIO_DETONADOR = "false";		
	public static final int  INICIO_VIDAS=5;
	public static final int ULTIMO_MAPA = 45;
	public static final int TIEMPO_FANTASMA = 15000;
	public static final float PREFERENCIAS_CONTROL_X = 20;
	public static final float PREFERENCIAS_CONTROL_Y = 20;
	public static final float PREFERENCIAS_CONTROL_Z = 2;
	public static final float BTN_SCALE = 0.7f;
	public static final float BTN2_X = 1161f;
	public static final float BTN1_Y = 110;
	public static final float BTN1_X = 953;
	public static final float BTN2_Y = 110;
	public static final float ZOOM = 1.2f;
	public static final int MUSIC_VOLUME = 5;
	public static final int SOUND_VOLUME = 5;
	
	
	public static int TOTAL_MAPAS=45;
	
	

}
