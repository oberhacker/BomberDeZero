package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.TransparentBitmapTextureAtlasSource;

public class Bomba {
	
	public BomberGame context;
	private BitmapTextureAtlas bombaBTA;
	private BuildableBitmapTextureAtlas fuegoBBTA;
	
	private TiledTextureRegion mFuegoCentroTR;
	private TiledTextureRegion mFuegoCentroDerechaTR;	
	private TiledTextureRegion mFuegoCentroIzquierdaTR;	
	private TiledTextureRegion mFuegoCentroArribaTR;	
	private TiledTextureRegion mFuegoCentroAbajoTR;	
	private TiledTextureRegion mFuegoHorizontalTR;
	private TiledTextureRegion mFuegoVerticalTR;
	
	private TiledTextureRegion mFuegoFinDerechaTR;
	private TiledTextureRegion mFuegoFinIzquierdaTR;
	private TiledTextureRegion mFuegoFinArribaTR;	
	private TiledTextureRegion mFuegoFinAbajoTR;
	
	private TiledTextureRegion bombaTR;
	
	
	
	public AnimatedSprite sprCentro;
	private AnimatedSprite sprCentroDerecha;
	private AnimatedSprite sprCentroIzquierda;
	private AnimatedSprite sprCentroArriba;
	private AnimatedSprite sprCentroAbajo;
	private AnimatedSprite sprArriba_1;
	private AnimatedSprite sprArriba_2;
	private AnimatedSprite sprAbajo_1;
	private AnimatedSprite sprAbajo_2;
	private AnimatedSprite sprDerecha_1;
	private AnimatedSprite sprDerecha_2;
	private AnimatedSprite sprIzquierda_1;
	private AnimatedSprite sprIzquierda_2;
	private AnimatedSprite sprFinArriba;
	private AnimatedSprite sprFinAbajo;
	private AnimatedSprite sprFinDerecha;
	private AnimatedSprite sprFinIzquierda;	
	
	private AnimatedSprite sprBomba;
	
	private SpriteGroup batch;
	
	
	private static final int TIEMPO = 100;
	private static final long[] ANIMATE_DURATION =new long[]{50, 50, 50, 50, 50};
	
	

	int fila;
	int columna;
	private int secuencia;
	private int tamExplosion;
	private boolean detonada; 
	
	
	public enum Posicion {
		CENTRO,
		CENTRO_ARRIBA,
		ARRIBA_1,
		ARRIBA_2,
		ARRIBA_3,
		CENTRO_ABAJO,
		ABAJO_1,
		ABAJO_2,
		ABAJO_3,
		CENTRO_DERECHA,
		DERECHA_1,
		DERECHA_2,
		DERECHA_3, 
		CENTRO_IZQUIERDA,
		IZQUIERDA_1,
		IZQUIERDA_2,
		IZQUIERDA_3,
		FIN_ARRIBA,
		FIN_ABAJO,
		FIN_DERECHA,
		FIN_IZQUIERDA;
	
	}
	
	
	public Bomba(BomberGame context){
		this.context=context;
	}

	
	
