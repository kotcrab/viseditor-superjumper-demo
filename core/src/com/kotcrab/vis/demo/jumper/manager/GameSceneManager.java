package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kotcrab.vis.demo.jumper.SuperJumper;

/** @author Kotcrab */
@Wire(injectInherited = true)
public class GameSceneManager extends BaseSceneManager {
	public enum GameState {GET_READY, RUNNING, PAUSED, GAME_OVER}

	GameState state = GameState.GET_READY;

	Sprite pauseSprite;
	Sprite resumeSprite;
	Sprite readySprite;
	Sprite gameOverSprite;

	public GameSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();
		pauseSprite = getSprite("pause");
		resumeSprite = getSprite("resume");
		readySprite = getSprite("ready");
		gameOverSprite = getSprite("gameover");

		resumeSprite.setAlpha(0);
		gameOverSprite.setAlpha(0);
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (state == GameState.RUNNING && pauseSprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			state = GameState.PAUSED;
			resumeSprite.setAlpha(1);
		}

		if (state == GameState.PAUSED && resumeSprite.getBoundingRectangle().contains(x, y)) {
			soundController.playClick();
			state = GameState.RUNNING;
			resumeSprite.setAlpha(0);
		}

		if (state == GameState.GET_READY) {
			soundController.playClick();
			state = GameState.RUNNING;
			readySprite.setAlpha(0);
		}

		return false;
	}

	public GameState getState () {
		return state;
	}

	public void gameOver () {
		state = GameState.GAME_OVER;
		gameOverSprite.setAlpha(1);
	}

}
