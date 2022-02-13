package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoGotaAzul;
import xnetcom.bomber.enemigos.EnemigoMoco;
import android.util.Log;

public class SpritePoolGotaAzul extends GenericPool<EnemigoGotaAzul> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolGotaAzul(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoGotaAzul onAllocatePoolItem() {
    	return  new EnemigoGotaAzul(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoGotaAzul pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoGotaAzul pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}