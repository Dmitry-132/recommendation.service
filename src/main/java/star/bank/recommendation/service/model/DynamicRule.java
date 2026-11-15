package star.bank.recommendation.service.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "dynamic_rule")
public class DynamicRule {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rule_condition", nullable = false, columnDefinition = "jsonb")
    private String ruleCondition;

    @Column(name = "hit_count", nullable = false)
    private Long hitCount = 0L;

    public DynamicRule() {
    }

    public DynamicRule(UUID id, String name, String description, String ruleCondition) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ruleCondition = ruleCondition;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRuleCondition() {
        return ruleCondition;
    }

    public Long getHitCount() {return hitCount;}

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public void setHitCount(Long hitCount) { this.hitCount = hitCount; }

    @Override
    public String toString() {
        return "DynamicRule{" +
                " name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}