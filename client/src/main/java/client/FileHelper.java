package client;

import storage.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {

    /**
     * Adds elements to the collection from csv file
     * @param filename the name of read csv file
     */
    /*public static ArrayList<Worker> addFromCsvFile(String filename) {
        ArrayList<Worker> res = new ArrayList<>();
        try {
            System.out.println("Reading " + filename);
            File file = new File(filename);
            Scanner fileInp = new Scanner(file);
            int i = 0;
            while (fileInp.hasNext()) {
                i++;
                String[] line = fileInp.nextLine().split(",", -1);
                if (i == 1)
                    continue;
                for (int j = 0; j < line.length; j++) {
                    if (line[j].isEmpty())
                        line[j] = null;
                }
                Long id;
                if (line[0] != null)
                    id = Long.parseLong(line[0]);
                else
                    id = null;
                String name;
                if (line[1] != null)
                    name = line[1];
                else
                    name = null;
                Coordinates coords;
                if (line[2] != null && line[3] != null)
                    coords = new Coordinates(Float.parseFloat(line[2]), Integer.parseInt(line[3]));
                else
                    coords = null;
                LocalDate date;
                try {
                    if (line[4] != null)
                        date = LocalDate.parse(line[4]);
                    else
                        date = null;
                }
                catch (Exception ex) {
                    date = LocalDate.now();
                }
                Long salary;
                if (line[5] != null)
                    salary = Long.parseLong(line[5]);
                else
                    salary = null;
                LocalDate startDate;
                if (line[6] != null)
                    startDate = LocalDate.parse(line[6]);
                else
                    startDate = null;
                Position pos;
                if (line[7] != null)
                    pos = Position.valueOf(line[7]);
                else
                    pos = null;
                Status st;
                if (line[8] != null)
                    st = Status.valueOf(line[8]);
                else
                    st = null;
                Integer q;
                Color r, e;
                Country t;
                if (line[9] != null)
                    q = Integer.parseInt(line[9]);
                else
                    q = null;
                if (line[10] != null)
                    r = Color.valueOf(line[10]);
                else
                    r = null;
                if (line[11] != null)
                    e = Color.valueOf(line[11]);
                else
                    e = null;
                if (line[12] != null)
                    t = Country.valueOf(line[12]);
                else
                    t = null;
                Person person = new Person(q, r, e, t);
                if (id == null || salary == null) {
                    System.out.println("Cannot add this element due to null objects in one of parameters");
                    continue;
                }
                Worker w = new Worker(name, coords, salary, startDate, pos, st, person);
                w.setId(id);
                w.setCreationDate(date);
                if (!w.isValid()) {
                    System.out.println("Object invalid");
                    continue;
                }
                if (res.add(w))
                    System.out.println("Worker with id " + w.getId() + " has been read");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not read the given file");
            return null;
        }
        catch (Exception e) {
            System.out.println("Incorrect input");
            return null;
        }
        return res;
    }*/

}
