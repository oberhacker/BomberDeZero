package xnetcom.bomber;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

class SpritePool extends GenericPool<TiledSprite> {
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
    public SpritePool(final TiledTextureRegion pFaceTextureRegion,BomberGame context) {
        mFaceTextureRegion = pFaceTextureRegion;  
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected TiledSprite onAllocatePoolItem() {
        final TiledSprite lSprite = new TiledSprite(0, 0, mFaceTextureRegion.deepCopy(),context.getVertexBufferObjectManager());
        lSprite.setIgnoreUpdate(true);
        lSprite.setScaleCenter(0, 0);
        return lSprite;
    }

    @Override
    protected void onHandleRecycleItem(final TiledSprite pSprite) {
    	pSprite.setVisible(false);
    	pSprite.setIgnoreUpdate(true);
    	
    }

    @Override
    protected void onHandleObtainItem(final TiledSprite pSprite) {
    }
}