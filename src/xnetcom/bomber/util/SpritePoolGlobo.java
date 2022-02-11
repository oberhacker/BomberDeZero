package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoGlobo;
import android.util.Log;

public class SpritePoolGlobo extends GenericPool<EnemigoGlobo> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolGlobo(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoGlobo onAllocatePoolItem() {
    	return  new EnemigoGlobo(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoGlobo pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoGlobo pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}