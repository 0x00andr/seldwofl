package com.ven.app;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by venin on 13/03/16.
 */
public class WorkServlet extends HttpServlet {
    private static final String PULL_QUEUE_NAME = "pull-queue";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Queue q = QueueFactory.getQueue(PULL_QUEUE_NAME);
        q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL)
                .payload(WorkServlet.class.getName() + " was requested at: " + new Date().toString()));
    }
}
