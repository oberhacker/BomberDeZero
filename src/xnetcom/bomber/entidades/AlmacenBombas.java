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
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.Matriz.Casilla;
import xnetcom.bomber.util.TransparentBitmapTextureAtlasSource;

public class AlmacenBombas {

	BomberGame context;
	public ArrayList<Bomba> almacen;
	public int secuencia = 0;
	private int nextBomba=0;
	public AtomicInteger bombasPlantadas;
	public BitmapTextureAtlas bombaBTA;

	public TiledTextureRegion bombaTR;
	public BitmapTextureAtlas fuegoBTA;
	public TiledTextureRegion fuegoTR;
	
	
	public AlmacenBombas(BomberGame context) {
		this.context = context;
		bombasPlantadas= new AtomicInteger(0);
	}

	public void carga() {
				
		
		this.bombaBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 256, TextureOptions.DEFAULT);
		this.bombaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bombaBTA, context, "gfx/bomba_ani90.png", 0, 0, 4, 2);

		this.fuegoBTA = new BitmapTextureAtlas(context.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		this.fuegoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBTA, context, "gfx/cruz.png", 0, 0, 7, 7);
		
		context.getEngine().getTextureManager().loadTexture(fuegoBTA);
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
	
	
	public void pausa(){
		for (Bomba bomba : almacen) {
			bomba.pausa();
		}		
	}
	public void play(){
		for (Bomba bomba : almacen) {
			bomba.play();
		}		
	}
	
	
	public void reinicia(){
		for (Bomba bomba : almacen) {
			bomba.reinicia();
		}
		Casilla[][] matrizMuros = context.escenaJuego.matriz.getMatrizmuros();
		for (Casilla[] casillas : matrizMuros) {
			for (Casilla casilla : casillas) {
				if (casilla.tipoCasilla==Matriz.BOMBA){
					casilla.tipoCasilla=Matriz.NADA;
				}
			}
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
