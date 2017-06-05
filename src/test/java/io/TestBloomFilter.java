package io;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import com.takin.emmet.string.StringUtil;

public class TestBloomFilter {

    private final BloomFilter<String> dealIdBloomFilter = BloomFilter.create(new Funnel<String>() {

        private static final long serialVersionUID = 1L;

        @Override
        public void funnel(String arg0, PrimitiveSink arg1) {
            arg1.putString(arg0, Charsets.UTF_8);
        }

    }, 1024 * 1024 * 32);

    public synchronized boolean containsDealId(String deal_id) {
        if (StringUtil.isEmpty(deal_id)) {
            return true;
        }
        boolean exists = dealIdBloomFilter.mightContain(deal_id);
        if (!exists) {
            dealIdBloomFilter.put(deal_id);
        }
        return exists;
    }
    
}
