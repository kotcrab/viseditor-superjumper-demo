package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kotcrab.vis.demo.jumper.SuperJumper;

/** @author Kotcrab */
@Wire(injectInherited = true)
public class MenuSceneManager extends BaseSceneManager {
	private Sprite playSprite;
	private Sprite helpSprite;
	private Sprite quitSprite;

	private Sprite soundOnSprite;
	private Sprite soundOffSprite;

	public MenuSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();

		playSprite = getSprite("play");
		helpSprite = getSprite("help");
		quitSprite = getSprite("quit");

		soundOnSprite = getSprite("soundOn");
		soundOffSprite = getSprite("soundOff");
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (playSprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			game.loadGameScene();
		}

		if (helpSprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			game.loadHelpScene();
		}

		if (quitSprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			Gdx.app.exit();
		}

		if (soundOnSprite.getBoundingRectangle().contains(x, y)) {
			soundController.setSoundEnabled(false);
			swapSpritesPosition(soundOnSprite, soundOffSprite);
		} else if (soundOffSprite.getBoundingRectangle().contains(x, y)) {
			soundController.setSoundEnabled(true);
			swapSpritesPosition(soundOffSprite, soundOnSprite);
		}

		return false;
	}

	public void swapSpritesPosition (Sprite sprite1, Sprite sprite2) {
		float xPos = sprite1.getX(), yPos = sprite1.getY();
		sprite1.setPosition(sprite2.getX(), sprite2.getY());
		sprite2.setPosition(xPos, yPos);
	}
}
