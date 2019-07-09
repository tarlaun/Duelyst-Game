package View;

import Model.Constants;
import Model.PM;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PMView {
    private PM pm;
    private JTextArea textArea;

    public PMView(int index, String viewerName, PM pm) {
        this.pm = pm;
        Border round = new LineBorder(Color.BLACK, 3, true);
        textArea = new JTextArea(pm.getSender() + ":\n" + pm.getMessage());
        textArea.setFont(Font.getFont(Font.SERIF));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(round);
        if (viewerName.equals(pm.getSender())) {
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
