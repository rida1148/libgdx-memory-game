package com.vyorkin.game.core;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.vyorkin.engine.E;
import com.vyorkin.engine.EngineRunner;
import com.vyorkin.engine.EngineSettings;
import com.vyorkin.engine.screens.GameScreen;
import com.vyorkin.engine.screens.LoadingScreen;

import com.vyorkin.game.core.domain.GameSettings;
import com.vyorkin.game.core.domain.PlayerProfile;
import com.vyorkin.game.core.resources.GameFont;
import com.vyorkin.game.core.resources.GameSkin;
import com.vyorkin.game.core.resources.GameSound;
import com.vyorkin.game.core.screens.MenuScreen;
import com.vyorkin.game.core.screens.SplashScreen;

public class MemoryGame extends EngineRunner {
	public static final String VERSION = "0.0.0.01 Pre-Alpha";
	public static final String TITLE = "libgdx game template";
	
	public static final int WIDTH = 1280;	// 480
	public static final int HEIGHT = 800;	// 320
	
	private final EngineSettings engineSettings;
	private GameSettings gameSettings;
	private PlayerProfile profile;
	
	{
		engineSettings = new EngineSettings(TITLE, VERSION, WIDTH, HEIGHT);
//		engineSettings.cursorFileName = GameTexture.CURSOR;
		engineSettings.fullscreen = false;
		engineSettings.useGL20 = false;
	};
	
	@Override
	public EngineSettings getSettings() {
		return engineSettings;
	}
	
	@Override
	protected void initialize() {
		gameSettings = new GameSettings();
		profile = new PlayerProfile();
		
		E.assets.load(GameSound.MENU_ENTER_CLICK, Sound.class);
		E.assets.load(GameSound.MENU_ENTER_HIT, Sound.class);
		E.assets.load(GameSound.MENU_EXIT, Sound.class);
		E.assets.load(GameSound.MENU_SELECT, Sound.class);
		
		E.assets.load(GameSkin.UI, Skin.class);
		E.assets.load(GameFont.CONSOLAS, BitmapFont.class);
	}
	
	@Override
	protected GameScreen getNextScreen(GameScreen screen) {
		if (screen == null) {
			return new SplashScreen();
		} else if (screen instanceof SplashScreen) {
			return new MenuScreen(gameSettings, profile);
		} else if (screen instanceof MenuScreen) {
			MenuScreen menuScreen = (MenuScreen)screen;
			return menuScreen.getNextScreen();
		} else if (screen instanceof LoadingScreen) {
			LoadingScreen loadingScreen = (LoadingScreen)screen;
			return loadingScreen.getNextScreen();
		}
		
		return null;
	}
}
