package xnetcom.bomber;



import java.io.IOException;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;

import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class Prueba extends SimpleBaseGameActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int CAMERA_WIDTH = 1200;
	public static final int CAMERA_HEIGHT = 720;	
	public float factorForma =1.15625f;

	// ===========================================================
	// Fields
	// ===========================================================

	public SmoothCamera camaraJuego;

	public ITexture mPlayerTexture;
	public TiledTextureRegion mPlayerTextureRegion;

	public TMXTiledMap mTMXTiledMap;
	public int mCactusCount;
	
	public ITexture mOnScreenControlBaseTexture;
	public ITextureRegion mOnScreenControlBaseTextureRegion;
	public ITexture mOnScreenControlKnobTexture;
	public ITextureRegion mOnScreenControlKnobTextureRegion;
	public DigitalOnScreenControl mDigitalOnScreenControl;
	public Rectangle currentTileRectangle;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "The tile the player is walking on will be highlighted.", Toast.LENGTH_LONG).show();

		this.camaraJuego = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 1000f, 1000f, 1);
		this.camaraJuego.setBoundsEnabled(false);
		this.camaraJuego.setZoomFactor(2f);
//		mCamera.setCenter(10, 10);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camaraJuego);
	}

	@Override
	public void onCreateResources() throws IOException {
		this.mPlayerTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/player.png", TextureOptions.DEFAULT);
		this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3, 4);
		this.mPlayerTexture.load();
		
		
		this.mOnScreenControlBaseTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlBaseTexture);
		this.mOnScreenControlBaseTexture.load();

		this.mOnScreenControlKnobTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlKnobTexture);
		this.mOnScreenControlKnobTexture.load();
		
		
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();

		try {
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
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
			mTMXTiledMap.setScaleX(factorForma);
			this.mTMXTiledMap.setOffsetCenter(0, 0);
			
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		
					
		
				
		
		
		scene.attachChild(this.mTMXTiledMap);

		/* Make the camera not exceed the bounds of the TMXEntity. */
		this.camaraJuego.setBoundsEnabled(false);
		this.camaraJuego.setBounds(0, 0, this.mTMXTiledMap.getWidth(), this.mTMXTiledMap.getHeight());
		this.camaraJuego.setBoundsEnabled(true);


		/* Create the sprite and add it to the scene. */
		final AnimatedSprite player = new AnimatedSprite(20, CAMERA_HEIGHT-100, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
		player.setOffsetCenter(0, 0);
		
		final PhysicsHandler physicsHandler = new PhysicsHandler(player);
		player.registerUpdateHandler(physicsHandler);
		
		
		this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, 0, this.camaraJuego, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 100, pValueY * 100);
			}
		});
		this.mDigitalOnScreenControl.setAllowDiagonal(false);
//		mDigitalOnScreenControl.setWidth(mDigitalOnScreenControl.getWidth()*0.8f);
		final Sprite controlBase = this.mDigitalOnScreenControl.getControlBase();
		controlBase.setAlpha(0.5f);
		controlBase.setOffsetCenter(0, 0);

		
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		mDigitalOnScreenControl.setScale(2f);
//		mDigitalOnScreenControl.setWidth(mDigitalOnScreenControl.getWidthScaled()*0.8f);
		scene.setChildScene(this.mDigitalOnScreenControl);
		
		
		this.camaraJuego.setChaseEntity(player);
		
		final Path path = new Path(5).to(50, 740).to(50, 1000).to(820, 1000).to(820, 740).to(0);

//		player.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path, null, new IPathModifierListener() {
//			@Override
//			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
//
//			}
//
//			@Override
//			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
//				switch(pWaypointIndex) {
//					case 0:
//						player.animate(new long[] { 200, 200, 200 }, 0, 2, true);
//						break;
//					case 1:
//						player.animate(new long[] { 200, 200, 200 }, 3, 5, true);
//						break;
//					case 2:
//						player.animate(new long[] { 200, 200, 200 }, 6, 8, true);
//						break;
//					case 3:
//						player.animate(new long[] { 200, 200, 200 }, 9, 11, true);
//						break;
//				}
//			}
//
//			@Override
//			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
//
//			}
//
//			@Override
//			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
//
//			}
//		})));

		/* Now we are going to create a rectangle that will  always highlight the tile below the feet of the pEntity. */
		currentTileRectangle = new Rectangle(0, 0, this.mTMXTiledMap.getTileWidth(), this.mTMXTiledMap.getTileHeight(), this.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		currentTileRectangle.setScaleX(factorForma);
		currentTileRectangle.setPosition(mTMXTiledMap.getTMXLayers().get(0).getTileX(2), mTMXTiledMap.getTMXLayers().get(0).getTileY(2));		
		currentTileRectangle.setPosition(2*64*factorForma, currentTileRectangle.getY());
		
		scene.attachChild(currentTileRectangle);

		/* The layer for the player to walk on. */
		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);

		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				
//				float x=CAMERA_WIDTH;
//				float y = CAMERA_HEIGHT;
//				mCamera.setCenter(x, y);
				System.out.println();
//				currentTileRectangle.setPosition(2*64*1.21875f, currentTileRectangle.getY());
//				mTMXTiledMap.getTMXLayers().get(3).getTMXTile(3, 3).setTextureRegion(mTMXTiledMap.getTMXLayers().get(2).getTMXTile(0, 14).getTextureRegion());
				
				
				
				
//				currentTileRectangle.setPosition(2*64*1.21875f, currentTileRectangle.getY());mTMXTiledMap.getTMXLayers().get(0).getTMXTile(1, 1).;
				
				
				
//				/* Get the scene-coordinates of the players feet. */
				final float[] playerFootCordinates = player.convertLocalCoordinatesToSceneCoordinates(16, 1);
//
//				/* Get the tile the feet of the player are currently waking on. */
				final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
				if(tmxTile != null) {
//					 tmxTile.setTextureRegion(null); <-- Eraser-style removing of tiles =D
					currentTileRectangle.setPosition(tmxLayer.getTileX(tmxTile.getTileColumn())*factorForma, tmxLayer.getTileY(tmxTile.getTileRow()));
//					System.out.println("RectanguloX "+currentTileRectangle.getX() +"Y "+currentTileRectangle.getY());
				}
			}
		});
		scene.attachChild(player);

		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
