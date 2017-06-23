package de.uni_potsdam.hpi.asg.breezegui;

/*
 * Copyright (C) 2017 Norman Kluge
 * 
 * This file is part of ASGbreezeGui.
 * 
 * ASGbreezeGui is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ASGbreezeGui is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ASGbreezeGui.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BreezeGuiRunMain {

    public static void main(String[] args) {
        int status = main2(args);
        System.exit(status);
    }

    public static int main2(String[] args) {
        int status = 0;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
            return 1;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Breeze files", "breeze");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String[] args2 = new String[]{selectedFile.getAbsolutePath()};
            status = BreezeGuiMain.main2(args2);
        } else {
            status = 1;
        }

        return status;
    }
}
