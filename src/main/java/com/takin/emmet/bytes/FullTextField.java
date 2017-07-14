package com.takin.emmet.bytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * fulltext域的序列化协议 <br>
 * tokenlength\token\\001\contentlenght\tf\fieldnum\position\fieldnum\position
 * 
 * 
 * @author wangyazhou
 * 
 */
public class FullTextField {

    private static final String ENCODING = "UTF-8";

    private static final String TOKEN_SPI = "\001";

    private Map<String, List<FullTextPosting>> tokenset = new HashMap<String, List<FullTextPosting>>();

    public FullTextField(String n) throws Exception {

    }

    /**
     * 添加一个位置信息
     * 
     * @param posting
     * @return
     */
    public final boolean add(FullTextPosting posting) {
        boolean retval = posting.validate();
        if (retval) {
            List<FullTextPosting> poslist = tokenset.get(posting.getToken());
            if (poslist == null) {
                poslist = new ArrayList<>();
                tokenset.put(posting.getToken(), poslist);
            }
            poslist.add(posting);
        }
        return retval;
    }

    /**
     * 序列化
     * 
     * @return
     * @throws UnsupportedFieldTypeException
     * @throws UnsupportedEncodingException
     */
    public final ByteBuffer serialize() throws UnsupportedEncodingException {
        ByteBuffer buf = ByteBuffer.allocate(GetSerializeLen());
        buf.order(ByteOrder.LITTLE_ENDIAN);
        final Set<String> orderset = sortByToken(tokenset.keySet());
        boolean isfirst = true;
        int currentpos = 0;
        for (String token : orderset) {
            if (isfirst) {
                isfirst = false;
            } else {
                buf.put(" ".getBytes(ENCODING));// 不同的token以"空格"分开
            }

            byte[] tokenbyte = token.getBytes(ENCODING);
            buf.putShort((short) tokenbyte.length);// token的长度
            buf.put(tokenbyte);// token的实际内容
            buf.put(TOKEN_SPI.getBytes(ENCODING));// token与实际内容的分隔符
            currentpos = buf.position();
            buf.putShort((short) 0);
            List<FullTextPosting> postinglist = tokenset.get(token);
            buf.put((byte) Math.min(postinglist.size(), 255));// tf,这个值可能会大于255
            // buf.put((byte) postinglist.get(0).getFieldNum());// 域的序号
            // buf.put((byte) postinglist.size());//此to
            Collections.sort(postinglist, new FieldNumComparator());
            for (int k = 0; k < Math.min(255, postinglist.size()); k++) {// 超过255个,是否要直接省略
                FullTextPosting posting = postinglist.get(k);
                buf.put((byte) posting.getFieldNum());
                buf.put((byte) posting.getPosition());
            }
            buf.putShort(currentpos, (short) (buf.position() - currentpos));// 回退到currentpos的位置
            // 写入此token的长度
        }
        buf.flip();
        return buf;
    }

    /**
     * 
     * 计算序列化之后的长度
     * 
     * @return
     * @throws UnsupportedFieldTypeException
     * @throws UnsupportedEncodingException
     */
    public final int GetSerializeLen() throws UnsupportedEncodingException {
        int length = 0;
        final Set<String> orderset = tokenset.keySet();
        boolean isfirst = true;
        for (String token : orderset) {
            if (isfirst) {
                isfirst = false;
            } else {
                length += 1;// 起始空格
            }
            length += 2;// short 表示 token的长度
            length += token.getBytes(ENCODING).length;
            length += 1;// \001
            length += 2;// token后面内容的总长度
            List<FullTextPosting> postinglist = tokenset.get(token);
            length += 1;// TF
            // length += 1;// 域NUM
            // length += 1;// 位置大小
            length += postinglist.size() * 2;
        }
        return length;
    }

    /**
     * 反序列化
     * 
     * @param buf
     */
    @SuppressWarnings("unused")
    public final void deserialize(ByteBuffer buf) {
        try {
            buf.flip();
            while (true) {
                short tokenlength = buf.getShort();
                byte[] tokenbyte = new byte[tokenlength];
                buf.get(tokenbyte, 0, tokenlength);
                String token = new String(tokenbyte);
                byte spi = buf.get();
                short cotent = buf.getShort();
                int tf = (int) buf.get();
                System.out.println(String.format("token:%s tf:%d", token, tf));
                for (int i = 0; i < tf; i++) {
                    int fieldnum = (int) buf.get();
                    int position = (int) buf.get();
                    System.out.println(String.format("\t\tfieldnum:%d positon:%d", fieldnum, position));
                }
                if (buf.position() == buf.limit()) {
                    break;
                }
                buf.get();// 空格
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Set<String> sortByToken(Set<String> set) {
        List<String> setList = new ArrayList<String>(set);
        Collections.sort(setList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        set = new LinkedHashSet<String>(setList);// 这里注意使用LinkedHashSet
        return set;
    }

    private static class FieldNumComparator implements Comparator<FullTextPosting> {
        @Override
        public int compare(FullTextPosting o1, FullTextPosting o2) {
            if (o1 == null || o2 == null) {
                return 1;
            }
            int v1 = o1.getFieldNum();
            int v2 = o2.getFieldNum();
            if (v1 > v2) {
                return -1;
            } else if (v1 < v2) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
