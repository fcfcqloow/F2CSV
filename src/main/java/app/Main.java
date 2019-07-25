package app;

import face.LBFWrapper;
import face.Point;
import face.Vector;
import face.interfaces.FacemarkWrapper;
import lombok.val;
import utils.FileManager;
import utils.TextFileManager.CSVManager;
import utils.Utils;

import static utils.StringUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        FileManager.init(true);
        FacemarkWrapper fw = new LBFWrapper();
        CSVManager
                csv1 = new CSVManager(),
                csv2 = new CSVManager();
        val files = FileManager.dirList("images");

        Arrays.stream(files).forEach(file -> {
            fw.setImage(file.getAbsolutePath());
            fw.fit();
            Utils.accept(file.getAbsolutePath());
            Point [] list = fw.getList().toArray(new Point[fw.getList().size()]);
            for (int i = 0; i < list.length; i++) {
                Point p1 = list[i];
                csv1.print(p1.x, p1.y);
                csv1.println();
                for (int j = i + 1; j < list.length; j++) {
                    Point p2 = list[j];
                    Vector v1 = new Vector(p1, p2);
                    csv2.print(i + 1, j + 1, v1.getAx(), v1.getAy());
                    csv2.println();
                }
            }
            try {
                File saveFile1 = new File(
                        strAppnend(
                                FileManager.BASE_PATH, "csv/",
                                file.getName().split("\\.")[0], ".csv"
                        )
                );
                File saveFile2 = new File(
                        strAppnend(
                                FileManager.BASE_PATH, "csv/",
                                file.getName().split("\\.")[0], ".Vector.csv"
                        )
                );

                saveFile1.createNewFile();
                csv1.save(saveFile1.getAbsolutePath());
                csv2.save(saveFile2.getAbsolutePath());
            } catch (IOException e) {
                Utils.warn("cant create file");
            } catch (ArrayIndexOutOfBoundsException arrE) {
                Utils.warn(arrE);
            }
        });
    }
}
