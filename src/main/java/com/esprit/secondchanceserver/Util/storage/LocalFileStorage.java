package com.esprit.secondchanceserver.Util.storage;

import com.esprit.secondchanceserver.Util.DebugUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LocalFileStorage {
    /*private static String UPLOAD_ROOT = "upload-dir";
    @Autowired
    private ResourceLoader resourceLoader;

    public Resource findOneImage(String filename){
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
    }

    public String findOneImageURL(String filename) *//*throws IOException*//*{
        try{
            DebugUtil.logError("********************FIND =" + "file:" + UPLOAD_ROOT + "/" + filename);
            DebugUtil.logError(resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename).getURI().toString());
            DebugUtil.logError(resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename).getURL().toString());
            return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename).getURI().toString();
        }catch (IOException e){
            DebugUtil.logError("ERROR");
        }
        return("nop");
    }

    public void createImage(MultipartFile file, String fileName) throws IOException {
        if (!file.isEmpty()){
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, fileName));
        }
    }

    public boolean deleteImage(String filename) throws IOException{
        return Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
    }*/
}
