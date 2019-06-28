package View;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.concurrent.Callable;

public class AlertMessage {
    private Alert alert;
    private AlertType type;
    private String context;
    private ButtonType[] buttons;

    public AlertMessage(String context, AlertType type, String... options) {
        alert = new Alert(type);
        this.context = context;
        alert.setContentText(context);
        switch (type) {
            case ERROR:
                alert.setHeaderText("ERROR!");
                break;
            case INFORMATION:
                alert.setHeaderText("SUCCESSFUL!");
                break;
        }
        buttons = new ButtonType[options.length];
        for (int i = 0; i < options.length; i++) {
            buttons[i] = new ButtonType(options[i]);
        }
        alert.getButtonTypes().setAll(buttons);
    }

    public Optional<ButtonType> getResult() {
        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}
