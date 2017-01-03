package com.dm.platform.model;

public class TDictItem {
    private Integer itemId;

    private String itemCode;

    private String itemName;

    private Long itemSeq;

    private Integer dictId;

    private Integer itemPid;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public Long getItemSeq() {
        return itemSeq;
    }

    public void setItemSeq(Long itemSeq) {
        this.itemSeq = itemSeq;
    }

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getItemPid() {
        return itemPid;
    }

    public void setItemPid(Integer itemPid) {
        this.itemPid = itemPid;
    }
}