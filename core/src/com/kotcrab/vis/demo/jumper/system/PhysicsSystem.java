package com.kotcrab.vis.demo.jumper.system;

import com.artemis.*;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.demo.jumper.component.Platform;
import com.kotcrab.vis.demo.jumper.component.Velocity;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager.GameState;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;

/** @author Kotcrab */
public class PhysicsSystem extends EntityProcessingSystem {
	ComponentMapper<VisSprite> spriteCm;
	ComponentMapper<Transform> transformCm;
	ComponentMapper<Velocity> velocityCm;
	ComponentMapper<Bounds> boundsCm;

	AspectSubscriptionManager subscriptionManager;
	EntitySubscription platformsSubscription;

	GameSceneManager gameScene;

	final float gravity = -9.81f;

	public PhysicsSystem () {
		super(Aspect.all(VisSprite.class, Velocity.class));
	}

	@Override
	protected void initialize () {
		super.initialize();
		platformsSubscription = subscriptionManager.get(Aspect.all(Platform.class));
	}

	@Override
	protected boolean checkProcessing () {
		return gameScene.getState() == GameState.RUNNING;
	}

	@Override
	protected void process (Entity e) {
		Rectangle bounds = boundsCm.get(e).bounds;
		Transform transform = transformCm.get(e);
		Velocity velocity = velocityCm.get(e);

		float delta = Math.min(1 / 10f, Gdx.graphics.getDeltaTime());

		if (velocity.x != 0) {
			transform.setX(transform.getX() + velocity.x * delta);
			velocity.x -= 0.15f;
			if (velocity.x < 0) velocity.x = 0;
		}

		velocity.y += gravity * delta;
		float targetY = transform.getY() + velocity.y * delta;

		if(velocity.y > 0) {
			transform.setY(targetY);
			return;
		}

		IntBag bag = platformsSubscription.getEntities();
		int[] data = bag.getData();

		for (int i = 0; i < bag.size(); i++) {
			Entity platform = world.getEntity(data[i]);
			Transform platformTransform = transformCm.get(platform);
			VisSprite platformSprite = spriteCm.get(platform);
			Bounds platfromBounds = boundsCm.get(platform);

			if (transform.getY() > platformTransform.getY()) {
				bounds.y = targetY;
				if (bounds.overlaps(platfromBounds.bounds)) {
					transform.setY(platformTransform.getY() + platformSprite.getHeight());
					velocity.y = 0;
					return;
				}
			}
		}
		transform.setY(targetY);
	}
}
