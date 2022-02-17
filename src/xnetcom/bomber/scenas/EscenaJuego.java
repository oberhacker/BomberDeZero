package xnetcom.bomber.scenas;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.debug.Debug;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.AlmacenEnemigos;
import xnetcom.bomber.enemigos.AlmacenEnemigos.TipoEnemigo;
import xnetcom.bomber.graficos.HudBomber;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Matriz;

public class EscenaJuego {

	public BomberGame context;
	public HudBomber hud;
	
	public Scene scene;
	public TMXTiledMap mTMXTiledMap;
	
	
	public Matriz matriz;
	
	public Sprite spritePuerta;

	public TMXLayer capaSuelo;
	public TMXLayer capaPiedrasSombra;
	public TMXLayer capaParedes;
	public TMXLayer capaBordeAbajo;
	public TMXLayer capaTechoPiedras;
	public TMXLayer capaEntidades;

	DatosMapa datosMapa;
	
	public EscenaJuego(BomberGame context) {			
		this.context = context;
		this.matriz= new Matriz();	
		this.hud = new HudBomber(context);
		
	}

	public void cargar() {
		
		BitmapTextureAtlas puerta_BTA =new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR);
		puerta_BTA.load();
		TextureRegion puerta_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(puerta_BTA, context, "gfx/puerta.png", 0, 0);
		
		spritePuerta = new Sprite(0,0, puerta_TR,context.getEngine().getVertexBufferObjectManager());
		spritePuerta.setWidth(Constantes.TILE_WIDTH+5);
		spritePuerta.setHeight(Constantes.TILE_HEIGHT+8);
		spritePuerta.setVisible(false);
		spritePuerta.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO-10);		
		spritePuerta.setOffsetCenter(0, 0);
		
		try {
			hud.carga();
			context.bomberman.carga();
			context.almacenBombas.carga();					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void reiniciaCargaMapa(){
		context.capaParedes.reiniciaPared();
		matriz.reiniciaMatriz();
		context.almacenBombas.reinicia();
		context.bomberman.reinicia();
	}
	
	
	
	public Scene onCreateScene(int numMap) {
		
		boolean primeraCarga;
		if (scene!=null){			
			primeraCarga=false;
			capaSuelo.detachSelf();
			capaPiedrasSombra.detachSelf();
			capaParedes.detachSelf();
			capaBordeAbajo.detachSelf();
			capaTechoPiedras.detachSelf();
			capaEntidades.detachSelf();			
			mTMXTiledMap.detachSelf();		
			reiniciaCargaMapa();
		}else{
			primeraCarga=true;
			scene= new Scene();	
		}
		

		try {
			context.almacenEnemigos.inicializaAlmacen();
			
			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
													
							if (pTMXTileProperties.containsTMXProperty("muro", "true")) {
								matriz.setValor(Matriz.MURO, pTMXTile.getTileRow(), pTMXTile.getTileColumn(),null,null);
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
								context.capaParedes.ponParedInicial(pTMXTile.getTileColumn(), pTMXTile.getTileRow(),true);
							} else if (pTMXTileProperties.containsTMXProperty("pared", "false")) {
								context.capaParedes.ponParedInicial(pTMXTile.getTileColumn(), pTMXTile.getTileRow(),false);								
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.MOCO,pTMXTile.getTileRow(),pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.MONEDA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "globo")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.GLOBO,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "fantasma")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.FANTASMA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "gotanaranja")) {
								Log.d("INICIO", "Colum"+pTMXTile.getTileColumn() +"fila "+pTMXTile.getTileRow());
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.GOTA_NARANJA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "gota")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.GOTA_AZUL,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GLOBO_AZUL")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.GLOBO_AZUL,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MOCO_ROJO")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.MOCO_ROJO,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MONEDA_MARRON")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.MONEDA_MARRON,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GOTA_ROJA")) {
								context.almacenEnemigos.creaEnemigoInicial(TipoEnemigo.GOTA_ROJA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "bomba")) {
								datosMapa.setM_bomba(datosMapa.getM_bomba()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "corazon")) {
								datosMapa.setM_bomba(datosMapa.getM_corazon()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "correr")) {
								datosMapa.setM_bomba(datosMapa.getM_correr()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "detonador")) {
								datosMapa.setM_bomba(datosMapa.getM_detonador()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "fantasma")) {
								datosMapa.setM_bomba(datosMapa.getM_fantasma()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "fuerza")) {
								datosMapa.setM_bomba(datosMapa.getM_fantasma()+1);
							} else if (pTMXTileProperties.containsTMXProperty("moneda", "potenciador")) {
								datosMapa.setM_bomba(datosMapa.getM_potenciador()+1);
							}							
						}
					});
			
			datosMapa= new DatosMapa();				
			String nombreMapa="tmx/mapa0.tmx";
			nombreMapa=nombreMapa.replace("0", numMap+"");			
			this.mTMXTiledMap = tmxLoader.loadFromAsset(nombreMapa);
