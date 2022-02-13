package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoGotaAzul;
import xnetcom.bomber.enemigos.EnemigoGotaRoja;
import xnetcom.bomber.enemigos.EnemigoMoco;
import android.util.Log;

public class SpritePoolGotaRoja extends GenericPool<EnemigoGotaRoja> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolGotaRoja(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoGotaRoja onAllocatePoolItem() {
    	return  new EnemigoGotaRoja(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoGotaRoja pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoGotaRoja pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}