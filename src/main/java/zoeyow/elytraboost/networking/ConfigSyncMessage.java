package zoeyow.elytraboost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import zoeyow.elytraboost.Config;

//this message is used to sync config between server and client
//The logic is as follows: if server side set serverOverride to true, then client side is forced to sync
//otherwise client side can set ignoreServer to true to prevent syncing
public class ConfigSyncMessage implements IMessage {
    private float velocityToAddServer;
    private boolean serverOverrideServer;
    private boolean ignoreServerServer;
    private float velocityCapServer;
    private float accelerationProportionServer;
    private float decelerationProportionServer;
    private float sprintingFactorServer;
    private boolean applyExhaustionServer;
    private float exhaustionFactorServer;
    private int soundFactorServer;

    public ConfigSyncMessage() {
    }

    public ConfigSyncMessage(float velocityToAddServer, boolean serverOverrideServer, boolean ignoreServerServer,
                             float velocityCapServer, float accelerationProportionServer,
                             float decelerationProportionServer, float sprintingFactorServer,
                             boolean applyExhaustionServer, float exhaustionFactorServer, int soundFactorServer) {
        this.velocityToAddServer = velocityToAddServer;
        this.serverOverrideServer = serverOverrideServer;
        this.ignoreServerServer = ignoreServerServer;
        this.velocityCapServer = velocityCapServer;
        this.accelerationProportionServer = accelerationProportionServer;
        this.decelerationProportionServer = decelerationProportionServer;
        this.sprintingFactorServer = sprintingFactorServer;
        this.applyExhaustionServer = applyExhaustionServer;
        this.exhaustionFactorServer = exhaustionFactorServer;
	this.soundFactorServer = soundFactorServer;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        velocityToAddServer = buf.readFloat();
        serverOverrideServer = buf.readBoolean();
        ignoreServerServer = buf.readBoolean();
        velocityCapServer = buf.readFloat();
        accelerationProportionServer = buf.readFloat();
        decelerationProportionServer = buf.readFloat();
        sprintingFactorServer = buf.readFloat();
        applyExhaustionServer = buf.readBoolean();
        exhaustionFactorServer = buf.readFloat();
	soundFactorServer = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(velocityToAddServer);
        buf.writeBoolean(serverOverrideServer);
        buf.writeBoolean(ignoreServerServer);
        buf.writeFloat(velocityCapServer);
        buf.writeFloat(accelerationProportionServer);
        buf.writeFloat(decelerationProportionServer);
        buf.writeFloat(sprintingFactorServer);
        buf.writeBoolean(applyExhaustionServer);
        buf.writeFloat(exhaustionFactorServer);
	buf.writeInt(soundFactorServer);
    }

    public static class ConfigSyncMessageHandler implements IMessageHandler<ConfigSyncMessage, IMessage> {

        @Override
        public IMessage onMessage(ConfigSyncMessage message, MessageContext ctx) {
            if (message.serverOverrideServer || !Config.ignoreServer) {
                Config.syncClientConfigVariables(message.velocityToAddServer, message.serverOverrideServer,
                        message.ignoreServerServer, message.velocityCapServer, message.accelerationProportionServer,
                        message.decelerationProportionServer, message.sprintingFactorServer,
                        message.applyExhaustionServer, message.exhaustionFactorServer,message.soundFactorServer);
            }
            return null;
        }
    }
}
