package View;

import Model.Constants;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PMView {
    private String name;
    private String message;
    private JTextArea textArea;

    public PMView(int index, boolean isMine, String name, String message) {
        this.name = name;
        this.message = message;
        Border round = new LineBorder(Color.BLACK, 3, true);
        textArea = new JTextArea(name + ":\n" + message);
        textArea.setFont(Font.getFont(Font.SERIF));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(round);
        if (isMine) {
            textArea.setLocation((int) (Constants.CENTRE_X - Constants.CHAT_X_SHIFT), (int) (Constants.CENTRE_Y -
                    Constants.CHAT_Y_SHIFT + index * textArea.getSize().getHeight()));
        } else {
            textArea.setLocation((int) (Constants.CENTRE_X + Constants.CHAT_X_SHIFT - textArea.getSize().getWidth()),
                    (int) (Constants.CENTRE_Y - Constants.CHAT_Y_SHIFT + index * textArea.getSize().getHeight()));
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
