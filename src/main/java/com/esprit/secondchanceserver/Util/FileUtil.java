package com.esprit.secondchanceserver.Util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {
    public static String UPLOAD_FOLDER = "E://SecondChance//Upload-dir//";
    public static String DOWNLOAD_URL = "/SecondChance/Upload-dir/";

    public static void saveFiles(List<MultipartFile> files, List<String> fileNames) throws IOException {
        int i =0 ;
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + fileNames.get(i));
            DebugUtil.logError("saving to"  + " : " + path);
            Files.write(path, bytes);
            i++;
        }
    }

    public static void deleteFile(String fileName){
        try{
            File file = new File(UPLOAD_FOLDER + fileName);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getFileExtension (String fileName){
        return "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
