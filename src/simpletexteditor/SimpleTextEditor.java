package simpletexteditor;

import javax.swing.*;
import java.awt.*;

public class SimpleTextEditor extends JFrame {

    public SimpleTextEditor() {
        init();
    }

    private void init() {
        FileManager fileManager = new FileManager(this);
        TextEditor textEditor = new TextEditor(this, fileManager);

        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (fileManager.saveChanges(textEditor)) {
                    dispose();
                }
            }
        });

        MenuBar menuBar = new MenuBar(fileManager, textEditor);
        ToolBar toolbar = new ToolBar(25, 25, textEditor);
        JScrollPane textEditorScrollPane = new JScrollPane(textEditor);
        textEditorScrollPane.setBorder(BorderFactory.createEmptyBorder());
        StatusBar statusBar = new StatusBar(textEditor);

        setJMenuBar(menuBar);
        add(toolbar, BorderLayout.NORTH);
        add(textEditorScrollPane, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        fileManager.newFile(textEditor);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Look and feel not found: " + e);
        }

        new SimpleTextEditor().setVisible(true);
    }

}
