
package com.monstrous.gdx.tests.webgpu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.monstrous.gdx.webgpu.graphics.g2d.WgBitmapFont;
import com.monstrous.gdx.webgpu.graphics.g2d.WgSpriteBatch;
import com.monstrous.gdx.webgpu.graphics.WgTexture;

// demonstrates the use of WebGPUSpriteBatch
// shows texture from file, texture from pixmap, texture region, sprite
//
public class WebGPUTestSpriteBatch  {

	// application
	static class TestApp extends ApplicationAdapter {
		private WgSpriteBatch batch;
		private WgTexture texture;
		private WgTexture texture2;
		private WgTexture textureAlpha;
		private TextureRegion region;
		private Sprite sprite;
		private float sx = 10;
		private WgBitmapFont font;


		public void create () {
			batch = new WgSpriteBatch();

			texture = new WgTexture(Gdx.files.internal("data/badlogic.jpg"));


			// create a texture from a pixmap
			Pixmap pm = new Pixmap(128, 128, Pixmap.Format.RGBA8888);
			pm.setColor(Color.BLUE);
			pm.fill();
			pm.setColor(Color.YELLOW);
			pm.fillCircle(64, 64, 32);
			texture2 = new WgTexture(pm);

			textureAlpha = new WgTexture(Gdx.files.internal("data/particle.png"));

			region = new TextureRegion(texture, 100, 100);
			sprite = new Sprite(texture);
			sprite.setScale(0.3f);

			font = new WgBitmapFont();
		}

		@Override
		public void render () {

			sx += Gdx.graphics.getDeltaTime() * 60f;
			if(sx > Gdx.graphics.getWidth() + 20)
				sx = -10;

			sprite.setRotation(sx);
			sprite.setCenter(sx,20);

			batch.begin(Color.FOREST);

			//batch.setBlendFactor(WGPUBlendFactor.SrcAlpha, WGPUBlendFactor.OneMinusSrcAlpha);
			batch.draw(texture, 100, 100);
			batch.draw(texture2, 400, 300);
			batch.draw(textureAlpha, 200, 400);
			batch.draw(region, 400, 100);


			batch.draw(texture2, 400, 300);

			//batch.disableBlending();
			//batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);	// GL compatibility
			//batch.setBlendFactor(WGPUBlendFactor.One, WGPUBlendFactor.Zero);	// WebGPU constants
			sprite.draw(batch);

			font.draw(batch, "Hello, world!", 20, 400);

////			for(int x = 0; x < 600; x++){
////				batch.draw(textureAlpha, x, 400);
////			}

			batch.end();

			//System.out.println("render calls:"+batch.renderCalls+"rects:"+batch.numRects+"/"+batch.maxFillLevel);
		}


		@Override
		public void resize (int width, int height) {
			Gdx.app.log("resize", "");
			batch.getProjectionMatrix().setToOrtho2D(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}

		@Override
		public void dispose () {
			batch.dispose();
			texture.dispose();
			texture2.dispose();
			textureAlpha.dispose();

		}


	}
}
