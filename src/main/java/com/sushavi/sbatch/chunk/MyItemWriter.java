package com.sushavi.sbatch.chunk;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MyItemWriter implements ItemWriter<Long> {

    @Override
    public void write(List<? extends Long> list) throws Exception {
        System.out.println("inside write method");
        list.forEach(System.out::println);
    }
}
