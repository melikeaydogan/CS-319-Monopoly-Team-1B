package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import network.ChatMessage;
import network.MonopolyClient;

public class ChatController {
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    MonopolyClient monopolyClient;

    @FXML
    protected void handleSendButton(ActionEvent e) {
        monopolyClient.sendObject(new ChatMessage(textField.getText()));
        textField.setText("");
        textArea.setText(monopolyClient.getChatLog().getMessage());
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public MonopolyClient getMonopolyClient() {
        return monopolyClient;
    }

    public void setMonopolyClient(MonopolyClient monopolyClient) {
        this.monopolyClient = monopolyClient;
    }
}
