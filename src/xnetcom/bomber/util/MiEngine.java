package xnetcom.bomber.util;




import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 22:28:34 - 27.03.2010
 */

@SuppressLint("WrongCall")
public class MiEngine extends Engine {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Camera camaraNormal;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MiEngine(final EngineOptions pEngineOptions, final Camera camaraNormal) {
		super(pEngineOptions);
		this.camaraNormal = camaraNormal;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Deprecated
	@Override
	public Camera getCamera() {
		return super.mCamera;
	}

	public Camera getCamaraJuego() {
		return super.mCamera;
	}

	public Camera getCamaraNormal() {
		return this.camaraNormal;
	}
	
	public void setCaramaJuego(){
		selCamera=1;
	}
	
	public void setCamaraNormal(){
		selCamera=2;
	}
	

	int selCamera=1;

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onDrawScene(GLState pGLState, Camera pCamera) {
		final Camera firstCamera = this.getCamaraJuego();
		final Camera secondCamera = this.getCamaraNormal();		
		if (selCamera == 1) {
			if (this.mScene != null) {
				this.mScene.onDraw(pGLState, firstCamera);
			}
			firstCamera.onDrawHUD(pGLState);
		} else {
			if (this.mScene != null) {
				this.mScene.onDraw(pGLState, secondCamera);
			}
			secondCamera.onDrawHUD(pGLState);
		}

	}

	
	@Override
	protected Camera getCameraFromSurfaceTouchEvent(final TouchEvent pTouchEvent) {
		if(selCamera==1) {
			return this.getCamaraJuego();
		} else {
			return this.getCamaraNormal();
		}
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	
	
	
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
