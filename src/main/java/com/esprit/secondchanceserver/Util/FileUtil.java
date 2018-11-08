package com.esprit.secondchanceserver.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.util.List;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.file.*;

public class FileUtil {

    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;" +
                    "AccountName=secondchance;" +
                    "AccountKey=gTXdMRvJj56ioasBER1xrjkNLJ6bGQjAIcr0ja6uEDzfJojuAvKk1/C5M4ClDalaQ7ao+Ji2xTnZIE2y7L7znQ==;" +
                    "EndpointSuffix=core.windows.net";
    public static final String SAP_TOKEN = "?sv=2017-07-29&ss=f&srt=sco&sp=r&se=2018-09-01T02:05:44Z&st=2018-04-30T18:05:44Z&spr=https&sig=fXPoy4hXL43MSBz5zGXEmPzqV9vyAiVS7beL6GsPzCA%3D";

    /*public static String UPLOAD_FOLDER = "E://SecondChance//Upload-dir//";
    public static String DOWNLOAD_URL = "/SecondChance/Upload-dir/";*/

    public static void saveFileToCloud(MultipartFile file, String fileName) throws URISyntaxException, StorageException, IOException {
        try {
            if (!file.isEmpty()) {
                CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
                CloudFileClient fileClient = storageAccount.createCloudFileClient();
                CloudFileShare share = fileClient.getShareReference("secondchancefs");
                if (share.createIfNotExists()) {
                    System.out.println("New share created");
                }
                CloudFileDirectory rootDir = share.getRootDirectoryReference();
                CloudFile cloudFile = rootDir.getFileReference(fileName);
                byte[] bytes = file.getBytes();
                cloudFile.uploadFromByteArray(bytes, 0, bytes.length);
            }

        } catch (InvalidKeyException invalidKey) {
            DebugUtil.logError("Authentication Error to cloud file share");
        }
    }

    public static String getFileUrlFromCloud(String fileName) throws URISyntaxException, StorageException, IOException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudFileClient fileClient = storageAccount.createCloudFileClient();
        CloudFileShare share = fileClient.getShareReference("secondchancefs");
        if (share.createIfNotExists()) {
            System.out.println("New share created");
        }

        CloudFileDirectory rootDir = share.getRootDirectoryReference();

        CloudFile file = rootDir.getFileReference(fileName);

        return file.getUri().toString()+SAP_TOKEN;
    }

    public static boolean deleteFileFromCloud(String fileName) throws URISyntaxException, StorageException, IOException, InvalidKeyException {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
        CloudFileClient fileClient = storageAccount.createCloudFileClient();
        CloudFileShare share = fileClient.getShareReference("secondchancefs");
        CloudFileDirectory rootDir = share.getRootDirectoryReference();

        CloudFile file;

        file = rootDir.getFileReference(fileName);
        return file.deleteIfExists();
    }

    public static String getFileExtension(String fileName) {
        return "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }


}
