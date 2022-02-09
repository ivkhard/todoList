package org.example.myWork.logic;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskDaoImpl implements CustomTaskDao {
    private final EntityManager entityManager;

    @Override
    public List<Task> findAllFiltered(String query, boolean excludeCompleted, User user) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Task t ");
        List<String> conditions = new ArrayList<>(2);
        List<Pair<String, Object>> parameters = new ArrayList<>(2);
        if (Strings.isNotBlank(query)) {
            conditions.add("t.description like :desc");
            parameters.add(new Pair<String, Object>("desc", "%" + query + "%"));
        }
        if (excludeCompleted) {
            conditions.add("t.done <> true");
        }
        if (user != null) {
            conditions.add("t.owner.id = " + user.getId());
        }
        if (!conditions.isEmpty()) {
            jpql.append("WHERE ").append(String.join(" AND ", conditions));
        }
        TypedQuery<Task> typedQuery = entityManager.createQuery(jpql.toString(), Task.class);
        for (Pair<String, Object> param : parameters) {
            typedQuery.setParameter(param.getKey(), param.getValue());
        }
        return typedQuery.getResultList();
    }
}
