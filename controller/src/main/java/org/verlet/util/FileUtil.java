package org.verlet.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static int fileByteStreamUpload(CommonsMultipartFile[] files, HttpServletRequest request) {
        for (CommonsMultipartFile multipartFile : files) {
            logger.info("文件名====>" + multipartFile.getOriginalFilename());
            if (multipartFile.isEmpty()) {
                return 1;
            }
            try {
                String path = request.getContextPath();
                String fileName = multipartFile.getOriginalFilename();
                int n = fileName.lastIndexOf('.');
                String newFileName = fileName.substring(0, n) + "-" + System.currentTimeMillis() + fileName.substring(n);
                FileOutputStream fileOutputStream = new FileOutputStream(new File(path + "/upload", newFileName));

                InputStream inputStream = multipartFile.getInputStream();

                byte[] bytes = new byte[1024];
                int len;
                while ((len = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                logger.error("上传文件异常", e);
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static int fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multiRequest.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = multiRequest.getFile(iterator.next());
                if (file != null) {
                    String fileName = file.getOriginalFilename();
                    if (StringUtils.isNotBlank(fileName)) {
                        logger.info(fileName);
                        String path = request.getContextPath();
                        int n = fileName.lastIndexOf('.');
                        String newFileName = fileName.substring(0, n) + "-" + System.currentTimeMillis() + fileName.substring(n);
                        File newFile = new File(path + "/upload", newFileName);
                        file.transferTo(newFile);
                        return 0;
                    }
                }
            }
        }
        return 1;
    }
}
