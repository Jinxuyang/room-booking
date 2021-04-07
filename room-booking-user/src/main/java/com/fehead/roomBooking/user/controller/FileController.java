package com.fehead.roomBooking.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author Verge
 * @Date 2021/4/6 16:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/v1/file")
@Slf4j
public class FileController {

    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName,
                             HttpServletResponse response) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("/static/"+fileName);

        InputStream fis = classPathResource.getInputStream();

        response.setContentType("application/force-download");
        response.addHeader("Content-disposition", "attachment;fileName=" + fileName);

        OutputStream os = response.getOutputStream();

        byte[] buf = new byte[1024];
        int len;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

   /* @PostMapping("/upload")
    public CommonReturnType uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ClassPathResource resource = new ClassPathResource("/static/"+file.getName());

        File dest = resource.getFile();
        try {
            file.transferTo(dest);
            return CommonReturnType.create("上传成功");
        } catch (Exception e){
            return CommonReturnType.create(e.getMessage(),"failed");
        }
    }*/
}
