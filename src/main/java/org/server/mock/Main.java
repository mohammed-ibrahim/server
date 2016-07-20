package org.server.mock;

import org.reboot.server.client.*;
import org.reboot.server.route.*;
import org.reboot.server.core.*;

import java.util.*;

class Main {
    public static void main(String []args) {

        List<Route> routes = new ArrayList<Route>();
        routes.add(new Route("/new", Method.GET, MockExecutor.class));
        Server server = new Server(9899, 5, 2000, 2000, routes);
        server.startServer();
    }
}
