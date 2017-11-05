package org.verlet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 创建删除文件(夹)工具类
 *
 * @author huangsikai
 */
public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 根据文件路径和文件格式创建文件
     *
     * @param destFileName 文件路径及文件名 文件格式
     * @return boolean
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            log.info("创建单个文件 {} 失败，目标文件已存在！", destFileName);
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            log.info("创建单个文件 {} 失败，目标不能是目录！", destFileName);
            return false;
        }
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                log.info("创建目录文件所在的目录失败！");
                return false;
            }
        }

        // 创建目标文件
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                log.info("创建单个文件 {} 失败！", destFileName);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("创建单个文件 {} 失败！", destFileName);
            return false;
        }
    }

    /**
     * 创建文件夹
     *
     * @param destDirName 文件夹路径
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            log.info("创建目录 {} 失败，目标目录已存在！", destDirName);
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建单个目录
        if (dir.mkdirs()) {
            return true;
        }
        return false;
    }

    /**
     * 创建临时文件
     *
     * @param prefix  文件名
     * @param suffix  文件格式
     * @param dirName 文件夹路径
     * @return
     */
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        try {
            if (dirName == null) {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                return tempFile.getCanonicalPath();
            } else {
                File dir = new File(dirName);
                // 如果临时文件所在目录不存在，首先创建
                if (!dir.exists()) {
                    if (!FileUtil.createDir(dirName)) {
                        log.info("创建临时文件失败，不能创建临时文件所在目录！");
                        return null;
                    }
                }
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("创建临时文件失败 {} ", e.getMessage());
            return null;
        }
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录(文件夹)以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        }
        return false;
    }
}