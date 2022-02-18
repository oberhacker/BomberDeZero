package xnetcom.bomber.graficos;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
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
import xnetcom.bomber.util.Constantes;
import android.graphics.Typeface;

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

	TextureRegion btn_1_TR;
	TextureRegion btn_2_TR;

	Sprite btn_1;
	Sprite btn_2;
	Sprite pause;
	Sprite menu;
	public Text debugText;
	
	public DigitalOnScreenControl mDigitalOnScreenControl;
	private TextureRegion hudTR;
	private TiledTextureRegion iconosHUDTR;
	private Font mFontDigital;
	private Text ct_tiempo;
	private Text ct_vidas;
	private Text ct_bombas;
	private Text ct_explosion;
	private Text ct_monedas;

	private void screenControl(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
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
		
		BitmapTextureAtlas pause_BTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		TextureRegion pause_BTA_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pause_BTA, context, "gfx/pause.png", 0, 0);
		pause_BTA.load();
		
		BitmapTextureAtlas menu_BTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		TextureRegion menu_BTA_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_BTA, context, "gfx/menu_btn.png", 0, 0);
		menu_BTA.load();
		
		
		
		BitmapTextureAtlas iconosHUD = new BitmapTextureAtlas(context.getTextureManager(),256, 32, TextureOptions.BILINEAR);	
		iconosHUD.load();
		this.iconosHUDTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(iconosHUD, context, "gfx/iconosHUD.png",0,0,6,1);		
		
		
		BitmapTextureAtlas hudBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),1024, 128, TextureOptions.BILINEAR);	
		hudBitmapTextureAtlas.load();
		this.hudTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(hudBitmapTextureAtlas, context, "gfx/Hud_Marcador.png",0,0);
		
		
		this.mFontDigital = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(),"DigitaldreamFat.ttf", 30, true, android.graphics.Color.BLACK);
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
				}){
			// ajustamos la sensibilidad
			@Override
			protected void onUpdateControlKnob(final float pRelativeX, final float pRelativeY) {				
				float sensibilidad=0.1f;
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
		
		
		
		menu = new Sprite(0, 0, menu_BTA_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN) {
					pause();
				}

				return true;
			}
		};		
		
		menu.setOffsetCenter(0, 0);
		menu.setPosition(30, context.CAMERA_HEIGHT-100);
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
		pause.setPosition(context.CAMERA_WIDTH-120, context.CAMERA_HEIGHT-100);
		pause.setAlpha(0.7f);
		
		
		controlBase.setAlpha(0.5f);
		controlBase.setOffsetCenter(0, 0);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.setScale(2f);
		
		
		
		// preparamos el marcador
		
		
		
		
		
		
		
	}

	
	
	public Sprite creaMarcador(){
		Sprite marcador= new Sprite(0, 0, hudTR, context.getVertexBufferObjectManager());
		
		TiledSprite spr_detonador = new TiledSprite(0, 0, iconosHUDTR,context.getVertexBufferObjectManager());
		TiledSprite spr_vidas = new TiledSprite(0,0, iconosHUDTR,context.getVertexBufferObjectManager());
		TiledSprite spr_bombas = new TiledSprite(0, 0, iconosHUDTR,context.getVertexBufferObjectManager());
		TiledSprite spr_explosion = new TiledSprite(0, 0, iconosHUDTR,context.getVertexBufferObjectManager());
		TiledSprite spr_monedas = new TiledSprite(0, 0, iconosHUDTR,context.getVertexBufferObjectManager());
		
		spr_detonador.setCurrentTileIndex(0);
		spr_vidas.setCurrentTileIndex(1);
		spr_bombas.setCurrentTileIndex(2);
		spr_explosion.setCurrentTileIndex(3);
		spr_monedas.setCurrentTileIndex(4);
		
		
		ct_tiempo = new Text(0, 0, mFontDigital,"TIME 3:30",context.getVertexBufferObjectManager());
		ct_vidas = new Text(0, 0, mFontDigital, ":99",context.getVertexBufferObjectManager());
		ct_bombas = new Text(0, 0, mFontDigital, ":10",context.getVertexBufferObjectManager());
		ct_explosion = new Text(0, 0, mFontDigital, ":4",context.getVertexBufferObjectManager());
		ct_monedas = new Text(0, 0, mFontDigital, ":10",context.getVertexBufferObjectManager());
		
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
		
		spr_detonador.setX(393+5);
		spr_detonador.setY(8);
		marcador.attachChild(spr_detonador);
		
		spr_bombas.setX(440+5);
		spr_bombas.setY(8);
		marcador.attachChild(spr_bombas);
		
		ct_bombas.setX(465+8);
		ct_bombas.setY(7);
		marcador.attachChild(ct_bombas);
		
		spr_explosion.setX(555+5+5);
		spr_explosion.setY(8);
		marcador.attachChild(spr_explosion);
		
		ct_explosion.setX(582+5+8);
		ct_explosion.setY(7);
		marcador.attachChild(ct_explosion);
		
		spr_monedas.setX(665);
		spr_monedas.setY(8);
		marcador.attachChild(spr_monedas);		
		
		ct_monedas.setX(700);
		ct_monedas.setY(7);
		marcador.attachChild(ct_monedas);
		
//		marcador.setScaleY(1.3f);
		marcador.setOffsetCenterY(0);
		marcador.setPosition(context.CAMERA_WIDTH/2, context.CAMERA_HEIGHT-marcador.getHeightScaled());
		
		return marcador;
	}
	
	
	
	public void pause(){		
		context.menuMapas.verMenuMapas();		

	}
	

	public void apretarBotonPlantabomba() {
		context.vibrar(VIBRAR_BOTON);
		context.almacenBombas.plantaBomba();		
		System.out.println("APRETADOOOOOOO");

	}


	public void apretarBotonExplosion() {
		context.vibrar(VIBRAR_BOTON);
		context.almacenBombas.detonarBomba();
		System.out.println("APRETADOOOOOOO");
	}
	
	public void recolocaElementos(){
		btn_1.setPosition((context.CAMERA_WIDTH - 30)-btn_1.getWidthScaled(), 10);
		btn_2.setPosition((context.CAMERA_WIDTH - 30)-(30+btn_2.getWidthScaled()*2), 10);
	}

	IUpdateHandler updater;
	public void attachScena(Scene scene) {
		scene.setChildScene(this.mDigitalOnScreenControl);	
		
		hud.attachChild(menu);
		hud.attachChild(pause);
		hud.attachChild(btn_1);
		hud.attachChild(btn_2);
		
		hud.registerTouchArea(btn_1);
		hud.registerTouchArea(btn_2);
		hud.registerTouchArea(pause);
		hud.registerTouchArea(menu);
		debugText.setVisible(Constantes.DEBUG_TEXT);
		
		hud.attachChild(debugText);
		
		hud.attachChild(creaMarcador());		
		
		context.getEngine().getCamera().setHUD(hud);
		recolocaElementos();
		
		if (updater==null){
			updater=new IUpdateHandler() {
				@Override
				public void onUpdate(float pSecondsElapsed) {				
					
				}

				@Override
				public void reset() {

				}
			};
			scene.registerUpdateHandler(updater);
		}		
	}

}
