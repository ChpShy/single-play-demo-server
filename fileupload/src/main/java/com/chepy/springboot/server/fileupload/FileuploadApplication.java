package com.chepy.springboot.server.fileupload;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@SpringBootApplication
public class FileuploadApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(FileuploadApplication.class);
        builder.bannerMode(Banner.Mode.OFF).run(args);
//        SpringApplication.run(FileuploadApplication.class, args);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String index(String data, HttpServletRequest request, HttpServletResponse response) throws IOException {
       String val = request.getParameter("data");
        String fileStr = val.split(",")[1];
        BASE64Decoder decoder = new BASE64Decoder();
        //Base64解码
        byte[] bt = decoder.decodeBuffer(fileStr);
//        byte[] bt = Base64.decodeBase64(fileStr);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        OutputStream out = null;
        try {
            file = new File("D:\\"+System.currentTimeMillis()+".png");
            for(int i=0;i<bt.length;++i)
            {
                if(bt[i]<0)
                {//调整异常数据
                    bt[i]+=256;
                }
            }

            out = new FileOutputStream("D:\\"+System.currentTimeMillis()+".png");
            out.write(bt);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            out.close();
        }

        response.setHeader("Access-Control-Allow-Origin", "*");
        return "this is a spring boot project";
    }
}
