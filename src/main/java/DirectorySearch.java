import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class DirectorySearcher implements Callable<String> {
    private File file;
    private ExecutorService pool;

    DirectorySearcher(File file, ExecutorService pool) {
        this.file = file;
        this.pool = pool;
    }

    @Override
    public String call() {
        ArrayList<Future<String>> results = new ArrayList<>();
        StringBuffer foundFiles = new StringBuffer();

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                results.add(pool.submit(new DirectorySearcher(f, pool)));
            }

            for (Future<String> textFile : results) {
                try {
                    String fileName = textFile.get();
                    if (!fileName.equals(""))
                        foundFiles.append(fileName.trim() + "\n");
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    e.getStackTrace();
                }
            }
            return foundFiles.toString();

        } else if (file.getName().contains(".txt")) {
            pool.submit(new SearchAndDeleteWords(file));
            return file.getName();
        } else {
            return "";
        }
    }

}