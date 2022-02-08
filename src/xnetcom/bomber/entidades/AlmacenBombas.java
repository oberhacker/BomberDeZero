package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.TransparentBitmapTextureAtlasSource;

public class AlmacenBombas {

	BomberGame context;
	public ArrayList<Bomba> almacen;
	public int secuencia = 0;
	private int nextBomba=0;
	public AtomicInteger bombasPlantadas;
	public BitmapTextureAtlas bombaBTA;
	public BuildableBitmapTextureAtlas fuegoBBTA;
	public TiledTextureRegion mFuegoCentroTR;
	public TiledTextureRegion mFuegoCentroDerechaTR;
	public TiledTextureRegion mFuegoCentroIzquierdaTR;
	public TiledTextureRegion mFuegoCentroArribaTR;
	public TiledTextureRegion mFuegoCentroAbajoTR;
	public TiledTextureRegion mFuegoHorizontalTR;
	public TiledTextureRegion mFuegoVerticalTR;

	public TiledTextureRegion mFuegoFinDerechaTR;
	public TiledTextureRegion mFuegoFinIzquierdaTR;
	public TiledTextureRegion mFuegoFinArribaTR;
	public TiledTextureRegion mFuegoFinAbajoTR;

	public TiledTextureRegion bombaTR;
	
	
	public AlmacenBombas(BomberGame context) {
		this.context = context;
		bombasPlantadas= new AtomicInteger(0);
	}

	public void carga() {
		
		
		
		this.bombaBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(bombaBTA, new TransparentBitmapTextureAtlasSource(512, 256), 0, 0);
		this.fuegoBBTA = new BuildableBitmapTextureAtlas(context.getTextureManager(), 2048, 256, TextureOptions.DEFAULT);
		this.mFuegoCentroTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_r_ani_74.png", 5, 1);
		this.mFuegoCentroAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_abajo_r_ani_74.png", 5, 1);
		this.mFuegoCentroArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_arriba_r_ani_74.png", 5, 1);
		this.mFuegoCentroDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_derecha_r_ani_74.png", 5, 1);
		this.mFuegoCentroIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_centro_izquierda_r_ani_74.png", 5, 1);
		this.mFuegoFinAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_abajo_r_ani_74.png", 5, 1);
		this.mFuegoFinArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_arriba_r_ani_74.png", 5, 1);
		this.mFuegoFinDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_derecha_r_ani_74.png", 5, 1);
		this.mFuegoFinIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_fin_izquierda_r_ani_74.png", 5, 1);
		this.mFuegoHorizontalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_horizontal_r_ani_74.png", 5, 1);
		this.mFuegoVerticalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "gfx/fuego_vertical_r_ani_74.png", 5, 1);
		this.bombaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bombaBTA, context, "gfx/bomba_ani90.png", 0, 0, 4, 2);

		try {
			fuegoBBTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(2, 2, 2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		context.getEngine().getTextureManager().loadTexture(fuegoBBTA);
		context.getEngine().getTextureManager().loadTexture(bombaBTA);
		
		
		
		almacen = new ArrayList<Bomba>();
		Bomba mBomba = null;
		for (int i = 0; i < Constantes.MAXIMOBOMBAS+5; i++) {
			Bomba bomba = new Bomba(context);
			bomba.creaBatch();
			mBomba = bomba;
			almacen.add(bomba);
		}
	}

	public void onSceneCreated() {
		for (Bomba bomba : almacen) {
			bomba.onSceneCreated();
		}
	}
	
	
	private Bomba circulaBomba(){
		if (nextBomba>=almacen.size()){
			nextBomba=0;
		}
		Bomba bomba = almacen.get(nextBomba);
		nextBomba++;
		return bomba;
		
	}
	
	
	public void reinicia(){
		for (Bomba bomba : almacen) {
			bomba.reinicia();
		}
		bombasPlantadas= new AtomicInteger(0);
		
	}
	
	
	public void detonarBomba(){
		if (context.gameManager.detonador){
			Bomba mBomba=null;
			for (Bomba bomba : almacen) {
				if (!bomba.isDetonada()){
					if (mBomba==null){
						mBomba=bomba;
					}
					if (bomba.secuencia<mBomba.secuencia){
						mBomba=bomba;
					}
				}
			}
			if (mBomba!=null)mBomba.detonar();
		}
	}
	
	public void plantaBomba(){
		if (bombasPlantadas.get()<context.gameManager.bombaNum){
			Bomba bomba = circulaBomba();
			secuencia++;
			bomba.plantarBomba(context.gameManager.bombaTam, secuencia, context.gameManager.detonador);
					
		}
		
	}
	

}
