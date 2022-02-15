package xnetcom.bomber.graficos;

import java.util.ArrayList;
import java.util.Iterator;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Constantes;
import xnetcom.bomber.util.Coordenadas;
import xnetcom.bomber.util.Matriz;
import xnetcom.bomber.util.Matriz.Casilla;
import xnetcom.bomber.util.MiSpriteGroup;
import xnetcom.bomber.util.SpritePoolParedes;

public class CapaParedes {
	
	public BomberGame context;
	public SpritePoolParedes spritePoolArriba;
	public MiSpriteGroup spriteGroupArriba;
	
	public SpritePoolParedes spritePoolAbajo;
	public MiSpriteGroup spriteGroupAbajo;
	
	public ArrayList<TrozoPared> listaMuros;
	public ArrayList<TrozoPared> listaMurosMentira;
	
	private Iterator<AnimatedSprite> itr;
	private ArrayList<AnimatedSprite> almacenExplosiones;
	public MiSpriteGroup grupoExplosiones;
	
	
	public CapaParedes(final BomberGame context){
		this.context=context;
		this.listaMuros=new ArrayList<TrozoPared>();
		this.listaMurosMentira=new ArrayList<TrozoPared>();
		
	}

	
	public void reiniciaPared(){
		for (TrozoPared trozo : listaMuros) {
			spritePoolArriba.recyclePoolItem(trozo.trozoArriba);
			spritePoolAbajo.recyclePoolItem(trozo.trozoAbajo);
		}
		listaMuros.clear();		
	}
	
	
	public void pintaExplosion(Coordenadas coodenadas){
		if (!itr.hasNext()){
			itr= almacenExplosiones.iterator();
		}
		AnimatedSprite spr = itr.next();
		spr.setPosition(coodenadas.getX()-80,coodenadas.getY()+50);
		spr.setVisible(true);
		spr.setZIndex(Constantes.ZINDEX_MUROFRAGMENTOS);
		
		spr.animate(30, false, new ListenerExplotar(spr));		
		if (!spr.hasParent()) {
//			context.escenaJuego.scene.attachChild(spr);
			grupoExplosiones.attachChild(spr);
		}
	}
	
	
	public void carga() {

		BitmapTextureAtlas explosionBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 512, TextureOptions.NEAREST_PREMULTIPLYALPHA);
		explosionBTA.load();
		
		
		almacenExplosiones = new ArrayList<AnimatedSprite>();
		grupoExplosiones= new MiSpriteGroup(explosionBTA, 30, context.getVertexBufferObjectManager());
		grupoExplosiones.setOffsetCenter(0, 0);
		grupoExplosiones.setPosition(0, 0);
		grupoExplosiones.setZIndex(Constantes.ZINDEX_MUROFRAGMENTOS);
		
		for (int i = 0; i < 20; i++) {
			AnimatedSprite ans =new AnimatedSprite(0, 0, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(explosionBTA, context, "gfx/pared_explo.png", 0, 0, 2, 2),context.getVertexBufferObjectManager());
			ans.setScaleCenter(0, 0);
			ans.setOffsetCenter(0, 0);
			almacenExplosiones.add(ans);
			
		}	
		itr= almacenExplosiones.iterator();

