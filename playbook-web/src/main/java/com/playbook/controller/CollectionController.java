package com.playbook.controller;

import com.playbook.service.FileService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CollectionController {

    private final FileService fileService;

    public CollectionController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/collections/upload")
    public String showUploadForm(Model model){
        return ("collections/upload");
    }

    @PostMapping("/collections/upload")
    public String uploadCSVFile(@RequestPart("file") MultipartFile file,
                                RedirectAttributes flash) throws Exception {
        fileService.loadFile(file);
        flash.addFlashAttribute("success", "Fichero cargado correctamente!");
        return("redirect:/");
    }
}
