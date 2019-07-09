package View;

import Model.ChatRoom;
import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class ChatView {
    private AnchorPane pane;
    private JPanel panel;
    private String viewerName;

    public ChatView(ChatRoom chatRoom, String viewerName) {
        this.viewerName = viewerName;
        panel = new JPanel();
        pane = new AnchorPane();
        for (int i = 0; i < chatRoom.getPMs().size(); i++) {
            panel.add(new PMView(i, viewerName, chatRoom.getPMs().get(i)).getTextArea());
        }
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        pane.getChildren().add(swingNode);
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> swingNode.setContent(panel));
    }

    public AnchorPane getPane() {
        return pane;
    }
}
