package de.libgdx.ss;

import sun.awt.image.PixelConverter.Bgrx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {
	
	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 640;
	
	private ShapeRenderer shapeRenderer;
	private Viewport viewport;
	private Camera camera;
	private SpriteBatch batch;
	private Texture hoot;
	private Texture ship_Texture;
	private Texture space_background;
	private SpaceShip ship;
	private float shipWidth = 100;
	private float shipHeight = 100;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		clearScreen();
		draw();
		updateMovement(delta);
	}
	
	private void draw()
	{
		batch.setProjectionMatrix(camera.projection);
		batch.setTransformMatrix(camera.view);
		batch.begin();
		batch.draw(space_background,0,0,WORLD_WIDTH,WORLD_HEIGHT);
		ship.draw(batch);
		batch.end();
		
		shapeRenderer.setProjectionMatrix(camera.projection);
		shapeRenderer.setTransformMatrix(camera.view);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		ship.drawDebug(shapeRenderer);
		shapeRenderer.end();
	}
	
	private void updateMovement(float delta)
	{
		ship.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		camera = new OrthographicCamera();
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);
		camera.update();
		viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		hoot = new Texture("hoothoot.png");
		space_background = new Texture("bg_galaxy.png");
		ship_Texture = new Texture("ship_sheet.png");
		ship = new SpaceShip(WORLD_WIDTH/2 - shipWidth/2, 0,shipWidth,shipHeight,ship_Texture);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
	
	private void clearScreen()
	{
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