	public void cargaTexturas() {
		this.bombaBTA = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(bombaBTA, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		this.fuegoBBTA = new BuildableBitmapTextureAtlas(context.getTextureManager(),2048, 256, TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA);		
		this.mFuegoCentroTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_r_ani_90.png", 5, 1);
		this.mFuegoCentroAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_abajo_r_ani_90.png", 5, 1);
		this.mFuegoCentroArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_arriba_r_ani_90.png", 5, 1);
		this.mFuegoCentroDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_derecha_r_ani_90.png",5, 1);
		this.mFuegoCentroIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_izquierda_r_ani_90.png",5, 1);
		this.mFuegoFinAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_abajo_r_ani_90.png", 5, 1);
		this.mFuegoFinArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_arriba_r_ani_90.png", 5, 1);
		this.mFuegoFinDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_derecha_r_ani_90.png", 5, 1);
		this.mFuegoFinIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_izquierda_r_ani_90.png",5, 1);
		this.mFuegoHorizontalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_horizontal_r_ani_90.png", 5, 1);
		this.mFuegoVerticalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_vertical_r_ani_90.png", 5, 1);
		this.bombaTR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bombaBTA, context, "gfx/bomba_ani90.png",0,0,4, 2);
		
			
		try {
			fuegoBBTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,2));// posible error gles2
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.getEngine().getTextureManager().loadTexture(fuegoBBTA);
		context.getEngine().getTextureManager().loadTexture(bombaBTA);			
	}
	
	
	public void normaliza(AnimatedSprite sprite){
		sprite.setWidth(Constantes.TILE_WIDTH);
		sprite.setHeight(Constantes.TILE_HEIGHT);	
		sprite.setOffsetCenter(0, 0);
	}
	
	public void creaBatch(){		
		// Srpite maestro
		
		sprCentro = new AnimatedSprite(296, 256, mFuegoCentroTR,context.getVertexBufferObjectManager()){
			public void setAlpha(float pAlpha) {
				sprAbajo_1.setAlpha(pAlpha);
				sprAbajo_2.setAlpha(pAlpha);
				sprArriba_1.setAlpha(pAlpha);
				sprArriba_2.setAlpha(pAlpha);
				sprCentroAbajo.setAlpha(pAlpha);
				sprCentroArriba.setAlpha(pAlpha);
				sprCentroDerecha.setAlpha(pAlpha);
				sprCentroIzquierda.setAlpha(pAlpha);
				sprDerecha_1.setAlpha(pAlpha);
				sprDerecha_2.setAlpha(pAlpha);
				sprFinAbajo.setAlpha(pAlpha);
				sprFinArriba.setAlpha(pAlpha);
				sprFinDerecha.setAlpha(pAlpha);
				sprFinIzquierda.setAlpha(pAlpha);
				sprIzquierda_1.setAlpha(pAlpha);
				sprIzquierda_2.setAlpha(pAlpha);
				super.setAlpha(pAlpha);
			}
			
			

			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				sprAbajo_1.setCurrentTileIndex(pTileIndex);
				sprAbajo_2.setCurrentTileIndex(pTileIndex);
				sprArriba_1.setCurrentTileIndex(pTileIndex);
				sprArriba_2.setCurrentTileIndex(pTileIndex);
				sprCentroAbajo.setCurrentTileIndex(pTileIndex);
				sprCentroArriba.setCurrentTileIndex(pTileIndex);
				sprCentroDerecha.setCurrentTileIndex(pTileIndex);
				sprCentroIzquierda.setCurrentTileIndex(pTileIndex);
				sprDerecha_1.setCurrentTileIndex(pTileIndex);
				sprDerecha_2.setCurrentTileIndex(pTileIndex);
				sprFinAbajo.setCurrentTileIndex(pTileIndex);
				sprFinArriba.setCurrentTileIndex(pTileIndex);
				sprFinDerecha.setCurrentTileIndex(pTileIndex);
				sprFinIzquierda.setCurrentTileIndex(pTileIndex);
				sprIzquierda_1.setCurrentTileIndex(pTileIndex);
				sprIzquierda_2.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}			
					
		};
		
		normaliza(sprCentro);		
		
		sprCentroDerecha = new AnimatedSprite(sprCentro.getX()+sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroDerechaTR,context.getVertexBufferObjectManager());
		normaliza(sprCentroDerecha);
		
		sprCentroIzquierda = new AnimatedSprite(sprCentro.getX()-sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroIzquierdaTR,context.getVertexBufferObjectManager());
		normaliza(sprCentroIzquierda);
		sprCentroArriba = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+sprCentro.getHeight(), mFuegoCentroArribaTR,context.getVertexBufferObjectManager());
		normaliza(sprCentroArriba);
		sprCentroAbajo = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-sprCentro.getHeight(), mFuegoCentroAbajoTR,context.getVertexBufferObjectManager());
		normaliza(sprCentroAbajo);
		sprArriba_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*2), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		normaliza(sprArriba_1);
		sprArriba_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*3), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		normaliza(sprArriba_2);
		sprAbajo_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*2), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		normaliza(sprAbajo_1);
		sprAbajo_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*3), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		normaliza(sprAbajo_2);
		sprDerecha_1 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*2), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprDerecha_1);
		sprDerecha_2 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*3), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprDerecha_2);
		sprIzquierda_1 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*2), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprIzquierda_1);
		sprIzquierda_2 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*3), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprIzquierda_2);
		
		sprFinArriba = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*4), mFuegoFinArribaTR,context.getVertexBufferObjectManager());
		normaliza(sprFinArriba);
		sprFinAbajo = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*4), mFuegoFinAbajoTR,context.getVertexBufferObjectManager());
		normaliza(sprFinAbajo);
		
		sprFinDerecha = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*4), sprCentro.getY(), mFuegoFinDerechaTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprFinDerecha);
		sprFinIzquierda = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*4), sprCentro.getY(), mFuegoFinIzquierdaTR,context.getVertexBufferObjectManager());//gles2
		normaliza(sprFinIzquierda);
		
		
		sprBomba= new AnimatedSprite(0, 0, bombaTR,context.getVertexBufferObjectManager());
		sprBomba.setZIndex(Constantes.ZINDEX_BOMBA);
		

		 batch = new SpriteGroup(fuegoBBTA, 21,context.getVertexBufferObjectManager());
		 
		 batch.attachChild(sprAbajo_1);
		 batch.attachChild(sprAbajo_2);
	
		 
		 batch.attachChild(sprArriba_1);
		 batch.attachChild(sprArriba_2);
	
		 
		 batch.attachChild(sprDerecha_1);
		 batch.attachChild(sprDerecha_2);

		 
		 batch.attachChild(sprIzquierda_1);
		 batch.attachChild(sprIzquierda_2);

		 
		 batch.attachChild(sprCentro);
		 
		 batch.attachChild(sprCentroAbajo);
		 batch.attachChild(sprCentroArriba);
		 batch.attachChild(sprCentroDerecha);
		 batch.attachChild(sprCentroIzquierda);
		 
		 batch.attachChild(sprFinAbajo);
		 batch.attachChild(sprFinArriba);
		 batch.attachChild(sprFinDerecha);
		 batch.attachChild(sprFinIzquierda);		
		
