package com.jiafang.dao.impl;

import com.jiafang.service.Page;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.VersionDao;
import com.jiafang.model.Version;

import java.util.List;

@Repository
public class VersionDaoImpl implements VersionDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public VersionDaoImpl() {
	}
	
	@Override
    public Version queryVersion(String channel, int platform) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Version.class)
                .add(Restrictions.and(Restrictions.eq("platform", platform), Restrictions.eq("channel", channel))).addOrder(Order.desc("version")).addOrder(Order.desc("id"));
        query.setMaxResults(1);
        return (Version) query.uniqueResult();
    }

	public Version addVersion(Version version) {
		sessionFactory.getCurrentSession().saveOrUpdate(version);
		return version;
	}

	public void delVersion(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("delete from version where id=" + id);
        query.executeUpdate();
        session.getTransaction().commit();
	}

	@Override
	public List<Version> queryVersions(Page page, int platform) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Version.class)
				.add(Restrictions.eq("platform", platform)).addOrder(Order.desc("version")).addOrder(Order.desc("id"));
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getIndex());
		List<Version> list = query.list();
		query = sessionFactory.getCurrentSession().createCriteria(Version.class)
				.add(Restrictions.eq("platform", platform));
		query.setProjection(Projections.rowCount());
        page.setTotal(Integer.parseInt(query.uniqueResult().toString()));
		return list;
	}

}
