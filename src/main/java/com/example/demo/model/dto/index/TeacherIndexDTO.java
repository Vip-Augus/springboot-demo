package com.example.demo.model.dto.index;

import com.example.demo.model.dto.ScoreDTO;
import lombok.Data;

import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/13
 */
@Data
public class TeacherIndexDTO extends IndexDTO{
    protected List<ScoreDTO> scoreLsit;
}
