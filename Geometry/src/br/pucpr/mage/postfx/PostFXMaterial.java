package br.pucpr.mage.postfx;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import br.pucpr.mage.FrameBuffer;
import br.pucpr.mage.Material;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;

public class PostFXMaterial implements Material {
    private float width;
    private float height;
    private Texture texture;
    private Shader shader;
    
    public PostFXMaterial(String effectName, FrameBuffer fb) {
        shader = Shader.loadProgram("/br/pucpr/mage/resource/postfx/fxVertexShader.vert", 
                effectName + ".frag");    
        setFrameBuffer(fb);
    }
    
    public PostFXMaterial(Texture tex, float w, float h) {
        shader = Shader.loadProgram(
                "/br/pucpr/mage/resource/postfx/fxVertexShader.vert", 
                "/br/pucpr/mage/resource/postfx/fxNone.frag"
        );    
        this.texture = tex;
        this.width = w;
        this.height = h;
    }
    
    public static PostFXMaterial defaultPostFX(String name, FrameBuffer fb) {
        return new PostFXMaterial("/br/pucpr/mage/resource/postfx/" + name, fb);
    }
    
    public void setFrameBuffer(FrameBuffer frameBuffer) {
        this.texture = frameBuffer.getTexture();
        this.width = frameBuffer.getWidth();
        this.height = frameBuffer.getHeight();
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public Texture getTexture() {
        return texture;
    }
    
    @Override
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public void apply() {
        shader.setUniform("width", width);
        shader.setUniform("height", height);
        
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        shader.setUniform("uTexture", 0);
    }
}
