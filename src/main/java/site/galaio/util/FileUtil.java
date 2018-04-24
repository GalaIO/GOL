package site.galaio.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by GalaIO on 2018/4/24.
 */
public class FileUtil {
    /**
     * Throw up a file chooser and return the file that user selected.
     * @param dlgTitle
     * @param externFiletype
     * @return
     * @throws FileNotFoundException
     *
     * <PRE>
     *     FileInputStream in = new FileInputStream(FileUtil.userSelected("select dlg", ".json");
     * </PRE>
     *
     */
    public static File userSelected(String dlgTitle, String externFiletype) throws FileNotFoundException {
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY );
        fileChooser.setDialogTitle(dlgTitle);
        fileChooser.setFileFilter(new FileNameExtensionFilter(externFiletype, externFiletype));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        throw new FileNotFoundException("No file selected by user.");
    }
}
