package HRS;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BackupManager {
    private static final String BACKUP_DIRECTORY = "backups";
    private static final long BACKUP_INTERVAL_MS = 24 * 60 * 60 * 1000; // 24 hours

    private String sourceDirectory;

    public BackupManager(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;

        // Create the backup directory if it doesn't exist
        createBackupDirectory();

        // Schedule regular backups
        scheduleBackups();
    }

    private void createBackupDirectory() {
        File backupDir = new File(BACKUP_DIRECTORY);
        if (!backupDir.exists()) {
            if (backupDir.mkdir()) {
                System.out.println("Backup directory created: " + backupDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create backup directory.");
            }
        }
    }

    private void performBackup() {
        // Generate a timestamp for the backup folder
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String backupFolderName = "backup_" + timestamp;

        // Define the full backup directory path
        String backupFolderPath = Paths.get(BACKUP_DIRECTORY, backupFolderName).toString();

        try {
            // Create the backup folder
            Files.createDirectory(Paths.get(backupFolderPath));
            System.out.println("Backup folder created: " + backupFolderPath);

            // Copy files from the source directory to the backup directory
            Path sourcePath = Paths.get(sourceDirectory);
            Path backupPath = Paths.get(backupFolderPath);
            Files.walk(sourcePath).forEach(source -> {
                Path target = backupPath.resolve(sourcePath.relativize(source));
                try {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("Backup completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Backup failed.");
        }
    }

    private void scheduleBackups() {
        Timer timer = new Timer(true); // Create a daemon timer

        // Schedule backups at specified intervals
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                performBackup();
            }
        }, 0, BACKUP_INTERVAL_MS);

        System.out.println("BackupManager: Scheduled backups every " +
                BACKUP_INTERVAL_MS / (60 * 60 * 1000) + " hours.");
    }

    public static void main(String[] args) {
        // Specify the source directory to be backed up
        String sourceDirectory = "data_to_backup";

        // Create an instance of BackupManager
        BackupManager backupManager = new BackupManager(sourceDirectory);

        // You can continue with other tasks in your application
        // The backups will be performed automatically at the specified intervals.
    }
}

