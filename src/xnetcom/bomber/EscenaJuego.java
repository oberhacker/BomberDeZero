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
	public Hud hud;
	public BomberMan bomberman;
	public Scene scene;
	public TMXTiledMap mTMXTiledMap;
	
	public EscenaJuego (BomberGame context){
		this.context=context;
		this.hud = new Hud(context);
		this.bomberman=new BomberMan(context);
	}
	
	public void cargar() throws IOException{
		hud.carga();
		bomberman.carga();
	}
	
	
	
	public Scene onCreateScene(){
		scene = new Scene();		
		try {
			
			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), context.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					/* We are going to count the tiles that have the property "cactus=true" set. */
					if(pTMXTileProperties.containsTMXProperty("cactus", "true")) {
						
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
		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				final float[] playerFootCordinates = bomberman.getSprite().convertLocalCoordinatesToSceneCoordinates(16, 1);
				final TMXLayer tmxLayer = mTMXTiledMap.getTMXLayers().get(0);
				final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
				if(tmxTile != null) {
//					 tmxTile.setTextureRegion(null); <-- Eraser-style removing of tiles =D
					bomberman.currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn())*context.factorForma, tmxLayer.getTileY(tmxTile.getTileRow()));

				}
			}
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
		});		
		
		return scene;
	}
	
	
	
	
	
	
	
	
	
	
}
