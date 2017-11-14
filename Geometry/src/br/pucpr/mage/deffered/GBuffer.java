package br.pucpr.mage.deffered;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import br.pucpr.mage.Texture;
import br.pucpr.mage.TextureParameters;

public class GBuffer {
    private int id;
    private int idDepth;
    
    private int width;
    private int height;
    
    private Texture position;
    private Texture normal;
    private Texture albedo;
    
    public GBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        
        //Cria o depth bufer
        idDepth = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, idDepth);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT16, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        
        //Configura o frame buffer
        TextureParameters nearest = new TextureParameters(GL_NEAREST);
        position = new Texture(width, height, nearest, true);
        normal = new Texture(width, height, nearest, true);
        albedo = new Texture(width, height, nearest);
        
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);                 
        
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, position.getId(), 0);        
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT1, GL_TEXTURE_2D, normal.getId(), 0);        
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT2, GL_TEXTURE_2D, albedo.getId(), 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, idDepth);
        
        //Indica quais serão os buffers de saída
        IntBuffer drawBuffs = BufferUtils.createIntBuffer(3);
        drawBuffs.put(GL_COLOR_ATTACHMENT0)
            .put(GL_COLOR_ATTACHMENT1)
            .put(GL_COLOR_ATTACHMENT2)
            .flip();        
        glDrawBuffers(drawBuffs);
        
        glBindFramebuffer(GL_FRAMEBUFFER, 0);       
    }
    
    public static GBuffer forCurrentViewport() {        
        IntBuffer viewport = BufferUtils.createIntBuffer(4);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
        
        viewport.get(); //x
        viewport.get(); //y
        int w = viewport.get();
        int h = viewport.get();
        return new GBuffer(w, h);
    }
    
    public int getId() {
        return id;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public Texture getPosition() {
        return position;
    }
    
    public Texture getNormal() {
        return normal;
    }
    
    public Texture getAlbedo() {
        return albedo;
    }
    
    public GBuffer bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        return this;
    }
    
    public GBuffer unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        return this;
    }
}
