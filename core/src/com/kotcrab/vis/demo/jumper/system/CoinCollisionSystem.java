package com.kotcrab.vis.demo.jumper.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.demo.jumper.component.Coin;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.component.VisText;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
public class CoinCollisionSystem extends EntityProcessingSystem implements AfterSceneInit {
	ComponentMapper<VisSprite> spriteCm;
	ComponentMapper<Bounds> boundsCm;
	ComponentMapper<VisText> textCm;

	VisIDManager idManager;

	Bounds playerBounds;
	VisText scoreText;
	int score = 0;

	public CoinCollisionSystem () {
		super(Aspect.all(VisSprite.class, Coin.class));
	}

	@Override
	public void afterSceneInit () {
		playerBounds = boundsCm.get(idManager.get("player"));
		scoreText = textCm.get(idManager.get("score"));
	}

	@Override
	protected void process (Entity e) {
		VisSprite coin = spriteCm.get(e);
		Bounds bounds = boundsCm.get(e);
		if (bounds.overlaps(playerBounds.bounds)) {
			score++;
			scoreText.setText(String.valueOf(score));
			e.deleteFromWorld();
		}
	}
}
