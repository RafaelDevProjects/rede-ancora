package br.com.redeAncora.app.Domain;

import java.io.Serializable;

import br.com.redeAncora.app.dto.PecaDTO;

/**
 * Classe que representa uma peca no sistema.
 * Implementa Serializable para permitir a transferência de objetos entre Activities.
 */
public class PecasDomain implements Serializable {
    private String id;
    private String title;
    private String description;
    private String picUrl;
    private String detalhes;
    private String marca;
    private String HighestSpeed;
    private double price;
    private double rating;
    private String category; // Adicionando categoria
    private boolean isFavorito;

    public PecasDomain(PecaDTO dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.detalhes = dto.getDetalhes();
        this.HighestSpeed = dto.getHighestSpeed();
        this.marca = dto.getMarca();
        this.picUrl = dto.getPicUrl();
        this.rating = dto.getRating();
        this.price = dto.getPrice();
        this.isFavorito = dto.getisFavorito();
        this.category = dto.getCategory();
    }

    public boolean getisFavorito() {
        return isFavorito;
    }

    public void setFavorito(boolean favorito) {
        this.isFavorito = favorito;
    }

    public PecasDomain() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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
        return HighestSpeed;
    }

    public void setHighestSpeed(String highestSpeed) {
        HighestSpeed = highestSpeed;
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
}