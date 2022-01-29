package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import xnetcom.bomber.entidades.AlmacenBombas;
import xnetcom.bomber.entidades.BomberMan;
import xnetcom.bomber.graficos.CapaParedes;
import xnetcom.bomber.graficos.SoundManager;
import xnetcom.bomber.scenas.Carga;
import xnetcom.bomber.scenas.EscenaJuego;
import xnetcom.bomber.scenas.Inicio;
import xnetcom.bomber.util.MiEngine;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class BomberGame extends SimpleBaseGameActivity {

	// ===========================================================
	// Constants
	// ===========================================================

	public static int CAMERA_WIDTH = 1280;
	public static int CAMERA_HEIGHT = 720;


	// ===========================================================
	// Fields
	// ===========================================================

	public SmoothCamera camaraJuego;
	Camera camaraNormal;
	public EscenaJuego escenaJuego;
	public Vibrator vibrator;
	private boolean vibrar = true;

	public Carga escenaCarga;
	public MiEngine miengine;

	public Inicio escenaInicio;
	public SoundManager soundManager;
	public BomberMan bomberman;
	public AlmacenBombas almacenBombas;
	public CapaParedes capaParedes;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Toast.makeText(this, "The tile the player is walking on will be highlighted.", Toast.LENGTH_LONG).show();
		DetectorRatio();
		this.camaraJuego = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 1000f, 1000f, 1);
		this.camaraJuego.setBoundsEnabled(false);
		this.camaraJuego.setZoomFactor(1.2f);
		this.camaraNormal = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.camaraJuego);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);

		miengine = new MiEngine(engineOptions, this.camaraNormal);
		miengine.setCamaraNormal();
		return engineOptions;
	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return miengine;
	}

	@Override
	public void onCreateResources() throws IOException {
		escenaInicio = new Inicio(this);
		escenaCarga = new Carga(this);
		escenaJuego = new EscenaJuego(this);
		soundManager= new SoundManager(this);
		bomberman=new BomberMan(this);
		almacenBombas= new AlmacenBombas(this);
		capaParedes= new CapaParedes(this);
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mEngine.enableVibrator(this);

		escenaCarga.onLoadComplete();
		return escenaCarga.scenaCarga;

	}

	// ===========================================================
	// Methods
	// ===========================================================

	public void DetectorRatio() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels;
		int height = metrics.heightPixels;

		float ratio = (float) width / height;
		Float newWidth = (int) CAMERA_HEIGHT * ratio;
		CAMERA_WIDTH = newWidth.intValue();
	}

	public boolean isVibrar() {
		return vibrar;
	}

	public void vibrar(final long milisegundos) {
		if (vibrar) {
			this.runOnUiThread(new Runnable() {
				public void run() {
					vibrator.vibrate(milisegundos);
				}
			});			
		}
	}

	public void setVibrar(boolean vibrar) {
		this.vibrar = vibrar;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
