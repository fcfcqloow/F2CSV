package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utils {
    public  String resource(String path){
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return this.getClass().getResource(path).getPath().replaceFirst("/", "");
        }
        return this.getClass().getResource(path).getPath();
    }

    public static <T>void warn(T value) {
        yellow.println("[Warn]" + value);
    }
    public static <T>void err(T value) { black.ePrintln("[ERROR] " + value); }
    public static <T>void info(T value) {
        white.println("[INFO] " + value);
    }
    public static <T>void accept(T value) {
        green.println("[Accept] " + value);
    }

    public static ConsoleColor  white = new ConsoleColor("WHITE");
    public static ConsoleColor  green = new ConsoleColor("GREEN");
    public static ConsoleColor  black = new ConsoleColor("BLACK");
    public static ConsoleColor  red = new ConsoleColor("RED");
    public static ConsoleColor  yellow = new ConsoleColor("YELLOW");
    public static ConsoleColor  cyan = new ConsoleColor("CYAN");
    public static ConsoleColor  purple = new ConsoleColor("PURPLE");
    public static ConsoleColor  blue = new ConsoleColor("BLUE");
    public static class ConsoleColor{
        public static final String RESET = "\u001b[0m";
        public static final String BLACK = "\u001b[30m";
        public static final String RED = "\u001b[31m";
        public static final String GREEN = "\u001b[32m";
        public static final String YELLOW = "\u001b[33m";
        public static final String BLUE = "\u001b[34m";
        public static final String PURPLE = "\u001b[35m";
        public static final String CYAN = "\u001b[36m";
        public static final String WHITE = "\u001b[37m";
        private String useColor;
        ConsoleColor(String color)  {
            for(Field f : this.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if(f.getName().equals(color)){
                    try {
                        useColor = ((String) f.get(this));
                    } catch (IllegalArgumentException | IllegalAccessException e) {

                        e.printStackTrace();
                    }
                }
            }

        }
        public  <T>void pn(T t, T ... ts){

            System.out.println(useColor + t + RESET);
        }

        public  <T>void p(T t, T ... ts){
            System.out.print(useColor + t + RESET);
        }


        public  <T> void pTF( boolean judge, T trueStr, T falseStr){
            if(judge){
                println(useColor + trueStr + RESET);
            }else{
                println(useColor + falseStr + RESET);
            }

        }
        public  <T> void pTF( boolean judge, T trueStr){
            if(judge){
                println(useColor + trueStr + RESET);
            }

        }
        public  <T> void println (T t) {
            System.out.print(useColor);
            System.out.println(t + RESET);
        }
        public  void println() {
            System.out.println();
        }
        public  <T> void print(T t){
            System.out.print(useColor + t + RESET);
        }
        public  <T> void ePrint(T t){
            System.err.print(useColor + t + RESET);
        }
        public  <T> void ePrintln(T t){
            System.err.println(useColor + t + RESET);
        }
        public  <T> void printw(T t){
            System.out.print(useColor + t + "\r\n" + RESET);
        }
        public   <T> void arrayPrintln(T ... t){
            for(T temp : t){
                println(useColor + temp + RESET);
            }
        }
        public   <T> void arrayPrint(String  joins, T ... t){
            for(T temp : t){
                print(useColor + temp + joins + RESET);
            }
        }
        public   <T> void arrayPrint( T ... t){
            for(T temp : t){
                print(useColor + temp + RESET);
            }
        }

    }
    public static String copyFile(File in, File out, boolean reset){
        Path input = in.toPath();
        Path output = out.toPath();

        if(reset){
            try {
                Files.deleteIfExists (output);
                Files.copy(input, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    public static <T>boolean copyFile(File in, File out, T ... t){
        Path input = in.toPath();
        Path output = out.toPath();

        try {
            Files.deleteIfExists (output);
            Files.copy(input, output);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public static void copyFiles(File in, File outDir){
        File[] fileList = in.listFiles();
        File topOutFile = new File(outDir.getAbsolutePath().toString());
        if(!topOutFile.exists()){
            topOutFile.mkdirs();
        }
        if(fileList == null){
            copyFile(in, new File(outDir.getAbsolutePath().toString()  + "/" + in.getName()));
        }else{
            for(File file : fileList){

                if(file.isDirectory()){
                    File outFile = new File(outDir.getAbsolutePath().toString() + "/" + file.getName());
                    outFile.mkdirs();
                    copyFiles(file, outFile);

                }
                else {
                    copyFile(file , new File(outDir.getAbsolutePath().toString() + "/" +file.getName()));

                }
            }
        }
    }

}
