package com.kotcrab.vis.demo.jumper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.demo.jumper.manager.HelpSceneManager;
import com.kotcrab.vis.demo.jumper.manager.MenuSceneManager;
import com.kotcrab.vis.demo.jumper.system.*;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.SceneLoader.SceneParameter;
import com.kotcrab.vis.runtime.scene.VisAssetManager;

public class SuperJumper extends ApplicationAdapter {
	SpriteBatch batch;
	VisAssetManager manager;
	SoundController soundController;

	String scenePath;
	Scene scene;

	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new VisAssetManager(batch);
		manager.getLogger().setLevel(Logger.ERROR);

		soundController = new SoundController(manager);

		loadMenuScene();
	}

	public SoundController getSoundController () {
		return soundController;
	}

	public void loadMenuScene () {
		unloadPreviousScene();

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(new SpriteBoundsCreator());
		parameter.config.addSystem(new SpriteBoundsUpdater());
		parameter.config.addSystem(new MenuSceneManager(this));

		scenePath = "scene/mainmenu.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
	}

	public void loadHelpScene () {
		unloadPreviousScene();

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(new SpriteBoundsCreator());
		parameter.config.addSystem(new SpriteBoundsUpdater());
		parameter.config.addSystem(new HelpSceneManager(this));

		scenePath = "scene/help.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
	}

	public void loadGameScene () {
		unloadPreviousScene();

		PlatformSpawnerSystem spawnerSystem;

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(new SpriteBoundsCreator());
		parameter.config.addSystem(new GameSceneManager(this));
		parameter.config.addSystem(spawnerSystem = new PlatformSpawnerSystem());
		parameter.config.addSystem(new PlayerSystem());
		parameter.config.addSystem(new PhysicsSystem());
		parameter.config.addSystem(new CoinCollisionSystem());
		parameter.config.addSystem(new CameraControllerSystem());
		parameter.config.addSystem(new SpriteBoundsUpdater());

		scenePath = "scene/game.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
		spawnerSystem.setTargetLayerId(scene.getLayerDataByName("Game").id);
	}

	private void unloadPreviousScene () {
		if (scenePath != null) {
			manager.unload(scenePath);
			scenePath = null;
			scene = null;
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scene.render();
	}

	@Override
	public void resize (int width, int height) {
		scene.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}
}
