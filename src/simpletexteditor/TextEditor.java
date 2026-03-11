package simpletexteditor;

import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.*;

public class TextEditor extends JTextArea {

    private JFrame frame;
    private FileManager fileManager;
    private UndoManager undoManager = new UndoManager();
    private ArrayList<Runnable> themeChangedListeners = new ArrayList<>();
    private boolean dark = true;

    public TextEditor(JFrame frame, FileManager fileManager) {
        this.frame = frame;
        this.fileManager = fileManager;

        setMargin(new Insets(10, 10, 10, 10));
        setLineWrap(true);
        setWrapStyleWord(true);
        setFont(new Font("Monospaced", Font.PLAIN, 20));
        setBackground(UIColors.TEXTEDITOR_DARK);
        setForeground(UIColors.FONT_DARK);
        setCaretColor(UIColors.FONT_DARK);

        addThemeChangedListener(() -> {
            if (isDarkTheme()) {
                setBackground(UIColors.TEXTEDITOR_DARK);
                setForeground(UIColors.FONT_DARK);
                setCaretColor(UIColors.FONT_DARK);
            } else {
                setBackground(UIColors.TEXTEDITOR_LIGHT);
                setForeground(UIColors.FONT_LIGHT);
                setCaretColor(UIColors.FONT_LIGHT);
            }
        });

        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onEditorChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onEditorChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onEditorChanged();
            }
        };

        getDocument().addDocumentListener(listener);
        addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                Font font = getFont();
                int size = font.getSize();

                if (e.getWheelRotation() < 0) {
                    size = Math.min(40, size + 1);
                } else {
                    size = Math.max(10, size - 1);
                }
                setFont(font.deriveFont((float) size));
                e.consume();
            }
        });

        getDocument().addUndoableEditListener(e -> {
            undoManager.addEdit(e.getEdit());
        });

        getInputMap().put(KeyStroke.getKeyStroke("control Z"), "undo");
        getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                undo();
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("control Y"), "redo");
        getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                redo();
            }
        });
    }

    public void onEditorChanged() {
        updateTitle();
    }

    public void updateTitle() {
        String name = (fileManager.getCurrentFile() == null) ? "untitled.txt" : fileManager.getCurrentFile().getName();
        frame.setTitle((!fileManager.isFileSaved(this) ? "● " : "") + name + " - SimpleEditor");
    }

    public void loadContent(String content) {
        setText(content);

        undoManager.discardAllEdits();

        updateTitle();
    }

    public void undo() {
        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    public void redo() {
        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    public void toggleTheme() {
        dark = !dark;
        fireThemeChanged();
    }

    public void addThemeChangedListener(Runnable listener) {
        themeChangedListeners.add(listener);
    }

    private void fireThemeChanged() {
        themeChangedListeners.forEach(Runnable::run);
    }

    public boolean isDarkTheme() {
        return dark;
    }
}
