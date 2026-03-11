package simpletexteditor;

import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {

    public ToolBar(int iconWidth, int iconHeight, TextEditor textEditor) {
        setOrientation(JToolBar.HORIZONTAL);
        setFloatable(false);
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));
        setBackground(UIColors.TOOLBAR_DARK);

        JButton copy = createIconButton("/icons/copy.png", iconWidth, iconHeight, textEditor);
        JButton paste = createIconButton("/icons/paste.png", iconWidth, iconHeight, textEditor);
        JButton cut = createIconButton("/icons/cut.png", iconWidth, iconHeight, textEditor);
        JButton selectAll = createIconButton("/icons/selectall.png", iconWidth, iconHeight, textEditor);
        JButton undo = createIconButton("/icons/undo.png", iconWidth, iconHeight, textEditor);
        JButton redo = createIconButton("/icons/redo.png", iconWidth, iconHeight, textEditor);

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                setBackground(UIColors.TOOLBAR_DARK);
            } else {
                setBackground(UIColors.TOOLBAR_LIGHT);
            }
        });

        copy.addActionListener((e) -> {
            textEditor.copy();
            textEditor.requestFocusInWindow();
        });
        paste.addActionListener((e) -> {
            textEditor.paste();
            textEditor.requestFocusInWindow();
        });
        cut.addActionListener((e) -> {
            textEditor.cut();
            textEditor.requestFocusInWindow();
        });
        selectAll.addActionListener((e) -> {
            textEditor.selectAll();
            textEditor.requestFocusInWindow();
        });
        undo.addActionListener(e -> {
            textEditor.undo();
            textEditor.requestFocusInWindow();
        });
        redo.addActionListener(e -> {
            textEditor.redo();
            textEditor.requestFocusInWindow();
        });

        add(copy);
        add(paste);
        add(cut);
        add(selectAll);
        add(Box.createHorizontalStrut(20));
        add(undo);
        add(redo);
    }

    private JButton createIconButton(String path, int width, int height, TextEditor textEditor) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));

        JButton btn = new JButton(icon);
        btn.setIcon(IconUtils.recolor(icon, UIColors.FONT_DARK));

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                btn.setIcon(IconUtils.recolor(icon, UIColors.FONT_DARK));
            } else {
                btn.setIcon(IconUtils.recolor(icon, UIColors.FONT_LIGHT));
            }
        });

        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setBackground(null);
        btn.setBorder(BorderFactory.createEmptyBorder());

        return btn;
    }

}
