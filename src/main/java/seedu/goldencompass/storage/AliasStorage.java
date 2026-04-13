package seedu.goldencompass.storage;

import seedu.goldencompass.command.Executor;
import seedu.goldencompass.exception.GoldenCompassException;
import seedu.goldencompass.ui.Ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class AliasStorage {
    private final String filePath;

    public AliasStorage(String filePath) {
        this.filePath = filePath;
    }

    public void save(Map<String, String> aliasMap) {
        try {
            File f = new File(filePath);
            File parentDir = f.getParentFile();

            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileWriter fw = new FileWriter(f)) {
                for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
                    if (!entry.getKey().equals(entry.getValue())) {
                        fw.write(entry.getKey() + " | " + entry.getValue() + System.lineSeparator());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving aliases.");
        }
    }

    public void load(Executor executor) {
        File f = new File(filePath);
        if (!f.exists()) {
            return;
        }

        StringBuilder errorLog = new StringBuilder();
        boolean requiresCleanup = false;

        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String line = s.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\s*\\|\\s*");

                if (parts.length == 2) {
                    String alias = parts[0];
                    String command = parts[1];

                    try {
                        executor.addAlias(command, alias);
                    } catch (GoldenCompassException e) {
                        errorLog.append("Warning: Could not load alias [")
                                .append(line).append("] -> ").append(e.getMessage()).append("\n");
                        requiresCleanup = true;
                    }
                } else {
                    errorLog.append("Warning: Corrupted alias skipped and cleaned: [")
                            .append(line).append("]\n");
                    requiresCleanup = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading aliases.");
        }

        if (errorLog.length() > 0) {
            new Ui().print(errorLog.toString().trim());
        }

        if (requiresCleanup) {
            save(executor.getAliasMap());
        }
    }
}
