package com.takin.emmet.bytes;

/**
 * 一个域分词之后的位置对象
 * 
 * @author wangyazhou
 * 
 */
public class FullTextPosting {

    private int fieldNum;// 域的序号
    private String token; // the Term 词本身
    private int freq = 1;// 默认1
    private int position; // 位置
    private int offset;// 偏移量

    public FullTextPosting() {
    }

    public FullTextPosting(int fieldNum, String token, int freq, int position, int offset) {
        this.fieldNum = fieldNum;
        this.token = token;
        this.freq = freq;
        this.position = position;
        this.offset = offset;
    }

    /**
     * 按字典序排列
     * 
     * @param other
     * @return
     */
    public final int compareTo(FullTextPosting other) {
        return token.compareTo(other.token);
    }

    /**
     * 判断此位置倒排对象是否有效
     * 
     * @return true:有效 false:无效
     */
    public boolean validate() {
        return fieldNum > 0 && fieldNum < 256 && //
                        token != null && token.length() > 0 && //
                        freq > 0 && position > 0 && position < 256;

    }

    /**
     * fieldnum position token
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + fieldNum;
        result = prime * result + position;
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        return result;
    }

    /**
     * fieldnum position token 三个相等则认为是同一个posting
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FullTextPosting other = (FullTextPosting) obj;
        if (fieldNum != other.fieldNum)
            return false;
        if (position != other.position)
            return false;
        if (token == null) {
            if (other.token != null)
                return false;
        } else if (!token.equals(other.token))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Posting [fieldNum=" + fieldNum + ", token=" + token + ", freq=" + freq + ", position=" + position + ", offset=" + offset + "]";
    }

    public int getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(int fieldNum) {
        this.fieldNum = fieldNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
