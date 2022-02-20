package xnetcom.bomber.entidades;

import java.util.ArrayList;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.graficos.CapaParedes.TrozoPared;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.SpritePoolBooster;
import xnetcom.bomber.util.Util;

public class AlmacenMonedas {

	BomberGame context;
	public TiledTextureRegion monedasTR;
	
	SpritePoolBooster spritePool;
	ArrayList<Moneda> almacen;
	
	public enum TipoMoneda{
		MCORAZON,
		MDETONADOR,
		MFUERZA,
		MFANTASMA,
		MBOMBA,
		MVELOCIDAD,
		MEXPLOSION,
		MSORPRESA,
		MNULA,
	}
	
	
	public AlmacenMonedas(BomberGame context) {
		this.context = context;
	}
	
	
	public void carga(){		
		BitmapTextureAtlas monedasBTA = new BitmapTextureAtlas(context.getTextureManager(), 1024, 64, TextureOptions.BILINEAR);
		this.monedasTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(monedasBTA, context, "gfx/monedas.png", 0, 0, 7, 1);
		monedasBTA.load();	
		spritePool= new SpritePoolBooster(context);
		almacen= new ArrayList<Moneda>();
		posiciones= new ArrayList<Integer>();
	}

	public void creaMoneda(TipoMoneda tipo, Coordenadas coordenadas){		
		Moneda moneda = spritePool.obtainPoolItem();
		moneda.setTipoMoneda(tipo);
		moneda.ponerEnCoodenadas(coordenadas);		
		almacen.add(moneda);
	}	
	
	
	public ArrayList<Integer>posiciones; 
	public void barajeaMonedas(){	
		posiciones.clear();
		for (Moneda moneda : almacen) {
			boolean added=false;
			do{
				int seleccion=Util.tomaDecision(1, context.capaParedes.listaMuros.size()-1);
				boolean metido = estaMetido(seleccion);
				if (!metido){
					posiciones.add(Integer.valueOf(seleccion));
					Coordenadas coordenadas = context.capaParedes.listaMuros.get(seleccion).coodenadas;						
					moneda.ponerEnCoodenadas(coordenadas);
					added=true;
				}
			}while (!added);
		}		
	}


	private boolean estaMetido(int seleccion) {
		for (Integer pos : posiciones) {
			if (pos.equals(Integer.valueOf(seleccion))){
				return true;
			}
		}
		return false;
	}
	
	
	public void revelaMoneda( Coordenadas coodenadas, int secuencia){
		for (Moneda moneda : almacen) {		
			moneda.revelarMoneda(coodenadas,secuencia);			
		}
	}
	
	public void explotarMonedas(ArrayList<Coordenadas> coordenadas, int secuencia){		
		for (Moneda moneda : almacen) {		
			for (Coordenadas coordenada : coordenadas) {
				moneda.explotaMoneda(coordenada, secuencia);				
			}	
		}
	}
	
	public void reiniciar(){
		posiciones.clear();
		for (Moneda moneda : almacen) {
			spritePool.recyclePoolItem(moneda);
		}
		almacen.clear();		
	}


	public void cogerMoneda(Coordenadas coordenadas) {
		Moneda monedaCogida = null;
		for (Moneda moneda : almacen) {
			if (moneda.isVisible()&& moneda.coordenadas.getColumna()==coordenadas.getColumna()&& moneda.coordenadas.getFila()==coordenadas.getFila()){
				monedaCogida=moneda;
			}
		}
		if (monedaCogida!=null){
			almacen.remove(monedaCogida);
			spritePool.recyclePoolItem(monedaCogida);	
			context.gameManager.cogerMoneda(monedaCogida.tipoMonena);
			
		}
		
	}

}
