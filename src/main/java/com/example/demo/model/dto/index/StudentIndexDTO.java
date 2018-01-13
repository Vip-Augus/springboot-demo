package com.example.demo.model.dto.index;

import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.dto.MessageListDTO;
import lombok.Data;

import java.util.List;

/**
 * @author guojiawei
 * @date 2018/1/13
 */
@Data
public class StudentIndexDTO extends IndexDTO{
    protected List<MessageListDTO> messageList;
}
