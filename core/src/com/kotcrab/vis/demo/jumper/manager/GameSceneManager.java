package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.kotcrab.vis.demo.jumper.SuperJumper;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.runtime.component.Invisible;

/** @author Kotcrab */
public class GameSceneManager extends BaseSceneManager {
	private ComponentMapper<Bounds> boundsCm;

	public enum GameState {GET_READY, RUNNING, PAUSED, GAME_OVER}

	GameState state = GameState.GET_READY;

	Entity pauseEntity;
	Entity resumeEntity;
	Entity readyEntity;
	Entity gameOverEntity;

	Bounds pauseBounds;
	Bounds resumeBounds;

	public GameSceneManager (SuperJumper game) {
		super(game);
	}

	@Override
	public void afterSceneInit () {
		super.afterSceneInit();
		pauseEntity = idManager.get("pause");
		resumeEntity = idManager.get("resume");
		readyEntity = idManager.get("ready");
		gameOverEntity = idManager.get("gameover");

		pauseBounds = boundsCm.get(pauseEntity);
		resumeBounds = boundsCm.get(resumeEntity);

		resumeEntity.edit().add(new Invisible());
		gameOverEntity.edit().add(new Invisible());
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		unprojectVec.set(screenX, screenY, 0);
		cameraManager.getUiCamera().unproject(unprojectVec);

		float x = unprojectVec.x;
		float y = unprojectVec.y;

		if (state == GameState.RUNNING && pauseBounds.contains(x, y)) {
			soundController.playClick();
			state = GameState.PAUSED;
			resumeEntity.edit().remove(new Invisible());
		}

		if (state == GameState.PAUSED && resumeBounds.contains(x, y)) {
			soundController.playClick();
			state = GameState.RUNNING;
			resumeEntity.edit().add(new Invisible());
		}

		if (state == GameState.GET_READY) {
			soundController.playClick();
			state = GameState.RUNNING;
			readyEntity.edit().add(new Invisible());
		}

		return false;
	}

	public GameState getState () {
		return state;
	}

	public void gameOver () {
		state = GameState.GAME_OVER;
		gameOverEntity.edit().remove(new Invisible());
	}

}