		BitmapTextureAtlas btaParedAbajo = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas btaParedArriba = new BitmapTextureAtlas(context.getTextureManager(), 1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TiledTextureRegion textureArriba = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedArriba, context, "gfx/muroswais74_bigV8_arriba.png", 0, 0, 4, 1);
		TiledTextureRegion textureAbajo = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaParedAbajo, context, "gfx/muroswais74_bigV8_abajo.png", 0, 0, 4, 1);
		btaParedAbajo.load();
		btaParedArriba.load();

		spritePoolArriba = new SpritePoolParedes(textureArriba, context);
		spriteGroupArriba = new MiSpriteGroup(btaParedArriba, 220, context.getVertexBufferObjectManager());
		spriteGroupArriba.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ARRIBA);
		spriteGroupArriba.setScaleCenter(0, 0);
		// no funciona el offset center asi se centra
		spriteGroupArriba.setPosition(-1 * Constantes.TILE_WIDTH, 1 * Constantes.TILE_HEIGHT);

		spritePoolAbajo = new SpritePoolParedes(textureAbajo, context);
		spriteGroupAbajo = new MiSpriteGroup(btaParedAbajo, 220, context.getVertexBufferObjectManager());
		spriteGroupAbajo.setZIndex(Constantes.ZINDEX_CAPA_PAREDES_ABAJO);
		spriteGroupAbajo.setScaleCenter(0, 0);
		// no funciona el offset center asi se centra
		spriteGroupAbajo.setPosition(-1 * Constantes.TILE_WIDTH, 1 * Constantes.TILE_HEIGHT);

	}
	

	public void restauraInicial(){
		context.escenaJuego.matriz.eliminaParedesMatriz();
		reiniciaPared();
		Casilla[][] matriz = context.escenaJuego.matriz.getMatrizmuros();			
		for (int y = 2; y < matriz.length; y++) {
			for (int x = 2; x < matriz.length; x++) {			
				if(matriz[y][x].tipoCasilla!=Matriz.PARED && matriz[y][x].paredInicial){
					matriz[y][x].tipoCasilla=Matriz.PARED;
					ponParedInicial(x,y, true);
				}
			}
		}
		recalculaPared();		
	}
	
	
	int paredesDeMentidaMAX=23;
	int totalParecesMentira=0;
	public void ponParedInicial(int columna,int fila, boolean paredVerdadera){
		if (!paredVerdadera && totalParecesMentira>=paredesDeMentidaMAX){
			Log.w("PAREDES", "SUFICIENTES");
			return;
		}
		
		AnimatedSprite spriteArriba = spritePoolArriba.obtainPoolItem();		
		AnimatedSprite spriteAbajo = spritePoolAbajo.obtainPoolItem();
		Coordenadas coodenadas= new Coordenadas(columna, fila);
		
		spriteArriba.setPosition(coodenadas.getX(), coodenadas.getY()+3);	
		if (spriteArriba.hasParent()){
			System.out.println("tiene parent");
		}else{
			spriteGroupArriba.attachChild(spriteArriba);	
		}
		
		
		spriteAbajo.setPosition(coodenadas.getX(), coodenadas.getY()+3);		
		if (spriteAbajo.hasParent()){
			System.out.println("tiene parent");
		}else{
			spriteGroupAbajo.attachChild(spriteAbajo);	
		}
		
		spriteArriba.setVisible(true);
		spriteGroupAbajo.setVisible(true);
		TrozoPared trozo= new TrozoPared(context,spriteArriba, spriteAbajo,coodenadas,paredVerdadera);
		
		if (paredVerdadera){
			listaMuros.add(trozo);
			context.escenaJuego.matriz.setParedInicial(fila, columna, trozo);
		}else{
			listaMurosMentira.add(trozo);
			totalParecesMentira++;
		}
		
		
	}
	
	
	
	public void onSceneCreated(){
		context.escenaJuego.scene.attachChild(spriteGroupAbajo);
		context.escenaJuego.scene.attachChild(spriteGroupArriba);
		context.escenaJuego.scene.attachChild(grupoExplosiones);
	}
	
	
	public static int SOLO=0;
	public static int IZQUIERDA=1;
	public static int CENTRO=2;
	public static int DERECHA=3;
	
	public void recalculaPared(){
		Casilla[][] matriz = context.escenaJuego.matriz.getMatrizmuros();		
		for (int y = 2; y < matriz.length; y++) {
			for (int x = 2; x < matriz.length; x++) {
				Casilla casilla = matriz[y][x];
				int trozo = matriz[y][x].tipoCasilla;
				if (trozo==Matriz.PARED){
					// miramos si hay toro a la derecha
					 if (matriz[y][x+1].tipoCasilla==Matriz.PARED && matriz[y][x-1].tipoCasilla==Matriz.PARED){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(CENTRO);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(CENTRO);
					}else if (matriz[y][x+1].tipoCasilla==Matriz.PARED ){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(IZQUIERDA);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(IZQUIERDA);
					}else if (matriz[y][x-1].tipoCasilla==Matriz.PARED ){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(DERECHA);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(DERECHA);
					}else if (matriz[y][x+1].tipoCasilla!=Matriz.PARED){
						casilla.trozoPared.trozoAbajo.setCurrentTileIndex(SOLO);
						casilla.trozoPared.trozoArriba.setCurrentTileIndex(SOLO);
					}
				}

			}				
		}		
	}
	
	
	public void recalculaTrozosLaterales(Coordenadas coordenada){
		Coordenadas coordenadaDerecha= new Coordenadas(coordenada.getColumna()+1, coordenada.getFila());
		Coordenadas coordenadaIzquierda= new Coordenadas(coordenada.getColumna()-1, coordenada.getFila());		
		recalculaTrozo(coordenadaDerecha);
		recalculaTrozo(coordenadaIzquierda);
	}
	
	public void recalculaTrozo(Coordenadas coordenada){
		Casilla[][] matriz = context.escenaJuego.matriz.getMatrizmuros();
		Casilla casilla = context.escenaJuego.matriz.getValor(coordenada.getFila(), coordenada.getColumna());	
		if (casilla.tipoCasilla==Matriz.PARED){
			// miramos si hay toro a la derecha
			 if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla==Matriz.PARED && matriz[coordenada.getFila()][coordenada.getColumna()-1].tipoCasilla==Matriz.PARED){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(CENTRO);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(CENTRO);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla==Matriz.PARED ){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(IZQUIERDA);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(IZQUIERDA);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()-1].tipoCasilla==Matriz.PARED ){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(DERECHA);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(DERECHA);
			}else if (matriz[coordenada.getFila()][coordenada.getColumna()+1].tipoCasilla!=Matriz.PARED){
				casilla.trozoPared.trozoAbajo.setCurrentTileIndex(SOLO);
				casilla.trozoPared.trozoArriba.setCurrentTileIndex(SOLO);
			}
		}

		
	}
	
	
	public class TrozoPared{		
		
		public AnimatedSprite trozoArriba;
		public AnimatedSprite trozoAbajo;
		public Coordenadas coodenadas;
		public BomberGame context;
		public boolean verdadera;
		
		public void setVidible(boolean visible){
			trozoArriba.setVisible(visible);
			trozoAbajo.setVisible(visible);
		}
		
		public TrozoPared(BomberGame context, AnimatedSprite trozoArriba, AnimatedSprite trozoAbajo,Coordenadas coodenadas, boolean verdadera){
			this.trozoArriba=trozoArriba;
			this.trozoAbajo=trozoAbajo;
			this.coodenadas=coodenadas;
			this.context=context;
			this.verdadera=verdadera;
		}
		
		public void explota(){
			context.escenaJuego.matriz.setValor(Matriz.NADA, coodenadas.getFila(), coodenadas.getColumna(), null, null);
			
//			trozoArriba.detachSelf();
//			trozoAbajo.detachSelf();
			
			spritePoolAbajo.recyclePoolItem(trozoAbajo);
			spritePoolArriba.recyclePoolItem(trozoArriba);
			
			trozoAbajo.setVisible(false);
			trozoArriba.setVisible(false);
			
			pintaExplosion(coodenadas);
			context.capaParedes.recalculaTrozosLaterales(coodenadas);
		}
	}

	
	public class ListenerExplotar implements IAnimationListener{

		private AnimatedSprite sprt;		

		public ListenerExplotar(AnimatedSprite sprt){
			this.sprt=sprt;
		}

		@Override
		public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
				int pInitialLoopCount) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
				int pOldFrameIndex, int pNewFrameIndex) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
				int pRemainingLoopCount, int pInitialLoopCount) {
	
			
		}
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			context.runOnUpdateThread(new Runnable() {
				public void run() {
					sprt.detachSelf();
				}});
//			this.sprt.setVisible(false);
		}
		
	}
	
	
	
	

}
