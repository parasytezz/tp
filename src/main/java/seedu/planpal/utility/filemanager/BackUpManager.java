package seedu.planpal.utility.filemanager;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.model.enums.CompressionMethod;
import seedu.planpal.exceptions.PlanPalExceptions;

import java.io.File;

public class BackUpManager {
    private static final String PASSWORD = "iLoveCS2113";
    private static final String SOURCE_FOLDER = "./data/";
    private static final String BACKUP_FOLDER = "./backup_data/";
    private static final String EXTRACT_FOLDER = "./";
    private static final String BACKUP_ZIP_FILE = "/data.zip";

    private static void createDirectory(String path){
        File directory = new File(path);
        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    public static void backupData() throws PlanPalExceptions {
        backupData(SOURCE_FOLDER, BACKUP_FOLDER);
    }

    public static void backupData(String sourceFolder, String backupFolder) throws PlanPalExceptions {
        createDirectory(backupFolder); // Ensure the backup directory exists
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(CompressionMethod.DEFLATE);
            parameters.setEncryptionMethod(EncryptionMethod.AES);
            parameters.setEncryptFiles(true);

            ZipFile zipFile = new ZipFile(backupFolder + BACKUP_ZIP_FILE, PASSWORD.toCharArray());
            zipFile.addFolder(new File(sourceFolder), parameters);
        } catch (Exception e) {
            throw new PlanPalExceptions(e.getMessage());
        }
    }

    public static void restoreData() throws PlanPalExceptions {
        restoreData(BACKUP_FOLDER, EXTRACT_FOLDER);
    }

    public static void restoreData(String backupFolder, String targetFolder) throws PlanPalExceptions {
        createDirectory(targetFolder); // Ensure the target directory is ready for the extracted data
        try {
            ZipFile zipFile = new ZipFile(backupFolder + BACKUP_ZIP_FILE);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(PASSWORD.toCharArray());
            }
            zipFile.extractAll(targetFolder);
        } catch (Exception e) {
            throw new PlanPalExceptions(e.getMessage());
        }
    }
}
