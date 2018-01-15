package com.example.demo.util.convert;

import com.example.demo.model.PubFile;
import com.example.demo.model.dto.PubFileDTO;
import com.example.demo.util.PeriodUtil;
import com.google.common.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 公共文件Converter
 *
 * @author by JingQ on 2018/1/15
 */
@Service
public class PubFileConverter extends ModelMapper {

    /**
     * dto ---> do
     * @param dto   dto
     * @return  do
     */
    public PubFile dto2DO(PubFileDTO dto) {
        if (dto == null) {
            return null;
        }
        return this.map(dto, PubFile.class);
    }

    /**
     * do List ---> dto List
     * @param files  do List
     * @return      dto List
     */
    public List<PubFileDTO> files2DTOs(List<PubFile> files) {
        if (CollectionUtils.isEmpty(files)) {
            return null;
        }
        List<PubFileDTO> fileDTOS = this.map(files, new TypeToken<List<PubFileDTO>>() {
        }.getType());
        for (PubFileDTO dto : fileDTOS) {
            dto.setUploadTime(PeriodUtil.format(dto.getUploadDate(), "yyyy-MM-dd HH:mm:ss"));
        }
        return fileDTOS;
    }

}
