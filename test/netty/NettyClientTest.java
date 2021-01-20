package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NettyClientTest {

    @Test
    void encode() {

        EmbeddedChannel emChannel = new EmbeddedChannel();
        TankMsg tankMsg = new TankMsg(5, 8);

        emChannel.pipeline().addLast(new TankMsgEncoder());
        emChannel.writeOutbound(tankMsg);
        ByteBuf buf = emChannel.readOutbound();
        int i = buf.readInt();
        int j = buf.readInt();
        System.out.println(i+"--"+j);
    }

    @Test
    void decode() {
        EmbeddedChannel ec = new EmbeddedChannel();

        ec.pipeline().addLast(new TankMsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(8);

        ec.writeInbound(buf);

        TankMsg tm = ec.readInbound();
        System.out.println(tm.x);
        System.out.println(tm.y);
    }
}