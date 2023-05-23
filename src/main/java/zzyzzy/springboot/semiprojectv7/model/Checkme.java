package zzyzzy.springboot.semiprojectv7.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class Checkme {

    @NotBlank(message = "이름은 반드시 입력하셔야 합니다!!")
    private String name;

    @NotBlank(message = "주민번호는 반드시 입력하셔야 합니다!!")
    private String jumin1;

    @NotBlank(message = "주민번호는 반드시 입력하셔야 합니다!!")
    private String jumin2;

}
