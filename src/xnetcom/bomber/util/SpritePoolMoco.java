package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoMoco;
import android.util.Log;

public class SpritePoolMoco extends GenericPool<EnemigoMoco> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolMoco(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoMoco onAllocatePoolItem() {
    	return  new EnemigoMoco(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoMoco pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoMoco pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}