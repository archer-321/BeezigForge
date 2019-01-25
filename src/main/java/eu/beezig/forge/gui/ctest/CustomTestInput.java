package eu.beezig.forge.gui.ctest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

public class CustomTestInput extends GuiScreen {

    private GuiTextField input;
    private CustomTestGui parent;

    CustomTestInput(CustomTestGui parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        input = new GuiTextField(1, fontRendererObj, width / 2 - 150, height / 6 + 80, 300, 20);
        buttonList.add(new GuiButton(2, width / 2 - 152, height / 6 + 140, 150, 20, "Done"));
        buttonList.add(new GuiButton(3, width / 2 + 2, height / 6 + 140, 150, 20, "Cancel"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        input.drawTextBox();
        drawCenteredString(fontRendererObj, "Add a custom test message. Use {p} for the player's name.",
                width / 2, height / 6, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        input.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        input.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 2 /* Done */:
                String text = input.getText();
                Minecraft.getMinecraft().displayGuiScreen(parent);
                if (!text.isEmpty())
                    parent.getList().add(text);
                break;
            case 3 /* Cancel */:
                Minecraft.getMinecraft().displayGuiScreen(parent);
                break;
        }
        super.actionPerformed(button);
    }
}