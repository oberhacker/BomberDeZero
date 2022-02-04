package xnetcom.bomber.scenas;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.entidades.Icono_bomba;
import xnetcom.bomber.util.Constantes;
import android.graphics.Color;

public class MenuMapas {

	BomberGame context;
	public Scene escena;

	public int centroX;
	public int centroY;
	ArrayList<Icono_bomba> bombas;
	private Font mFont;
	
	Rectangle cuadrado;
	
	public int paginaActual=0;
	public boolean moviendo;
	public MenuMapas(BomberGame context) {
		this.context = context;
		bombas = new ArrayList<Icono_bomba>();
		centroX = context.CAMERA_WIDTH / 2;
		centroY = context.CAMERA_HEIGHT / 2;
	}

	public void carga() {

		escena = new Scene();

		FontFactory.setAssetBasePath("font/");
		this.mFont = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "acegaffigan.ttf",
				Constantes.FONT_BOMBA_MAPA, true, Color.LTGRAY);
		mFont.load();

		BitmapTextureAtlas inicio_BTA = new BitmapTextureAtlas(context.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion fondo_inicio_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(inicio_BTA, context, "gfx/fondoBombas.jpg", 0, 0);
		inicio_BTA.load();

		BitmapTextureAtlas icono_BTA = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		icono_BTA.load();

		BitmapTextureAtlas iconoflecha_BTA = new BitmapTextureAtlas(context.getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		iconoflecha_BTA.load();
		TextureRegion iconoflecha_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(iconoflecha_BTA, context, "gfx/btn_flecha.png", 0, 0);

		TiledTextureRegion icono_bombas_TR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(icono_BTA, context, "gfx/bombas_mapas.png", 0, 0, 5, 1);

		Sprite fondo = new Sprite(context.CAMERA_WIDTH / 2, context.CAMERA_HEIGHT / 2, fondo_inicio_TR, context.getEngine().getVertexBufferObjectManager());

		escena.setBackground(new SpriteBackground(fondo));
		
		
		
		cuadrado= new Rectangle(0, 0, 10, 10,context.getVertexBufferObjectManager());
		cuadrado.setColor(100, 100, 100);
		cuadrado.setAlpha(0);
		cuadrado.setOffsetCenter(0, 0);
		
		escena.attachChild(cuadrado);
		

		int numberoBomba = 1;

		int offsetX = 400;
		int offsetY = 250;
		int filaEspacio = 180;
		int separacionBombas = 200;
		for (int pagina = 0; pagina < 3; pagina++) {
			// for de filas
			for (int fila = 0; fila < 3; fila++) {
				// for de columnass
				for (int elemento = 0; elemento < 5; elemento++, numberoBomba++) {
					Icono_bomba icono_bomba = new Icono_bomba(context, mFont, numberoBomba,(pagina*context.CAMERA_WIDTH)+ centroX + (elemento * separacionBombas) - offsetX, centroY + offsetY - fila * filaEspacio, icono_bombas_TR);
					bombas.add(icono_bomba);
					cuadrado.attachChild(icono_bomba);
				}
			}
		}
		
	
		Sprite flechaDerecha = new Sprite(centroX+100, 0,  iconoflecha_TR,context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				System.out.println("flecha d");
				if (pSceneTouchEvent.getAction()==1){
					System.out.println("flecha dd");
					if(!moviendo&& paginaActual!=2){
						cuadrado.registerEntityModifier(new MoveXModifier(0.5f, cuadrado.getX(), cuadrado.getX()-context.CAMERA_WIDTH){
							@Override
							protected void onModifierStarted(IEntity pItem) {
								paginaActual++;
								moviendo=true;
							}
							@Override
							protected void onModifierFinished(IEntity pItem) {
								moviendo=false;
							}
						});
					}
				}				
				return true;
			}
		};		
		
		
		flechaDerecha.setOffsetCenterY(0);
		flechaDerecha.setY(5);
		flechaDerecha.setFlippedHorizontal(true);
		
		
		Sprite flechaIzquierda = new Sprite(centroX - 100, 0, iconoflecha_TR, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				System.out.println("flecha d");
				if (pSceneTouchEvent.getAction() == 1) {
					if (!moviendo && paginaActual != 0) {
						cuadrado.registerEntityModifier(new MoveXModifier(0.5f, cuadrado.getX(), cuadrado.getX() + context.CAMERA_WIDTH) {

							@Override
							protected void onModifierStarted(IEntity pItem) {
								moviendo = true;
								paginaActual--;

							}

							@Override
							protected void onModifierFinished(IEntity pItem) {
								moviendo = false;
							}
						});
					}
				}
				return true;
			}
		};
		

		flechaIzquierda.setOffsetCenterY(0);
		flechaIzquierda.setY(5);
		
		escena.registerTouchArea(flechaDerecha);
		escena.registerTouchArea(flechaIzquierda);	
		
		escena.attachChild(flechaDerecha);
		escena.attachChild(flechaIzquierda);

	}
}
