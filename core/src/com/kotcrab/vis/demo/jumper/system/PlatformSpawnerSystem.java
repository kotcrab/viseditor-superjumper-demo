package com.kotcrab.vis.demo.jumper.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.kotcrab.vis.demo.jumper.component.Coin;
import com.kotcrab.vis.demo.jumper.component.Platform;
import com.kotcrab.vis.runtime.component.*;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.system.render.RenderBatchingSystem;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
public class PlatformSpawnerSystem extends EntityProcessingSystem implements AfterSceneInit {
	ComponentMapper<VisSprite> spriteCm;
	ComponentMapper<Transform> transformCm;

	RenderBatchingSystem renderSystme;
	CameraManager cameraManager;
	VisIDManager idManager;

	VisSprite platformTemplate;
	VisSprite coinTemplate;

	VisSprite playerSprite;

	float lastPlatformY = 1f;
	int platformCount;

	boolean playerOnPlatform;
	private int targetLayerId;

	public PlatformSpawnerSystem () {
		super(Aspect.all(VisSprite.class, Platform.class));
	}

	@Override
	public void afterSceneInit () {
		platformTemplate = spriteCm.get(idManager.get("platform"));
		coinTemplate = spriteCm.get(idManager.get("coin"));

		playerSprite = spriteCm.get(idManager.get("player"));

		idManager.get("floor").edit().add(new Platform());
	}

	@Override
	protected void begin () {
		if (platformCount < 10 && platformTemplate != null) {
			VisSprite platformSprite = new VisSprite(platformTemplate);
			Transform platformTransform = new Transform();

			world.createEntity().edit()
					.add(new Renderable(10))
					.add(new Layer(targetLayerId))
					.add(new VisSprite(platformTemplate))
					.add(platformTransform)
					.add(new Origin())
					.add(new Platform());

			platformTransform.setPosition(MathUtils.random(0.23f, 2.8f), lastPlatformY);

			if (MathUtils.randomBoolean(0.3f)) {
				VisSprite coinSprite = new VisSprite(coinTemplate);
				Transform coinTransform = new Transform();

				world.createEntity().edit()
						.add(new Renderable(0))
						.add(new Layer(targetLayerId))
						.add(coinSprite)
						.add(coinTransform)
						.add(new Origin())
						.add(new Coin());

				coinTransform.setPosition(platformTransform.getX() + platformSprite.getWidth() / 2.0f, platformTransform.getY() + 0.5f);
			}

			renderSystme.markDirty();

			lastPlatformY += 1.2f;
			platformCount++;
		}

		playerOnPlatform = false;
	}

	@Override
	protected void process (Entity e) {
		//delete platforms outside screen
		Transform platformTransform = transformCm.get(e);
		if (platformTransform.getY() < cameraManager.getCamera().position.y - 2.6f) {
			platformCount--;
			e.deleteFromWorld();
		}
	}

	public void setTargetLayerId (int targetLayerId) {
		this.targetLayerId = targetLayerId;
	}
}
