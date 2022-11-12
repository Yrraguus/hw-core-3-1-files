import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

    public void saveGame(int i) throws FileNotFoundException {
        try (FileOutputStream fos = new FileOutputStream("Games/savegames/save" + i + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}