//		batch.setVisible(false);
//		sprBomba.setVisible(false);
		batch.setZIndex(Constantes.ZINDEX_FUEGO);
//		batch.setIgnoreUpdate(true);
		
		sprCentro.animate(200, false);
		context.escenaJuego.scene.attachChild(batch);	
		context.escenaJuego.scene.sortChildren();	

	
		
	}
	
	
	public void reiniciaBatch() {		
		sprCentro.setVisible(false);			
		sprAbajo_1.setVisible(false);
		sprAbajo_2.setVisible(false);	
		sprArriba_1.setVisible(false);	
		sprArriba_2.setVisible(false);	
		sprCentroAbajo.setVisible(false);	
		sprCentroArriba.setVisible(false);	
		sprCentroDerecha.setVisible(false);	
		sprCentroIzquierda.setVisible(false);	
		sprDerecha_1.setVisible(false);	
		sprDerecha_2.setVisible(false);	
		sprFinAbajo.setVisible(false);	
		sprFinArriba.setVisible(false);	
		sprFinDerecha.setVisible(false);	
		sprFinIzquierda.setVisible(false);	
		sprIzquierda_1.setVisible(false);	
		sprIzquierda_2.setVisible(false);		
	}
	
	
	
	
	
	public boolean plantarBomba(int tamExplosion, int secuencia){
		 int posX=context.bomberman.getColumna();
		 int posY=context.bomberman.getFila();
		 
		 // si no se puede plantar no hacemos nada
		 if (!sePuedePlantarBomba(posX, posY)){
			 return false;
		 }		 
		 context.escenaJuego.matriz.setValor(posX, posY, Matriz.BOMBA);		
		 fila=posX;
		 columna=posY;
		 this.secuencia=secuencia;
		 context.soundManager.plantaBomba();
		 this.tamExplosion=tamExplosion;
//		 this.detonada=false;context.get
//		 batch.setPosition(context.getGameManager().getCurrentTileRectangle().getX(),context.getGameManager().getCurrentTileRectangle().getY());
		return true;
		
	}
	
	public boolean sePuedePlantarBomba(int fila, int columna){
		return(context.escenaJuego.matriz.getValor(fila, columna)==Matriz.NADA);	
	}
	
	
	
	
}
