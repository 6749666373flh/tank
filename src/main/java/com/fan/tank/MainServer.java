package com.fan.tank;

import com.fan.tank.net.Server;

public class MainServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.serverStart();
    }
}
