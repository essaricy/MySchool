package com.myschool.loaders.image;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

public class ImagesRename {

	public static void main(String[] args) {
		ImagesRename imagesRename = new ImagesRename();
		imagesRename.start();
	}

	private void start() {
		System.out.println("#####");
		File directory = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\demo\\fileserver\\image\\employee");
		//File directory = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\demo\\fileserver\\image\\student");
		if (directory.exists() && directory.isDirectory()) {
			List<File> files = (List<File>) FileUtils.listFiles(directory, null, true);
			//Collections.shuffle(files, new Random(files.size()));
			System.out.println("files " + files);
			for (int index = 0; index < files.size(); index++) {
				File file = files.get(index);
				String randomUUID = UUID.randomUUID().toString();
				String name = file.getName();
				String extn = name.substring(name.lastIndexOf("."), name.length());
				File remaedFile = new File(file.getParent(), randomUUID + extn);
				file.renameTo(remaedFile);
				files.set(index, remaedFile);
			}
			System.out.println("Assigned random names to the files.");
			Collections.sort(files);
			for (int index = 0; index < files.size(); index++) {
				File file = files.get(index);
				String name = file.getName();
				String extn = name.substring(name.lastIndexOf("."), name.length());
				File renamedFile = new File(file.getParent(), (index+1) + extn);
				file.renameTo(renamedFile);
			}
			System.out.println("done");
		}
	}
}
