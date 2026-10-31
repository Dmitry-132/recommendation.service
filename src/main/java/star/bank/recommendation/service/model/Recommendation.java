package star.bank.recommendation.service.model;

import java.util.UUID;

public class Recommendation {
    private UUID id;
    private String name;
    private String text;

    public Recommendation() {
    }

    public Recommendation(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
