package com.kotcrab.vis.demo.jumper;

import com.artemis.BaseSystem;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.kotcrab.vis.demo.jumper.manager.GameSceneManager;
import com.kotcrab.vis.demo.jumper.manager.HelpSceneManager;
import com.kotcrab.vis.demo.jumper.manager.MenuSceneManager;
import com.kotcrab.vis.demo.jumper.system.*;
import com.kotcrab.vis.demo.jumper.util.Holder;
import com.kotcrab.vis.runtime.RuntimeContext;
import com.kotcrab.vis.runtime.data.SceneData;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.SceneLoader.SceneParameter;
import com.kotcrab.vis.runtime.scene.SystemProvider;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.util.EntityEngineConfiguration;

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
		parameter.config.addSystem(SpriteBoundsCreator.class);
		parameter.config.addSystem(SpriteBoundsUpdater.class);
		parameter.config.addSystem(new SystemProvider() {
			@Override
			public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
				return new MenuSceneManager(SuperJumper.this);
			}
		});

		scenePath = "scene/mainmenu.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
	}

	public void loadHelpScene () {
		unloadPreviousScene();

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(SpriteBoundsCreator.class);
		parameter.config.addSystem(SpriteBoundsUpdater.class);
		parameter.config.addSystem(new SystemProvider() {
			@Override
			public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
				return new HelpSceneManager(SuperJumper.this);
			}
		});

		scenePath = "scene/help.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
	}

	public void loadGameScene () {
		unloadPreviousScene();

		final Holder<PlatformSpawnerSystem> spawnerSystem = Holder.empty();

		SceneParameter parameter = new SceneParameter();
		parameter.config.addSystem(SpriteBoundsCreator.class);
		parameter.config.addSystem(new SystemProvider() {
			@Override
			public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
				return new GameSceneManager(SuperJumper.this);
			}
		});
		parameter.config.addSystem(new SystemProvider() {
			@Override
			public BaseSystem create (EntityEngineConfiguration config, RuntimeContext context, SceneData data) {
				spawnerSystem.value = new PlatformSpawnerSystem();
				return spawnerSystem.value;
			}
		});
		parameter.config.addSystem(PlayerSystem.class);
		parameter.config.addSystem(PhysicsSystem.class);
		parameter.config.addSystem(CoinCollisionSystem.class);
		parameter.config.addSystem(CameraControllerSystem.class);
		parameter.config.addSystem(SpriteBoundsUpdater.class);

		scenePath = "scene/game.scene";
		scene = manager.loadSceneNow(scenePath, parameter);
		spawnerSystem.value.setTargetLayerId(scene.getLayerDataByName("Game").id);
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
