package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.kotcrab.vis.demo.jumper.SuperJumper;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.runtime.component.Transform;

/** @author Kotcrab */
public class MenuSceneManager extends BaseSceneManager {
	private Bounds playSprite;
	private Bounds helpSprite;
	private Bounds quitSprite;

	private Entity soundOnEntity;
	private Entity soundOffEntity;

	private Bounds soundOnBounds;
	private Bounds soundOffBounds;

	public MenuSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		playSprite = getSpriteBounds("play");
		helpSprite = getSpriteBounds("help");
		quitSprite = getSpriteBounds("quit");

		soundOnEntity = idManager.get("soundOn");
		soundOffEntity = idManager.get("soundOff");

		soundOnBounds = boundsCm.get(soundOnEntity);
		soundOffBounds = boundsCm.get(soundOffEntity);
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (playSprite.contains(x, y)) {
			soundController.playClick();
			game.loadGameScene();
		}

		if (helpSprite.contains(x, y)) {
			soundController.playClick();
			game.loadHelpScene();
		}

		if (quitSprite.contains(x, y)) {
			soundController.playClick();
			Gdx.app.exit();
		}

		if (soundOnBounds.contains(x, y)) {
			soundController.setSoundEnabled(false);
			swapSpritesPosition(soundOnEntity, soundOffEntity);
		} else if (soundOffBounds.contains(x, y)) {
			soundController.setSoundEnabled(true);
			swapSpritesPosition(soundOffEntity, soundOnEntity);
		}

		return false;
	}

	public void swapSpritesPosition (Entity entity1, Entity entity2) {
		Transform transform1 = transformCm.get(entity1);
		Transform transform2 = transformCm.get(entity2);

		float xPos = transform1.getX(), yPos = transform1.getY();
		transform1.setPosition(transform2.getX(), transform2.getY());
		transform2.setPosition(xPos, yPos);
	}
}
