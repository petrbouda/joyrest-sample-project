package org.joyrest.sample.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed implements Serializable {

    private static final long serialVersionUID = 8612496546494451823L;

    private String title;
    private String description;
    private Date publishDate;

    public Feed() {
    }

    public Feed(String title, String description, Date publishDate) {
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feed)) return false;
        Feed feed = (Feed) o;
        return Objects.equals(title, feed.title) &&
                Objects.equals(description, feed.description) &&
                Objects.equals(publishDate, feed.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, publishDate);
    }
}
