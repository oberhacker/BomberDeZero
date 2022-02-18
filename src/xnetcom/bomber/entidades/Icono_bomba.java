package xnetcom.bomber.entidades;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;

public class Icono_bomba extends AnimatedSprite{

	
	
	String nombre="Mapa";
	
	/*
	 * Estado 	0 en gris no disponible
	 * 			1 disponible cero estrellas
	 * 			2 disponible una estella
	 * 			3 disponble dos estrellas
	 * 			4 disponible tres esrellas
	 */
	
	
	private BomberGame context;
	private Font fuente;
	private Text numero;
	private int estado=0;
	private int fontSize=50;
	private int offsetX=0;
	private int offsetY=0;
	private int numMapa;
	
	public int getNumMapa() {
		return numMapa;
	}


	public void setNumMapa(int numMapa) {
		this.numMapa = numMapa;
	}

	private TiledTextureRegion mBombaTexturaTR;
	
	public Icono_bomba(BomberGame context,Font fuente,int numMapa,float pX, float pY,TiledTextureRegion pTiledTextureRegion) {	
		
		super(pX, pY, pTiledTextureRegion,context.getVertexBufferObjectManager());	
		setScale(0.9f);
		this.context=context;
		this.fuente=fuente;		
		numero = new Text((this.getWidthScaled()/2)+4,(this.getHeightScaled()/2)+10, fuente, ""+numMapa,context.getVertexBufferObjectManager());
		mBombaTexturaTR=pTiledTextureRegion;
		this.attachChild(numero);		
		this.numMapa=numMapa;
		nombre+=""+numMapa;

	}

		
	
	public void setEstrellas(int estado){
		this.estado=estado;
		switch (estado) {
		case -1:
			this.setCurrentTileIndex(4);
			numero.setVisible(false);
			break;
		case 0:
			this.setCurrentTileIndex(0);
			numero.setVisible(true);
			break;
		case 1:
			this.setCurrentTileIndex(1);
			numero.setVisible(true);
			break;
		case 2:
			this.setCurrentTileIndex(2);
			numero.setVisible(true);
			break;
		case 3:
			this.setCurrentTileIndex(3);
			numero.setVisible(true);
			break;
			
		}
		
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.getAction() == 0 ){
			tocado();
			return false;
		}
		return true;
	}
	
	
	public void tocado(){
		try {
			if (estado==-1){
				return;
			}
			Scene scene4 = context.escenaJuego.onCreateScene(numMapa);
			context.getEngine().setScene(scene4);
			if (estado!=-1){
				// cargar mapa
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
