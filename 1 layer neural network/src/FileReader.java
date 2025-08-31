import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileReader {
    File mainDirectory;
    List<String> languages;

    public FileReader(String mainDirectoryName) {
        mainDirectory = new File(mainDirectoryName);
        languages = new LinkedList<>();

        for (File file : mainDirectory.listFiles()) {
            if (!file.getName().equalsIgnoreCase(".DS_Store")) {
                languages.add(file.getName());
            }
        }
    }

    public List<String> getText(String language) {
        List<String> result = new LinkedList<>();

        if (languages.contains(language)) {
            File languageFile = new File(
                mainDirectory.getName() + File.separator + language);

            if (languageFile.isDirectory()) {
                for (File file : languageFile.listFiles()) {
                    try {
                        BufferedReader reader = new BufferedReader(new java.io.FileReader(file));

                        String fileContent = "";

                        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                            fileContent += line;
                        }

                        result.add(fileContent);
                    } catch (IOException e) {
                        System.out.println(file.getName() + ": reading failed");
                    }
                }
            }
        } else {
            System.out.println("language does not exists");
        }

        return result;
    }

    public List<List<String>> getText() {
        List<List<String>> result = new LinkedList<>();

        for (String language : languages) {
            result.add(getText(language));
        }

        return result;
    }
}
