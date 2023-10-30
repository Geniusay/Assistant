package io.github.util.file;

import io.github.util.path.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 *    Genius
 **/
public class CommonFileUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonFileUtils.class);

    /**
     * 判断文件是否存在
     *
     * @param dir 文件路径需要包含文件名
     * @return Boolean
     */
    public static Boolean isFileExist(String dir) {
        return new File(dir).exists();
    }

    /**
     * 读取文件
     * @param file
     */
    public static void writeFile(String file,String fileContent) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                new FileOutputStream(file))) {
            bufferedOutputStream.write(fileContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
    public static File createFile(String fileSavePath,String fileName){
        return createFile(PathUtils.smartFilePath(fileSavePath,fileName));
    }

    /**
     * MultipartFile 转 File
     * @param multipartFile
     * @return
     */
    public static File multipartFileToFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }



    /**
     * 复制文件 比 Files更快
     *
     * @param srcPath  源文件路径
     * @param destPath 目标文件路径
     * @return File
     */
    public static File copyFile(String srcPath, String destPath) {
        try (
                FileChannel src = new FileInputStream(srcPath).getChannel();
                FileChannel dest = new FileInputStream(destPath).getChannel()
        ) {
            dest.transferFrom(src, 0, src.size());
        } catch (IOException e) {
            logger.error("复制文件失败", e);
            return null;
        }
        return new File(destPath);
    }

    /**
     * 删除文件
     *
     * @param path     文件路径
     * @param filename 文件名
     * @return boolean
     */
    public static boolean deleteFile(String path, String filename) {
        return deleteFile(Paths.get(path, filename).toString());
    }

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return boolean
     */
    public static boolean deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            logger.error("删除文件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 文件目录递归删除
     *
     * @param path 文件路径
     * @return boolean
     */
    public static boolean deleteDirectory(String path) throws IOException {
        FileCondition condition = file -> !file.toString().startsWith("C:") || !file.toString().startsWith("root");
        return deleteDirectory0(path, condition, condition, condition);
    }

    /**
     * 文件目录递归删除
     *
     * @param path      文件路径
     * @param visit     访问文件时触发该方法
     * @param preVisit  访问子目录前触发该方法
     * @param postVisit 访问目录之后触发该方法
     * @return boolean
     * @throws IOException IOException
     */
    public static boolean deleteDirectory(String path, FileCondition visit, FileCondition preVisit, FileCondition postVisit) throws IOException {
        return deleteDirectory0(path, visit, preVisit, postVisit);
    }

    /**
     * 递归删除
     *
     * @param path 文件路径
     * @return File
     */
    private static boolean deleteDirectory0(String path, FileCondition visit, FileCondition preVisit, FileCondition postVisit) throws IOException {
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
                    // 在访问文件时触发该方法
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!visit.condition(file)) {
                            logger.info("文件被跳过: {}", file);
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        Files.delete(file);
                        logger.info("文件被删除: {}", file);
                        return FileVisitResult.CONTINUE;
                    }

                    // 在访问子目录前触发该方法
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!preVisit.condition(dir)) {
                            logger.info("目录被跳过: {}", dir);
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        logger.info("目录被访问: {}", dir);
                        return FileVisitResult.CONTINUE;
                    }

                    // 在访问目录之后触发该方法
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        if (!postVisit.condition(dir)) {
                            logger.info("目录被跳过: {}", dir);
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                        Files.delete(dir);
                        logger.info("目录被删除: {}", dir);
                        return FileVisitResult.CONTINUE;
                    }

                    // 在访问失败时触发该方法
                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                        // 写一些具体的业务逻辑
                        return super.visitFileFailed(file, exc);
                    }

                }
        );
        return true;
    }

}
