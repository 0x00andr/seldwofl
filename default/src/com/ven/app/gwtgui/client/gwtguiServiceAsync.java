package com.ven.app.gwtgui.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface gwtguiServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void createTask(AsyncCallback<Void> async);
    void getValue(AsyncCallback<String> async);
}
