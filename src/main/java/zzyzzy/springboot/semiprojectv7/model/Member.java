package zzyzzy.springboot.semiprojectv7.model;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mbno;

    private String name;
    private String jumin1;
    private String jumin2;

    @NotBlank(message = "아이디는 필수항목입니다!!")
    @Length(min=6, max=16, message = "아이디는 최소6자, 최대16자이어야 합니다!!")
    private String userid;

    @NotBlank(message = "비밀번호는 필수항목입니다!!")
    @Length(min=6, max=16, message = "비밀번호는 최소6자, 최대16자이어야 합니다!!")
    private String passwd;

    private String zipcode;

    @NotBlank(message = "기본주소는 필수항목입니다!!")
    private String addr1;

    @NotBlank(message = "나머지주소는 필수항목입니다!!")
    private String addr2;

    private String email;

    private String phone;

    @CreatedDate
    @Column(insertable = false, updatable = false)
    private LocalDateTime regdate;

    // -----------------------------

    @Transient   // 필드 영속성은 지원 x
    @NotBlank(message = "우편번호는 필수항목입니다!!")
    private String zip1;

    @Transient
    @NotBlank(message = "우편번호는 필수항목입니다!!")
    private String zip2;

    @Transient
    @NotBlank(message = "이메일은 필수항목입니다!!")
    private String email1;

    @Transient
    @NotBlank(message = "이메일은 필수항목입니다!!")
    private String email2;

    @Transient
    @NotBlank(message = "전화번호는 필수항목입니다!!")
    private String tel2;

    @Transient
    @NotBlank(message = "전화번호는 필수항목입니다!!")
    private String tel3;

}
