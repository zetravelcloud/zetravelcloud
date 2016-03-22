package com.zetravelcloud.webapp.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class GenericJPARepository<T, ID extends Serializable> extends SimpleJpaRepository<T, Serializable> implements BaseRepository<T, Serializable> {

    private final EntityManager em;
//    Class<T> typeParameterClass;
	
	public GenericJPARepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
	}

//	public GenericJPARepository(Class<T> domainClass, EntityManager em) {
//		super(domainClass, em);
//		this.em = em;
//		this.typeParameterClass = domainClass;
//	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T entity){
		Session session = this.em.unwrap(Session.class);
		Example example = Example.create(entity);
		Criteria criteria = session.createCriteria(super.getDomainClass()).add(example);
		return criteria.list();
	}

}
