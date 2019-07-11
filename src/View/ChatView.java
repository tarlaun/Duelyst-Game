package View;

import Model.ChatRoom;
import Model.Constants;
import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

public class ChatView {
    private AnchorPane pane;
    private JPanel panel;
    private String viewerName;
    private ImageView backGround;

    public ChatView(ChatRoom chatRoom, String viewerName) {
        this.viewerName = viewerName;
        panel = new JPanel();
        pane = new AnchorPane();
        for (int i = 0; i < chatRoom.getPMs().size(); i++) {
            panel.add(new PMView(i, viewerName, chatRoom.getPMs().get(i)).getTextArea());
        }
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);
        backGround = new ImageView(new Image("play/play_background.jpg"));
        backGround.setFitWidth(2 * Constants.CHAT_X_SHIFT);
        backGround.setFitHeight(2 * Constants.CHAT_Y_SHIFT);
        swingNode.resize(backGround.getFitWidth(), backGround.getFitHeight());
        swingNode.relocate(Constants.CENTRE_X - Constants.CHAT_X_SHIFT, Constants.CENTRE_Y - Constants.CHAT_Y_SHIFT - 50);
        pane.getChildren().addAll(backGround);
        if (chatRoom.getPMs().size() != 0) {
            pane.getChildren().addAll(swingNode);
        }
        pane.setPrefWidth(2 * Constants.CHAT_X_SHIFT);
        pane.setPrefWidth(2 * Constants.CHAT_Y_SHIFT);
        pane.relocate(Constants.CENTRE_X - Constants.CHAT_X_SHIFT, Constants.CENTRE_Y - Constants.CHAT_Y_SHIFT - 50);
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> swingNode.setContent(panel));
    }

    public AnchorPane getPane() {
        return pane;
    }
}
