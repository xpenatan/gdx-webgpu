
package com.monstrous.gdx.tests.webgpu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Array;
import com.monstrous.gdx.webgpu.graphics.g3d.WgModelBatch;
import com.monstrous.gdx.webgpu.graphics.WgTexture;

/** Simple test showing how to use a height map. Uses {@link HeightField}.
 * @author Xoppa */
public class HeightMapTest extends BaseG3dTest {
	HeightField field;
	Renderable ground;
	Environment environment;
	boolean morph = true;
	Texture texture;

	@Override
	public void create () {
		super.create();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.5f, -1.0f, -0.8f));

		texture = new WgTexture(Gdx.files.internal("data/badlogic.jpg"));

		int w = 20, h = 20;
		Pixmap data = new Pixmap(Gdx.files.internal("data/g3d/heightmap.png"));
		field = new HeightField(true, data, true, Usage.Position | Usage.Normal | Usage.ColorUnpacked | Usage.TextureCoordinates);
		data.dispose();
		field.corner00.set(-10f, 0, -10f);
		field.corner10.set(10f, 0, -10f);
		field.corner01.set(-10f, 0, 10f);
		field.corner11.set(10f, 0, 10f);
		field.color00.set(0, 0, 1, 1);
		field.color01.set(0, 1, 1, 1);
		field.color10.set(1, 0, 1, 1);
		field.color11.set(1, 1, 1, 1);
		field.magnitude.set(0f, 5f, 0f);
		field.update();

		ground = new Renderable();
		ground.environment = environment;
		ground.meshPart.mesh = field.mesh;
		ground.meshPart.primitiveType = GL20.GL_TRIANGLES;
		ground.meshPart.offset = 0;
		ground.meshPart.size = field.mesh.getNumIndices();
		ground.meshPart.update();
		ground.material = new Material(TextureAttribute.createDiffuse(texture));
	}

	@Override
	protected void render (WgModelBatch batch, Array<ModelInstance> instances) {
		batch.render(instances);
		batch.render(ground);
	}

	@Override
	public void dispose () {
		super.dispose();
		texture.dispose();
		field.dispose();
	}
}
