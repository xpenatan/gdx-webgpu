/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.monstrous.gdx.webgpu.graphics.utils;

import com.badlogic.gdx.Gdx;
import com.monstrous.gdx.webgpu.application.WgGraphics;
import com.monstrous.gdx.webgpu.wrappers.RenderPassBuilder;
import com.monstrous.gdx.webgpu.wrappers.WebGPURenderPass;
import com.badlogic.gdx.graphics.Color;

/** WebGPU version to clear the screen
 *  */
public final class WgScreenUtils {
	private static final Color backgroundColor = new Color();

	/** Clears the color buffers with the specified Color.
	 * @param color Color to clear the color buffers with. */
	public static void clear (Color color) {
		clear(color.r, color.g, color.b, color.a, false);
	}

	/** Clears the color buffers with the specified color. */
	public static void clear (float r, float g, float b, float a) {
		clear(r, g, b, a, false);
	}

	/** Clears the color buffers and optionally the depth buffer.
	 * @param color Color to clear the color buffers with.
	 * @param clearDepth Clears the depth buffer if true. */
	public static void clear (Color color, boolean clearDepth) {
		clear(color.r, color.g, color.b, color.a, clearDepth);
	}

	/** Clears the color buffers and optionally the depth buffer.
	 * @param clearDepth Clears the depth buffer if true. */
	public static void clear (float r, float g, float b, float a, boolean clearDepth) {
		clear(r, g, b, a, clearDepth, false);
	}

	/** Clears the color buffers, optionally the depth buffer and whether to apply antialiasing (requires to set number of samples
	 * in the launcher class).
	 *
	 * @param clearDepth Clears the depth buffer if true.
	 * @param applyAntialiasing applies multi-sampling for antialiasing if true. */
	public static void clear (float r, float g, float b, float a, boolean clearDepth, boolean applyAntialiasing) {
		backgroundColor.set(r,g,b,a);
		WgGraphics gfx = (WgGraphics) Gdx.graphics;
		WebGPURenderPass renderPass = RenderPassBuilder.create("ScreenUtils.clear", backgroundColor, clearDepth, gfx.getContext().getSamples());
		renderPass.end();

		// clearDepth and antiAliasing are ignored
//
//		Gdx.gl.glClearColor(r, g, b, a);
//		int mask = GL20.GL_COLOR_BUFFER_BIT;
//		if (clearDepth) mask = mask | GL20.GL_DEPTH_BUFFER_BIT;
//		if (applyAntialiasing && Gdx.graphics.getBufferFormat().coverageSampling) mask = mask | GL20.GL_COVERAGE_BUFFER_BIT_NV;
//		Gdx.gl.glClear(mask);
	}

//	/** Returns the current framebuffer contents as a {@link TextureRegion} with a width and height equal to the current screen
//	 * size. The base {@link Texture} always has {@link MathUtils#nextPowerOfTwo} dimensions and RGBA8888 {@link Format}. It can be
//	 * accessed via {@link TextureRegion#getTexture}. The texture is not managed and has to be reloaded manually on a context loss.
//	 * The returned TextureRegion is flipped along the Y axis by default. */
//	public static TextureRegion getFrameBufferTexture () {
//		final int w = Gdx.graphics.getBackBufferWidth();
//		final int h = Gdx.graphics.getBackBufferHeight();
//		return getFrameBufferTexture(0, 0, w, h);
//	}
//
//	/** Returns a portion of the current framebuffer contents specified by x, y, width and height as a {@link TextureRegion} with
//	 * the same dimensions. The base {@link Texture} always has {@link MathUtils#nextPowerOfTwo} dimensions and RGBA8888
//	 * {@link Format}. It can be accessed via {@link TextureRegion#getTexture}. This texture is not managed and has to be reloaded
//	 * manually on a context loss. If the width and height specified are larger than the framebuffer dimensions, the Texture will
//	 * be padded accordingly. Pixels that fall outside of the current screen will have RGBA values of 0.
//	 *
//	 * @param x the x position of the framebuffer contents to capture
//	 * @param y the y position of the framebuffer contents to capture
//	 * @param w the width of the framebuffer contents to capture
//	 * @param h the height of the framebuffer contents to capture */
//	public static TextureRegion getFrameBufferTexture (int x, int y, int w, int h) {
//		final int potW = MathUtils.nextPowerOfTwo(w);
//		final int potH = MathUtils.nextPowerOfTwo(h);
//
//		final Pixmap pixmap = Pixmap.createFromFrameBuffer(x, y, w, h);
//		final Pixmap potPixmap = new Pixmap(potW, potH, Format.RGBA8888);
//		potPixmap.setBlending(Blending.None);
//		potPixmap.drawPixmap(pixmap, 0, 0);
//		Texture texture = new Texture(potPixmap);
//		TextureRegion textureRegion = new TextureRegion(texture, 0, h, w, -h);
//		potPixmap.dispose();
//		pixmap.dispose();
//
//		return textureRegion;
//	}
//
//	/** @deprecated use {@link Pixmap#createFromFrameBuffer(int, int, int, int)} instead. */
//	@Deprecated
//	public static Pixmap getFrameBufferPixmap (int x, int y, int w, int h) {
//		return Pixmap.createFromFrameBuffer(x, y, w, h);
//	}
//
//	/** Returns the current framebuffer contents as a byte[] array with a length equal to screen width * height * 4. The byte[]
//	 * will always contain RGBA8888 data. Because of differences in screen and image origins the framebuffer contents should be
//	 * flipped along the Y axis if you intend save them to disk as a bitmap. Flipping is not a cheap operation, so use this
//	 * functionality wisely.
//	 *
//	 * @param flipY whether to flip pixels along Y axis */
//	public static byte[] getFrameBufferPixels (boolean flipY) {
//		final int w = Gdx.graphics.getBackBufferWidth();
//		final int h = Gdx.graphics.getBackBufferHeight();
//		return getFrameBufferPixels(0, 0, w, h, flipY);
//	}
//
//	/** Returns a portion of the current framebuffer contents specified by x, y, width and height, as a byte[] array with a length
//	 * equal to the specified width * height * 4. The byte[] will always contain RGBA8888 data. If the width and height specified
//	 * are larger than the framebuffer dimensions, the Texture will be padded accordingly. Pixels that fall outside of the current
//	 * screen will have RGBA values of 0. Because of differences in screen and image origins the framebuffer contents should be
//	 * flipped along the Y axis if you intend save them to disk as a bitmap. Flipping is not a cheap operation, so use this
//	 * functionality wisely.
//	 *
//	 * @param flipY whether to flip pixels along Y axis */
//	public static byte[] getFrameBufferPixels (int x, int y, int w, int h, boolean flipY) {
//		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
//		final ByteBuffer pixels = BufferUtils.newByteBuffer(w * h * 4);
//		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);
//		final int numBytes = w * h * 4;
//		byte[] lines = new byte[numBytes];
//		if (flipY) {
//			final int numBytesPerLine = w * 4;
//			for (int i = 0; i < h; i++) {
//				((Buffer)pixels).position((h - i - 1) * numBytesPerLine);
//				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
//			}
//		} else {
//			((Buffer)pixels).clear();
//			pixels.get(lines);
//		}
//		return lines;
//
//	}
}
