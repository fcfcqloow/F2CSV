package utils;

import lombok.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import static utils.StringUtils.strAppnend;

public class TextFileManager {
    private PrintWriter printWriter;
    private Scanner scanner;
    private String path;
    public TextFileManager(String path) {
        this.path = path.replaceAll("\n", System.getProperty("line.separator"));
        try {
            this.printWriter = new PrintWriter(new File(this.path));
            this.scanner = new Scanner(new File(this.path));
            this.scanner.useDelimiter(System.getProperty("line.separator"));
        }catch (Exception e) {}
    }
    public String readText() {
        String text = "";
        while (this.scanner.hasNext()){
            text += this.scanner.next();
        }
        return text;

    }
    public void printText(String value) {
        this.printWriter.print(value);
    }
    public void printlnText(String value) {
        this.printWriter.println(value);
    }
    public void close() {
        this.printWriter.flush();
        this.printWriter.close();
        this.scanner.close();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Data
    public static class CSVManager {
        private String body = "";
        public <T> void print(@NonNull T val){
            this.body = strAppnend(this.body, val.toString(), ",");
        }
        public <T> void println(@NonNull T val) {
            this.body = strAppnend(this.body, val.toString(), "\n");
        }
        public  void println() {
            this.body = strAppnend(this.body, "\n");
        }
        public void reset(){
            this.body = "";
        }
        public void save(@NonNull String path){
            @Cleanup TextFileManager tf = new TextFileManager(path);
            tf.printText(this.body);

        }
    }
}
