package xnetcom.bomber;

import java.io.IOException;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

public class Hud {
	
	public BomberGame context;
	public BomberMan bomberman;
	
	public Hud(BomberGame context){
		this.context=context;
	}

	public AssetBitmapTexture mOnScreenControlBaseTexture;
	public TextureRegion mOnScreenControlBaseTextureRegion;
	public AssetBitmapTexture mOnScreenControlKnobTexture;
	public TextureRegion mOnScreenControlKnobTextureRegion;
	
	public DigitalOnScreenControl mDigitalOnScreenControl;
	PhysicsHandler physicsHandler;
	
	public void addBomberman(BomberMan bomberman){
		AnimatedSprite player = bomberman.getSprite();
		physicsHandler = new PhysicsHandler(player);
		player.registerUpdateHandler(physicsHandler);
	}
	
	
	private void screenControl(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY){
		if (context.escenaJuego.bomberman!=null){			
			if (pValueX>0){
				//derecha
				context.escenaJuego.bomberman.moverDerecha();
			}else if (pValueX<0){
				//izquierda
				context.escenaJuego.bomberman.moverIzquierda();
			}else if (pValueY>0){
				//arriba
				context.escenaJuego.bomberman.moverArriba();
			}else if (pValueY<0){
				//abajo
				context.escenaJuego.bomberman.moverAbajo();
			}else{
				//parado
				context.escenaJuego.bomberman.parar();
			}	
		}		
	}
	
	public void carga() throws IOException{
		
		this.mOnScreenControlBaseTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlBaseTexture);
		this.mOnScreenControlBaseTexture.load();

		this.mOnScreenControlKnobTexture = new AssetBitmapTexture(context.getTextureManager(), context.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlKnobTexture);
		this.mOnScreenControlKnobTexture.load();
		
				
		this.mDigitalOnScreenControl = new DigitalOnScreenControl(0, 0, context.camaraJuego, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, context.getVertexBufferObjectManager(), new IOnScreenControlListener() {
			
			float pValueXAnterior;
			float pValueYAnterior;
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if (pValueXAnterior!=pValueX || pValueY!=pValueYAnterior){
					screenControl(pBaseOnScreenControl, pValueX, pValueY);
					pValueXAnterior=pValueX;
					pValueYAnterior=pValueY;
				}
				
			}
		});
		final Sprite controlBase = this.mDigitalOnScreenControl.getControlBase();
		controlBase.setAlpha(0.5f);
		controlBase.setOffsetCenter(0, 0);
		this.mDigitalOnScreenControl.getControlKnob().setScale(1.25f);
		this.mDigitalOnScreenControl.setScale(2f);		
	}
	
	public void attachScena(Scene scene){
		scene.setChildScene(this.mDigitalOnScreenControl);
	}
	
	
}
