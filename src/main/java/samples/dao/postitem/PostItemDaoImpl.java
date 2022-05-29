package samples.dao.postitem;

import samples.entity.PostItem;
import samples.enums.Status;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Stateless
public class PostItemDaoImpl implements PostItemDao {

    @PersistenceContext(unitName = "jeeHibernateSamples")
    private EntityManager entityManager;

    @Override
    public void insert(PostItem postItem) {
        entityManager.persist(postItem);
    }

    @Override
    public void update(PostItem postItem) {
        entityManager.merge(postItem);
    }

    @Override
    public void delete(PostItem postItem) {
        entityManager.remove(postItem);
    }

    public PostItem findById(Long id) {
        return entityManager.find(PostItem.class, id);
    }

    @Override
    public List<PostItem> find(Status status, Date time) {
        TypedQuery<PostItem> query = entityManager.createQuery("SELECT PI FROM PostItem PI" +
                " WHERE PI.status = :status AND PI.receiveDate <= :time", PostItem.class);
        query.setParameter("status", status);
        query.setParameter("time", time);
        return query.getResultList();
    }

    @Override
    public List<Long> findAllIdNotDeliveredOrderBYReceiveDateDesc() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT PI.id FROM PostItem PI" +
                " WHERE PI.status = :status" +
                " ORDER BY PI.receiveDate ASC", Long.class);
        query.setParameter("status", Status.NOT_DELIVERED);
        return query.getResultList();
    }

    @Override
    public List<PostItem> finaAll() {
        TypedQuery<PostItem> query = entityManager.createQuery("SELECT PI FROM PostItem PI", PostItem.class);
        return query.getResultList();
    }

    @Override
    public List<PostItem> findByIdList(List<Long> idList) {
        Query query = entityManager.createQuery("SELECT PI FROM PostItem PI" +
                " WHERE PI.id IN :idList" +
                " ORDER BY PI.receiveDate ASC");
        query.setParameter("idList", idList);
        return query.getResultList();
    }

    @Override
    public int count(Status status, Date time) {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM PostItem PI" +
                " WHERE PI.status = :status AND PI.receiveDate <= :time");
        query.setParameter("status", status);
        query.setParameter("time", time);
        return ((Number) query.getSingleResult()).intValue();
    }

}