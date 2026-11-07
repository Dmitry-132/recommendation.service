package star.bank.recommendation.service.model;

public class DynamicRuleDto {

    private String id; //uuid
    private String name;
    private String description;
    private String rule;


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "DynamicRuleDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }
}
