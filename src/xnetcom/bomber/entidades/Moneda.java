package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.entidades.AlmacenMonedas.TipoMoneda;
import xnetcom.bomber.util.Coordenadas;

public class Moneda extends TiledSprite{
	
	TipoMoneda tipoMonena;
	BomberGame context;
	public Coordenadas coordenadas;
	
	public Moneda(BomberGame context, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager ) {
		super(-500, -500, pTiledTextureRegion, pVertexBufferObjectManager);
		setOffsetCenter(0, 0);
		this.context=context;		
		setVisible(false);				
	}
	
	int secuencia=-1;
	// la secuencia es el mnumero de la bomba para que no la explote
	public boolean revelarMoneda(Coordenadas coordenadas, int secuencia){
		if (!isVisible()&& this.coordenadas.getColumna()==coordenadas.getColumna() && this.coordenadas.getFila()==coordenadas.getFila()){
			setVisible(true);	
			this.secuencia=secuencia;
			return true;
		}else{
			return false;
		}		
	}
	
	public boolean explotaMoneda(Coordenadas coordenadas, int secuencia){
		if (this.secuencia!=secuencia && this.coordenadas.getColumna()==coordenadas.getColumna() && this.coordenadas.getFila()==coordenadas.getFila()){
			setVisible(false);
			return true;
		}else{
			return false;
		}
		
	}
	
	public void ponerEnCoodenadas(Coordenadas coordenadas){
		this.coordenadas=coordenadas;
		setVisible(false);		
		setPosition(coordenadas.getX()-3, coordenadas.getYCorregido());
	}	
	
	public void setTipoMoneda(TipoMoneda tipoMonena){		
		this.tipoMonena=tipoMonena;
		switch (tipoMonena) {
		case MBOMBA:
			setCurrentTileIndex(0);
			break;
		case MDETONADOR:
			setCurrentTileIndex(3);
			break;
		case MCORAZON:
			setCurrentTileIndex(1);
			break;
		case MEXPLOSION:
			setCurrentTileIndex(6);
			break;
		case MFANTASMA:
			setCurrentTileIndex(4);
			break;
		case MFUERZA:
			setCurrentTileIndex(5);
			break;
		case MVELOCIDAD:
			setCurrentTileIndex(2);
			break;
		case MSORPRESA:
			
			break;
			
		default:
			break;
		}
	}
	


	
	

}
