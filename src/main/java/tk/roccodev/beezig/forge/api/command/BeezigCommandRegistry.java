package tk.roccodev.beezig.forge.api.command;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import tk.roccodev.beezig.forge.Log;
import tk.roccodev.beezig.forge.tabcompletion.BeezigCommandExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BeezigCommandRegistry {

    public static void register(Object obj) {
        try {
            Class cl = obj.getClass();
            String name = (String) cl.getMethod("getName").invoke(obj);
            String[] aliases = (String[]) cl.getMethod("getAliases").invoke(obj);
            Method exec = cl.getMethod("execute", String[].class);
            Method tabCompletion = tabCompletion(cl);

            List<String> aliasesList = Arrays.stream(aliases)
                    .map(s -> s.replace("/", "")).collect(Collectors.toList());

            String firstAlias = aliasesList.size() < 1 ? name : aliasesList.get(0);

            ClientCommandHandler.instance.registerCommand(new BeezigCommandExecutor() {
                @Override
                public String getCommandName() {
                    return firstAlias;
                }

                public String getInternalName() {
                    return name;
                }

                @Override
                public String getCommandUsage(ICommandSender sender) {
                    return "";
                }


                @Override
                public void processCommand(ICommandSender sender, String[] args) throws CommandException {
                    try {
                        if(!(boolean)exec.invoke(obj,(Object) args)) {
                            Minecraft.getMinecraft().thePlayer
                                    .sendChatMessage("/" + firstAlias + " " + String.join(" ", args));
                        }
                    } catch (Exception e) {
                        sender.addChatMessage(new ChatComponentText(Log.error + "An unexpected error occurred " +
                                "§cwhile attempting to §cperform the command."));
                        e.printStackTrace();
                    }
                }

                @Override
                public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
                    if(!(sender instanceof EntityPlayer)) return super.addTabCompletionOptions(sender, args, pos);
                    try {
                        return tabCompletion == null
                                ? super.addTabCompletionOptions(sender, args, pos)
                                : (List<String>) tabCompletion.invoke(obj, ((EntityPlayer)sender).getGameProfile(), args);
                    } catch (Exception ignored) {}
                    return super.addTabCompletionOptions(sender, args, pos);
                }

                @Override
                public List<String> getCommandAliases() {
                    return aliasesList;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Method tabCompletion(Class cl) {
        try {
            return cl.getMethod("addTabCompletionOptions",
                    GameProfile.class, String[].class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}