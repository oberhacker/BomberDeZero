package xnetcom.bomber.scenas;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.BomberGame;

public class Loading {
	public BomberGame context;

	private BitmapTextureAtlas mBarraTexture;
	private TextureRegion textureR;

	public Scene scenaCarga;
	Sprite barra;

	public Loading(BomberGame context) {
		this.context = context;
	}

	public void load(){
		onLoadResources();
		onLoadScene();
	}
	
	private void onLoadResources() {
		this.mBarraTexture = new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		this.textureR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBarraTexture, context, "gfx/loadingx2.jpg", 0, 0);
		mBarraTexture.load();
	}

	private void onLoadScene() {
		scenaCarga = new Scene();
		barra = new Sprite(0, 0, textureR, context.getVertexBufferObjectManager());
		barra.setPosition(context.CAMERA_WIDTH/2, context.CAMERA_HEIGHT/2);
		scenaCarga.attachChild(barra);
	}
	
	public void cargaMapa(final int numMapa){		
		context.getMiEngine().setScene(scenaCarga);
		context.getMiEngine().setCamaraNormal();
		barra.registerEntityModifier(new DelayModifier(0.5f){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				try{
					Scene scene4 = context.escenaJuego.onCreateScene(numMapa);
					context.getEngine().setScene(scene4);
				}catch(Exception e){
					System.out.println();
				}

			}
		});	
		
	}
	

	

}
