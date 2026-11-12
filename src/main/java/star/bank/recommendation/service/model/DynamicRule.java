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
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @JdbcTypeCode(SqlTypes.JSON) // указывает тип для Hibernate
    @Column(name = "rule_condition", nullable = false, columnDefinition = "jsonb") //сырой SQL-тип данных, который Hibernate подставит при генерации таблицы (если у тебя включен ddl-auto), либо который он будет использовать для маппинга.
    //(JSON Binary) хранит данные не как текст, а в бинарном оптимизированном формате
    private String ruleCondition;

    @Column(name = "hit_count", nullable = false)
    private Long hitCount = 0L;

    @Transient
    private boolean isNew = true; //добавил, когда пытался решить проблему с переносом UUID в создаваемые рекомендации. Проверить, можно ли его убрать, так как по итогу просто вписал все нужное в сам метод

    public DynamicRule() { //проверить нужны ли? они вроде ток на финал поля или для доп логики
    }
    public DynamicRule(String name, String description, String ruleCondition) {
        this.name = name;
        this.description = description;
        this.ruleCondition = ruleCondition;
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