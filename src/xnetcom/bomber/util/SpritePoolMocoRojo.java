package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoMoco;
import xnetcom.bomber.enemigos.EnemigoMocoRojo;
import android.util.Log;

public class SpritePoolMocoRojo extends GenericPool<EnemigoMocoRojo> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolMocoRojo(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected EnemigoMocoRojo onAllocatePoolItem() {
    	return  new EnemigoMocoRojo(context);
    }

    
    
    
    @Override
    protected void onHandleRecycleItem(final EnemigoMocoRojo pSprite) {
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final EnemigoMocoRojo pSprite) {
  
    	Log.d("POOL", "onHandleObtainItem");
    }
}