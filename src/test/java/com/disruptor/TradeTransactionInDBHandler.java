package com.disruptor;

import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>, WorkHandler<TradeTransaction> {

    private static final AtomicInteger count = new AtomicInteger();

    @Override
    public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(TradeTransaction event) throws Exception {
        //这里做具体的消费逻辑  
        event.setId(count.getAndIncrement() + "");//简单生成下ID  
        System.out.println(event.getId());
    }
}