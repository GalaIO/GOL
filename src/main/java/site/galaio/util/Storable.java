package site.galaio.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by GalaIO on 2018/4/24.
 */
public interface Storable {

    void load(InputStream in) throws IOException;

    void flush(OutputStream out) throws IOException;

}
