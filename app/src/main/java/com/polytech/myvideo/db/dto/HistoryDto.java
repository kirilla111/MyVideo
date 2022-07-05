package com.polytech.myvideo.db.dto;

public class HistoryDto extends FavouriteDto {
    /**
     * Дата обращения
     */
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HistoryDto(String id, String name, String path, String date) {
        super(id,name, path);
        this.date = date;
    }
}
