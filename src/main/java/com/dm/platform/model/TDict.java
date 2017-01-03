package com.dm.platform.model;

public class TDict {
    private Integer dictId;

    private String dictDesc;

    private String dictName;

    private Long dictSeq;

    private Long dictStatus;

    private String dictType;

    private String dictCode;

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc == null ? null : dictDesc.trim();
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public Long getDictSeq() {
        return dictSeq;
    }

    public void setDictSeq(Long dictSeq) {
        this.dictSeq = dictSeq;
    }

    public Long getDictStatus() {
        return dictStatus;
    }

    public void setDictStatus(Long dictStatus) {
        this.dictStatus = dictStatus;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType == null ? null : dictType.trim();
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode == null ? null : dictCode.trim();
    }
}