package xnetcom.bomber.util;

import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.enemigos.EnemigoFantasma;
import xnetcom.bomber.enemigos.EnemigoMoco;
import xnetcom.bomber.enemigos.EnemigoMoneda;
import xnetcom.bomber.entidades.Moneda;
import android.util.Log;

public class SpritePoolBooster extends GenericPool<Moneda> {

    BomberGame context;

    // ===========================================================
    // Constructors
    // ===========================================================
    public SpritePoolBooster(BomberGame context) {
        this.context=context;
    }


    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    protected Moneda onAllocatePoolItem() {
    	Moneda moneda = new Moneda(context,context.almacenMonedas.monedasTR,context.getVertexBufferObjectManager());
    	moneda.setZIndex(Constantes.ZINDEX_CAPA_SUELO+2);
//    	moneda.setZIndex(1000);
    	context.escenaJuego.scene.attachChild(moneda);    	
    	return moneda;
    }
    
    
    @Override
    protected void onHandleRecycleItem(final Moneda pSprite) {
    	pSprite.setVisible(false);
    	Log.d("POOL", "onHandleRecycleItem");    	
    }

    @Override
    protected void onHandleObtainItem(final Moneda pSprite) {
    	pSprite.setVisible(false);
    }
}