package lukuvinkit;

import java.util.List;

public class ReadingTip {

    private String title;
    private String description;
    private String author;
    private String url;
    private List<String> tags;

    public ReadingTip(String title, String description, String author, String url, List<String> tags) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.url = url;
        this.tags = tags;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
