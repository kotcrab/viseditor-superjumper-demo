package com.kotcrab.vis.demo.jumper.manager;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.kotcrab.vis.demo.jumper.SoundController;
import com.kotcrab.vis.demo.jumper.SuperJumper;
import com.kotcrab.vis.demo.jumper.component.Bounds;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;

/** @author Kotcrab */
public abstract class BaseSceneManager extends Manager implements InputProcessor, AfterSceneInit {
	protected ComponentMapper<Bounds> boundsCm;
	protected ComponentMapper<Transform> transformCm;
	protected ComponentMapper<VisSprite> spriteCm;

	protected SuperJumper game;
	protected SoundController soundController;

	protected CameraManager cameraManager;
	protected VisIDManager idManager;

	protected Vector3 unprojectVec = new Vector3();

	public BaseSceneManager (SuperJumper game) {
		this.game = game;
		this.soundController = game.getSoundController();
	}

	protected Bounds getSpriteBounds (String id) {
		Entity entity = idManager.get(id);
		return boundsCm.get(entity);
	}

	@Override
	public void afterSceneInit () {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public boolean keyDown (int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}
}
