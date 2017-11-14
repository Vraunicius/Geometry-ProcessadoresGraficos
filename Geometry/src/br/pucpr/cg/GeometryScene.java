package br.pucpr.cg;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import br.pucpr.mage.ArrayBuffer;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Window;

public class GeometryScene implements Scene {
    private static final String GEO_PATH = "/br/pucpr/mage/resource/geometry/";
    
    private int vao;
    private ArrayBuffer positions;
    private ArrayBuffer colors;
    
    private Shader shader;

    ArrayBuffer createArrayBuffer(int size, float ... values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.length);
        valueBuffer.put(values).flip();
        return new ArrayBuffer(size, valueBuffer);
    }

    @Override
    public void init() {
        glEnable(GL_CULL_FACE);
        vao = glGenVertexArrays();
        
        glBindVertexArray(vao);
        positions = createArrayBuffer(2,
                0.5f,  0.5f, // Top-right
                -0.5f,  0.5f, // Top-left                
                0.5f, -0.5f, // Bottom-right
               -0.5f, -0.5f  // Bottom-left
        );
        
        colors = createArrayBuffer(3,
                1.0f, 0.0f, 0.0f, // Top-left
                0.0f, 1.0f, 0.0f, // Top-right
                0.0f, 0.0f, 1.0f, // Bottom-right
                1.0f, 1.0f, 0.0f // Bottom-left
        );
        
        shader = Shader.loadProgram(
                GEO_PATH + "color.vert",
                GEO_PATH + "colorHouse.geom",
                GEO_PATH + "color.frag"
        );
        
        glBindVertexArray(0);
    }

    @Override
    public void update(float secs) {
    }
    
    @Override
    public void draw() {
        glBindVertexArray(vao);
        
        glClear(GL_COLOR_BUFFER_BIT);
        shader.bind();
        
        positions.bind();        
        shader.setAttribute("aPosition", positions);
        colors.bind();
        shader.setAttribute("aColor", colors);
        
        glBindVertexArray(vao);        
        positions.draw(GL_POINTS);
        glBindVertexArray(0);
        shader.unbind();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {        
        new Window(new GeometryScene(), "Geometry Shader", 1024, 748).show();
    }
}
