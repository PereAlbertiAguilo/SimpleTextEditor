package simpletexteditor;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.BadLocationException;

public class StatusBar extends JPanel {

    private JLabel position = new JLabel("Ln 1, Col 1");

    public StatusBar(TextEditor textEditor) {

        setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));
        setBackground(UIColors.MENUBAR_DARK);
        position.setForeground(UIColors.FONT_DARK);

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                setBackground(UIColors.MENUBAR_DARK);
                position.setForeground(UIColors.FONT_DARK);
            } else {
                setBackground(UIColors.MENUBAR_LIGHT);
                position.setForeground(UIColors.FONT_LIGHT);
            }
        });

        add(position, BorderLayout.WEST);

        textEditor.addCaretListener(e -> updatePosition(textEditor));
    }

    private void updatePosition(TextEditor textEditor) {
        int caret = textEditor.getCaretPosition();

        try {
            int line = textEditor.getLineOfOffset(caret);
            int col = caret - textEditor.getLineStartOffset(line);

            position.setText("Ln " + (line + 1) + ", Col " + (col + 1));

        } catch (BadLocationException ignored) {
        }
    }
}
