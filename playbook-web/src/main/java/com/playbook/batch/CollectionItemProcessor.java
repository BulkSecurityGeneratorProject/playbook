package com.playbook.batch;

import com.playbook.entity.Coleccion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CollectionItemProcessor implements ItemProcessor<Coleccion, Coleccion> {

    private static final Logger log = LoggerFactory.getLogger(CollectionItemProcessor.class);

    @Override
    public Coleccion process(final Coleccion item) throws Exception {
        log.info("--> Coleccion " + item);
        return item;
    }
}
