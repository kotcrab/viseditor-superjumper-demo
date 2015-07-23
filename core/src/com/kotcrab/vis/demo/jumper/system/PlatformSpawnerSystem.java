package com.kotcrab.vis.demo.jumper.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.kotcrab.vis.demo.jumper.component.CoinComponent;
import com.kotcrab.vis.demo.jumper.component.PlatformComponent;
import com.kotcrab.vis.runtime.component.LayerComponent;
import com.kotcrab.vis.runtime.component.RenderableComponent;
import com.kotcrab.vis.runtime.component.SpriteComponent;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.RenderBatchingSystem;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
@Wire
public class PlatformSpawnerSystem extends EntityProcessingSystem implements AfterSceneInit {
	ComponentMapper<SpriteComponent> spriteCm;

	RenderBatchingSystem renderSystme;
	CameraManager cameraManager;
	VisIDManager idManager;

	Sprite platformTemplateSprite;
	Sprite coinTemplateSprite;

	Sprite playerSprite;

	float lastPlatformY = 1f;
	int platformCount;

	boolean playerOnPlatform;

	public PlatformSpawnerSystem () {
		super(Aspect.all(SpriteComponent.class, PlatformComponent.class));
	}

	@Override
	public void afterSceneInit () {
		platformTemplateSprite = spriteCm.get(idManager.get("platform")).sprite;
		coinTemplateSprite = spriteCm.get(idManager.get("coin")).sprite;
		playerSprite = spriteCm.get(idManager.get("player")).sprite;

		idManager.get("floor").edit().add(new PlatformComponent());
	}

	@Override
	protected void begin () {
		if (platformCount < 10 && platformTemplateSprite != null) {
			SpriteComponent spriteComponent = new SpriteComponent(new Sprite(platformTemplateSprite));

			world.createEntity().edit()
					.add(new RenderableComponent(10))
					.add(new LayerComponent(0))
					.add(spriteComponent)
					.add(new PlatformComponent());

			spriteComponent.setX(MathUtils.random(0.23f, 2.8f));
			spriteComponent.setY(lastPlatformY);

			if (MathUtils.randomBoolean(0.3f)) {
				SpriteComponent spriteCoinComponent = new SpriteComponent(new Sprite(coinTemplateSprite));

				world.createEntity().edit()
						.add(new RenderableComponent(0))
						.add(new LayerComponent(0))
						.add(spriteCoinComponent)
						.add(new CoinComponent());

				spriteCoinComponent.setPosition(spriteComponent.getX() + spriteComponent.getWidth() / 2.0f,
						spriteComponent.getY() + 0.5f);
			}

			renderSystme.markDirty();

			lastPlatformY += 1.2f;
			platformCount++;
		}

		playerOnPlatform = false;
	}

	@Override
	protected void process (Entity e) {
//		if (playerOnPlatform) return;
//
		Sprite platformSprite = spriteCm.get(e).sprite;
//
		if (platformSprite.getY() < cameraManager.getCamera().position.y - 2.6f) {
			platformCount--;
			e.deleteFromWorld();
		}

//		if (playerSprite.getY() == platformSprite.getY() + platformSprite.getHeight()) {
//			playerOnPlatform = true;
//			return;
//		}
//
////		if (playerSprite.getY() >= platformSprite.getY() && playerSprite.getBoundingRectangle().overlaps(platformSprite.getBoundingRectangle())) {
//		if (playerSprite.getY() >= platformSprite.getY() + platformSprite.getHeight() && playerSprite.getBoundingRectangle().overlaps(platformSprite.getBoundingRectangle())) {
//			playerOnPlatform = true;
//			playerSprite.setY(platformSprite.getY() + platformSprite.getHeight());
//		}
	}
}
