package com.kotcrab.vis.demo.jumper.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.kotcrab.vis.demo.jumper.component.CoinComponent;
import com.kotcrab.vis.runtime.component.SpriteComponent;
import com.kotcrab.vis.runtime.component.TextComponent;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
@Wire
public class CoinCollisionSystem extends EntityProcessingSystem implements AfterSceneInit {
	ComponentMapper<SpriteComponent> spriteCm;
	ComponentMapper<TextComponent> textCm;

	VisIDManager idManager;

	SpriteComponent playerSprite;
	int score = 0;
	TextComponent scoreText;

	public CoinCollisionSystem () {
		super(Aspect.all(SpriteComponent.class, CoinComponent.class));
	}

	@Override
	public void afterSceneInit () {
		playerSprite = spriteCm.get(idManager.get("player"));
		scoreText = textCm.get(idManager.get("score"));
	}

	@Override
	protected void process (Entity e) {
		SpriteComponent coin = spriteCm.get(e);

		if (coin.getBoundingRectangle().overlaps(playerSprite.getBoundingRectangle())) {
			score++;
			scoreText.setText(String.valueOf(score));
			e.deleteFromWorld();
		}
	}
}
