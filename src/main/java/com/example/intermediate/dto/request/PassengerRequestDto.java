package com.example.intermediate.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequestDto {



    //성별
    @NotBlank
    private String gender;
    //국적
    @NotBlank
    private String country;
    //이름
    @NotBlank
    @Size(min = 2, max = 8)
    @Pattern(regexp = "^[A-Za-z가-힣]+$")
    private String name;
    //생일
    @NotBlank
    private String birth;
    //이메일
    @NotBlank
    private String email;
    //전화번호
    @NotBlank
    private String number;


}
