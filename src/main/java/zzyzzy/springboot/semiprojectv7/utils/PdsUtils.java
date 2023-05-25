package zzyzzy.springboot.semiprojectv7.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import zzyzzy.springboot.semiprojectv7.model.PdsAttach;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public HttpHeaders getHeader(String fname, String uuid) {
        fname = UriUtils.encode(fname, StandardCharsets.UTF_8);

        // 다운로드할 파일의 전체 경로 작성
        String dfname = makeDfname(fname, uuid);
        HttpHeaders header = new HttpHeaders();
        try {
            header.add("Content-Type",
                    Files.probeContentType(Paths.get(dfname)));
            header.add("Content-Disposition",
                    "attachment; filename=" + fname + "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return header;
    }

    private String makeDfname(String fname, String uuid) {
        int pos = fname.lastIndexOf(".");
        String name = fname.substring(0, pos);
        String ext = fname.substring(pos+1);

        return saveDir + name + uuid + "." + ext;
    }

    public UrlResource getResource(String fname, String uuid) {
        fname = UriUtils.encode(fname, StandardCharsets.UTF_8);

        // 다운로드할 파일 객체 생성
        UrlResource resource = null;
        try {
            resource = new UrlResource("file:" + makeDfname(fname, uuid));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resource;
    }
}
