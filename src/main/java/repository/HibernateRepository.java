package repository;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created by steven on 26/08/16.
 */
public class HibernateRepository extends RepositoryImpl {
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Override
    public <T> List<T> read(String queryString, Map<String, Object> params) {
        final Session session = getSession();
        try {
            Query query = session.createQuery(queryString);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            final List<T> entities = query.list();
            return entities;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T readSingleItem(String queryString, Map<String, Object> params) {
        final Session session = getSession();
        try {
            Query query = session.createQuery(queryString);
            if (params != null && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            final T entity = (T)query.list().get(0);
            return entity;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public <T> T write(T item) {
        final Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();
            session.persist(item);
            tx.commit();
            return item;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            session.close();
        }
    }
}
