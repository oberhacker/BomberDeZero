package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;

public class EscenaJuego {

	public BomberGame context;
	public HudBomber hud;
	public BomberMan bomberman;
	public Scene scene;
	public TMXTiledMap mTMXTiledMap;

	public EscenaJuego(BomberGame context) {
		this.context = context;
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
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "globo")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "fantasma")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "gotanaranja")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "GLOBO_AZUL")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("enemigo", "MOCO_ROJO")) {
								System.out.println("muro");
							}else if (pTMXTileProperties.containsTMXProperty("enemigo", "MONEDA_MARRON")) {
								System.out.println("muro");
							}else if (pTMXTileProperties.containsTMXProperty("enemigo", "GOTA_ROJA")) {
								System.out.println("muro");
							}

						}
					});

			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/mapa0.tmx");
			this.mTMXTiledMap.setOffsetCenter(0, 0);
			mTMXTiledMap.setScaleCenter(0, 0);
			mTMXTiledMap.setScaleX(context.factorForma);
			this.mTMXTiledMap.setOffsetCenter(0, 0);

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}

		scene.attachChild(this.mTMXTiledMap);

		context.camaraJuego.setBoundsEnabled(false);
		context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth(), this.mTMXTiledMap.getHeight());
		context.camaraJuego.setBoundsEnabled(true);

		hud.attachScena(scene);
		context.camaraJuego.setChaseEntity(bomberman.getSprite());
		hud.addBomberman(bomberman);
		bomberman.onCreateScene(scene);
		context.miengine.setCaramaJuego();
		return scene;
	}
	
	public void cargaMapa(){
		
		mTMXTiledMap.detachSelf();
		try {

			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(),
					new ITMXTilePropertiesListener() {
						@Override
						public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
	
							if (pTMXTileProperties.containsTMXProperty("muro", "true")) {
								System.out.println("muro");
							} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
								System.out.println("muro");
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
			mTMXTiledMap.setScaleX(context.factorForma);
			this.mTMXTiledMap.setOffsetCenter(0, 0);

		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		
		scene.attachChild(this.mTMXTiledMap);
		
		context.camaraJuego.setBoundsEnabled(false);
		context.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth(), this.mTMXTiledMap.getHeight());
		context.camaraJuego.setBoundsEnabled(true);
		
		
	}
	

}
