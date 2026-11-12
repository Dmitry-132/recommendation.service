package star.bank.recommendation.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import star.bank.recommendation.service.model.DynamicRule;

import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository<DynamicRule, UUID> {

    @Modifying  //обозначает запрос как UPDATE/DELETE (по умолчанию все @Query запросы-SELECT)
    @Transactional //откатит транзакцию в случае сбоя
    @Query("UPDATE DynamicRule r SET r.hitCount = r.hitCount + 1 WHERE r.id = :id") //@Query использует JPQL. можно использовать '?', но нужно его нумеровать ?1, ?2
    void incrementHitCount(@Param("id") UUID id);
}
// SQL-триггеры по идее надо пилить на уровне БД, позже это надо будет доучить
