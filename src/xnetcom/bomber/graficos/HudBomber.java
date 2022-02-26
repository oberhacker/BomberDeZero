package xnetcom.bomber.graficos;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.preferencias.Preferencias;
import xnetcom.bomber.util.Constantes;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class HudBomber {

	private static final int VIBRAR_BOTON = 50;
	public BomberGame context;

	public HUD hud;

	public HudBomber(BomberGame context) {
		this.context = context;
		hud = new HUD();
	}

	public AssetBitmapTexture mOnScreenControlBaseTexture;
	public TextureRegion mOnScreenControlBaseTextureRegion;
	public AssetBitmapTexture mOnScreenControlKnobTexture;
	public TextureRegion mOnScreenControlKnobTextureRegion;

	public TiledSprite spr_detonador;
	TextureRegion btn_1_TR;
	TextureRegion btn_2_TR;

	Sprite btn_1;
	Sprite btn_2;
	Sprite pause;
	Sprite menu;
	public Text debugText;

	Sprite marcador;
	Sprite fondo;

	public DigitalOnScreenControl mDigitalOnScreenControl;
	private TextureRegion hudTR;
	private TiledTextureRegion iconosHUDTR;
	private Font mFontDigital;
	private Text ct_tiempo;
	private Text ct_vidas;
	private Text ct_bombas;
	private Text ct_explosion;
	private Text ct_monedas;
	private Text controlSizeTxt;
	private Text butonsSizeTxt;
	private Text zoomTxt;
	private Text musicTxt;
	private Text soundTxt;
	private Text vibrationTxt;

	private Sprite cruceta_mas, cruceta_menos, restore;
	private Sprite btn_1_mas, btn_1_menos;
	private Sprite zoom_mas, zoom_menos;
	private Sprite music_mas, music_menos, sound_mas, sound_menos;
	private TiledSprite ticSpr;

	private void screenControl(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
		if (context.gameManager.pausa) {
			return;
		}
		if (context.bomberman != null) {

			if (pValueX > 0) {
				// derecha
				context.vibrar(20);
				context.bomberman.moverDerecha();
			} else if (pValueX < 0) {
				// izquierda
				context.vibrar(20);
				context.bomberman.moverIzquierda();
			} else if (pValueY > 0) {
				// arriba
				context.vibrar(20);
				context.bomberman.moverArriba();
			} else if (pValueY < 0) {
				// abajo
				context.vibrar(20);
				context.bomberman.moverAbajo();
			} else {
				// parado
				context.bomberman.detenerPararAnimacion();
			}
		}
	}

	public void carga() throws IOException {
		cargarPreferencias();

		BitmapTextureAtlas pause_BTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		TextureRegion pause_BTA_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pause_BTA, context, "gfx/pause.png", 0, 0);
		pause_BTA.load();

		BitmapTextureAtlas fondo_BTA = new BitmapTextureAtlas(context.getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);
		TextureRegion fondo_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(fondo_BTA, context, "gfx/menu3.jpg", 0, 0);
		fondo_BTA.load();

		BitmapTextureAtlas menu_BTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		TextureRegion menu_BTA_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_BTA, context, "gfx/menu_btn.png", 0, 0);
		menu_BTA.load();

		BitmapTextureAtlas iconosHUD = new BitmapTextureAtlas(context.getTextureManager(), 256, 32, TextureOptions.BILINEAR);
		iconosHUD.load();
		this.iconosHUDTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(iconosHUD, context, "gfx/iconosHUD.png", 0, 0, 6, 1);

		BitmapTextureAtlas hudBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 1024, 128, TextureOptions.BILINEAR);
		hudBitmapTextureAtlas.load();
		this.hudTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(hudBitmapTextureAtlas, context, "gfx/Hud_Marcador.png", 0, 0);

		this.mFontDigital = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "DigitaldreamFat.ttf", 30, true,
				android.graphics.Color.BLACK);
		mFontDigital.load();

		Font mFont = FontFactory.create(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48);
		mFont.load();
		debugText = new Text(300, 600, mFont, "Seconds elapsed:", "Seconds elapsed: XXXXXX".length(), context.getVertexBufferObjectManager());

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
				}) {
			// ajustamos la sensibilidad
			@Override
			protected void onUpdateControlKnob(final float pRelativeX, final float pRelativeY) {
				float sensibilidad = 0.1f;
				// cuanto pRelativeX > 0.2 mayor sea el numero menos sensible
				// sera
				if (pRelativeX == 0 && pRelativeY == 0) {
					super.onUpdateControlKnob(0, 0);
				}
				// System.out.println("pRelativeX"+pRelativeX);

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

			protected boolean onHandleControlBaseTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pausa) {
					Log.d("TOUCH", "getControlBase x " + getControlBase().getX());
					Log.d("TOUCH", "pSceneTouchEvent x " + pSceneTouchEvent.getX());
					getControlBase().setPosition(pSceneTouchEvent.getX() / 2, pSceneTouchEvent.getY() / 2);
					refreshControlKnobPosition();

				} else {
					super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

				}
				return true;
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

		menu = new Sprite(0, 0, menu_BTA_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN && this.isVisible()) {
					toMenu();
				}

				return true;
			}
		};

		menu.setOffsetCenter(0, 0);
		menu.setPosition(30, context.CAMERA_HEIGHT - 100);
		menu.setAlpha(0.7f);

		pause = new Sprite(0, 0, pause_BTA_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN) {
					pause();
				}

				return true;
			}
		};
		pause.setOffsetCenter(0, 0);
		pause.setPosition(context.CAMERA_WIDTH - 120, context.CAMERA_HEIGHT - 100);
		pause.setAlpha(0.7f);

		controlBase.setAlpha(0.5f);
		// controlBase.setOffsetCenter(0, 0);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.setScale(zControl);

		fondo = new Sprite(context.CAMERA_WIDTH / 2, context.CAMERA_HEIGHT / 2, fondo_TR, context.getVertexBufferObjectManager());
		fondo.setVisible(false);

		// controlBase.setPosition(xControl+controlBase.getWidthScaled()/2,yControl+controlBase.getHeightScaled()/2);
		controlBaseSetPosition(xControl, yControl);

		mFont = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "DD.ttf", 30, true,
				android.graphics.Color.BLACK);// gles2
		mFont.load();

		BitmapTextureAtlas masBTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.DEFAULT);
		BitmapTextureAtlas menosBTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.DEFAULT);
		masBTA.load();
		menosBTA.load();

		TextureRegion masTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(masBTA, context, "gfx/mas07.png", 0, 0);
		TextureRegion menosTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menosBTA, context, "gfx/menos07.png", 0, 0);

		controlSizeTxt = new Text(0, 0, mFont, "Control size:", context.getVertexBufferObjectManager());
		butonsSizeTxt = new Text(0, 0, mFont, "Buttons size:", context.getVertexBufferObjectManager());
		zoomTxt = new Text(0, 0, mFont, "Zoom:", context.getVertexBufferObjectManager());
		musicTxt = new Text(0, 0, mFont, " Music level:00", context.getVertexBufferObjectManager());
		soundTxt = new Text(0, 0, mFont, " Sound level:00", context.getVertexBufferObjectManager());
		vibrationTxt = new Text(0, 0, mFont, " Vibration: ", context.getVertexBufferObjectManager());

		zoomTxt.setOffsetCenter(0, 0);
		zoomTxt.setPosition(20, 350);
		zoomTxt.setVisible(false);
		zoom_mas = new Sprite(190, 365, masTR, context.getVertexBufferObjectManager());
		zoom_mas.setVisible(false);

		zoom_menos = new Sprite(280, 365, menosTR, context.getVertexBufferObjectManager());
		zoom_menos.setVisible(false);

		butonsSizeTxt.setOffsetCenter(0, 0);
		butonsSizeTxt.setPosition(20, 420);
		butonsSizeTxt.setVisible(false);

		btn_1_mas = new Sprite(370, 440, masTR, context.getVertexBufferObjectManager());
		btn_1_menos = new Sprite(460, 440, menosTR, context.getVertexBufferObjectManager());
		btn_1_mas.setVisible(false);
		btn_1_menos.setVisible(false);

		controlSizeTxt.setOffsetCenter(0, 0);
		controlSizeTxt.setPosition(20, 495);
		controlSizeTxt.setVisible(false);

		cruceta_mas = new Sprite(370, 515, masTR, context.getVertexBufferObjectManager());
		cruceta_menos = new Sprite(460, 515, menosTR, context.getVertexBufferObjectManager());

		cruceta_mas.setVisible(false);
		cruceta_menos.setVisible(false);

		
		
		BitmapTextureAtlas ticBTA = new BitmapTextureAtlas(context.getTextureManager(),128, 64, TextureOptions.DEFAULT);	
		ticBTA.load();
		TiledTextureRegion ticTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ticBTA, context, "gfx/tic.png",0,0,2, 1);	
		ticSpr = new TiledSprite(0, 0, ticTR,context.getVertexBufferObjectManager());
		ticSpr.setOffsetCenter(0, 0);
		ticSpr.setPosition(context.CAMERA_WIDTH-90, 330);
		ticSpr.setVisible(false);			
		
		vibrationTxt.setVisible(false);
		vibrationTxt.setOffsetCenter(0, 0);
		vibrationTxt.setPosition(context.CAMERA_WIDTH-355, 340);
		

		sound_mas = new Sprite(context.CAMERA_WIDTH-150, 440, masTR, context.getVertexBufferObjectManager());
		sound_menos = new Sprite(context.CAMERA_WIDTH-60, 440, menosTR, context.getVertexBufferObjectManager());
		sound_menos.setVisible(false);
		sound_mas.setVisible(false);
		
		soundTxt.setVisible(false);
		soundTxt.setOffsetCenter(0, 0);
		soundTxt.setPosition(context.CAMERA_WIDTH-550, 420);
		
		
		music_mas = new Sprite(context.CAMERA_WIDTH-150, 515, masTR, context.getVertexBufferObjectManager());
		music_menos= new Sprite(context.CAMERA_WIDTH-60, 515, menosTR, context.getVertexBufferObjectManager());
		music_mas.setVisible(false);
		music_menos.setVisible(false);
		
		musicTxt.setPosition(context.CAMERA_WIDTH-550, 495);
		musicTxt.setOffsetCenter(0, 0);
		musicTxt.setVisible(false);
		
		
		
		
		BitmapTextureAtlas restore_btn = new BitmapTextureAtlas(context.getTextureManager(), 256, 128, TextureOptions.DEFAULT);
		restore_btn.load();
		TextureRegion restoreTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(restore_btn, context, "gfx/restore_btn.png", 0, 0);
		restore = new Sprite(context.CAMERA_WIDTH / 2, 60, restoreTR, context.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == 0 && isVisible()) {
					reseteaPreferencias();
				}
				return false;
			}
		};
		restore.setVisible(false);

	}

	public void controlBaseSetPosition(float x, float y) {
		Sprite controlBase = this.mDigitalOnScreenControl.getControlBase();
		controlBase.setPosition(x + controlBase.getWidthScaled() / 2, y + controlBase.getHeightScaled() / 2);
	}

	float xControl = Constantes.PREFERENCIAS_CONTROL_X;
	float yControl = Constantes.PREFERENCIAS_CONTROL_Y;
	float zControl = Constantes.PREFERENCIAS_CONTROL_Z;

	public void cargarPreferencias() {
		float xControl = Preferencias.leerPreferenciasFloat("xControl");
		float yControl = Preferencias.leerPreferenciasFloat("yControl");
		float zControl = Preferencias.leerPreferenciasFloat("zControl");

		if (xControl != -1) {
			this.xControl = xControl;
		}
		if (yControl != -1) {
			this.yControl = yControl;
		}
		if (zControl != -1) {
			this.zControl = zControl;
		}
	}

	public void reseteaPreferencias() {
		xControl = Constantes.PREFERENCIAS_CONTROL_X;
		yControl = Constantes.PREFERENCIAS_CONTROL_Y;
		zControl = Constantes.PREFERENCIAS_CONTROL_Z;
		Preferencias.guardarPrefenrenciasFloat("xControl", xControl);
		Preferencias.guardarPrefenrenciasFloat("yControl", yControl);
		Preferencias.guardarPrefenrenciasFloat("zControl", zControl);
		controlBaseSetPosition(xControl, yControl);

	}

	public void guardarPreferencias() {
		Sprite controlBase = this.mDigitalOnScreenControl.getControlBase();
		Preferencias.guardarPrefenrenciasFloat("xControl", controlBase.getX() - controlBase.getWidthScaled() / 2);
		Preferencias.guardarPrefenrenciasFloat("yControl", controlBase.getY() - controlBase.getHeightScaled() / 2);
		Preferencias.guardarPrefenrenciasFloat("zControl", zControl);
	}

	public void toMenu() {
		context.menuMapas.verMenuMapas();
	}

	public Sprite creaMarcador() {
		marcador = new Sprite(0, 0, hudTR, context.getVertexBufferObjectManager());

		spr_detonador = new TiledSprite(0, 0, iconosHUDTR, context.getVertexBufferObjectManager());
		TiledSprite spr_vidas = new TiledSprite(0, 0, iconosHUDTR, context.getVertexBufferObjectManager());
		TiledSprite spr_bombas = new TiledSprite(0, 0, iconosHUDTR, context.getVertexBufferObjectManager());
		TiledSprite spr_explosion = new TiledSprite(0, 0, iconosHUDTR, context.getVertexBufferObjectManager());
		TiledSprite spr_monedas = new TiledSprite(0, 0, iconosHUDTR, context.getVertexBufferObjectManager());

		spr_detonador.setCurrentTileIndex(0);
		spr_vidas.setCurrentTileIndex(1);
		spr_bombas.setCurrentTileIndex(2);
		spr_explosion.setCurrentTileIndex(3);
		spr_monedas.setCurrentTileIndex(4);

		ct_tiempo = new Text(0, 0, mFontDigital, "TIME 3:30", context.getVertexBufferObjectManager());
		ct_vidas = new Text(0, 0, mFontDigital, ":99", context.getVertexBufferObjectManager());
		ct_bombas = new Text(0, 0, mFontDigital, ":10", context.getVertexBufferObjectManager());
		ct_explosion = new Text(0, 0, mFontDigital, ":4", context.getVertexBufferObjectManager());
		ct_monedas = new Text(0, 0, mFontDigital, ":10", context.getVertexBufferObjectManager());

		spr_detonador.setOffsetCenter(0, 0);
		spr_vidas.setOffsetCenter(0, 0);
		spr_bombas.setOffsetCenter(0, 0);
		spr_explosion.setOffsetCenter(0, 0);
		spr_monedas.setOffsetCenter(0, 0);
		ct_tiempo.setOffsetCenter(0, 0);
		ct_vidas.setOffsetCenter(0, 0);
		ct_bombas.setOffsetCenter(0, 0);
		ct_explosion.setOffsetCenter(0, 0);
		ct_monedas.setOffsetCenter(0, 0);

		ct_tiempo.setX(20);
		ct_tiempo.setY(7);
		marcador.attachChild(ct_tiempo);

		spr_vidas.setX(270);
		spr_vidas.setY(8);
		marcador.attachChild(spr_vidas);

		ct_vidas.setX(300);
		ct_vidas.setY(7);
		marcador.attachChild(ct_vidas);

		spr_detonador.setX(393 + 5);
		spr_detonador.setY(8);
		marcador.attachChild(spr_detonador);

		spr_bombas.setX(440 + 5);
		spr_bombas.setY(8);
		marcador.attachChild(spr_bombas);

		ct_bombas.setX(465 + 8);
		ct_bombas.setY(7);
		marcador.attachChild(ct_bombas);

		spr_explosion.setX(555 + 5 + 5);
		spr_explosion.setY(8);
		marcador.attachChild(spr_explosion);

		ct_explosion.setX(582 + 5 + 8);
		ct_explosion.setY(7);
		marcador.attachChild(ct_explosion);

		spr_monedas.setX(665);
		spr_monedas.setY(8);
		marcador.attachChild(spr_monedas);

		ct_monedas.setX(700);
		ct_monedas.setY(7);
		marcador.attachChild(ct_monedas);

		// marcador.setScaleY(1.3f);
		marcador.setOffsetCenterY(0);
		marcador.setPosition(context.CAMERA_WIDTH / 2, context.CAMERA_HEIGHT - marcador.getHeightScaled());

		return marcador;
	}

	boolean pausa = false;

	public void pause() {
		if (pausa) {
			salMenuPausa();
			guardarPreferencias();
			context.gameManager.play();
			pausa = false;
		} else {
			muestraMenuPausa();
			context.gameManager.pausa();
			pausa = true;
		}

	}

	public void salMenuPausa() {
		music_mas.setVisible(false);
		music_menos.setVisible(false);
		musicTxt.setVisible(false);
		soundTxt.setVisible(false);
		sound_mas.setVisible(false);
		sound_menos.setVisible(false);
		vibrationTxt.setVisible(false);
		ticSpr.setVisible(false);
		cruceta_mas.setVisible(false);
		cruceta_menos.setVisible(false);
		controlSizeTxt.setVisible(false);
		btn_1_mas.setVisible(false);
		btn_1_menos.setVisible(false);
		butonsSizeTxt.setVisible(false);
		zoom_menos.setVisible(false);
		zoomTxt.setVisible(false);
		zoom_mas.setVisible(false);
		fondo.setVisible(false);
		marcador.setVisible(true);
		menu.setVisible(true);
		restore.setVisible(false);
	}

	public void muestraMenuPausa() {
		music_mas.setVisible(true);
		music_menos.setVisible(true);
		musicTxt.setVisible(true);
		soundTxt.setVisible(true);
		sound_mas.setVisible(true);
		sound_menos.setVisible(true);
		vibrationTxt.setVisible(true);
		ticSpr.setVisible(true);
		cruceta_mas.setVisible(true);
		cruceta_menos.setVisible(true);
		controlSizeTxt.setVisible(true);
		btn_1_mas.setVisible(true);
		btn_1_menos.setVisible(true);
		butonsSizeTxt.setVisible(true);
		zoom_menos.setVisible(true);
		zoomTxt.setVisible(true);
		zoom_mas.setVisible(true);
		restore.setVisible(true);
		fondo.setVisible(true);
		marcador.setVisible(false);
		menu.setVisible(false);
	}

	public void apretarBotonPlantabomba() {
		if (context.gameManager.pausa) {
			return;
		}
		context.vibrar(VIBRAR_BOTON);
		context.almacenBombas.plantaBomba();
		System.out.println("APRETADOOOOOOO");

	}

	public void apretarBotonExplosion() {
		if (context.gameManager.pausa) {
			return;
		}
		context.vibrar(VIBRAR_BOTON);
		context.almacenBombas.detonarBomba();
		System.out.println("APRETADOOOOOOO");
	}

	public void recolocaElementos() {
		btn_2.setPosition((context.CAMERA_WIDTH - 30) - btn_2.getWidthScaled(), 10);
		btn_1.setPosition((context.CAMERA_WIDTH - 30) - (30 + btn_1.getWidthScaled() * 2), 10);
	}

	TimerHandler timer;

	public void attachScena(Scene scene) {
		hud.attachChild(fondo);
		hud.attachChild(music_menos);
		hud.attachChild(music_mas);
		hud.attachChild(musicTxt);
		hud.attachChild(soundTxt);
		hud.attachChild(sound_mas);
		hud.attachChild(sound_menos);
		hud.attachChild(cruceta_mas);
		hud.attachChild(vibrationTxt);
		hud.attachChild(cruceta_menos);
		hud.attachChild(ticSpr);
		hud.attachChild(zoom_mas);
		hud.attachChild(zoom_menos);
		hud.attachChild(controlSizeTxt);
		hud.attachChild(btn_1_mas);
		hud.attachChild(btn_1_menos);
		hud.attachChild(butonsSizeTxt);
		hud.attachChild(restore);
		hud.attachChild(menu);
		hud.attachChild(zoomTxt);

		hud.setChildScene(mDigitalOnScreenControl);

		hud.attachChild(pause);
		hud.attachChild(btn_1);
		hud.attachChild(btn_2);

		hud.registerTouchArea(btn_1);
		hud.registerTouchArea(restore);
		hud.registerTouchArea(btn_2);
		hud.registerTouchArea(pause);
		hud.registerTouchArea(menu);
		debugText.setVisible(Constantes.DEBUG_TEXT);

		hud.attachChild(debugText);

		hud.attachChild(creaMarcador());

		context.getEngine().getCamera().setHUD(hud);
		recolocaElementos();

		update();
		if (timer == null) {
			timer = new TimerHandler(1f, true, new ITimerCallback() {
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler) {
					try {
						update();
					} catch (Exception e) {
					}
				}
			});
		}
		scene.registerUpdateHandler(timer);
	}

	public void update() {
		ct_vidas.setText(":" + context.gameManager.vidas);
		ct_bombas.setText(":" + context.gameManager.bombaNum);
		ct_explosion.setText(":" + context.gameManager.bombaTam);
		if (context.gameManager.detonador) {
			spr_detonador.setCurrentTileIndex(0);
		} else {
			spr_detonador.setCurrentTileIndex(5);
		}
		int cuenta = context.escenaJuego.datosMapa.getBoosterTotales() - context.gameManager.boostersCogidos - context.gameManager.boostersExplotados;
		ct_monedas.setText(":" + cuenta);

	}

}
