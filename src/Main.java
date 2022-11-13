import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        File games = new File("Games");
        games.mkdir();

        List<String> dirList4src = Arrays.asList("src", "main", "test");
        List<String> dirList4res = Arrays.asList("res", "drawables", "vectors", "icons");
        List<String> dirList4savegames = Arrays.asList(null, "savegames");
        List<String> dirList4temp = Arrays.asList(null, "temp");
        List<String> fileList4main = Arrays.asList("src/main/", "Main.java", "Utils.java");
        List<String> fileList4temp = Arrays.asList("temp/", "temp.txt");
        List[] dirLists = new List[]{dirList4src, dirList4res, dirList4savegames, dirList4temp};
        List[] fileLists = new List[]{fileList4main, fileList4temp};

        dirCreationFromArray(dirLists, result);
        fileCreationFromArray(fileLists, result);
        try (FileWriter writer = new FileWriter("Games/temp/temp.txt", true)) {
            writer.write(String.valueOf(result));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        GameProgress save1 = new GameProgress(100, 20, 1, 123);
        GameProgress save2 = new GameProgress(85, 15, 2, 43232);
        GameProgress save3 = new GameProgress(30, 5, 4, 43121);

        save1.saveGame(1);
        save2.saveGame(2);
        save3.saveGame(3);
        List<String> gameProgressList = Arrays.asList("save1.dat", "save2.dat", "save3.dat");
        zipFiles("Games/savegames/gameProgress.zip", gameProgressList);
        deleteFiles("Games/savegames/", gameProgressList);

    }

    public static void dirCreationFromArray(List[] dirLists, StringBuilder result) {
        for (int i = 0; i < dirLists.length; i++) {
            dirCreationFromList(dirLists[i], result);
        }
    }

    public static void dirCreationFromList(List<String> list, StringBuilder result) {
        if (list.get(0) != null) {
            for (int i = 1; i < list.size(); i++) {
                File f = new File("Games/" + list.get(0) + "/" + list.get(i));
                if (f.mkdirs()) {
                    result.append(DateTimeFormatter.ofPattern("H:mm:ss dd-MM-yy")
                            .format(LocalDateTime.now())).append(" создан каталог ").append(f).append("\n");
                }
            }
        } else {
            for (int i = 1; i < list.size(); i++) {
                File f = new File("Games/" + list.get(i));
                if (f.mkdirs()) {
                    result.append(DateTimeFormatter.ofPattern("H:mm:ss dd-MM-yy").format(LocalDateTime.now()))
                            .append(" создан каталог ").append(f).append("\n");
                }
            }
        }
    }

    public static void fileCreationFromArray(List[] fileLists, StringBuilder result) {
        for (int i = 0; i < fileLists.length; i++) {
            fileCreationFromList(fileLists[i], result);
        }
    }

    public static void fileCreationFromList(List<String> list, StringBuilder result) {
        for (int i = 1; i < list.size(); i++) {
            File file = new File("Games/" + list.get(0) + list.get(i));
            try {
                if (file.createNewFile())
                    result.append(DateTimeFormatter.ofPattern("H:mm:ss dd-MM-yy").format(LocalDateTime.now()))
                            .append(" создан файл ").append(file).append("\n");
            } catch (IOException ex) {
                result.append(DateTimeFormatter.ofPattern("H:mm:ss dd-MM-yy").format(LocalDateTime.now()))
                        .append(" ошибка создания файла ").append(file).append("\n");
            }
        }
    }

    public static void zipFiles(String path, List<String> data) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String s : data) {
                FileInputStream fis = new FileInputStream("Games/savegames/" + s);
                ZipEntry entry = new ZipEntry(s);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteFiles(String path, List<String> data) {
        for (String s : data) {
            File file = new File(path + s);
            file.delete();
        }
    }
}