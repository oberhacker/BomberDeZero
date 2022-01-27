package xnetcom.bomber.scenas;

import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import android.content.Entity;
import xnetcom.bomber.AlmacenEnemigos;
import xnetcom.bomber.AlmacenEnemigos.TipoEnemigo;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.entidades.Bomba;
import xnetcom.bomber.entidades.BomberMan;
import xnetcom.bomber.graficos.HudBomber;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Matriz;

public class EscenaJuego {

	public BomberGame context;
	public HudBomber hud;
	
	public Scene scene;
	public TMXTiledMap mTMXTiledMap;
	
	
	public Matriz matriz;
	public AlmacenEnemigos almacenEnemigos;
	

	public TMXLayer capaSuelo;
	public TMXLayer capaPiedrasSombra;
	public TMXLayer capaParedes;
	public TMXLayer capaBordeAbajo;
	public TMXLayer capaTechoPiedras;

	public Bomba bomba;
	
	public EscenaJuego(BomberGame context) {			
		this.context = context;
		this.matriz= new Matriz();	
		this.almacenEnemigos= new AlmacenEnemigos(context);
		this.hud = new HudBomber(context);
		this.bomba= new Bomba(context);
	}

	public void cargar() {
		try {
			hud.carga();
			context.bomberman.carga();
			bomba.cargaTexturas();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public TMXTile pTMXTilePared;
	public Scene onCreateScene() {
		scene = new Scene();
		try {

			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {

							if (pTMXTileProperties.containsTMXProperty("muro", "true")) {
								matriz.setValor(Matriz.MURO, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
								matriz.setValor(Matriz.PARED, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
								pTMXTilePared=pTMXTile;
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MOCO,pTMXTile.getTileRow(),pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MONEDA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "globo")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GLOBO,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "fantasma")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.FANTASMA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "gotanaranja")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GOTA_NARANJA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GLOBO_AZUL")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GLOBO_AZUL,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MOCO_ROJO")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MOCO_ROJO,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MONEDA_MARRON")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MONEDA_MARRON,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GOTA_ROJA")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GOTA_ROJA,pTMXTile.getTileRow(), pTMXTile.getTileColumn());
							}
						}
					});

			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/mapa1.tmx");
			this.mTMXTiledMap.setOffsetCenter(0, 0);
			mTMXTiledMap.setScaleCenter(0, 0);
			mTMXTiledMap.setScaleX(Constantes.FARTOR_FORMA);
			this.mTMXTiledMap.setOffsetCenter(0, 0);

			capaSuelo = mTMXTiledMap.getTMXLayers().get(0);
			capaPiedrasSombra = mTMXTiledMap.getTMXLayers().get(1);
			capaParedes = mTMXTiledMap.getTMXLayers().get(2);
			capaBordeAbajo = mTMXTiledMap.getTMXLayers().get(3);
			capaTechoPiedras = mTMXTiledMap.getTMXLayers().get(4);
			
		

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		
		
		capaSuelo.setParent(null);
		capaPiedrasSombra.setParent(null);
		capaParedes.setParent(null);
		capaBordeAbajo.setParent(null);
		capaTechoPiedras.setParent(null);
		
		
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
		capaParedes.setZIndex(Constantes.ZINDEX_CAPA_PAREDES);
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
		capaBordeAbajo.setScaleX(Constantes.FARTOR_FORMA);
		capaTechoPiedras.setScaleX(Constantes.FARTOR_FORMA);		

		mTMXTiledMap.sortChildren();
		context.camaraJuego.setBoundsEnabled(false);
		context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth()*Constantes.FARTOR_FORMA, this.mTMXTiledMap.getHeight());
		context.camaraJuego.setBoundsEnabled(true);

		hud.attachScena(scene);		
		context.bomberman.onCreateScene(scene);
		context.camaraJuego.setChaseEntity(context.bomberman.getSprite());
		context.miengine.setCaramaJuego();
		
		bomba.creaBatch();
		scene.sortChildren();
		return scene;
	}

	public void cargaMapaBORRARESTEMETPODO() {

		mTMXTiledMap.detachSelf();
		try {

			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {

							if (pTMXTileProperties.containsTMXProperty("muro", "true")) {

								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {

								System.out.println("pared");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								System.out.println("muro");
							}

						}
					});

			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/mapa1.tmx");
			this.mTMXTiledMap.setOffsetCenter(0, 0);
			mTMXTiledMap.setScaleCenter(0, 0);
			mTMXTiledMap.setScaleX(Constantes.FARTOR_FORMA);
			this.mTMXTiledMap.setOffsetCenter(0, 0);

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

		scene.attachChild(this.mTMXTiledMap);

		context.camaraJuego.setBoundsEnabled(false);
//		context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth(), this.mTMXTiledMap.getHeight());
//		context.camaraJuego.setBoundsEnabled(true);
		
		capaSuelo = mTMXTiledMap.getTMXLayers().get(0);
		capaPiedrasSombra = mTMXTiledMap.getTMXLayers().get(1);
		capaParedes = mTMXTiledMap.getTMXLayers().get(2);
		capaBordeAbajo = mTMXTiledMap.getTMXLayers().get(3);
		capaTechoPiedras = mTMXTiledMap.getTMXLayers().get(4);
		
		mTMXTiledMap.setZIndex(Constantes.ZINDEX_CAPA_SUELO);
		capaSuelo.setZIndex(Constantes.ZINDEX_CAPA_SUELO);
		capaPiedrasSombra.setZIndex(Constantes.ZINDEX_CAPA_PIEDRAS_SOMBRA);
		capaParedes.setZIndex(Constantes.ZINDEX_CAPA_PAREDES);
		capaBordeAbajo.setZIndex(Constantes.ZINDEX_CAPA_BORDE_ABAJO);
		capaTechoPiedras.setZIndex(Constantes.ZINDEX_CAPA_TECHO_PIEDRAS);
		scene.sortChildren();

	}

}
