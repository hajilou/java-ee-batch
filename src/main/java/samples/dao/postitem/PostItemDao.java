package samples.dao.postitem;

import samples.entity.PostItem;
import samples.enums.Status;

import java.util.Date;
import java.util.List;

public interface PostItemDao {

    public void insert(PostItem postItem);

    public void update(PostItem postItem);

    public void delete(PostItem postItem);

    public PostItem findById(Long id);

    List<PostItem> find(Status status, Date time);

    List<Long> findAllIdNotDeliveredOrderBYReceiveDateDesc();

    int count(Status status, Date time);

    List<PostItem> findByIdList(List<Long> split);

    List<PostItem> finaAll();
}