package com.ven.app.gwtgui.server;

import com.google.appengine.api.modules.ModulesService;
import com.google.appengine.api.modules.ModulesServiceFactory;
import com.google.appengine.api.taskqueue.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ven.app.gwtgui.client.gwtguiService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class gwtguiServiceImpl extends RemoteServiceServlet implements gwtguiService {
    public static final String PULL_QUEUE_NAME = "pull-queue";
    public static final String BG_MODULE_NAME = "bgmod";
    public static final String BG_MODULE_PATH = "/worker";

    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    /* save empty task in default queue */
    public void createTask(){
        Queue queue = QueueFactory.getDefaultQueue();
        ModulesService modulesApi = ModulesServiceFactory.getModulesService();

        queue.add(TaskOptions.Builder.withUrl(BG_MODULE_PATH).header("Host",
                modulesApi.getVersionHostname(BG_MODULE_NAME, null)));
    }


    public String getValue(){
        String retVal = null;
        Queue q = QueueFactory.getQueue(PULL_QUEUE_NAME);
        List<TaskHandle> tasks = null;
        try {
            tasks = q.leaseTasks(1, TimeUnit.SECONDS, 1);
        }catch (TransientFailureException tfe){
            tfe.printStackTrace();
        }

        if(tasks != null && !tasks.isEmpty()) {
            TaskHandle task = tasks.get(0);
            if (task != null) {
                retVal = new String(task.getPayload());

                q.deleteTask(task);  // we MUST remove the processed task
            }
        }
        return retVal;
    }
}