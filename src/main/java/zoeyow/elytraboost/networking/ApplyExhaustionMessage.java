package zoeyow.elytraboost.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import zoeyow.elytraboost.Config;

public class ApplyExhaustionMessage implements IMessage {
    private float exhaustion;
    private int flaptime;

    public ApplyExhaustionMessage() {
    }

    public ApplyExhaustionMessage(float exhaustion) {
        this.exhaustion = exhaustion;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        exhaustion = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(exhaustion);
    }

    public static class ApplyExhaustionMessageHandler implements IMessageHandler<ApplyExhaustionMessage, IMessage> {

        @Override
        public IMessage onMessage(ApplyExhaustionMessage message, MessageContext ctx) {
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;

            if (!serverPlayer.isCreative()) {
                serverPlayer.getServerWorld().addScheduledTask(() ->  {
                    serverPlayer.addExhaustion(message.exhaustion);
		    if (serverPlayer.getEntityWorld().getTotalWorldTime() % (Config.soundFactor/(Math.round(message.exhaustion*100)+1)) == 0L) {
			    serverPlayer.getEntityWorld().playSound(null,serverPlayer.getPosition(),SoundEvents.ENTITY_ENDERDRAGON_FLAP,SoundCategory.PLAYERS,0.5f,1.5f);
			};
                });
                //test
                //serverPlayer.sendMessage(new TextComponentString("add exhaustion" + message.exhaustion));
            }
            return null;
        }
    }
}
