package simpletexteditor;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileManager {

    private File currentFile;

    private final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

    private JFrame frame;

    private String savedContent = "";

    public FileManager(JFrame frame) {
        this.frame = frame;

        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(txtFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    public void newFile(TextEditor textEditor) {
        if (!saveChanges(textEditor)) {
            return;
        }

        currentFile = null;

        textEditor.setText("");
        savedContent = "";
        textEditor.updateTitle();
    }

    public void openFile(TextEditor textEditor) {
        try {
            int res = fileChooser.showOpenDialog(frame);
            switch (res) {
                case JFileChooser.APPROVE_OPTION -> {
                    if (!saveChanges(textEditor)) {
                        return;
                    }

                    File selectedFile = fileChooser.getSelectedFile();
                    Path filePath = selectedFile.toPath();
                    currentFile = selectedFile;

                    String content = Files.readString(filePath);
                    savedContent = content;
                    textEditor.loadContent(content);
                }
                case JFileChooser.CANCEL_OPTION -> {
                }

            }
        } catch (HeadlessException | IOException ex) {
            showError(ex);
        }
    }

    public void saveFile(TextEditor textEditor) {
        if (currentFile == null) {
            saveFileAs(textEditor);
            return;
        }

        try {
            Files.writeString(currentFile.toPath(), textEditor.getText());
            savedContent = textEditor.getText();
            textEditor.updateTitle();
        } catch (IOException ex) {
            showError(ex);
        }
    }

    public void saveFileAs(TextEditor textEditor) {
        if (currentFile == null && textEditor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "There is no content to save.",
                    "Empty File",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int res = fileChooser.showSaveDialog(frame);
            if (res == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("" + selectedFile.getName());
                if (selectedFile.exists()) {
                    int opt = JOptionPane.showConfirmDialog(
                            frame,
                            "The file \"" + selectedFile.getName() + "\" already exists.\nDo you want to replace it?",
                            "Confirm Overwrite",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );
                    if (opt == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                currentFile = selectedFile;
                Files.writeString(currentFile.toPath(), textEditor.getText());

                savedContent = textEditor.getText();
                textEditor.updateTitle();
            }
        } catch (HeadlessException | IOException ex) {
            showError(ex);
        }
    }

    public boolean saveChanges(TextEditor textEditor) {
        if (isFileSaved(textEditor)) {
            return true;
        }

        int opt = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to save changes?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION);

        switch (opt) {
            case JOptionPane.YES_OPTION -> saveFile(textEditor);
            case JOptionPane.NO_OPTION -> {
                return true;
            }
            case JOptionPane.CANCEL_OPTION -> {
                return false;
            }
        }
        return true;
    }

    public boolean isFileSaved(TextEditor textEditor) {
        if (currentFile == null) {
            return textEditor.getText().trim().isEmpty();
        }
        return savedContent.equals(textEditor.getText());
    }

    private void showError(Exception e) {
        JOptionPane.showMessageDialog(
                frame,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public File getCurrentFile() {
        return currentFile;
    }
}
