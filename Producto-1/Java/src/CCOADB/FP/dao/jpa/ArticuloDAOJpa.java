package CCOADB.FP.dao.jpa;

import CCOADB.FP.dao.ArticuloDAO;
import CCOADB.FP.modelo.Articulo;
import CCOADB.FP.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ArticuloDAOJpa implements ArticuloDAO {

    @Override
    public void insertar(Articulo articulo) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(articulo);
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Articulo> obtenerTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Articulo> query =
                    em.createQuery("SELECT a FROM Articulo a", Articulo.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Articulo buscarPorId(int id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Articulo.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Articulo> query =
                    em.createQuery("SELECT a FROM Articulo a WHERE a.codigo = :codigo", Articulo.class);

            query.setParameter("codigo", codigo);

            List<Articulo> resultado = query.getResultList();

            return resultado.isEmpty() ? null : resultado.get(0);

        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarStock(int idArticulo, int nuevoStock) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Articulo articulo = em.find(Articulo.class, idArticulo);

            if (articulo != null) {
                articulo.setStockDisponible(nuevoStock);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void reducirStock(java.sql.Connection conn, String codigo, int cantidad) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TypedQuery<Articulo> query =
                    em.createQuery("SELECT a FROM Articulo a WHERE a.codigo = :codigo", Articulo.class);

            query.setParameter("codigo", codigo);

            Articulo articulo = query.getResultList().stream().findFirst().orElse(null);

            if (articulo != null) {
                articulo.setStockDisponible(articulo.getStockDisponible() - cantidad);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
