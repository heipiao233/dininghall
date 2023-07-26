package net.heipiao.dininghall.screen;

import org.lwjgl.glfw.GLFW;

import net.heipiao.dininghall.Utils;
import net.heipiao.dininghall.network.Network;
import net.heipiao.dininghall.network.UpdatePOSAmountC2SPacket;
import net.heipiao.dininghall.screen.handler.POSScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class POSScreen extends AbstractInventoryScreen<POSScreenHandler> {

    private int val = 0;

    public POSScreen(POSScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext var1, float var2, int var3, int var4) {
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
