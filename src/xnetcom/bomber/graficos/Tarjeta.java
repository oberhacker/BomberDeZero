package xnetcom.bomber.graficos;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;

public class Tarjeta {
	
	private BomberGame context;
	public Sprite sprTarjeta;
	private TiledSprite sprRetry;
	private Sprite sprTomenu;
	private TiledSprite sprNext;
	private Sprite sprCleared;
	private Sprite sprFailed;
	private AnimatedSprite sprStar1;
	private AnimatedSprite sprStar2;
	private AnimatedSprite sprStar3;
	
	
	
	public Tarjeta(BomberGame context){
		this.context=context;
	}
	
	
	public void carga(){
		BitmapTextureAtlas tarjeta_BTA =new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR);		
		TextureRegion tarjeta_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(tarjeta_BTA, context, "gfx/tarjeta.png", 0, 0);
		tarjeta_BTA.load();
		
		BitmapTextureAtlas retry_BTA =new BitmapTextureAtlas(context.getTextureManager(),256, 128, TextureOptions.BILINEAR);	
		TiledTextureRegion retry_TR =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(retry_BTA, context, "gfx/retry.png",0,0,  2, 1);
		retry_BTA.load();
		
		BitmapTextureAtlas tomenu_BTA =new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR);	
		TextureRegion tomenu_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(tomenu_BTA, context, "gfx/tomenu.png", 0, 0);
		tomenu_BTA.load();
		
		BitmapTextureAtlas next_BTA =new BitmapTextureAtlas(context.getTextureManager(),256, 128, TextureOptions.BILINEAR);	
		TiledTextureRegion next_TR =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(next_BTA, context, "gfx/next.png",0,0,  2, 1);
		next_BTA.load();	
		
		BitmapTextureAtlas cleared_BTA =new BitmapTextureAtlas(context.getTextureManager(),512, 64, TextureOptions.BILINEAR);	
		TextureRegion cleared_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(cleared_BTA, context, "gfx/cleared.png", 0, 0);
		cleared_BTA.load();
		
		BitmapTextureAtlas failed_BTA =new BitmapTextureAtlas(context.getTextureManager(),512, 64, TextureOptions.BILINEAR);	
		TextureRegion failed_TR =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(failed_BTA, context, "gfx/failed.png", 0, 0);
		failed_BTA.load();
		
		BitmapTextureAtlas star_BTA =new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR);	
		TiledTextureRegion star_TR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(star_BTA, context, "gfx/estrellas.png",0,0,  3, 2);
		star_BTA.load();		
		
		
		sprTarjeta = new Sprite(0,0, tarjeta_TR, context.getVertexBufferObjectManager());		
		sprRetry = new TiledSprite(0, 0, retry_TR, context.getVertexBufferObjectManager());
		sprTomenu = new Sprite(0, 0, tomenu_TR, context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == 0 && sprTarjeta.isVisible()){					
					context.menuMapas.verMenuMapas();
				}
				return false;
			}
		};
		sprNext = new TiledSprite(0, 0, next_TR, context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == 0 && getCurrentTileIndex()==0&& sprTarjeta.isVisible()){		
					context.loading.cargaMapa(context.escenaJuego.datosMapa.getNumeroMapa()+1);					
				}
				return false;
			}
		};
		
		sprCleared = new Sprite(0, 0, cleared_TR, context.getVertexBufferObjectManager());
		sprFailed = new Sprite(0, 0, failed_TR, context.getVertexBufferObjectManager());
		sprStar1 = new AnimatedSprite(0, 0, star_TR, context.getVertexBufferObjectManager());
		sprStar2 = new AnimatedSprite(0, 0, star_TR, context.getVertexBufferObjectManager());
		sprStar3 = new AnimatedSprite(0, 0, star_TR, context.getVertexBufferObjectManager());
		
		sprRetry.setOffsetCenter(0, 0);
		sprRetry.setPosition(50, 16);
		
		sprTomenu.setOffsetCenterY(0);
		sprTomenu.setPosition(sprTarjeta.getWidth()/2, 16);
		
		
		sprNext.setOffsetCenter(0, 0);		
		sprNext.setPosition(sprTarjeta.getWidth()-sprNext.getWidth()-50, 16);
		
		
		sprCleared.setOffsetCenterY(0);
		sprCleared.setScaleY(1.3f);		
		sprCleared.setPosition(sprTarjeta.getWidth()/2, sprTarjeta.getHeight()-sprCleared.getHeightScaled()-8);
		
		sprStar2.setScale(1.1f);
		sprStar2.setPosition((sprTarjeta.getWidth()/2)+100-10, (sprTarjeta.getHeight()/2)+100);
		
		
		sprStar1.setScale(1.1f);
		sprStar1.setPosition((sprTarjeta.getWidth()/2)-10, (sprTarjeta.getHeight()/2)+100);
		
		sprStar3.setScale(1.1f);
		sprStar3.setPosition((sprTarjeta.getWidth()/2)+200-10, (sprTarjeta.getHeight()/2)+100);
		
		sprFailed.setOffsetCenterY(0);
		sprFailed.setScaleY(1.3f);		
		sprFailed.setPosition(sprTarjeta.getWidth()/2, sprTarjeta.getHeight()-sprCleared.getHeightScaled()-8);
		
		
		sprTarjeta.attachChild(sprStar3);
		sprTarjeta.attachChild(sprStar1);
		sprTarjeta.attachChild(sprStar2);	
		sprTarjeta.attachChild(sprCleared);		
		sprTarjeta.attachChild(sprFailed);
		sprTarjeta.attachChild(sprNext);	
		sprTarjeta.attachChild(sprRetry);	
		sprTarjeta.attachChild(sprTomenu);	
		sprTarjeta.setPosition(context.CAMERA_WIDTH/2, (context.CAMERA_HEIGHT/2)+70);
			
		
	}
	
	public void attachScene(){		
		sprTarjeta.setVisible(false);
		if (!sprTarjeta.hasParent()){
			context.escenaJuego.hud.hud.registerTouchArea(sprTomenu);
			context.escenaJuego.hud.hud.registerTouchArea(sprNext);
			
			context.escenaJuego.hud.hud.attachChild(sprTarjeta);
		}
				
	}
	
	
	
	public void muestraTarjeta(final int estrellas){

		reiniciaTarjeta();
		new Thread() {
			public void run() {				
				try {
					sprNext.setCurrentTileIndex(0);
					sprRetry.setCurrentTileIndex(0);
					sprTarjeta.setVisible(true);
					switch (estrellas) {
					case 1:
						sprFailed.setVisible(false);
						sprCleared.setVisible(true);
						sprStar1.animate(50,false);			
						break;
					case 2:
						sprFailed.setVisible(false);
						sprCleared.setVisible(true);
						sprStar1.animate(50,false);	
						sleep(500);
						sprStar2.animate(50,false);	
						break;
					case 3:
						sprFailed.setVisible(false);
						sprCleared.setVisible(true);
						sprStar1.animate(50,false);	
						sleep(500);
						sprStar2.animate(50,false);
						sleep(500);
						sprStar3.animate(50,false);	
						break;
					default:
						sprNext.setCurrentTileIndex(1);
						sprRetry.setCurrentTileIndex(1);
						sprFailed.setVisible(true);
						sprCleared.setVisible(false);
						break;
					}					
					
					 if (context.escenaJuego.datosMapa.getNumeroMapa()==Constantes.ULTIMO_MAPA){
						sprNext.setCurrentTileIndex(1);
					 }
					
					
				} catch (Exception e) {
					Log.e("Errrorrrrrrrrrrrrrrr", "", e);
				}
			};
		}.start();
	}
	
	public void reiniciaTarjeta(){
		sprStar1.setCurrentTileIndex(0);
		sprStar2.setCurrentTileIndex(0);
		sprStar3.setCurrentTileIndex(0);
	}
	
	

	

}
