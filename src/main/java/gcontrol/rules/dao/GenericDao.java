package gcontrol.rules.dao;
import java.io.File;
/**
 * This package contain  class as Repository is used to call the GenericDao
 */
/**
 * To Import Classes to access their functionality
 */
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * This class use as Repository to call all Method in all possible cases to
 * interact with Databases for their different CRUD and other Functionality
 * 
 * @author Sachin Nahar
 *
 */
@Repository
@SuppressWarnings("rawtypes")
@Transactional
public class GenericDao 
{	
	private SessionFactory sessionFactory;
	private Session session;	
	@SuppressWarnings("deprecation")
	public GenericDao()
	{
		try
		{
			File f = new File(System.getenv("Rule_Engine")+"/template/hibernate.cfg.xml");
			Configuration config = new Configuration();
	  	  	sessionFactory = config.configure(f).buildSessionFactory();
			/*Configuration cfg=new Configuration();    
			cfg.configure("hibernate.cfg.xml"); 
			sessionFactory=cfg.buildSessionFactory(); */   
	  	  	session=sessionFactory.openSession();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * save() method use for save the data in database
	 */
	public Object save(Object object) {
		try {

			Object obj = sessionFactory.getCurrentSession().save(object);
			System.out.println("save successfully");
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * update() method use for update row the data in database
	 */
	public Object update(Object object) {
		try {
			sessionFactory.getCurrentSession().update(object);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * saveAndUpdate() method use for save and update row in database
	 */
	public Object saveAndUpdate(Object object) {
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(object);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * saveOrUpdateAll() method use for save and update all data in database
	 */
	public Object saveOrUpdateAll(Collection collection) {
		try {

			for (Object object : collection) {
				sessionFactory.getCurrentSession().saveOrUpdate(object);

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * delete() method use for delete data in database
	 */
	public Object delete(Object object) {
		try {
			sessionFactory.getCurrentSession().delete(object);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * findAll() method use for find all data in database
	 */
	public Object findAll(Class clz) {
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clz);

			Object object = criteria.list();

			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * findByID() method use for find data by id in database
	 */
	public Object findByID(Class clz, Object value) {
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clz).add(Restrictions.idEq(value));

			Object object = criteria.uniqueResult();

			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * findByColumn() method use for find data by column in database
	 */
	public Object findByColumn(Class clz, String key, Object value) {
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clz).add(Restrictions.eq(key, value));

			Object object = criteria.list();

			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * findByColumn() method use for find data by column in database
	 */
	public Object findByColumnUnique(Class clz, String key, Object value) {
		try {

			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clz).add(Restrictions.eq(key, value));

			Object object = criteria.uniqueResult();

			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * executeSqlQuery() method used to execute query in database
	 * 
	 * @param sql
	 * @return results
	 */
	public Object executeSqlQuery(String sql) {
		try {
			
			System.out.println("executing query ::-"+sql);
			
			Transaction tx=	sessionFactory.getCurrentSession().beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			
			System.out.println("insert or update successfully");
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * executeAnySqlQuery() method used to execute any sql query in database
	 * 
	 * @param sql
	 * @return results
	 */
	public Object executeAnySqlQuery(String sql) {
		try {

			
			Session session1=sessionFactory.openSession();
			Transaction tx=	session.beginTransaction();
				SQLQuery query = session.createSQLQuery(sql);
			 System.out.println("Query "+query);
			query.executeUpdate();
			
			tx.commit();
			
			session1.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * executeSqlQuery() method used to execute query in database when query anf
	 * class is passed
	 * 
	 * @param sql
	 * @param clz
	 * @return results
	 */
	public Object executeSqlQuery(String sql, Class clz) {
		try {

			SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.addEntity(clz);
			Object results = query.list();

			return results;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * executeProcesure() method is used to execute the Procesure
	 * 
	 * @param clz
	 * @param sql
	 * @param objects
	 * @return result
	 */

	public Object executeThirdPartyProcesure(Class clz, String sql) {

		Object result = new Object();
		System.out.println("sql-"+sql);
		Query query = session.createSQLQuery(sql);

		if (clz != null) {
			query.setResultTransformer(Transformers.aliasToBean(clz));
		} else {

			query.setResultTransformer(AliasToEntityLinkedHashMapResultTransformer.INSTANCE);
		}

		/*for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
*/
		result= query.list();

		return result;
	}

	
	
	public Object executeDynamicProcesure(Class clz, String sql,Object ...objects) {

		Object result = new Object();
		System.out.println("sql-"+sql);
		
		Query query = session.createSQLQuery(sql);

		if (clz != null) {
			query.setResultTransformer(Transformers.aliasToBean(clz));
		} else {

			query.setResultTransformer(AliasToEntityLinkedHashMapResultTransformer.INSTANCE);
		}

		for (int i = 0; i < objects.length; i++) {
			System.out.println(objects[i]);
			
			query.setParameter(i, objects[i]);
		}

		result= query.list();

		return result;
	}
}
