package com.bafomdad.uniquecrops.gui;

import com.bafomdad.uniquecrops.events.UCTickHandler;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.RubiksCubeItem;
import com.bafomdad.uniquecrops.network.PacketColorfulCube;
import com.bafomdad.uniquecrops.network.UCPacketHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class GuiColorfulCube extends Screen {

    double lastRotationTime;
    boolean hasRotated = false;

    public GuiColorfulCube() {

        super(StringTextComponent.EMPTY);
    }

    @Override
    public void init() {

        int k = this.width / 2;
        int l = this.height / 2;
        buttons.add(new Button(k - 40, l - 40, 80, 80, DialogTexts.GUI_PROCEED, (button) -> { this.clickTeleport(); }){
            @Override
            public void renderButton(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {}
        });
    }

    @Override
    public boolean isPauseScreen() {

        return false;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {

        //TODO: real fix
        ms.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        double time = (UCTickHandler.ticksInGame + UCTickHandler.partialTicks) * 12F;
        float blitOffset = minecraft.getItemRenderer().blitOffset;
        ms.translate(0, 0, blitOffset - 0.25);
        ms.scale(0.25F, 0.25F, 0.25F);
//        ms.translate(-0.125, -0.125, 0);
        Vector3i vec3 = this.getRotationVec();
        Vector3f vecf = new Vector3f(vec3.getX(), vec3.getY(), vec3.getZ());
        boolean north = vec3.equals(Direction.NORTH.getNormal());
        if (hasRotated) {
            if (lastRotationTime == -1)
                this.lastRotationTime = time;
            double rotationElapsed = time - lastRotationTime;
            if (!north) {
                ms.mulPose(new Quaternion(vecf, (float)rotationElapsed, true));
                if (rotationElapsed >= 90F) {
                    this.lastRotationTime = -1;
                    this.hasRotated = false;
                }
            } else {
                ms.mulPose(Vector3f.YP.rotationDegrees((float)rotationElapsed + 90F));
                if ((float)rotationElapsed + 90F >= 180F) {
                    this.lastRotationTime = -1;
                    this.hasRotated = false;
                }
            }
        } else {
            if (!north)
                ms.mulPose(new Quaternion(vecf, 90F, true));
            else
                ms.mulPose(Vector3f.YP.rotationDegrees(180F));
        }
        ItemStack cubeStack = new ItemStack(UCItems.RUBIKS_CUBE.get());
        IRenderTypeBuffer.Impl renderBuffer = minecraft.renderBuffers().crumblingBufferSource();
        itemRenderer.renderStatic(cubeStack, ItemCameraTransforms.TransformType.FIXED, 0xF000F0, OverlayTexture.NO_OVERLAY, ms, renderBuffer);
//        IBakedModel model = itemRenderer.getItemModelWithOverrides(cubeStack, null, null);
//        itemRenderer.renderModel(model, cubeStack, 15728880, OverlayTexture.NO_OVERLAY, ms, renderBuffer.getBuffer(RenderType.getCutout()));
        RenderSystem.disableBlend();
        ms.popPose();
        super.render(ms, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (this.hasRotated)
            return super.keyPressed(keyCode, scanCode, modifiers);

        int i = ((RubiksCubeItem)UCItems.RUBIKS_CUBE.get()).getRotation(getCube());
        switch (keyCode) {
            case GLFW.GLFW_KEY_W: rotateUp(i); break;
            case GLFW.GLFW_KEY_A: rotateLeft(i); break;
            case GLFW.GLFW_KEY_S: rotateDown(i); break;
            case GLFW.GLFW_KEY_D: rotateRight(i); break;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (button == 0 && !buttons.isEmpty()) {
            if (buttons.get(0).isHovered())
                this.clickTeleport();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private Vector3i getRotationVec() {

        int rot = ((RubiksCubeItem)UCItems.RUBIKS_CUBE.get()).getRotation(getCube());
        Direction facing = Direction.from3DDataValue(rot);
        return facing.getNormal();
    }

    private void rotateLeft(int i) {

        Direction facing = Direction.from3DDataValue(i);
        if (facing == Direction.UP || facing == Direction.DOWN) return;
        updateCube(facing.getClockWise().ordinal());
    }

    private void rotateRight(int i) {

        Direction facing = Direction.from3DDataValue(i);
        if (facing == Direction.UP || facing == Direction.DOWN) return;
        updateCube(facing.getCounterClockWise().ordinal());
    }

    private void rotateUp(int i) {

        Direction facing = Direction.from3DDataValue(i);
        if (facing != Direction.UP && facing != Direction.DOWN)
            updateCube(Direction.UP.ordinal());
        if (facing == Direction.DOWN) {
            updateCube(Direction.NORTH.ordinal());
        }
    }

    private void rotateDown(int i) {

        Direction facing = Direction.from3DDataValue(i);
        if (facing != Direction.DOWN && facing != Direction.UP)
            updateCube(Direction.DOWN.ordinal());
        if (facing == Direction.UP) {
            updateCube(Direction.NORTH.ordinal());
        }
    }

    private void updateCube(int rotation) {

        UCPacketHandler.INSTANCE.sendToServer(new PacketColorfulCube(rotation, false));
        this.hasRotated = true;
    }

    private ItemStack getCube() {

        ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
        if (stack.getItem() == UCItems.RUBIKS_CUBE.get())
            return stack;

        return ItemStack.EMPTY;
    }

    private void clickTeleport() {

        ItemStack cube = getCube();
        if (!cube.isEmpty()) {
            int rot = ((RubiksCubeItem)cube.getItem()).getRotation(cube);
            UCPacketHandler.INSTANCE.sendToServer(new PacketColorfulCube(rot, true));
            this.onClose();
        }
    }
}
