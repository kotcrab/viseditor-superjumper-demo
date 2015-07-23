package com.kotcrab.vis.demo.jumper.system;

import com.artemis.*;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.demo.jumper.component.PlatformComponent;
import com.kotcrab.vis.demo.jumper.component.VelocityComponent;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager.GameState;
import com.kotcrab.vis.runtime.component.SpriteComponent;

/** @author Kotcrab */
@Wire
public class PhysicsSystem extends EntityProcessingSystem {
	ComponentMapper<SpriteComponent> spriteCm;
	ComponentMapper<VelocityComponent> velocityCm;

	AspectSubscriptionManager subscriptionManager;
	EntitySubscription platformsSubscription;

	GameSceneManager gameScene;

	final float gravity = -9.81f;

	public PhysicsSystem () {
		super(Aspect.all(SpriteComponent.class, VelocityComponent.class));
	}

	@Override
	protected void initialize () {
		super.initialize();
		platformsSubscription = subscriptionManager.get(Aspect.all(PlatformComponent.class));
	}

	@Override
	protected boolean checkProcessing () {
		return gameScene.getState() == GameState.RUNNING;
	}

	@Override
	protected void process (Entity e) {
		Sprite sprite = spriteCm.get(e).sprite;
		VelocityComponent velocity = velocityCm.get(e);

		float delta = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());

		if (velocity.x != 0) {
			sprite.setX(sprite.getX() + velocity.x * delta);
			velocity.x -= 0.15f;
			if (velocity.x < 0) velocity.x = 0;
		}

		velocity.y += gravity * delta;
		float targetY = sprite.getY() + velocity.y * delta;

		if(velocity.y > 0) {
			sprite.setY(targetY);
			return;
		}

		IntBag bag = platformsSubscription.getEntities();
		int[] data = bag.getData();

		for (int i = 0; i < bag.size(); i++) {
			Entity platform = world.getEntity(data[i]);
			Sprite platformSprite = spriteCm.get(platform).sprite;

			if (sprite.getY() > platformSprite.getY()) {
				Rectangle bounds = sprite.getBoundingRectangle();
				bounds.y = targetY;
				if (bounds.overlaps(platformSprite.getBoundingRectangle())) {
					sprite.setY(platformSprite.getY() + platformSprite.getHeight());
					velocity.y = 0;
					return;
				}
			}
		}
		sprite.setY(targetY);
	}
}
