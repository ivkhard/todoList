package org.example.myWork.logic;

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
        StringBuilder jpql = new StringBuilder("from Task t ");
        List<String> conditions = new ArrayList<>(2);
        TypedQuery<Task> typedQuery = entityManager.createQuery(jpql.toString(), Task.class);
        if (Strings.isNotBlank(query)) {
            conditions.add("t.description like :desc");
            typedQuery.setParameter("desc", "%" + query + "%");
        }
        if (excludeCompleted) {
            conditions.add("t.done <> true");
        }
        if (user != null) {
            conditions.add("t.owner like :");
            typedQuery.setParameter("own", "%" + user + "%");
        }
        if (!conditions.isEmpty()) {
            jpql.append("where ").append(String.join(" and ", conditions));
        }
        return typedQuery.getResultList();
    }
}
