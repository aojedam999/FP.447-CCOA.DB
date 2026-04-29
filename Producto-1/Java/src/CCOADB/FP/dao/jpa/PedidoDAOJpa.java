package CCOADB.FP.dao.jpa;

import CCOADB.FP.dao.PedidoDAO;
import CCOADB.FP.modelo.Articulo;
import CCOADB.FP.modelo.Pedido;
import CCOADB.FP.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PedidoDAOJpa implements PedidoDAO {

    @Override
    public void insertar(Pedido pedido) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            em.persist(pedido);

            Articulo a = em.find(Articulo.class, pedido.getArticulo().getId());
            a.setStockDisponible(a.getStockDisponible() - pedido.getUnidades());

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Pedido> query =
                    em.createQuery("SELECT p FROM Pedido p", Pedido.class);

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    @Override
    public Pedido buscarPorId(int id) {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Pedido.class, id);

        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarEstado(int idPedido) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Pedido pedido = em.find(Pedido.class, idPedido);

            if (pedido != null) {
                pedido.actualizarEstado();
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