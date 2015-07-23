package com.kotcrab.vis.demo.jumper.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.runtime.component.SpriteComponent;
import com.kotcrab.vis.runtime.component.TextComponent;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
@Wire
public class CameraControllerSystem extends BaseSystem implements AfterSceneInit {
	ComponentMapper<SpriteComponent> spriteCm;
	ComponentMapper<TextComponent> textCm;

	VisIDManager idManager;
	GameSceneManager gameManager;
	CameraManager cameraManager;

	SpriteComponent player;

	SpriteComponent background;
	SpriteComponent pause;
	SpriteComponent resume;
	SpriteComponent gameOver;

	TextComponent textScore;
	TextComponent score;

	float startCameraY;

	boolean init;

	@Override
	public void afterSceneInit () {
		init = true;

		player = spriteCm.get(idManager.get("player"));

		background = spriteCm.get(idManager.get("background"));
		pause = spriteCm.get(idManager.get("pause"));
		resume = spriteCm.get(idManager.get("resume"));
		gameOver = spriteCm.get(idManager.get("gameover"));

		textScore = textCm.get(idManager.get("scoretext"));
		score = textCm.get(idManager.get("score"));

		startCameraY = cameraManager.getCamera().position.y;
	}

	@Override
	protected boolean checkProcessing () {
		return init;
	}

	@Override
	protected void processSystem () {
		OrthographicCamera camera = cameraManager.getCamera();
		float yp = player.getY();
		if (yp > camera.position.y) {
			float delta = yp - camera.position.y;
			camera.position.y = yp;

			background.sprite.translateY(delta);
			pause.sprite.translateY(delta);
			resume.sprite.translateY(delta);
			gameOver.sprite.translateY(delta);

			textScore.setY(textScore.getY() + delta);
			score.setY(score.getY() + delta);
		}

		if (player.getY() < cameraManager.getCamera().position.y - 2.9f) {
			gameManager.gameOver();
		}
	}
}
