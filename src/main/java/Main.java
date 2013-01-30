import helper.JavaDocHelper;
import helper.SVN;
import org.apache.commons.io.FileUtils;
import org.tmatesoft.svn.core.SVNException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dsantos
 * Date: 1/29/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    private File sourceDirectory;
    private static final String[] JAVA_EXTENSIONS = new String[]{"java"};
    private static final boolean RECURSIVE_YES = true;

    public Main(File sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public static void main(String[] arguments) throws IOException, SVNException {
        File sourceDirectory = new File(arguments[0]);
        Main main = new Main(sourceDirectory);
        SVN svn = new SVN();
        List<File> nonJavaDocClasses = main.getAllNonJavaDocClasses();
        int changedFiles = 0;
        for (File file : nonJavaDocClasses) {
            String author = svn.getAuthor(file);
            System.out.println(file.getName() + " has been created by " + author);
            if(JavaDocHelper.addAuthorTag(file, author)) {
                changedFiles++;
            }
        }
        System.out.println("==============================");
        System.out.println(changedFiles + " classes have now the JavadocType.");
        System.out.println("==============================");
    }

    public List<File> getAllNonJavaDocClasses() throws IOException {
        Collection<File> javaFiles = FileUtils.listFiles(sourceDirectory, JAVA_EXTENSIONS, RECURSIVE_YES);
        List<File> nonJavaDocClasses = new ArrayList<File>();
        for (File javaFile : javaFiles) {
            if (!JavaDocHelper.containsClassJavaDoc(javaFile)) {
                nonJavaDocClasses.add(javaFile);
            }
        }
        return nonJavaDocClasses;
    }
}
