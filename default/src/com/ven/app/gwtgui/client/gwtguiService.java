package com.ven.app.gwtgui.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("gwtguiService")
public interface gwtguiService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    void createTask();
    String getValue();
    /**
     * Utility/Convenience class.
     * Use gwtguiService.App.getInstance() to access static instance of gwtguiServiceAsync
     */
    public static class App {
        private static gwtguiServiceAsync ourInstance = GWT.create(gwtguiService.class);

        public static synchronized gwtguiServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
