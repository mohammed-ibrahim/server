package org.server.mock;

import org.reboot.server.client.*;
import org.reboot.server.route.*;

public class MockExecutor implements Controller {

    public HttpResponse process(HttpRequest request) {

        HttpResponse response = (HttpResponse)request.getRouteExtendedData();
        return response;
    }
}
