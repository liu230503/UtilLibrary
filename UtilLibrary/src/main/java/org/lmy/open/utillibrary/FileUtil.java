package org.lmy.open.utillibrary;

import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import org.lmy.open.utillibrary.path.PathUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * @author lmy
 * @ClassName: FileUtil
 * @Package: com.mxnavi.busines.core.util
 * @Date: 2018/08/28
 */
public class FileUtil {
    /**
     * 日期格式
     */
    public static final String DATA_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式
     */
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * XML解析编码方式 UTF-8
     */
    public static final String XML_ENCODING = "UTF-8";
    /**
     * 换行符
     */
    public static final String XML_ENTER_SIGN = "\n";
    /**
     * TAG
     */
    private static final String TAG = FileUtil.class.getName();
    /**
     * 字节进制
     */
    private static final int BASICS_BYTE = 1024;

    /**
     * 追加方式保存文件
     *
     * @param log 日志
     */
    public static void saveLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }

        final String name = "log" + DateUtil.getDateTime(DATA_PATTERN) + ".txt";
        log = XML_ENTER_SIGN + DateUtil.getDateTime(TIME_PATTERN) + ":  " + log;
        saveFile(PathUtil.getInstance().getLogPath(), name, log, true);
    }

    /**
     * 保存文件 UTF-8编码
     *
     * @param filePath 文件路径（“/”结尾）
     * @param fileName 文件名
     * @param str      保存的数据
     * @param append   是否追加记录
     */
    public static synchronized void saveFile(final String filePath,
                                             final String fileName, final String str, final boolean append) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        FileOutputStream fos = null;
        OutputStreamWriter out = null;
        try {
            makeDir(filePath);
            final File file = new File(filePath, fileName);
            fos = new FileOutputStream(file, append);
            out = new OutputStreamWriter(fos, XML_ENCODING);
            out.write(str);
            out.close();
        } catch (final Exception e) {
            Log.e(TAG, "Save log to file error !" + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 检测路径是否存在，不存在则创建
     *
     * @param path 路径
     */
    public static void makeDir(final String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 删除过期文件
     *
     * @param path    路径
     * @param maxSave 最大存储数量
     */
    public static void deleteOldFiles(String path, int maxSave) {
        File tempDir = new File(path);
        if (tempDir != null && tempDir.exists()) {
            File[] fileList = tempDir.listFiles();
            if (fileList != null) {
                int fileNum = fileList.length;
                TreeMap<Long, File> tm = new TreeMap<>();
                for (int i = 0; i < fileNum; i++) {
                    Long tempLong = fileList[i].lastModified();
                    if (tm.containsKey(tempLong)) {
                        tm.put(tempLong + i + 1, fileList[i]);
                    } else {
                        tm.put(tempLong, fileList[i]);
                    }
                }

                List<File> tempDeleteFiles = new ArrayList<>();
                for (int i = 0; i < fileNum; i++) {
                    if (i >= maxSave) {
                        File tempFile = tm.get(tm.lastKey());
                        if (tempFile != null) {
                            tempDeleteFiles.add(tempFile);
                        }
                        if (tm.containsKey(tm.lastKey())) {
                            tm.remove(tm.lastKey());
                        }
                    } else {
                        if (tm.containsKey(tm.lastKey())) {
                            tm.remove(tm.lastKey());
                        }
                    }
                }
                for (File tempFile : tempDeleteFiles) {
                    tempFile.delete();
                }
            }
        }
    }

    /**
     * 字节大小转换
     *
     * @param size 大小
     * @return 转换后的结果
     */
    public static String convertSize(long size) {
        DecimalFormat mFormat = new DecimalFormat("##0.0");
        if (size >= BASICS_BYTE * BASICS_BYTE * BASICS_BYTE) {
            float sizeInG = size * 1.0f / (BASICS_BYTE * BASICS_BYTE * BASICS_BYTE);
            return (mFormat.format(sizeInG) + "G").replace(".0", "");
        } else if (size >= BASICS_BYTE * BASICS_BYTE) {
            float sizeInM = size * 1.0f / (BASICS_BYTE * BASICS_BYTE);
            return (mFormat.format(sizeInM) + "M").replace(".0", "");
        } else if (size > BASICS_BYTE) {
            float sizeInKB = size * 1.0f / BASICS_BYTE;
            return mFormat.format(sizeInKB) + "KB";
        } else if (size == 0) {
            return "0B";
        } else {
            float sizeInB = size * 1.0f;
            return mFormat.format(sizeInB) + "B";
        }
    }


}
