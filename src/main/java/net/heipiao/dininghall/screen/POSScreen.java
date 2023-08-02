package net.heipiao.dininghall.screen;

import org.lwjgl.glfw.GLFW;

import net.heipiao.dininghall.Utils;
import net.heipiao.dininghall.network.Network;
import net.heipiao.dininghall.network.UpdatePOSAmountC2SPacket;
import net.heipiao.dininghall.screen.handler.POSScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class POSScreen extends HandledScreen<POSScreenHandler> {

    private int val = 0;
    private static final Identifier TEXTURE = new Identifier("dininghall:textures/gui/pos.png");

    public POSScreen(POSScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext var1, float var2, int var3, int var4) {
        var1.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers) || this.handleKey(keyCode);
    }

    private boolean handleKey(int keyCode) {
        if (GLFW.GLFW_KEY_ENTER == keyCode) {
            Network.INSTANCE.sendToServer(new UpdatePOSAmountC2SPacket(this.handler.getPos(), val, this.handler.getWorld()));
        }
        if (GLFW.GLFW_KEY_BACKSPACE == keyCode) {
            val = val / 10;
        }
        var num = Utils.getNumKey(keyCode);
        if (num == -1) return false;
        val = val * 10 + num;
        return true;
    }

}
