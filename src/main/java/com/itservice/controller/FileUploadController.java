package com.itservice.controller;

import com.itservice.model.Serializator;
import com.itservice.model.StringForm;
import com.itservice.storage.StorageFileNotFoundException;
import com.itservice.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.Arrays;
import java.util.List;


@Controller
public class FileUploadController {

	private final StorageService storageService;
	@Autowired
	private Serializator serializator;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/file_download")
	public String downloadedFiles(@RequestParam("file") MultipartFile multipartFile,
								  @ModelAttribute("stringForm") StringForm stringForm,
								  Model model) throws IOException {
		File filePath = new File("upload-dir");
		filePath.mkdir();  // создаем каталог
		File newfile = new File(filePath + "\\" + stringForm.value  + ".txt");
		try {
			newfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (OutputStream os = new FileOutputStream(newfile)) {
			os.write(multipartFile.getBytes());
		}
		List<String> types = Arrays.asList("magicSquare", "subString");
		model.addAttribute("types", types);
		model.addAttribute("stringForm", serializator.deserialization(newfile));
		System.out.println(serializator.deserialization(newfile).toString());
		return "form_calculation";
	}

	@PostMapping("/file_upload")
	public String handleFileUpload(@ModelAttribute("stringForm") StringForm stringForm,
								   RedirectAttributes redirectAttributes) {

		File filePath = new File("upload-dir");
		filePath.mkdir();  // создаем каталог
		File fileFile = new File(filePath + "\\" + stringForm.value  + ".txt");
		try {
			fileFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		serializator.serialization(stringForm, fileFile);
		InputStream stream = null;
		try {
			stream = new FileInputStream(fileFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		MultipartFile multipartFileToSend = null;
		try {
			multipartFileToSend = new MockMultipartFile("file", fileFile.getName(), MediaType.TEXT_HTML_VALUE, stream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		storageService.store(multipartFileToSend);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + multipartFileToSend.getOriginalFilename() + "!");

		return "redirect:/form_calculation";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}



}
