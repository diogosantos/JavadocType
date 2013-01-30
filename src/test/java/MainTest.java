import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: dsantos
 * Date: 1/29/13
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainTest {

    @Test
    public void testGetAllNonJavaDocClasses() throws IOException {
        Main main = new Main(new File(getClass().getResource("srcDir").getPath()));
        List<File> allNonJavaDocClasses = main.getAllNonJavaDocClasses();
        assertEquals(2, allNonJavaDocClasses.size());
        assertEquals("NonJavaDocClass.java", allNonJavaDocClasses.get(0).getName());
        assertEquals("WeirdJavaDocClass.java", allNonJavaDocClasses.get(1).getName());
    }

}
