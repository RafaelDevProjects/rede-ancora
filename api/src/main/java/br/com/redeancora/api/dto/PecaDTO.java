package br.com.redeancora.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


public class PecaDTO {
    private String id;
    private String title;
    private String description;
    private String picUrl;
    private String detalhes;
    private String marca;
    private String highestSpeed;
    private double price;
    private double rating;
    private String category;
    @JsonProperty("isFavorito")
    private boolean isFavorito;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getHighestSpeed() {
        return highestSpeed;
    }

    public void setHighestSpeed(String highestSpeed) {
        this.highestSpeed = highestSpeed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean getisFavorito() {
        return isFavorito;
    }

    public void setisFavorito(boolean favorito) {
        isFavorito = favorito;
    }
}
