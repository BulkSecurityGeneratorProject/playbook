package com.playbook.batch.listener;

import com.playbook.entity.Coleccion;
import com.playbook.error.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@Component
public class CollectionItemReaderListener implements ItemReadListener<Coleccion> {
    @Override
    public void beforeRead(){
    }

    @Override
    public void afterRead(Coleccion coleccion) {
    }

    @Override
    public void onReadError(Exception e){
        log.error("Error al leer");
        throw new JobException("Error al lerr: " + e.getMessage());
    }
}
