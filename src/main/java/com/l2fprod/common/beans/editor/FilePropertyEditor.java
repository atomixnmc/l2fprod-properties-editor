/*
 * Copyright 2015 Matthew Aguirre
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.common.beans.editor;

import com.l2fprod.common.annotations.EditorRegistry;
import com.l2fprod.common.swing.ComponentFactory;
import com.l2fprod.common.swing.LookAndFeelTweaks;
import com.l2fprod.common.swing.PercentLayout;
import com.l2fprod.common.swing.UserPreferences;
import com.l2fprod.common.util.ResourceManager;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

/**
 * FilePropertyEditor. <br>
 *
 */
@EditorRegistry(type = File.class)
public class FilePropertyEditor extends AbstractPropertyEditor {

    protected JTextField textfield;
    private JButton button;
    //private JButton cancelButton;
    private boolean _treated_as_string = false;

    public FilePropertyEditor() {
        this(true);
    }

    public FilePropertyEditor(boolean asTableEditor) {
        editor = new JPanel(new PercentLayout(PercentLayout.HORIZONTAL, 0)) {
            @Override
            public void setEnabled(boolean enabled) {
                super.setEnabled(enabled);
                textfield.setEnabled(enabled);
                button.setEnabled(enabled);
//                cancelButton.setEnabled(enabled);
            }
        };
        ((JPanel) editor).add("*", textfield = new JTextField());
        ((JPanel) editor).add(button = ComponentFactory.Helper.getFactory()
                .createMiniButton());
        if (asTableEditor) {
            textfield.setBorder(LookAndFeelTweaks.EMPTY_BORDER);
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
//        ((JPanel) editor).add(cancelButton = ComponentFactory.Helper.getFactory()
//                .createMiniButton());
//        cancelButton.setText("X");
//        cancelButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selectNull();
//            }
//        });
        textfield.setTransferHandler(new FileTransferHandler());
    }

    class FileTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
            for (int i = 0, c = transferFlavors.length; i < c; i++) {
                if (transferFlavors[i].equals(DataFlavor.javaFileListFlavor)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean importData(JComponent comp, Transferable t) {
            try {
                List list = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                if (list.size() > 0) {
                    File oldFile = (File) getValue();
                    File newFile = (File) list.get(0);
                    String text = newFile.getAbsolutePath();
                    textfield.setText(text);
                    firePropertyChange(oldFile, newFile);
                }
            } catch (UnsupportedFlavorException | IOException e) {
                Logger.getLogger(FilePropertyEditor.class.getName()).log(Level.SEVERE, null, e);
            }
            return true;
        }
    }

    @Override
    public Object getValue() {
        if (_treated_as_string) {
            return textfield.getText().trim();
        } else {
            if ("".equals(textfield.getText().trim())) {
                return null;
            } else {
                return new File(textfield.getText());
            }
        }
    }

    @Override
    public void setValue(Object value) {
        if (value != null) {
            if (File.class.isAssignableFrom(value.getClass())) {
                textfield.setText(((File) value).getAbsolutePath());
            } else if (String.class.isAssignableFrom(value.getClass())) {
                _treated_as_string = true;
                textfield.setText((String) value);
            }
        }
    }

    protected void selectFile() {
        ResourceManager rm = ResourceManager.all(FilePropertyEditor.class);

        JFileChooser chooser = UserPreferences.getDefaultFileChooser();
        chooser.setDialogTitle(rm.getString("FilePropertyEditor.dialogTitle"));
        chooser.setApproveButtonText(
                rm.getString("FilePropertyEditor.approveButtonText"));
        chooser.setApproveButtonMnemonic(
                rm.getChar("FilePropertyEditor.approveButtonMnemonic"));
        customizeFileChooser(chooser);

        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(editor)) {
            Object o = getValue();
            File oldFile = null;
            if (String.class.isAssignableFrom(o.getClass())) {
                oldFile = new File((String) o);
            } else if (File.class.isAssignableFrom(o.getClass())) {
                oldFile = (File) o;
            }
            File newFile = chooser.getSelectedFile();
            String text = newFile.getAbsolutePath();
            textfield.setText(text);
            if (_treated_as_string) {
                firePropertyChange(oldFile.getAbsolutePath(), newFile.getAbsolutePath());
            } else {
                firePropertyChange(oldFile, newFile);
            }
        }
    }

    /**
     * Placeholder for subclasses to customize the JFileChooser shown to select
     * a file.
     *
     * @param chooser
     */
    protected void customizeFileChooser(JFileChooser chooser) {
    }

    protected void selectNull() {
        Object oldFile = getValue();
        //textfield.setText("");
        firePropertyChange(oldFile, oldFile);
    }

}
