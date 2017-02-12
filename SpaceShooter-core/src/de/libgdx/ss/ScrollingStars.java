package de.libgdx.ss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ScrollingStars {
	
	private final Texture stars;
	
	private float scrollSpeed = 50.0f;
	
	//Two simultaneous layers of stars following each other
	private Vector2 onePos = new Vector2(0,0);
	private Vector2 twoPos = new Vector2(0,0);
	
	private final GameScreen ref;
	
	public ScrollingStars(Texture stars, GameScreen reference)
	{
		this.stars = stars;
		this.ref = reference;
		twoPos.y = onePos.y + ref.WORLD_HEIGHT;
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(stars,onePos.x,onePos.y,ref.WORLD_WIDTH,ref.WORLD_HEIGHT);
		batch.draw(stars,twoPos.x,twoPos.y,ref.WORLD_WIDTH,ref.WORLD_HEIGHT);
	}

	public void update(float delta)
	{
		onePos.y -= scrollSpeed * delta;
		twoPos.y -= scrollSpeed * delta;
		
		if(onePos.y <= - ref.WORLD_HEIGHT)
		{
			onePos.y = ref.WORLD_HEIGHT;
		}
		
		if(twoPos.y <= -ref.WORLD_HEIGHT)
		{
			twoPos.y = ref.WORLD_HEIGHT;
		}
		
		System.out.println("POSITIONS 1: "+onePos.toString()+" 2: "+twoPos.toString());
	}
}
