package helper;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: dsantos
 * Date: 1/29/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class JavaDocHelper {

    private static final String JAVADOC_AUTHOR = "/**\n" +
            " * @author #author# \n" +
            " */ \n";

    public static boolean addAuthorTag(File file, String author) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        File out = File.createTempFile(file.getName(), String.valueOf(new Date().getTime()));
        PrintWriter writer = new PrintWriter(new FileWriter(out));
        boolean added = false;
        try {
            added = writeAuthorJavaDoc(reader, writer, author);
        } finally {
            reader.close();
            writer.close();
        }
        FileUtils.copyFile(out, file);
        return added;
    }

    private static boolean writeAuthorJavaDoc(BufferedReader reader, PrintWriter writer, String author) throws IOException {
        String line;
        boolean added = false;
        while ((line = reader.readLine()) != null) {
            if (isClassDeclarationLine(line)) {
                writer.print(getAuthorJavaDoc(author));
                added = true;
            }
            writer.println(line);
        }
        return added;
    }

    private static boolean isClassDeclarationLine(String line) {
        return line.startsWith("class ") || line.startsWith("public class ");
    }

    private static String getAuthorJavaDoc(String author) {
        return JAVADOC_AUTHOR.replace("#author#", author);
    }

    public static boolean containsClassJavaDoc(File javaFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(javaFile));
        try {
            return checkIfContainsJavaDoc(bufferedReader);
        } finally {
            bufferedReader.close();
        }
    }

    private static boolean checkIfContainsJavaDoc(BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (hasJavaDoc(line)) {
                return true;
            }
            if (isClassDeclarationLine(line)) {
                break;
            }
        }
        return false;
    }

    private static boolean hasJavaDoc(String line) {
        return line.contains("/**");
    }

}
