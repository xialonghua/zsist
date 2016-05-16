package com.jiafang.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.VersionDao;
import com.jiafang.model.Version;

@Repository
public class VersionDaoImpl implements VersionDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public VersionDaoImpl() {
	}
	
	@Override
	public Version queryVersion(String channel, int platform) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Version.class)
				.add(Restrictions.and(Restrictions.eq("platform", platform), Restrictions.eq("channel", channel))).addOrder(Order.desc("version"));
		query.setMaxResults(1);
		return (Version) query.uniqueResult();
	}

}
