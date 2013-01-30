package helper;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dsantos
 * Date: 1/29/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaDocHelperTest extends TestCase {

    public void testContainsClassJavaDoc() throws Exception {
        File nonJavaDoc = new File(getClass().getResource("../srcDir/NonJavaDocClass.java").getPath());
        assertFalse(JavaDocHelper.containsClassJavaDoc(nonJavaDoc));

        File javaDoc = new File(getClass().getResource("../srcDir/JavaDocClass.java").getPath());
        assertTrue(JavaDocHelper.containsClassJavaDoc(javaDoc));

        File notClassJavaDoc = new File(getClass().getResource("../srcDir/WeirdJavaDocClass.java").getPath());
        assertFalse(JavaDocHelper.containsClassJavaDoc(notClassJavaDoc));
    }

    public void testAddAuthorTag() throws IOException {
        File nonJavaDoc = new File(getClass().getResource("../srcDir/NonJavaDocClass.java").getPath());
        assertFalse(JavaDocHelper.containsClassJavaDoc(nonJavaDoc));

        JavaDocHelper.addAuthorTag(nonJavaDoc, "dsantos");

        assertTrue(JavaDocHelper.containsClassJavaDoc(nonJavaDoc));
    }

}
