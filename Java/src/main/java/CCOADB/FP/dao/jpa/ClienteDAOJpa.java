package CCOADB.FP.dao.jpa;

import CCOADB.FP.dao.ClienteDAO;
import CCOADB.FP.modelo.Cliente;
import CCOADB.FP.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClienteDAOJpa implements ClienteDAO {

    public void insertar(Cliente cliente) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Cliente> obtenerTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Cliente> query =
                    em.createQuery("SELECT c FROM Cliente c", Cliente.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    public Cliente buscarPorEmail(String email) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Cliente> query =
                    em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);

            query.setParameter("email", email);

            List<Cliente> resultado = query.getResultList();

            return resultado.isEmpty() ? null : resultado.get(0);

        } finally {
            em.close();
        }
    }
}