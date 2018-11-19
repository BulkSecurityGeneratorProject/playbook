package com.playbook.batch.listener;

import com.playbook.entity.Coleccion;
import com.playbook.error.exception.JobException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;

import java.util.List;

@Slf4j
@Component
public class CollectionItemWriterListener implements ItemWriteListener<Coleccion> {

    @Override
    public void beforeWrite(List<? extends Coleccion> list) {
    }

    @Override
    public void afterWrite(List<? extends Coleccion> list){
    }

    @Override
    public void onWriteError(Exception e, List<? extends Coleccion> list) {
        log.error("Error al escribir");
        throw new JobException("Error al escribir :" + e.getMessage());
    }
}
