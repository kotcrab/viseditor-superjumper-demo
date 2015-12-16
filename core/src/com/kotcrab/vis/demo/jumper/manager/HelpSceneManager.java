package com.kotcrab.vis.demo.jumper.manager;

import com.kotcrab.vis.demo.jumper.SuperJumper;
import com.kotcrab.vis.demo.jumper.component.Bounds;

/** @author Kotcrab */
public class HelpSceneManager extends BaseSceneManager {
	private Bounds arrow1Bounds;
	private Bounds arrow2Bounds;

	public HelpSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		arrow1Bounds = getSpriteBounds("arrow1");
		arrow2Bounds = getSpriteBounds("arrow2");
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (arrow1Bounds.contains(x, y)) {
			soundController.playClick();
			cameraManager.getCamera().position.x += 3.2f;
		} else if (arrow2Bounds.contains(x, y)) {
			soundController.playClick();
			cameraManager.getCamera().position.x -= 3.2f;
			game.loadMenuScene();
		}

		return false;
	}
}
