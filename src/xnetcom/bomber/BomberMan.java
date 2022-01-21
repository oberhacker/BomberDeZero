package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

public class BomberMan {
	
	private static long[] ANIMATE_DURATION = new long[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
	
	public BomberGame context;	
	public ITexture mPlayerTexture;
	public TiledTextureRegion mPlayerTextureRegion;	
	public Rectangle currentTileRectangle;	
//	public AnimatedSprite player;
	public AnimatedSprite bomberArriba;
	public AnimatedSprite bomberAbajo;
	
	private TiledTextureRegion mBombermanTextureRegionAniA;
	private TiledTextureRegion mBombermanTextureRegionAniB;
	private PhysicsHandler physicsHandler;
	
	public static int PIES_X=53;
	public static int PIES_Y=29;
	
	public static float VELOCIDAD_RECTO_X=150;
	public static float  VELOCIDAD_RECTO_Y=150;
	
	public float FACTOR_ACHATADO=0.9f;
	 
	public BomberMan(BomberGame context){
		this.context=context;
	}
	
	public AnimatedSprite getSprite(){
		return bomberAbajo;
	}
	
	public void carga() throws IOException{
		this.mPlayerTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/player.png", TextureOptions.DEFAULT);
		this.mPlayerTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mPlayerTexture, 3, 4);
		this.mPlayerTexture.load();		
//		this.player = new AnimatedSprite(20, context.CAMERA_HEIGHT-100, this.mPlayerTextureRegion, context.getVertexBufferObjectManager());
//		this.player.setOffsetCenter(0, 0);
		
		BitmapTextureAtlas tiledmaster90A = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBombermanTextureRegionAniA=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90A, context, "gfx/tiledmaster(125x104)ArribaB.png",0,0, 12, 5); 
		tiledmaster90A.load();
		
		BitmapTextureAtlas tiledmaster90B = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		this.mBombermanTextureRegionAniB=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90B, context, "gfx/tiledmaster(125x104)abajoS6.png",0,0, 12, 5);
		tiledmaster90B.load();
		
		
		bomberArriba=new AnimatedSprite(0,0 , mBombermanTextureRegionAniA,context.getVertexBufferObjectManager());
		bomberArriba.setOffsetCenter(0, 0);		
		bomberArriba.setScaleCenter(0, 0);		
		bomberArriba.setScaleY(FACTOR_ACHATADO);
		bomberAbajo=new AnimatedSprite(20, context.CAMERA_HEIGHT-100, mBombermanTextureRegionAniB,context.getVertexBufferObjectManager()){
			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				bomberArriba.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}
			@Override
			protected void onManagedDraw(GLState GLState, Camera pCamera) {
				// TODO Auto-generated method stub
//				bomberArriba.setPosition(cuadrado.getX()-28,cuadrado.getY()-55);
				super.onManagedDraw(GLState, pCamera);				
				//bomberB.dibuja(pGL, pCamera);				
			}
		};
		bomberAbajo.setOffsetCenter(0, 0);		
		bomberAbajo.setScaleCenter(0, 0);	
		bomberAbajo.setScaleY(FACTOR_ACHATADO);
		physicsHandler = new PhysicsHandler(bomberAbajo);	
		bomberAbajo.registerUpdateHandler(physicsHandler);
		
		bomberAbajo.attachChild(bomberArriba);
		
	}
	
	
	public void onCreateScene(Scene scene){
		
		currentTileRectangle = new Rectangle(0, 0, context.escenaJuego.mTMXTiledMap.getTileWidth(), context.escenaJuego.mTMXTiledMap.getTileHeight(), context.getVertexBufferObjectManager());
		/* Set the OffsetCenter to 0/0, so that it aligns with the TMXTiles. */
		currentTileRectangle.setOffsetCenter(0, 0);
		currentTileRectangle.setColor(50, 0, 0);
		currentTileRectangle.setScaleCenter(0, 0);
		currentTileRectangle.setScaleX(context.factorForma);
		currentTileRectangle.setPosition(context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileX(2), context.escenaJuego.mTMXTiledMap.getTMXLayers().get(0).getTileY(2));		
		currentTileRectangle.setPosition(2*64*context.factorForma, currentTileRectangle.getY());
		scene.attachChild(currentTileRectangle);
//		scene.attachChild(player);	
		
		
	

		
//		bomberAbajo.animate(ANIMATE_DURATION,24,35,true);
		scene.attachChild(bomberAbajo);
//		scene.attachChild(bomberArriba);
		
	}
	

	public void moverArriba(){
		animarArriba();
		physicsHandler.setVelocity(0,VELOCIDAD_RECTO_Y);
	}
	public void moverAbajo(){
		animarAbajo();
		physicsHandler.setVelocity(0, -VELOCIDAD_RECTO_Y);
	}
	public void moverDerecha(){
		animarDerecha();
		physicsHandler.setVelocity(VELOCIDAD_RECTO_X, 0);
	}
	public void moverIzquierda(){
		animarIzquierda();
		physicsHandler.setVelocity(-VELOCIDAD_RECTO_X, 0);
	}
	
	public void parar(){
		stopAnimation();
		physicsHandler.setVelocity(0, 0);
	}
	
	public void moverRecto(){		
		if (physicsHandler==null){
			physicsHandler = new PhysicsHandler(bomberAbajo);	
			bomberAbajo.registerUpdateHandler(physicsHandler);
		}		
		
	}
	
	
	public void animarIzquierda(){
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION,36,47,true);
	}
	public void animarDerecha(){
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION,24,35,true);
	}
	public void animarArriba(){
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION,12,23,true);
	}
	public void animarAbajo(){
		stopAnimation();
		bomberAbajo.animate(ANIMATE_DURATION,0,11,true);
	}	
	
	public void stopAnimation(){
		bomberAbajo.stopAnimation();
	}
	
}
