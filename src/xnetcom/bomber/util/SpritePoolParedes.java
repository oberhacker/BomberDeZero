package xnetcom.bomber.util;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;
import xnetcom.bomber.BomberGame;

public class SpritePoolParedes extends GenericPool<TiledSprite> {
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
    protected TiledSprite onAllocatePoolItem() {
        final TiledSprite lSprite = new TiledSprite(0, 0, mFaceTextureRegion.deepCopy(),context.getVertexBufferObjectManager());
        lSprite.setScaleCenter(0, 0);
        lSprite.setOffsetCenter(0, 0);     
        Log.d("POOL", "INICIALIZADO");
        return lSprite;
    }

    @Override
    protected void onHandleRecycleItem(final TiledSprite pSprite) {
    	pSprite.detachSelf();
    	Log.d("POOL", "onHandleRecycleItem");
    	
    }

    @Override
    protected void onHandleObtainItem(final TiledSprite pSprite) {
    	pSprite.reset();
    	Log.d("POOL", "onHandleObtainItem");
    }
}