import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        String path;
        File directory;

        do {
            System.out.println("Enter your directory path, where .txt files are located: ");
            path = scanner.nextLine();
            directory = new File(path);
        } while (!directory.exists());

        ExecutorService pool = Executors.newFixedThreadPool(5);
        DirectorySearcher searcher = new DirectorySearcher(directory, pool);
        Future<String> filesFounding = pool.submit(searcher);
        System.out.println(filesFounding.get());
        pool.shutdown();
    }
}