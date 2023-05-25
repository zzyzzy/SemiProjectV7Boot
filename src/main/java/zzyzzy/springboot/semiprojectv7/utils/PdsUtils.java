package zzyzzy.springboot.semiprojectv7.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import zzyzzy.springboot.semiprojectv7.model.PdsAttach;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Component("pdsUtils")
public class PdsUtils {

    // 첨부파일 저장 위치
    @Value("${saveDir}") private String saveDir;

    public String makeUUID() {
        String uuid = LocalDate.now() + "" + LocalTime.now();
        uuid = uuid.replace("-", "").replace(":", "").replace(".", "");

        return uuid;
    }

    public PdsAttach processUpload(
            MultipartFile attach, Map<String, Object> pinfo) {

        // 업로드할 파일 정보 취득
        PdsAttach pa = new PdsAttach();
        pa.setPno((Integer) pinfo.get("pno"));

        pa.setFname( attach.getOriginalFilename() );

        // 파일명1 : abc123.png -> 파일종류 : png
        // pa.setFtype( pa.getFname().split("[.]")[1] );
        // 파일명2 : abc123.987xyz.jpg -> 파일종류 : jpg
        int pos = pa.getFname().lastIndexOf(".") + 1;
        String ftpye = pa.getFname().substring(pos);
        pa.setFtype(ftpye);

        pa.setFsize(attach.getSize()/1024 + "");

        // 첨부파일을 파일시스템에 저장
        // 시스템에 저장시 사용할 파일명 : 파일이름UUID.확장자
        String fname = pa.getFname().substring(0, pos-1);
        String savefname = saveDir + fname +
               pinfo.get("uuid") + "." + pa.getFtype();

        try {
            attach.transferTo(new File(savefname));
        } catch (Exception ex) {
            System.out.println("업로드중 오류발생!!");
            ex.printStackTrace();
        }

        return pa;
    }
}
