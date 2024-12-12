package com.emadesko.core.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.emadesko.core.repository.Repository;
import com.emadesko.datas.entities.Entite;

public class RepositoryJpa<T extends Entite> implements Repository<T> {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("mySqlUnit");
    protected EntityManager em;
    protected Class<T> clazz;

    public RepositoryJpa(Class<T> clazz) {
        if (em == null) {
            this.em = emf.createEntityManager();
        }
        this.clazz = clazz;
    }

    @Override
    public void insert(T object) {
        object.setId(0);
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }


    @Override
    public List<T> select() {
        return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz).getResultList();
    }

    public T selectBy(String condition, String paramName, Object paramValue) {
        try {
            return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE " + condition, clazz)
                    .setParameter(paramName, paramValue)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<T> selectManyBy(String condition, String paramName, Object paramValue) {
        try {
            return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e WHERE " + condition, clazz)
                    .setParameter(paramName, paramValue)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(T object) {
        try {
            em.getTransaction().begin();
            if (object != null) {
                em.remove(object);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
    @Override
    public void update(T object) {
        try {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public T getById(int id) {
        return em.find(clazz, id);
    }
}
