package simpletexteditor;

import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {

    public MenuBar(FileManager fileManager, TextEditor textEditor) {
        setBackground(UIColors.MENUBAR_DARK);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        setLayout(new FlowLayout(FlowLayout.LEFT, 4, 0));

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                setBackground(UIColors.MENUBAR_DARK);
            } else {
                setBackground(UIColors.MENUBAR_LIGHT);
            }
        });

        add(fileMenu(fileManager, textEditor));
        add(editMenu(textEditor));
        add(toolsMenu(textEditor));
    }

    private JMenu fileMenu(FileManager fileManager, TextEditor textEditor) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(UIColors.FONT_DARK);

        JMenuItem newFile = new JMenuItem("New File");
        JMenuItem openFile = new JMenuItem("Open File");
        JMenuItem saveFile = new JMenuItem("Save File");
        JMenuItem saveFileAs = new JMenuItem("Save File As");

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                fileMenu.setForeground(UIColors.FONT_DARK);
            } else {
                fileMenu.setForeground(UIColors.FONT_LIGHT);
            }
        });

        newFile.addActionListener((e) -> {
            fileManager.newFile(textEditor);
        });
        openFile.addActionListener((e) -> {
            fileManager.openFile(textEditor);
        });
        saveFile.addActionListener((e) -> {
            fileManager.saveFile(textEditor);
        });
        saveFileAs.addActionListener((e) -> {
            fileManager.saveFileAs(textEditor);
        });

        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveFileAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveFileAs);

        return fileMenu;
    }

    private JMenu editMenu(TextEditor textEditor) {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(UIColors.FONT_DARK);

        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem selectAll = new JMenuItem("Select All");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                editMenu.setForeground(UIColors.FONT_DARK);
            } else {
                editMenu.setForeground(UIColors.FONT_LIGHT);
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
        undo.addActionListener((e) -> {
            textEditor.undo();
            textEditor.requestFocusInWindow();
        });
        redo.addActionListener((e) -> {
            textEditor.redo();
            textEditor.requestFocusInWindow();
        });

        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));

        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(cut);
        editMenu.add(selectAll);
        editMenu.add(undo);
        editMenu.add(redo);

        return editMenu;
    }

    private JMenu toolsMenu(TextEditor textEditor) {
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setForeground(UIColors.FONT_DARK);

        JMenuItem toggleTheme = new JMenuItem("Toggle Theme");

        textEditor.addThemeChangedListener(() -> {
            if (textEditor.isDarkTheme()) {
                toolsMenu.setForeground(UIColors.FONT_DARK);
            } else {
                toolsMenu.setForeground(UIColors.FONT_LIGHT);
            }
        });

        toggleTheme.addActionListener((e) -> {
            textEditor.toggleTheme();
        });

        toggleTheme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        toolsMenu.add(toggleTheme);

        return toolsMenu;
    }

}
