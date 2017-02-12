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
	
	private final Rectangle collisionRectangle;
	
	private float width = 100;
	private float height = 100;
	
	private float speed = 200.0f;
	
	private float xPos;
	private float yPos;
	
	private float xSpeed;
	private float oldXPos = 0;
	//Upper Flying Bound
	private float yUpperBound = 100.0f;
	
	GameScreen gameScreenRef;
	
	private TextureRegion[][] shipTexRegions;
	
	private final Animation animTurnLeft;
	private final Animation animTurnRight;
	private float animTurnTimer = 0.0f;
	private static final float FRAME_DURATION = 0.05f;
	private TextureRegion currentTexReg;
	
	public SpaceShip(float x, float y, float shipwidth, float shipheight, Texture shipTex)
	{
		System.out.println("Created a SpaceShip");
		xPos = x;
		yPos = y;
		width = shipwidth;
		height = shipheight;
		this.shipTexRegions = new TextureRegion(shipTex).split(100,100);
		
		animTurnLeft = new Animation(FRAME_DURATION,shipTexRegions[1][1],shipTexRegions[1][0],shipTexRegions[0][2],shipTexRegions[0][1]);
		animTurnLeft.setPlayMode(Animation.PlayMode.NORMAL);
		
		animTurnRight = new Animation(FRAME_DURATION,shipTexRegions[1][1],shipTexRegions[1][2],shipTexRegions[2][0],shipTexRegions[2][1]);
		animTurnRight.setPlayMode(Animation.PlayMode.NORMAL);
		
		collisionRectangle = new Rectangle(xPos,yPos,width,height);
	}
	
	public void draw(SpriteBatch spriteBatch)
	{
		//System.out.println(animTurnTimer);
		if(animTurnTimer >= 0)
		{
			currentTexReg = (TextureRegion) animTurnRight.getKeyFrame(animTurnTimer);
		}
		else
		{
			currentTexReg = (TextureRegion) animTurnLeft.getKeyFrame(Math.abs(animTurnTimer));
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
				if(!animTurnRight.isAnimationFinished(Math.abs(animTurnTimer)))
				{
					animTurnTimer += delta;
				}
				
				xPos += speed * delta;
			}
			
			if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && collisionRectangle.x > 0 && (!Gdx.input.isKeyPressed((Input.Keys.RIGHT))))
			{
				if(!animTurnLeft.isAnimationFinished(Math.abs(animTurnTimer)))
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
