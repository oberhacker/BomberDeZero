package xnetcom.bomber.util;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import android.util.Log;

public class MiSpriteGroup extends SpriteGroup {


	public MiSpriteGroup(ITexture pTexture, int pCapacity, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pTexture, pCapacity, pVertexBufferObjectManager);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		try {
			super.onManagedUpdate(pSecondsElapsed);
		} catch (Exception e) {
			Log.e("MiSpriteGroup", "onManagedUpdate");
		}
	}

	protected boolean onUpdateSpriteBatch() {
		final SmartList<IEntity> children = this.mChildren;
		if (children == null) {
			return false;
		} else {
			final int childCount = children.size();
			for (int i = 0; i < childCount; i++) {
				try {
					super.drawWithoutChecks((Sprite) children.get(i));
				} catch (Exception e) {
					Log.e("MiSpriteGroup", "onUpdateSpriteBatch");
				}
			}
			return true;
		}
	}

}
