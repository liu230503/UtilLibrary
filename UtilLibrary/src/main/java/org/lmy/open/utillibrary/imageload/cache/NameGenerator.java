package org.lmy.open.utillibrary.imageload.cache;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.utils.L;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lmy
 * @ClassName: NameGenerator
 * @Package: org.lmy.open.utillibrary.imageload.cache
 * @Date: 2018/07/31
 */
public class NameGenerator implements FileNameGenerator {
    /**
     * md5
     */
    private static final String HASH_ALGORITHM = "MD5";
    /**
     * 10位数字+26位字母
     */
    private static final int RADIX = 10 + 26;

    @Override
    public String generate(String imageUri) {
        return generate(imageUri, 0);
    }

    /**
     * 生成名称
     *
     * @param imageUri url
     * @param type     类型
     * @return md5加密后的名称
     */
    public String generate(String imageUri, int type) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        return type + "_" + bi.toString(RADIX);
    }

    /**
     * 生成MD5
     *
     * @param data uri数组
     * @return md5数组
     */
    private byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            L.e(e);
        }
        return hash;
    }
}
