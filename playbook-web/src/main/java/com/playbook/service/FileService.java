package com.playbook.service;

import com.playbook.error.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
public class FileService {

    @Autowired
    @Qualifier("loadCSV")
    private Job importCSVJob;

    @Autowired
    private JobLauncher launcher;

    public void loadFile(MultipartFile archivo) throws Exception {
        // Pasamos el fichero subido como parametro, y ponemos la hora para que no nos de error de job duplicado
        JobParameters params = new JobParametersBuilder().addString("input.file.name", createTempFile(archivo))
                .addLong("time",System.currentTimeMillis()).toJobParameters();
        JobExecution execution = launcher.run(importCSVJob, params);
        BatchStatus status = execution.getStatus();
        if(status != BatchStatus.COMPLETED){
            List<Throwable> allFailureExceptions = execution.getAllFailureExceptions();
            if(allFailureExceptions.size() > 0){
                throw new JobException("Error al ejecutar el Job ID " + execution.getJobId().toString() + ": " + allFailureExceptions.get(0).getCause());
            }
        }
        log.info("Job ID ".concat(execution.getJobId().toString()));
    }

    private String createTempFile(MultipartFile archivo){
        Path path = null;
        File file = null;
        try {
            path = Files.createTempFile("coleccion", ".csv");
            file = path.toFile();
            archivo.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            throw new MultipartException("Error al procesar el archivo csv");
        }

        return file.getAbsolutePath();
    }
}
