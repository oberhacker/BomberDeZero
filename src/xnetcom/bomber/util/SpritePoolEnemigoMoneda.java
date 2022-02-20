package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoMoco;
import xnetcom.bomber.enemigos.EnemigoMoneda;
import android.util.Log;

public class SpritePoolEnemigoMoneda extends GenericPool<EnemigoMoneda> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolEnemigoMoneda(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoMoneda onAllocatePoolItem() {
    	return  new EnemigoMoneda(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoMoneda pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoMoneda pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}