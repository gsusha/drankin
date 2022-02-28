package ru.sfedu.test.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sfedu.test.Constants;
import ru.sfedu.test.lab2.model.Beeean;
import ru.sfedu.test.lab2.model.Result;
import ru.sfedu.test.utils.HibernateUtil;

import java.util.List;

public class DataProviderHibernate {
    //TODO: Убрать после наследования
    private static final Logger log = LogManager.getLogger(DataProviderHibernate.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public List<Beeean> getAllEntity() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        SQLQuery sql = session.createSQLQuery("SELECT * FROM beeean");
        sql.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List list = sql.list();
        log.info(list);

        session.close();

        return list;
    }

    public Beeean getEntityById(long id) {
        return new Beeean();
    }

    public Beeean appendEntity(Beeean beeean) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(beeean);
        session.getTransaction().commit();
        session.close();
        return beeean;
    }

    public Result deleteEntity(long id) {
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }

    public Result updateEntity(Beeean beeean) {
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
