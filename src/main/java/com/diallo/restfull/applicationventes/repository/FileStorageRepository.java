package com.diallo.restfull.applicationventes.repository;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import com.diallo.restfull.applicationventes.controller.ResourceNotFoundException;



@Repository
public class FileStorageRepository {

	//	@Value("${STORAGE_FOLDER}")
	   private final Path rootLocation = Paths.get("C:\\Users\\pcperso\\uploads");
	   
	   public void saveFile(String originalFileName, InputStream inputStream) {
		   	Path resolvePath = getNormalize(originalFileName);
		   	try {
				Files.copy(inputStream, resolvePath);
			} catch (IOException e) {
				 throw new RuntimeException("FAIL!");
			}
	   }

	private Path getNormalize(String originalFileName) {
		Path resolvePath = rootLocation.resolve(originalFileName).normalize();
		return resolvePath;
	}
	   
	public Resource findByName(String fileName) {				
		try {
			Path resolvePath = rootLocation.resolve(fileName);
			return new UrlResource(resolvePath.toUri());
		} catch (MalformedURLException e) {
			throw new ResourceNotFoundException("Failed to upload!");
		}		
	}
	
	public void deleteAllByName(Collection<String> filenames) {		
		try {
			Set<String> collect = filenames.stream().filter(f -> f!=null).collect(Collectors.toSet());
			for (String filename : collect) {
				Path path = rootLocation.resolve(filename);
				Files.deleteIfExists(path);
			}
		} catch (IOException e) {
			throw new ResourceNotFoundException("Failed to upload!");
		}		
	}
}
