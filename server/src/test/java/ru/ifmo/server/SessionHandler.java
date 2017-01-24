package ru.ifmo.server;

/**
 * Created by Gil on 22-Jan-17.
 */
public class SessionHandler implements Handler {

    @Override
    public void handle(Request request, Response response) throws Exception {
        response.setCookie(new Cookie("VKJ", "name"));
        response.setBody(request.getHeaders().get("Cookie").getBytes());
//        request.getSession();
    }
}
