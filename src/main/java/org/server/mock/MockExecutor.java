package org.server.mock;

import org.reboot.server.client.*;
import org.reboot.server.route.*;

public class MockExecutor implements Controller {

    public HttpResponse process(HttpRequest request) {

        return new HttpResponse(200, "working successfully");
    }
}
