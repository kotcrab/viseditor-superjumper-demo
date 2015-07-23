package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kotcrab.vis.demo.jumper.SuperJumper;

/** @author Kotcrab */
@Wire(injectInherited = true)
public class HelpSceneManager extends BaseSceneManager {
	private Sprite arrow1Sprite;
	private Sprite arrow2Sprite;

	public HelpSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		arrow1Sprite = getSprite("arrow1");
		arrow2Sprite = getSprite("arrow2");
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (arrow1Sprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			cameraManager.getCamera().position.x += 3.2f;
		} else if (arrow2Sprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			cameraManager.getCamera().position.x -= 3.2f;
			game.loadMenuScene();
		}

		return false;
	}
}
