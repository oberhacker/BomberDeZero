package xnetcom.bomber.util;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;
import xnetcom.bomber.BomberGame;

public class SpritePoolParedes extends GenericPool<AnimatedSprite> {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================
    //private final VertexBufferObjectManager mVertexBufferObjectManager;
    private TiledTextureRegion mFaceTextureRegion;
    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolParedes(final TiledTextureRegion pFaceTextureRegion,BomberGame context) {
        mFaceTextureRegion = pFaceTextureRegion;  
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected AnimatedSprite onAllocatePoolItem() {
        final AnimatedSprite lSprite = new AnimatedSprite(0, 0, mFaceTextureRegion.deepCopy(),context.getVertexBufferObjectManager());
        lSprite.setScaleCenter(0, 0);
        lSprite.setOffsetCenter(0, 0);     
        Log.d("POOL", "INICIALIZADO");
        return lSprite;
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final AnimatedSprite pSprite) {
//    	context.runOnUpdateThread(new Runnable() {
//			public void run() {
//				pSprite.clearEntityModifiers();
//				pSprite.clearUpdateHandlers();
//				pSprite.setIgnoreUpdate(true);
//				pSprite.detachSelf();
//			}
//		});    	
    	pSprite.detachSelf();
    	Log.d("POOL", "onHandleRecycleItem");
    	
    }

    @Override
    protected void onHandleObtainItem(final AnimatedSprite pSprite) {
    	pSprite.reset();
    	Log.d("POOL", "onHandleObtainItem");
    }
}