package com.sushavi.sbatch.chunk;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyItemProcessor implements ItemProcessor<Integer, Long> {

    @Override
    public Long process(Integer integer) throws Exception {
        return (long) (integer + 20);
    }
}
