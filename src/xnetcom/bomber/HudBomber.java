package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

public class HudBomber {

	private static final int VIBRAR_BOTON = 50;
	public BomberGame context;


	public HUD hud;

	public HudBomber(BomberGame context) {
		this.context = context;
	}

	public AssetBitmapTexture mOnScreenControlBaseTexture;
	public TextureRegion mOnScreenControlBaseTextureRegion;
	public AssetBitmapTexture mOnScreenControlKnobTexture;
	public TextureRegion mOnScreenControlKnobTextureRegion;

	TextureRegion btn_1_TR;
	TextureRegion btn_2_TR;

	Sprite btn_1;
	Sprite btn_2;

	public DigitalOnScreenControl mDigitalOnScreenControl;

	private void screenControl(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
		if (context.escenaJuego.bomberman != null) {
			if (pValueX > 0) {
				// derecha
				context.escenaJuego.bomberman.moverDerecha();
			} else if (pValueX < 0) {
				// izquierda
				context.escenaJuego.bomberman.moverIzquierda();
			} else if (pValueY > 0) {
				// arriba
				context.escenaJuego.bomberman.moverArriba();
			} else if (pValueY < 0) {
				// abajo
				context.escenaJuego.bomberman.moverAbajo();
			} else {
				// parado
				context.escenaJuego.bomberman.detenerPararAnimacion();
			}
		}
	}

	public void carga() throws IOException {

		BitmapTextureAtlas btn_1_BTA = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btn_1_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_1_BTA, context, "gfx/btn_1.png", 0, 0);
		btn_1_BTA.load();

		BitmapTextureAtlas btn_2_BTA = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		btn_2_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_2_BTA, context, "gfx/btn_2.png", 0, 0);
		btn_2_BTA.load();

		this.mOnScreenControlBaseTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlBaseTexture);
		this.mOnScreenControlBaseTexture.load();

		this.mOnScreenControlKnobTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlKnobTexture);
		this.mOnScreenControlKnobTexture.load();

		this.mDigitalOnScreenControl = new DigitalOnScreenControl(15, 15, context.camaraJuego, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f,
				context.getVertexBufferObjectManager(), new IOnScreenControlListener() {

					float pValueXAnterior;
					float pValueYAnterior;

					@Override
					public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
						if (pValueXAnterior != pValueX || pValueY != pValueYAnterior) {
							screenControl(pBaseOnScreenControl, pValueX, pValueY);
							pValueXAnterior = pValueX;
							pValueYAnterior = pValueY;
						}

					}
				}){
			// ajustamos la sensibilidad
			@Override
			protected void onUpdateControlKnob(final float pRelativeX, final float pRelativeY) {				
				float sensibilidad=0.2f;
				// cuanto pRelativeX > 0.2 mayor sea el numero menos sensible sera
				if (pRelativeX == 0 && pRelativeY == 0) {
					super.onUpdateControlKnob(0, 0);
				}
				//System.out.println("pRelativeX"+pRelativeX);

				if (Math.abs(pRelativeX) > Math.abs(pRelativeY)) {
					
					if (pRelativeX > sensibilidad) {
						super.onUpdateControlKnob(0.5f, 0);
					} else if (pRelativeX < -sensibilidad) {
						super.onUpdateControlKnob(-0.5f, 0);
					} else {
						super.onUpdateControlKnob(0, 0);
					}
				} else {
					if (pRelativeY > sensibilidad) {
						super.onUpdateControlKnob(0, 0.5f);
					} else if (pRelativeY < -sensibilidad) {
						super.onUpdateControlKnob(0, -0.5f);
					} else {
						super.onUpdateControlKnob(0, 0);
					}
				}
			}
		};
		final Sprite controlBase = this.mDigitalOnScreenControl.getControlBase();

		btn_1 = new Sprite(0, 0, btn_1_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN) {
					apretarBotonPlantabomba();
				}
				return false;
			}
		};
		btn_1.setOffsetCenter(0, 0);
		btn_1.setScale(0.6f);
		btn_1.setScaleCenter(0, 0);
		btn_1.setAlpha(0.5f);
		
		btn_2 = new Sprite(0, 0, btn_2_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN) {
					apretarBotonExplosion();
				}
				return false;
			}
		};
		btn_2.setOffsetCenter(0, 0);
		btn_2.setScale(0.6f);
		btn_2.setScaleCenter(0, 0);
		btn_2.setAlpha(0.5f);
		
		controlBase.setAlpha(0.5f);
		controlBase.setOffsetCenter(0, 0);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.setScale(2f);
	}

	public void apretarBotonPlantabomba() {
		context.vibrar(VIBRAR_BOTON);
//		context.escenaJuego.cargaMapaBORRARESTEMETPODO();
		System.out.println("APRETADOOOOOOO");
	}

	public void apretarBotonExplosion() {
		context.vibrar(VIBRAR_BOTON);		
		context.escenaJuego.bomberman.getSprite().setZIndex(1000);
		System.out.println("APRETADOOOOOOO");
	}
	
	public void recolocaElementos(){
		btn_1.setPosition((context.CAMERA_WIDTH - 30)-btn_1.getWidthScaled(), 10);
		btn_2.setPosition((context.CAMERA_WIDTH - 30)-(30+btn_2.getWidthScaled()*2), 10);
	}

	public void attachScena(Scene scene) {
		scene.setChildScene(this.mDigitalOnScreenControl);

		hud = new HUD();
		
		hud.attachChild(btn_1);
		hud.attachChild(btn_2);
		
		hud.registerTouchArea(btn_1);
		hud.registerTouchArea(btn_2);
		
		context.getEngine().getCamera().setHUD(hud);
		recolocaElementos();
	}

}
