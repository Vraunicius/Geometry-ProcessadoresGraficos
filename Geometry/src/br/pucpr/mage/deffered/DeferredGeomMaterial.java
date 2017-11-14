package br.pucpr.mage.deffered;

import br.pucpr.mage.Texture;
import br.pucpr.mage.phong.SimpleMaterial;

public class DeferredGeomMaterial extends SimpleMaterial {
    private float specularPower;
    
    public DeferredGeomMaterial(float specular) {
        super("/br/pucpr/mage/resource/deferred/deferredGeom");
        this.specularPower = specular;
    }
    
    public float getSpecularPower() {
        return specularPower;
    }
    
    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }    
    
    public DeferredGeomMaterial setTextures(Texture ... textures) {
        for (int i = 0; i < textures.length; i++) {
            setTexture("uTex" + i, textures[i]);
        }
        return this;
    }

}
