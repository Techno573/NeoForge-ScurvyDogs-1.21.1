package net.techno573.scurvydogs.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.techno573.scurvydogs.entity.custom.ProceduralSpider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProceduralSpiderRenderer extends GeoEntityRenderer<ProceduralSpider> {
    private static final Logger LOGGER = LogManager.getLogger();

    public ProceduralSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new ProceduralSpiderModel());
        LOGGER.info("Renderer initialized for ProceduralSpider");
    }
}