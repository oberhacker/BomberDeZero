package xnetcom.bomber.scenas;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.BomberGame;
import android.os.AsyncTask;

public class Carga {

	public BomberGame context;

	private BitmapTextureAtlas mBarraTexture;
	private TextureRegion textureR;
	public Font mFont;
	private Text text;
	private Sprite barra;
	public Scene scenaCarga;

	public Carga(BomberGame context) {
		this.context = context;
		onLoadResources();
		onLoadScene();
	}

	private void onLoadResources() {
		this.mBarraTexture = new BitmapTextureAtlas(context.getTextureManager(), 2, 64, TextureOptions.DEFAULT);
		this.textureR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBarraTexture, context, "gfx/barra.png", 0, 0);
		this.mFont = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getAssets(), "font/DD.ttf", 60, true,android.graphics.Color.WHITE);
		mFont.load();
		mBarraTexture.load();
	}

	private void onLoadScene() {
		scenaCarga = new Scene();
		barra = new Sprite(0, 0, textureR, context.getVertexBufferObjectManager());
		barra.setPosition(0, 0);

		text = new Text(0, barra.getHeight() + 20, mFont, "Loading...", context.getVertexBufferObjectManager());
		text.setPosition(text.getWidth() / 2, text.getY());
		scenaCarga.attachChild(barra);
		scenaCarga.attachChild(text);
	}

	public void onLoadComplete() {
		context.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AsyncTask<Void, Void, Void>() {

					private Exception mException = null;

					@Override
					public void onPreExecute() {
						setPorcentaje(10);
//						try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}

					}

					@Override
					public Void doInBackground(final Void... params) {
						try {
							setPorcentaje(0);							
							context.loading.load();
							setPorcentaje(10);	
							context.escenaInicio.carga();
							setPorcentaje(30);
							context.soundManager.carga();
							setPorcentaje(35);
							context.menuMapas.carga();
							setPorcentaje(60);							
							context.capaParedes.carga();
							setPorcentaje(70);							
							context.escenaJuego.cargar();
							setPorcentaje(80);
							context.almacenEnemigos.carga();							
							setPorcentaje(90);
							context.tarjeta.carga();
							setPorcentaje(95);
							context.almacenMonedas.carga();							
							setPorcentaje(100);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

					@Override
					public void onPostExecute(final Void result) {						
						 context.getEngine().setScene(context.escenaInicio.escenaInicio);
					}
				}.execute((Void[]) null);

			}
		});

	}


	

	public void setPorcentaje(float porciento) {
		barra.setWidth((porciento / 100) * context.CAMERA_WIDTH * 2);
	}

}
