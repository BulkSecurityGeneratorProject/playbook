package com.playbook.batch.listener;


import com.playbook.entity.Coleccion;
import com.playbook.error.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@Component
public class CollectionItemStepListener implements ItemProcessListener<Coleccion, String> {

    @Override
    public void beforeProcess(Coleccion coleccion) {
    }

    @Override
    public void afterProcess(Coleccion coleccion, String s) {
    }

    @Override
    public void onProcessError(Coleccion coleccion, Exception e) {
        log.error("Error al procesar");
        throw new JobException("Error al procesar: " + e.getMessage());
    }
}
