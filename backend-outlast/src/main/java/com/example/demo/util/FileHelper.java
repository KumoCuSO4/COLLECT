package com.example.demo.util;

import com.example.demo.vo.FileInfoVO;
import com.example.demo.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class FileHelper {
    public static final String[] ExeType = {"zip","rar"};
    public static final String[] DocType = {"pdf","md","doc","docx","txt"};
    public static final String[] PicType = {"jpg","png"};
    /**
     * 保存文件，自动生成UUID作为文件名
     * @param directoryPath 目录路径（以 / 结尾）
     * @param file 文件
     * @return 保存成功后的文件名
     */
    public static ResultVO<FileInfoVO> save(String directoryPath, String[] typeRestrict, MultipartFile file) {
        if(!checkDirectoryPath(directoryPath)) {
            return new ResultVO<>(Constant.REQUEST_FAIL,"发生错误");
        }
        String size = String.format("%.2f", (file.getSize() * 1.0 / 1024 / 1024)) + " MB";
        String originalName = file.getOriginalFilename();
        String type = "unknown";
        if(originalName!=null && originalName.lastIndexOf(".")!=-1 && originalName.lastIndexOf(".")!=0){
            // 取扩展名
            type = originalName.substring(originalName.lastIndexOf(".")+1);
            if(type.equals("unknown") || !checkType(type,typeRestrict)) {
                return new ResultVO<>(Constant.REQUEST_FAIL,"文件格式不支持，允许的格式为："+ StringUtils.join(typeRestrict, ","));
            }
        }
        String uuid = UUID.randomUUID().toString();
        uuid = uuid + "." + type;
        try{
            // 根据目标地址构造文件输出流
            FileOutputStream fileOutputStream = new FileOutputStream(directoryPath + uuid);
            // 将文件复制到映射的地址
            FileCopyUtils.copy(file.getInputStream(),fileOutputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功上传", new FileInfoVO(uuid, type, size));
    }

    public static boolean checkType(String type, String[] typeRestrict) {
        for(String t:typeRestrict) {
            if(type.equals(t)) return true;
        }
        return false;
    }

    /**
     * 检查目录路径是否有效
     * @param directoryPath 目录路径
     * @return 若目录不存在则返回false，否则返回true
     */
    private static boolean checkDirectoryPath(String directoryPath) {
        File dir = new File(directoryPath);
        if(!dir.exists() || !dir.isDirectory()){
            return dir.mkdirs();
        }
        return true;
    }

    /**
     * 下载文件，根据uuid查找文件
     * @param directoryPath 文件目录
     * @param uuid 文件uuid
     * @param name 文件名
     * @param response 响应
     */
    public static void downloadFile(String directoryPath, String uuid, String name, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        response.setContentType("application/x-msdownload");
        try {
            Path filePath = Paths.get(directoryPath+uuid);
            Resource resource = new UrlResource(filePath.toUri());
            if(!resource.exists()) return;
            inputStream = resource.getInputStream();
            //设置文件ContentType类型
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = response.getOutputStream();
            //设置 header  Content-Disposition
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
            int b = 0;
            byte[] buffer = new byte[2048];
            while (b != -1) {
                b = inputStream.read(buffer);
                if (b != -1) {
                    outputStream.write(buffer, 0, b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String[] getFileList(String directoryPath) {
        if(!checkDirectoryPath(directoryPath)) return null;
        File dir = new File(directoryPath);
        String[] fileList = dir.list();
        return fileList;
    }

    /**
     * 删除文件
     * @param path 文件路径
     * @param uuid 文件uuid
     */
    public static void deleteFile(String path, String uuid) {
        File file = new File(path+uuid);
        file.delete();
    }
}
