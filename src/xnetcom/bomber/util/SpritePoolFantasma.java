package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoGlobo;
import android.util.Log;

public class SpritePoolFantasma extends GenericPool<EnemigoFantasma> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolFantasma(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoFantasma onAllocatePoolItem() {
    	return  new EnemigoFantasma(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoFantasma pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoFantasma pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}