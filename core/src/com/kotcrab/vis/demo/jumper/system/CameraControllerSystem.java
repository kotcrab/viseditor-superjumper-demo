package com.kotcrab.vis.demo.jumper.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
public class CameraControllerSystem extends BaseSystem implements AfterSceneInit {
	ComponentMapper<Bounds> boundsCm;

	VisIDManager idManager;
	GameSceneManager gameManager;
	CameraManager cameraManager;

	Bounds playerBounds;

	float startCameraY;

	boolean init;

	@Override
	public void afterSceneInit () {
		init = true;

		playerBounds = boundsCm.get(idManager.get("player"));

		startCameraY = cameraManager.getCamera().position.y;
	}

	@Override
	protected boolean checkProcessing () {
		return init;
	}

	@Override
	protected void processSystem () {
		OrthographicCamera camera = cameraManager.getCamera();
		float yp = playerBounds.getY();
		if (yp > camera.position.y) {
			camera.position.y = yp;
		}

		if (yp < cameraManager.getCamera().position.y - 2.9f) {
			gameManager.gameOver();
		}
	}
}
