package com.ven.app.gwtgui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class gwtgui implements EntryPoint {
    private final Logger log = Logger.getLogger(gwtgui.class.getName());

    private static final int TIMER_REFRESH_INTERVAL = 1000;
    private Timer refreshTimer;


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Click me");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                gwtguiService.App.getInstance().createTask(new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Failure on createTask(): " + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(Void result) {
                        label.setText("A new task has been created... just wait...");
                    }
                });
            }
        });

        refreshTimer = new Timer() {
            @Override
            public void run() {
                pullServer(label);
            }
        };
        refreshTimer.scheduleRepeating(TIMER_REFRESH_INTERVAL);
        RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);
    }

    private void pullServer(final Label l) {
        gwtguiService.App.getInstance().getValue(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                log.log(Level.SEVERE, "Failure on getValue(): " + caught.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                if(result != null) {
                    l.setText(result);
                }
            }
        });
    }
}
