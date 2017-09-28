/*
 *
 *  * Copyright (C) 2015 by  xunice@qq.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *
 */

package com.me.fanyin.zbme.views.course.play.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.yunqing.core.db.utils.LogUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹操作工具类
 *
 * @author xunice
 */
public class FileUtil {

    public final static String FILE_EXTENSION_SEPARATOR = ".";
    private static final int BUFFER_SIZE = 1024;
    public final static String VIDEOPATH = "video/";
    public final static String LECTURE = "lecture/";
    private static String DOWNLOADPATH;

    /**
     * read file
     * 读取文件内容
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();

            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     * 往文件内写东西
     *
     * @param filePath
     * @param content
     * @param append   is append, if true, write to the end of file, else clear content of file and write into it
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * write file
     * 往文件内写东西
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, java.io.InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     * 往文件内写东西
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file
     * 往文件内写东西
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(java.io.File, java.io.InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * 从二进制写到文件
     *
     * @param filePath
     * @param bytes
     */
    public static void writeByteFile(String filePath, byte[] bytes) {
        File distFile = new File(filePath);
        if (!distFile.getParentFile().exists()) {
            distFile.getParentFile().mkdirs();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath), BUFFER_SIZE);
            bos.write(bytes);
        } catch (Exception e) {
            LogUtils.e("save " + filePath + " failed!");
//            LogUtils.exception(e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * write file
     * 往文件内写东西
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * copy file
     * 复制文件
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * get file name from path, not include suffix
     * <p/>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     * 获取文件名字  不带扩展名
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     * <p/>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     * 获取文件名称 带扩展名
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     * <p/>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     * 获取文件夹名字
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (StringUtil.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     * 获取文件扩展名
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file, including the complete directory path required
     * to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     * 创建文件夹
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target directory already exists, false one of
     * the directories can not be created.
     * <ul>
     * <li>if {@link FileUtil#getFolderName(String)} return null, return false</li>
     * <li>if target directory already exists, return true</li>
     * <li>return {@link java.io.File#mkdirs()}</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (StringUtil.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     * 文件夹是否存在
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtil.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     * 删除文件或者文件夹
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (StringUtil.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 删除一个目录（可以是非空目录）
     *
     * @param dir
     * @param o   是否删除自己, true：删除; false：保留
     */
    public static boolean delDir(File dir, boolean o) {
        boolean delFlag = true;
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                if (!file.delete()) {
                    LogUtils.e("delete file error, file name is " + file.getName());
                    if (delFlag) {
                        delFlag = false;
                    }
                }
            } else if (file.isDirectory()) {
                boolean childFlag = delDir(file, true);// 递归
                if (delFlag && !childFlag) {
                    LogUtils.e("delete file error, file name is " + file.getName());
                    delFlag = false;
                }
            }
        }
        if (o) {
            dir.delete();
        }
        return delFlag;
    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     * 获取文件大小
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file does not exist.
     */
    public static long getFileSize(String path) {
        if (StringUtil.isEmpty(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 根据路径返回空间大小
     *
     * @param path
     * @return
     */
    public static long getSizeByPath(String path) {
        File file = new File(path);
        StatFs stat = new StatFs(file.getPath());
        long blockSize = 0, availableBlocks = 0;

        blockSize = stat.getBlockSize();
        availableBlocks = stat.getAvailableBlocks();

        return availableBlocks * blockSize;
    }

    /**
     * 得到存储路径
     *
     * @param context
     * @return
     */
    public static String getDownloadPath(Context context) {
        String downloadPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 有SD卡情况
            File path = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            if (path == null)
                return downloadPath;
            downloadPath = path.getPath() + "/";
        } else {
            // 无SD卡情况判断挂载点？
            ArrayList<String> mountList = getDevMountList();
            for (String mount : mountList) {
                long size = getSizeByPath(mount);
                if (size != 0) {
                    if (size / (1024 * 1024) > 200) {
                        // 有存储大小时获取
                        File download = new File(mount + "/Android/" + context.getPackageName() + "/files/Download");
                        if (!download.exists()) {
                            boolean createResult = download.mkdirs();
                            //LogUtils.d("创建目录 : "+download.getPath()+" >> 结果"+ createResult);
                        }
                        downloadPath = download.getPath() + "/";
                        break;
                    }
                }
            }
        }
        //LogUtils.d("当前课件下载目录 ： ", downloadPath);
        return downloadPath;

    }

    /**
     * 获得key存储路径
     *
     * @param context
     * @return
     */
    public static String getKeyPath(Context context) {
        String downloadPath = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 有SD卡情况
            File path = context.getFilesDir();
            if (path == null)
                return downloadPath;
            downloadPath = path.getPath() + "/playOnline/";
        } else {
            // 无SD卡情况判断挂载点？
            ArrayList<String> mountList = getDevMountList();
            for (String mount : mountList) {
                long size = getSizeByPath(mount);
                if (size != 0) {
                    if (size / (1024 * 1024) > 200) {
                        // 有存储大小时获取
                        File download = new File(mount + "/Android/" + context.getPackageName() + "/files/playOnline");
                        if (!download.exists()) {
                            boolean createResult = download.mkdirs();
                            //LogUtils.d("创建目录 : "+download.getPath()+" >> 结果"+ createResult);
                        }
                        downloadPath = download.getPath() + "/";
                        break;
                    }
                }
            }
        }
        return downloadPath;

    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if ((i + 2) <= toSearch.length && new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        LogUtils.d("挂载点列表: " + out);
        return out;
    }

    /**
     * 从文件中读取字符
     *
     * @param filePath
     * @return
     */
    public static String readFile(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            LogUtils.e("readTextFile " + filePath + " failed!");
//            LogUtils.exception(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    public static String getCacheCources(final Context context, final String userid) {
        final String mUserId = userid;
        String str = "";
        try {
            String line;
            File file;
            file = context.getFileStreamPath(mUserId + ".links");
            if (file.exists()) {
                final FileInputStream input;
                input = context.openFileInput(mUserId + ".links");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input));
                while ((line = reader.readLine()) != null) {
                    str += line;
                }
                reader.close();
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    public static String getCacheCourcesDetail(final Context context, final String userid, String courseId) {
        final String mUserId = userid;
        String str = "";
        try {
            String line;
            File file;
            file = context.getFileStreamPath(mUserId+ "_"+ courseId + ".links");
            if (file.exists()) {
                final FileInputStream input;
                input = context.openFileInput(mUserId + "_"+ courseId+ ".links");
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(input));
                while ((line = reader.readLine()) != null) {
                    str += line;
                }
                reader.close();
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

}
