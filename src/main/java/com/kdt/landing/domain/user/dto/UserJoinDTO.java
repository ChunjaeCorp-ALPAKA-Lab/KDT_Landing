package com.kdt.landing.domain.user.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.ColumnDefault;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @NotBlank(message = "**")
    private String pw;

    @NotBlank(message = "**")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @ColumnDefault("0")
    private int active;

    private String nowPassword;
    private String passwordConfirm;
}