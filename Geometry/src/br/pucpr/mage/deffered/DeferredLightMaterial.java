package br.pucpr.mage.deffered;

import br.pucpr.mage.phong.SimpleMaterial;

public class DeferredLightMaterial extends SimpleMaterial {
    public DeferredLightMaterial(GBuffer gb) {
        super("/br/pucpr/mage/resource/deferred/deferredLight");
        setGBuffer(gb);
    }
    
    public void setGBuffer(GBuffer gbuffer) {
        setTexture("uPosition", gbuffer.getPosition());
        setTexture("uNormal", gbuffer.getNormal());
        setTexture("uAlbedoSpec", gbuffer.getAlbedo());
    }
}
