package com.kotcrab.vis.demo.jumper.system;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.kotcrab.vis.demo.jumper.component.Velocity;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager.GameState;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
public class PlayerSystem extends BaseSystem implements AfterSceneInit {
	VisIDManager idManager;
	ComponentMapper<VisSprite> spriteCm;
	ComponentMapper<Transform> transformCm;

	GameSceneManager gameManager;

	Entity player;
	VisSprite sprite;
	Transform transform;
	Velocity velocity;

	@Override
	public void afterSceneInit () {
		player = idManager.get("player");
		player.edit().add(velocity = new Velocity());

		sprite = spriteCm.get(player);
		transform = transformCm.get(player);
	}

	@Override
	protected void processSystem () {
		if (gameManager.getState() == GameState.RUNNING) {
			if (Gdx.input.isKeyPressed(Keys.LEFT) && transform.getX() > 0.030f) {
				velocity.x = -1.5f;
				sprite.setFlip(true, false);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT) && transform.getX() < 2.8f) {
				velocity.x = 1.5f;
				sprite.setFlip(false, false);
			}

			if (Gdx.input.isKeyJustPressed(Keys.UP) && velocity.y == 0) {
				velocity.y = 6f;
			}
		}
	}
}
