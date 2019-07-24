package utils;

import java.io.File;
import static java.lang.System.*;
import static utils.StringUtils.*;
public class FileManager {
    public static final String BASE_PATH = strAppnend(getProperty("user.home"), "/.dod/");
    public static final File BASE_FILE_PATH  = new File(BASE_PATH);
    public static void init(boolean jar) {
        if (jar && !FileManager.BASE_FILE_PATH.exists()) {
            Utils.info("Create Files");
            Utils.info(new File("./resources").getAbsolutePath());
            Utils.copyFiles(new File("./resources"), FileManager.BASE_FILE_PATH);
        } else if (!FileManager.BASE_FILE_PATH.exists()) {
            Utils.info("Create Files");
            Utils.copyFiles(new File(new Utils().resource("/opencv")), FileManager.BASE_FILE_PATH);
        }
    }
    public static File[] dirList(String name) {
        File dir= new File(strAppnend(FileManager.BASE_PATH, name));
        return dir.listFiles();
    }
}
