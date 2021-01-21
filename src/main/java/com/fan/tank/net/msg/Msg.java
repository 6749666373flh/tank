package com.fan.tank.net.msg;

public abstract class Msg {

    public abstract byte[] toBytes();

    public abstract void parse(byte[] bytes);

    public abstract void handle();

    public abstract MsgType getMsgType();
}
