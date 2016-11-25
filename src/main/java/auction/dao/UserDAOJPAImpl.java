package auction.dao;

import auction.domain.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    private final EntityManager entityManager;

    //private HashMap<String, User> users;

    public UserDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        //users = new HashMap<String, User>();
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("User.count", User.class);
        return ((Long)q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }
        entityManager.persist(user);
    }

    @Override
    public void edit(User user) {
        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }
        entityManager.merge(user);
    }


    @Override
    public List<User> findAll() {
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(User.class));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public User findByEmail(String email) {
        Query q = entityManager.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);
        return (User)q.getSingleResult();
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }
}
