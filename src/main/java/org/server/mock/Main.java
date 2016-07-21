package org.server.mock;

import org.reboot.server.client.*;
import org.reboot.server.route.*;
import org.reboot.server.core.*;
import org.reboot.server.util.*;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String []args) {
        Main m = new Main();
        m.start(args);    
    }

    public void start(String []args) {

        List<HttpEntry> entries = crawlTheDirectory();

        if (entries.size() < 1) {
            throw new RuntimeException("No valid request/response pair(s) in the routes directory");
        }

        List<Route> routes = new ArrayList<Route>();
        for (HttpEntry entry: entries) {
            routes.add(new Route(entry.request.getResource(), entry.request.getMethod(), MockExecutor.class, entry.response));
        }

        //routes.add(new Route("/new", Method.GET, MockExecutor.class));
        Server server = new Server(9899, 5, 2000, 2000, routes);
        server.startServer();
    }

    public List<HttpEntry> crawlTheDirectory() {
        List<HttpEntry> entires = new ArrayList<HttpEntry>();

        try {
            File dir = new File("./routes");
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    System.out.println("Found Directory: " + file.toString());
                    HttpEntry entry = checkIsValidHttpEntry(file);
                    if (entry != null) {
                        entires.add(entry);
                    }
                }
            }
        } catch (Exception e) {
            Trace.traceError(e);
        }

        return entires;
    }

    public HttpEntry checkIsValidHttpEntry(File file) throws Exception {
        File requestFile = null;
        File responseFile = null;

        for (File subFile : file.listFiles()) {
            if ("request.raw".equals(subFile.getName())) {
                requestFile = subFile;
            }

            if ("response.raw".equals(subFile.getName())) {
                responseFile = subFile;
            }
        }

        if (requestFile == null) {
            System.out.println("Request file is missing for folder: " + file.getName());
            return null;
        }

        if (responseFile == null) {
            System.out.println("Response file is missing for folder: " + file.getName());
            return null;
        }

        System.out.println("All the necessary files are present");
        System.out.println("Reading request...");
        BufferedReader br = new BufferedReader(new FileReader(requestFile));
        HttpRequest request = new HttpRequest(br);
        br.close();

        System.out.println(request.toString());

        br = new BufferedReader(new FileReader(responseFile));
        HttpResponse response = HttpResponse.fromFile(br);
        br.close();

        System.out.println(response.toString());

        return new HttpEntry(request, response);
    }

    private class HttpEntry {
        public HttpRequest request;
        public HttpResponse response;

        public HttpEntry(HttpRequest request, HttpResponse response) {
            this.request = request;
            this.response = response;
        }
    }
}
