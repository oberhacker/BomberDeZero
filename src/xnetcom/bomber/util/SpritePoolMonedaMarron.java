package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoMoco;
import xnetcom.bomber.enemigos.EnemigoMoneda;
import xnetcom.bomber.enemigos.EnemigoMonedaMarron;
import android.util.Log;

public class SpritePoolMonedaMarron extends GenericPool<EnemigoMonedaMarron> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolMonedaMarron(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoMonedaMarron onAllocatePoolItem() {
    	return  new EnemigoMonedaMarron(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoMonedaMarron pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoMonedaMarron pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}