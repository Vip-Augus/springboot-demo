package com.example.demo.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共文件DTO
 *
 * @author by JingQ on 2018/1/15
 */
@Data
public class PubFileDTO implements Serializable{

    private static final long serialVersionUID = -8818231204010181090L;

    private Integer id;

    private String name;

    private Integer isDirectory;

    private String fileUrl;

    private Integer createId;

    private Date uploadDate;

    private String uploadTime;

    private String idNumber;

    private String parentPath;

}
