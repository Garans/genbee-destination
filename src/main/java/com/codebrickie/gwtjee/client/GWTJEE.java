package com.codebrickie.gwtjee.client;

import com.codebrickie.gwtjee.domain.Greeting;
import com.codebrickie.gwtjee.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GWTJEE implements EntryPoint {
    private static final String SERVER_ERROR = """
            An error occurred while attempting to contact the server. Please check your network connection and try again.
            """;

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    public void onModuleLoad() {
        var sendButton = new Button("Send");
        var nameField = new TextBox();
        nameField.setText("GWT User");
        var errorLabel = new Label();

        sendButton.addStyleName("sendButton");

        RootPanel.get("nameFieldContainer").add(nameField);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        nameField.setFocus(true);
        nameField.selectAll();

        var dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        var closeButton = new Button("Close");
        closeButton.getElement().setId("closeButton");
        var textToServerLabel = new Label();
        var serverResponseLabel = new HTML();
        var dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        closeButton.addClickHandler(event -> {
            dialogBox.hide();
            sendButton.setEnabled(true);
            sendButton.setFocus(true);
        });

        class MyHandler implements ClickHandler, KeyUpHandler {
            public void onClick(ClickEvent event) {
                sendNameToServer();
            }

            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    sendNameToServer();
                }
            }

            private void sendNameToServer() {
                errorLabel.setText("");
                var textToServer = nameField.getText();
                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText("Please enter at least four characters");
                    return;
                }

                sendButton.setEnabled(false);
                textToServerLabel.setText(textToServer);
                serverResponseLabel.setText("");
                greetingService.greetServer(textToServer, new AsyncCallback<>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(Greeting result) {
                        dialogBox.setText("Remote Procedure Call");
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(result.getGreetingText());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }

        var handler = new MyHandler();
        sendButton.addClickHandler(handler);
        nameField.addKeyUpHandler(handler);
    }
}