package command;

import collection.CollectionData;
import correspondency.ResponseCo;
import storage.Worker;

import javax.xml.ws.Response;
import java.io.FileOutputStream;

@PointCommand(name="save", description = "сохранить коллекцию в файл")
public class CommandSave extends Command {

    private String getStr() {
        String res = "";
        for (Worker it : CollectionData.collection.getCollection()) {
            String a = "", b = "", c = "", d = "", e = "", f = "", g = "", h = "", i = "", j = "", pos = "";
            a = it.getId().toString();
            if (it.getName() != null)
                b = it.getName();
            if (it.getCoordinates().getX() != null)
                c = it.getCoordinates().getX().toString();
            if (it.getCoordinates().getY() != null)
                d = it.getCoordinates().getY().toString();
            if (it.getCreationDate() != null)
                e = it.getCreationDate().toString();
            if (it.getStartDate() != null)
                f = it.getStartDate().toString();
            if (it.getStatus() != null)
                g = it.getStatus().name();
            if (it.getPerson().getEyeColor() != null)
                h = it.getPerson().getEyeColor().name();
            if (it.getPosition() != null)
                pos = it.getPosition().toString();
            if (it.getPerson().getHairColor() != null)
                i = it.getPerson().getHairColor().name();
            if (it.getPerson().getNationality() != null)
                j = it.getPerson().getNationality().name();

            res += a + "," + b + "," + c + "," +
                    d + "," + e + "," +
                    it.getSalary() + "," + f + "," + pos + "," + g + "," +
                    it.getPerson().getWeight() + "," + h + "," +
                    i + "," + j + "\n";
        }
        return res;
    }

    private static String path;

    public static void setPath(String name) {
        path = name;
    }

    @Override
    public void execute() {
        setResponse(new ResponseCo("No permission"));
    }

    public void save() {
        if (path == null)
            return;
        String data = "id,name,coordinates/x,coordinates/y,creationDate,salary," +
                "startDate,position,status,person/weight,person/eyeColor,person/hairColor,person/nationality\n";
        try {
            String str = this.getStr();
            FileOutputStream out = new FileOutputStream(path);
            byte[] strToBytes1 = data.getBytes();
            byte[] strToBytes2 = str.getBytes();
            out.write(strToBytes1);
            out.write(strToBytes2);
            out.close();
            System.out.println("Data saved to " + path);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Couldn't work with file");
        }
    }

}
