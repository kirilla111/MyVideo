package com.polytech.myvideo.db;

public class FavouriteDto {
    /**
     * ID
     */
    private String id;

    /**
     * Название файла
     */
    private String name;

    /**
     * Полный путь до файла/папки
     */
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public FavouriteDto(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

}
