package net.heipiao.dininghall;

import org.lwjgl.glfw.GLFW;

public enum Utils {
    ;

    public static int getNumKey(int scanCode) {
        if (GLFW.GLFW_KEY_0 <= scanCode && scanCode <= GLFW.GLFW_KEY_9) {
            return scanCode - 48;
        }
        return -1;
    }
}