//			this.mTMXTiledMap.setOffsetCenter(0, 0);
//			mTMXTiledMap.setScaleCenter(0, 0);
//			mTMXTiledMap.setScaleX(Constantes.FARTOR_FORMA);
//			this.mTMXTiledMap.setOffsetCenter(0, 0);

			capaSuelo = mTMXTiledMap.getTMXLayers().get(0);
			capaPiedrasSombra = mTMXTiledMap.getTMXLayers().get(1);
			capaParedes = mTMXTiledMap.getTMXLayers().get(2);
			capaBordeAbajo = mTMXTiledMap.getTMXLayers().get(3);
			capaTechoPiedras = mTMXTiledMap.getTMXLayers().get(4);
			capaEntidades = mTMXTiledMap.getTMXLayers().get(5);		

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		
		
		capaSuelo.setParent(null);
		capaPiedrasSombra.setParent(null);
		capaParedes.setParent(null);
		capaBordeAbajo.setParent(null);
		capaTechoPiedras.setParent(null);
		capaEntidades.setParent(null);		
		
		
		capaSuelo.setOffsetCenter(0, 0);
		capaPiedrasSombra.setOffsetCenter(0, 0);
		capaParedes.setOffsetCenter(0, 0);
		capaBordeAbajo.setOffsetCenter(0, 0);
		capaTechoPiedras.setOffsetCenter(0, 0);
		
		capaSuelo.setPosition(0, 0);
		capaPiedrasSombra.setPosition(0, 0);
		capaParedes.setPosition(0, 0);
		capaBordeAbajo.setPosition(0, 0);
		capaTechoPiedras.setPosition(0, 0);
		
		
		capaSuelo.setScaleCenter(0, 0);
		capaPiedrasSombra.setScaleCenter(0, 0);
		capaParedes.setScaleCenter(0, 0);
		capaBordeAbajo.setScaleCenter(0, 0);
		capaTechoPiedras.setScaleCenter(0, 0);
		
		
		capaSuelo.setZIndex(Constantes.ZINDEX_CAPA_SUELO);
		capaPiedrasSombra.setZIndex(Constantes.ZINDEX_CAPA_PIEDRAS_SOMBRA);
		capaParedes.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA);
		capaBordeAbajo.setZIndex(Constantes.ZINDEX_CAPA_BORDE_ABAJO);
		capaTechoPiedras.setZIndex(Constantes.ZINDEX_CAPA_TECHO_PIEDRAS);
		
		scene.attachChild(capaSuelo);
		scene.attachChild(capaPiedrasSombra);
		scene.attachChild(capaParedes);
		scene.attachChild(capaBordeAbajo);
		scene.attachChild(capaTechoPiedras);
		
		capaSuelo.setScaleX(Constantes.FARTOR_FORMA);
		capaPiedrasSombra.setScaleX(Constantes.FARTOR_FORMA);
		capaParedes.setScaleX(Constantes.FARTOR_FORMA);
		capaParedes.setVisible(false);
		capaBordeAbajo.setScaleX(Constantes.FARTOR_FORMA);
		capaTechoPiedras.setScaleX(Constantes.FARTOR_FORMA);		

		
		if (primeraCarga){
			context.camaraJuego.setBoundsEnabled(false);
			context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth()*Constantes.FARTOR_FORMA, this.mTMXTiledMap.getHeight());
			context.camaraJuego.setBoundsEnabled(true);
			
			hud.attachScena(scene);	
			context.almacenBombas.onSceneCreated();
			context.bomberman.onCreateScene(scene);
			context.camaraJuego.setChaseEntity(context.bomberman.getSprite());
			context.capaParedes.onSceneCreated();
			scene.attachChild(spritePuerta);  
		}
//		context.camaraJuego.setCenter(0, 832);
//		context.camaraJuego.updateChaseEntity();
//		context.camaraJuego.onUpdate(0.02f);
//		context.camaraJuego.setYMax(960);
		

		context.miengine.setCaramaJuego();		
		context.capaParedes.recalculaPared();		
		
		scene.sortChildren();
		context.gameManager.inicia();
		return scene;
	}

}
