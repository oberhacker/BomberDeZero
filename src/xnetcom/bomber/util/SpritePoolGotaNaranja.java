package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoGotaAzul;
import xnetcom.bomber.enemigos.EnemigoGotaNaranja;
import xnetcom.bomber.enemigos.EnemigoMoco;
import android.util.Log;

public class SpritePoolGotaNaranja extends GenericPool<EnemigoGotaNaranja> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolGotaNaranja(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoGotaNaranja onAllocatePoolItem() {
    	return  new EnemigoGotaNaranja(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoGotaNaranja pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoGotaNaranja pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}