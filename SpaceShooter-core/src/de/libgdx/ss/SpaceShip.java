package de.libgdx.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class SpaceShip {
	
	private final Rectangle collisionRectangle;
	
	private float width = 100;
	private float height = 100;
	
	private float speed = 200.0f;
	
	private float xPos;
	private float yPos;
	
	private float yUpperBound = 100.0f;
	
	GameScreen gameRef;
	
	private Texture spaceShipTexture;
	
	public SpaceShip(float x, float y, float shipwidth, float shipheight,Texture shipTex)
	{
		System.out.println("Created a SpaceShip");
		xPos = x;
		yPos = y;
		width = shipwidth;
		height = shipheight;
		collisionRectangle = new Rectangle(xPos,yPos,width,height);
		spaceShipTexture = shipTex;
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(spaceShipTexture,xPos,yPos,width,height);
	}
	
	public void drawDebug(ShapeRenderer shapeRenderer)
	{
		collisionRectangle.x = xPos;
		collisionRectangle.y = yPos;
		shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
	}
	
	public void update(float delta)
	{
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (collisionRectangle.x < GameScreen.WORLD_WIDTH - width))
		{
			xPos += speed * delta;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && collisionRectangle.x > 0)
		{
			xPos -= speed * delta;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && collisionRectangle.y < yUpperBound)
		{
			yPos += speed * delta;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && collisionRectangle.y > 0)
		{
			yPos -= speed * delta;
		}
	}
}
