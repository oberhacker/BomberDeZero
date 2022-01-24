package xnetcom.bomber;

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

import xnetcom.bomber.AlmacenEnemigos.TipoEnemigo;

public class EscenaJuego {

	public BomberGame context;
	public HudBomber hud;
	public BomberMan bomberman;
	public Scene scene;
	public TMXTiledMap mTMXTiledMap;
	
	
	public Matriz matriz;
	public AlmacenEnemigos almacenEnemigos;
	

	public TMXLayer capaSuelo;
	public TMXLayer capaPiedrasSombra;
	public TMXLayer capaParedes;
	public TMXLayer capaBordeAbajo;
	public TMXLayer capaTechoPiedras;

	public EscenaJuego(BomberGame context) {			
		this.context = context;
		this.matriz= new Matriz();	
		this.almacenEnemigos= new AlmacenEnemigos(context);
		this.hud = new HudBomber(context);
		this.bomberman = new BomberMan(context);
	}

	public void cargar() {
		try {
			hud.carga();
			bomberman.carga();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public Scene onCreateScene() {
		scene = new Scene();
		try {

			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {

							if (pTMXTileProperties.containsTMXProperty("muro", "true")) {
								matriz.setValor(matriz.MURO, pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
								matriz.setValor(matriz.PARED, pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")) {
								matriz.setValor(matriz.MURO, pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MONEDA,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "globo")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GLOBO,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "fantasma")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.FANTASMA,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "gotanaranja")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GOTA_NARANJA,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GLOBO_AZUL")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GLOBO_AZUL,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MOCO_ROJO")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MOCO_ROJO,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MONEDA_MARRON")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.MONEDA_MARRON,pTMXTile.getTileRow(), pTMXTile.getTileRow());
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GOTA_ROJA")) {
								almacenEnemigos.creaEnemigo(TipoEnemigo.GOTA_ROJA,pTMXTile.getTileRow(), pTMXTile.getTileRow());
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
			
			
			capaSuelo.setZIndex(Constantes.ZINDEX_CAPA_SUELO);
			capaPiedrasSombra.setZIndex(Constantes.ZINDEX_CAPA_PIEDRAS_SOMBRA);
			capaParedes.setZIndex(Constantes.ZINDEX_CAPA_PAREDES);
			capaBordeAbajo.setZIndex(Constantes.ZINDEX_CAPA_BORDE_ABAJO);
			capaTechoPiedras.setZIndex(Constantes.ZINDEX_CAPA_TECHO_PIEDRAS);
			

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

		scene.attachChild(this.mTMXTiledMap);

		context.camaraJuego.setBoundsEnabled(false);
		context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth()*Constantes.FARTOR_FORMA, this.mTMXTiledMap.getHeight());
		context.camaraJuego.setBoundsEnabled(true);

		hud.attachScena(scene);		
		bomberman.onCreateScene(scene);
		context.camaraJuego.setChaseEntity(bomberman.getSprite());
		context.miengine.setCaramaJuego();
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
