package site.galaio.util;

import site.galaio.util.exception.UserAssertException;

import java.util.zip.DataFormatException;

/**
 * Created by tianyi on 2018/4/22.
 * a assert simple util.
 */
public class AssertUtil {
    public static void assertTrue(boolean statement) throws UserAssertException {
        if (!statement) {
            throw new UserAssertException();
        }
    }

    public static void assertTrue(boolean statement, String message) throws UserAssertException {
        if (!statement) {
            throw new UserAssertException(message);
        }
    }

    public static void assertTrue(boolean statement, Exception ex) throws Exception {
        if (!statement) {
            throw ex;
        }
    }
}
