package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.ExperimentDetail;
import com.example.demo.model.ExperimentMessage;
import com.example.demo.model.ExperimentRecord;
import com.example.demo.model.Notice;
import com.example.demo.model.User;
import com.example.demo.service.*;
import com.example.demo.util.SessionUtil;
import com.example.demo.util.result.ListResult;
import com.example.demo.util.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 实验课记录控制器
 * Author by JingQ on 2018/1/6
 */
@Slf4j
@RestController
@RequestMapping("/web/epRecord")
public class ExperimentRecordController {


    private static final String BASE_DIR = "record/";
    @Autowired
    private ExperimentRecordService experimentRecordServiceImpl;

    @Autowired
    private FileManageService fileManageServiceImpl;

    @Autowired
    private ExperimentMessageService messageService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ExperimentUserService experimentUserService;

    @Autowired
    private ExperimentService experimentService;

    @Autowired
    private ExperimentDetailService experimentDetailServiceImpl;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public JSON addRecord(@RequestParam("epName") String epName,
                          @RequestParam("epId") Integer epId,
                          @RequestParam("uploadEndTime") String uploadEndTime,
                          @RequestParam("classroomId") Integer classroomId,
                          @RequestParam("file") MultipartFile file,
                          HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        if (file.isEmpty()) {
            log.error("未选择材料,发布实验失败: ", epId, epName);
            result.returnError("未选择材料,发布实验失败");
        } else {
            try {
                StringBuilder filePath = new StringBuilder(BASE_DIR);
                filePath.append(epName).append("/").append(epId).append("/");
                String url = fileManageServiceImpl.upload(file, filePath.toString());
                ExperimentRecord record = convertRecord(epName, epId, uploadEndTime, url, classroomId);
                result.returnSuccess(experimentRecordServiceImpl.add(record));
                log.info("成功添加实验记录:", record.getId());
                sendMessage(record, request);
            } catch (Exception e) {
                log.error("发布实验失败: ", epName, e);
                result.returnError("发布实验失败");
            }
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public JSON updateRecord(@RequestBody ExperimentRecord record, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        try {
            experimentRecordServiceImpl.update(record);
            result.returnSuccess(record);
            log.info("成功修改实验记录: ", record.getId());
        } catch (Exception e) {
            log.error("修改实验内容失败:", record.getId(), e);
            result.returnError("修改实验内容失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JSON getRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<ExperimentRecord> result = new SingleResult<>();
        try {
            ExperimentRecord record = experimentRecordServiceImpl.getById(id);
            result.returnSuccess(record);
        } catch (Exception e) {
            log.error("查询实验内容失败:", id, e);
            result.returnError("查询实验内容失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    @ResponseBody
    public JSON deleteRecord(@RequestParam("id") Integer id, HttpServletRequest request) {
        SingleResult<Integer> result = new SingleResult<>();
        try {
            result.returnSuccess(experimentRecordServiceImpl.deleteById(id));
            log.error("删除实验内容成功:", id);
        } catch (Exception e) {
            log.error("删除实验内容失败:", id, e);
            result.returnError("删除实验内容失败");
        }
        return (JSON) JSON.toJSON(result);    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSON queryListByEPId(@RequestParam("epId") Integer epId) {
        ListResult<ExperimentRecord> result = new ListResult<>();
        try {
            result.returnSuccess(experimentRecordServiceImpl.getListByEPId(epId));
        } catch (Exception e) {
            log.error("查询实验内容列表失败:", epId, e);
            result.returnError("查询实验内容列表失败");
        }
        return (JSON) JSON.toJSON(result);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ResponseBody
    public void exportScore(@RequestParam("epRecordId")Integer epRecordId, HttpServletRequest request, HttpServletResponse response) {
        ExperimentRecord record = experimentRecordServiceImpl.getById(epRecordId);
        try {
            List<ExperimentDetail> details = experimentDetailServiceImpl.getDetailsByEPRecordId(epRecordId, null);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;fileName="+ URLEncoder.encode(record.getEpName(), "UTF-8") + ".xls");
            HSSFWorkbook wb = export(details, record.getEpName());
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            log.error("导出分数失败: ", e);
        }
    }

    private ExperimentRecord convertRecord(String epName, Integer epId, String uploadEndTime, String fileUrl, Integer classroomId) {
        ExperimentRecord record = new ExperimentRecord();
        record.setEpId(epId);
        record.setEpName(epName);
        record.setUploadEndTime(uploadEndTime);
        record.setEpFileUrl(fileUrl);
        record.setClassroomId(classroomId);
        return record;
    }

    private void sendMessage(ExperimentRecord record, HttpServletRequest request) {
        try {
            User user = SessionUtil.getUser(request.getSession());
            Notice notice = new Notice();
            notice.setTitle("创建实验课" + record.getEpName() + "通知");
            notice.setContent(getContent(record));
            notice.setCreateId(user.getId());
            notice.setEpId(record.getEpId());
            notice.setCreateTime(new Date());
            noticeService.add(notice);
            List<Integer> studentIds = experimentUserService.getUserIdsByEpId(record.getEpId());
            new Thread() {
                @Override
                public void run() {
                    for(Integer id : studentIds) {
                        ExperimentMessage message = new ExperimentMessage();
                        message.setFromId(user.getId());
                        message.setToId(id);
                        message.setEpId(record.getEpId());
                        message.setTitle(notice.getTitle());
                        message.setContent(notice.getContent());
                        message.setHasRead(0);
                        message.setCreatedDate(new Date());
                        message.setConversationId(message.getConversationId());
                        messageService.addMessage(message);
                    }
                }
            }.start();
        } catch (Exception e) {
            log.error("发送消息失败：", e);
        }
    }

    private String getContent(ExperimentRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("您的实验课： ");
        sb.append(experimentService.getById(record.getEpId()).getName());
        sb.append(" 发布了新的实验：");
        sb.append(record.getEpName());
        sb.append(", 请前往【查看实验课】模块查看详情。");
        return sb.toString();
    }


    private static final String[] excelHeader = {"学号", "分数", "评语", "作业链接"};
    private HSSFWorkbook export(List<ExperimentDetail> details, String sheetName) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();

        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
        }

        for (int i = 0; i < details.size(); i++) {
            row = sheet.createRow(i + 1);
            ExperimentDetail detail = details.get(i);
            row.createCell(0).setCellValue(detail.getIdNumber());
            row.createCell(1).setCellValue(detail.getScore());
            row.createCell(2).setCellValue(detail.getComment());
            row.createCell(3).setCellValue(detail.getEpFileUrl());
        }

        sheet.autoSizeColumn((short)0); //调整第一列宽度
        sheet.autoSizeColumn((short)1); //调整第二列宽度
        sheet.autoSizeColumn((short)2); //调整第三列宽度
        sheet.autoSizeColumn((short)3); //调整第四列宽度

        return wb;
    }
}
