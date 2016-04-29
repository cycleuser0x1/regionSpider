package com.gordon.discountCrawler.model;

import java.util.Date;

/**
 * Created by wwz on 2016/2/22.
 */
public class DiscountProduct {
    private String Id;
    private Date releasedTime;
    private String title;
    private Double discountedPrice;
    private Double price;
    private String url;

    public DiscountProduct(String id, Date releasedTime, String title, Double discountedPrice, Double price) {
        this.Id = id;
        this.releasedTime = releasedTime;
        this.title = title;
        this.discountedPrice = discountedPrice;
        this.price = price;
    }

    public DiscountProduct(String id, Date releasedTime, String title, Double discountedPrice, Double price, String url) {
        this.Id = id;
        this.releasedTime = releasedTime;
        this.title = title;
        this.discountedPrice = discountedPrice;
        this.price = price;
        this.url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Date getReleasedTime() {
        return releasedTime;
    }

    public void setReleasedTime(Date releasedTime) {
        this.releasedTime = releasedTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DiscountProduct{" +
                "Id='" + Id + '\'' +
                ", releasedTime=" + releasedTime +
                ", title='" + title + '\'' +
                ", discountedPrice=" + discountedPrice +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountProduct that = (DiscountProduct) o;

        if (Id != null ? !Id.equals(that.Id) : that.Id != null) return false;
        if (releasedTime != null ? !releasedTime.equals(that.releasedTime) : that.releasedTime != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (discountedPrice != null ? !discountedPrice.equals(that.discountedPrice) : that.discountedPrice != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (releasedTime != null ? releasedTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (discountedPrice != null ? discountedPrice.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
