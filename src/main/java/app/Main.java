package app;

import face.LBFWrapper;
import face.interfaces.FacemarkWrapper;
import lombok.SneakyThrows;
import lombok.val;
import utils.FileManager;
import utils.TextFileManager;
import utils.Utils;

import static utils.StringUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        FileManager.init(true);
        FacemarkWrapper fw = new LBFWrapper();
        val csv = new TextFileManager.CSVManager();
        val files = FileManager.dirList("images");

        Arrays.stream(files).forEach(file -> {
            fw.setImage(file.getAbsolutePath());
            fw.fit();
            fw.getList().forEach(point -> {
                csv.print(point.x);
                csv.print(point.y);
                csv.println();
            });
            try {
                File saveFile = new File(
                        strAppnend(
                                FileManager.BASE_PATH, "csv/",
                                file.getName().split("\\.")[0], ".csv"
                        )
                );
                saveFile.createNewFile();
                csv.save(saveFile.getAbsolutePath());
            } catch (IOException e) {
                Utils.warn("cant create file");
            } catch (ArrayIndexOutOfBoundsException arrE) {
                Utils.warn(arrE);
            }
            csv.reset();
        });
    }
}
