package site.galaio;

import org.junit.Test;
import site.galaio.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by GalaIO on 2018/4/24.
 */
public class FileUtilTest {

    @Test
    public void testFileSelecteDlg() throws Exception {
        File file = FileUtil.userSelected("file select", ".txt");
        FileInputStream in = new FileInputStream(file);
        System.out.println("file contains byte size: " + in.read());
        in.close();
    }
}
