package xnetcom.bomber.scenas;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.BomberGame;

public class Inicio {

	private BomberGame context;	
	
	
	private Sprite sprtBombaPlay;
	private Sprite sprtOpciones;
	
	private Sprite misiones;
	private Sprite entrenamiento;

	
	public Scene escenaInicio;




	public Inicio(BomberGame context) {
		this.context=context;
	}
	
	public void carga(){
		escenaInicio= new Scene();
		
		BitmapTextureAtlas inicio_BTA =new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		TextureRegion fondo_inicio_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(inicio_BTA, context, "gfx/fondo_menu_inicio.png", 0, 0);
		inicio_BTA.load();	
		
		
		BitmapTextureAtlas bomba_inicio_BTA = new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion bomba_inicio_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bomba_inicio_BTA, context, "gfx/bomba_play_inicio.png", 0, 0);
		bomba_inicio_BTA.load();
		
		Sprite  fondo=new Sprite(context.CAMERA_WIDTH/2, context.CAMERA_HEIGHT/2, fondo_inicio_TR,context.getEngine().getVertexBufferObjectManager());
		
		
		sprtBombaPlay = new Sprite(context.CAMERA_WIDTH/2, (context.CAMERA_HEIGHT/2)-50, bomba_inicio_TR,context.getEngine().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(!isVisible())return false;
				if (pSceneTouchEvent.getAction() == 1) {
//					cargaMenuCampOentrenamiento();
					
					Scene scene = context.escenaJuego.onCreateScene();
					context.getEngine().setScene(scene);
					return false;
				} 
					return false;
			}
		};
		
		sprtBombaPlay.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.7f, 1f, 1.3f),new ScaleModifier(0.7f, 1.3f, 1f))));
		
		
		escenaInicio.attachChild(sprtBombaPlay);		
		escenaInicio.registerTouchArea(sprtBombaPlay);
		escenaInicio.setBackground(new SpriteBackground(fondo));	

	}

}
