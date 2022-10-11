package com.mmit;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void savePhoto(String photoName, String photoPath, MultipartFile file) {
		try {
			Path path = Path.of(photoPath);
			if(!Files.exists(path)) {
				Files.createDirectories(path);
			}
			
			InputStream fileData = file.getInputStream();
			
			Path uploadFile = path.resolve(photoName);
			
			Files.copy(fileData, uploadFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
