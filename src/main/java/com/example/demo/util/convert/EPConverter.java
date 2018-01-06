package com.example.demo.util.convert;

import com.example.demo.model.Experiment;
import com.example.demo.model.dto.ExperimentDTO;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 实验课程信息转换器
 * Author by JingQ on 2018/1/2
 */
@Service
public class EPConverter extends ModelMapper {

    public ExperimentDTO ep2DTO(Experiment ep) {
        if (ep == null) {
            return null;
        }
        return this.map(ep, ExperimentDTO.class);
    }

    public List<ExperimentDTO> epList2DTOList(List<Experiment> epList) {
        if (CollectionUtils.isEmpty(epList)) {
            return Lists.newArrayList();
        }
        return this.map(epList, new TypeToken<List<ExperimentDTO>>() {
        }.getType());
    }

}
