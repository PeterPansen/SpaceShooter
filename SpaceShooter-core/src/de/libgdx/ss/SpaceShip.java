package de.libgdx.ss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class SpaceShip {
	
	//Main collision rectangle used for collision detection
	private final Rectangle collisionRectangle;
	
	//Width and Height of our ship and its colliders
	private float width = 100;
	private float height = 100;
	
	//Movement speed of our ship
	private float speed = 200.0f;
	
	//x- and y-Position of the Ship
	private float xPos;
	private float yPos;
	
	//Speed along xAxis. Needed for animation and Physics. Calculated from former/old xPos
	private float xSpeed;
	private float oldXPos = 0;
	
	//Upper Flying Bound. How far can the player go up
	private float yUpperBound = 100.0f;
	
	//Reference to the gameScreen for WIDTH/HEIGHT etc
	private GameScreen gameScreenRef;
	
	//Animations
	//Our general turning animation. Goes left. Is flipped for right maneuvers
	private final Animation animTurn;
	private float animTurnTimer = 0.0f;
	private TextureRegion[][] shipTexRegions;
	private static final float FRAME_DURATION = 0.07f;
	private TextureRegion currentTexReg;
	
	
	public SpaceShip(float x, float y, float shipwidth, float shipheight, Texture shipTex)
	{
		System.out.println("Created a SpaceShip");
		xPos = x;
		yPos = y;
		width = shipwidth;
		height = shipheight;
		shipTexRegions = new TextureRegion(shipTex).split(100,100);
		
		animTurn = new Animation(FRAME_DURATION,shipTexRegions[1][1],shipTexRegions[1][0],shipTexRegions[0][2],shipTexRegions[0][1]);
		animTurn.setPlayMode(Animation.PlayMode.NORMAL);
		
		collisionRectangle = new Rectangle(xPos,yPos,width,height);
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		if(animTurnTimer > 0)
		{
			currentTexReg = (TextureRegion) animTurn.getKeyFrame(animTurnTimer);
			
			if(!currentTexReg.isFlipX())
			{
				currentTexReg.flip(true, false);
			}
		}
		
		else
		{
			currentTexReg = (TextureRegion) animTurn.getKeyFrame(Math.abs(animTurnTimer));
			
			if(currentTexReg.isFlipX())
			{
				currentTexReg.flip(true, false);
			}
		}
		
		spriteBatch.draw(currentTexReg, xPos, yPos, width, height);
	}
	
	public void drawDebug(ShapeRenderer shapeRenderer)
	{
		collisionRectangle.x = xPos;
		collisionRectangle.y = yPos;
		shapeRenderer.rect(collisionRectangle.x, collisionRectangle.y, collisionRectangle.width, collisionRectangle.height);
	}
	
	public void update(float delta)
	{
		xSpeed = xPos - oldXPos;
		oldXPos = xPos;
		
		if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) || (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)))
		{
			if(animTurnTimer > 0)
			{
				animTurnTimer -= delta;
			}
			if(animTurnTimer <= 0)
			{
				animTurnTimer += delta;
			}
		}
		
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (collisionRectangle.x < GameScreen.WORLD_WIDTH - width) && (!Gdx.input.isKeyPressed(Input.Keys.LEFT)))
			{
				if(!animTurn.isAnimationFinished(Math.abs(animTurnTimer)))
				{
					animTurnTimer += delta;
				}
				
				xPos += speed * delta;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && collisionRectangle.x > 0 && (!Gdx.input.isKeyPressed((Input.Keys.RIGHT))))
			{
				if(!animTurn.isAnimationFinished(Math.abs(animTurnTimer)))
				{
					animTurnTimer -= delta;
				}
	
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
