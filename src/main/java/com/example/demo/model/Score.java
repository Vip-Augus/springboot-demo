package com.example.demo.model;

import java.io.Serializable;

public class Score implements Serializable {
    private Integer id;

    private Integer eprecordId;

    private Integer teacherId;

    private Integer studentId;

    private Integer score;

    private String comment;

    private String createDate;

    private String modifyDate;

    private Integer submit;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEprecordId() {
        return eprecordId;
    }

    public void setEprecordId(Integer eprecordId) {
        this.eprecordId = eprecordId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", eprecordId=").append(eprecordId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", studentId=").append(studentId);
        sb.append(", score=").append(score);
        sb.append(", comment=").append(comment);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", submit=").append(submit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}