package com.guli.edu.guliedu.handler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException {

    private Integer code;//状态吗
    private String message;//返回信息

}
