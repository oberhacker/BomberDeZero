package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoGloboAzul;
import android.util.Log;

public class SpritePoolGloboAzul extends GenericPool<EnemigoGloboAzul> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolGloboAzul(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoGloboAzul onAllocatePoolItem() {
    	return  new EnemigoGloboAzul(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoGloboAzul pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoGloboAzul pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}