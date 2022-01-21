package xnetcom.bomber;



import java.io.IOException;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class BomberGame extends SimpleBaseGameActivity {
	
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int CAMERA_WIDTH = 1280;
	public static final int CAMERA_HEIGHT = 720;	
	public float factorForma =1.15625f;

	// ===========================================================
	// Fields
	// ===========================================================

	public SmoothCamera camaraJuego;	
	public EscenaJuego escenaJuego;

	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "The tile the player is walking on will be highlighted.", Toast.LENGTH_LONG).show();

		this.camaraJuego = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 1000f, 1000f, 1);
		this.camaraJuego.setBoundsEnabled(false);
		this.camaraJuego.setZoomFactor(1.2f);
//		mCamera.setCenter(10, 10);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camaraJuego);
	}

	@Override
	public void onCreateResources() throws IOException {
		escenaJuego= new EscenaJuego(this);
		escenaJuego.cargar();
		
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		Scene scene = escenaJuego.onCreateScene();
		return scene;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
