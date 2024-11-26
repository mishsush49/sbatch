package com.sushavi.sbatch.chunk;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyItemReader implements ItemReader<Integer> {

    List<Integer> numberLst = List.of(1,2,3,4,5,6,7,8,9,10);
    int i = 0;

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("inside My Item reader");
        Integer item;
        if(i<numberLst.size()){
            item = numberLst.get(i);
            i++;
            return item;
        }
        return null;
    }
}
