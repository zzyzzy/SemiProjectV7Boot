package zzyzzy.springboot.semiprojectv7.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component("pdsUtils")
public class PdsUtils {
    public String makeUUID() {
        String uuid = LocalDate.now() + "" + LocalTime.now();
        uuid = uuid.replace("-", "").replace(":", "").replace(".", "");

        return uuid;
    }
}
