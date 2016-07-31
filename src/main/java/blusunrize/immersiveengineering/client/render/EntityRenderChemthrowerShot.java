package blusunrize.immersiveengineering.client.render;

import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.entities.EntityChemthrowerShot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityRenderChemthrowerShot extends Render
{
	public EntityRenderChemthrowerShot(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1)
	{
		Fluid f = ((EntityChemthrowerShot)entity).getFluid();
		if(f==null)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.enableRescaleNormal();
		Tessellator tessellator = ClientUtils.tes();

		GlStateManager.disableCull();
		GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		TextureAtlasSprite sprite = ClientUtils.mc().getTextureMapBlocks().getAtlasSprite(f.getStill().toString());
		if(sprite!=null)
		{
			int colour = f.getColor();
			float a = (colour>>24&255)/255f;
			float r = (colour>>16&255)/255f;
			float g = (colour>>8&255)/255f;
			float b = (colour&255)/255f;
			ClientUtils.bindAtlas();
			GlStateManager.scale(.25f, .25f, .25f);
			VertexBuffer worldrenderer = ClientUtils.tes().getBuffer();
			worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
			worldrenderer.pos(-.25,-.25, 0).tex(sprite.getInterpolatedU(4), sprite.getInterpolatedV(4)).color(r,g,b,a).endVertex();
			worldrenderer.pos( .25,-.25, 0).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(4)).color(r,g,b,a).endVertex();
			worldrenderer.pos( .25, .25, 0).tex(sprite.getInterpolatedU(0), sprite.getInterpolatedV(0)).color(r,g,b,a).endVertex();
			worldrenderer.pos(-.25, .25, 0).tex(sprite.getInterpolatedU(4), sprite.getInterpolatedV(0)).color(r,g,b,a).endVertex();
			tessellator.draw();
		}
		GlStateManager.enableCull();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return new ResourceLocation("immersiveengineering:textures/models/bullet.png");
	}

}
