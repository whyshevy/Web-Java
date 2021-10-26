import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class SearchAndDeleteWords implements Callable<String> {
    private File file;

    public SearchAndDeleteWords(File file) {
        this.file = file;
    }

    @Override
    public String call() {
        try {
            Scanner reader = new Scanner(new FileReader(file));

            StringBuilder textInProcess = new StringBuilder();
            ArrayList<String> lines = new ArrayList<>();

            while(reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }

            for (String line : lines) {
                for(String word : line.split(" ")) {
                    if (word.length() > 5 || word.length() < 3 || word.equals("\n")) {
                        textInProcess.append(word + " ");
                    }
                }
                textInProcess.append("\n");
            }

            reader.close();

            FileWriter writer = new FileWriter(file);
            writer.write(textInProcess.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return file.getName();
    }
}
