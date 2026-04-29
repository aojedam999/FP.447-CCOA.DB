package CCOADB.FP.dao;

import CCOADB.FP.modelo.*;
import CCOADB.FP.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImp implements PedidoDAO {

    @Override
    public void insertar(Pedido pedido) {

        String sql = "INSERT INTO Pedidos " +
                "(id_cliente, id_articulo, fecha_pedido, estado, unidades) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, pedido.getCliente().getId());
            ps.setInt(2, pedido.getArticulo().getId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(pedido.getFechaHora()));
            ps.setString(4, pedido.getEstado().name());
            ps.setInt(5, pedido.getUnidades());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarEstado(int idPedido) {

        String sql = "UPDATE Pedidos SET estado = 'ENVIADO' WHERE id_pedido = ?";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, idPedido);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {

        List<Pedido> lista = new ArrayList<>();

        String sql =
                "SELECT p.id_pedido, p.fecha_pedido, p.estado, p.unidades, " +
                        "c.id_cliente, c.email, c.nombre, c.domicilio, c.nif_nie, c.tipo_cliente, c.cuota_anual, c.descuento_envio, " +
                        "a.id_articulo, a.codigo, a.descripcion, a.precio_venta, a.gastos_envio, a.tiempo_preparacion_min, a.stock_disponible " +
                        "FROM Pedidos p " +
                        "JOIN Clientes c ON p.id_cliente = c.id_cliente " +
                        "JOIN Articulos a ON p.id_articulo = a.id_articulo";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Cliente cliente;

                String tipo = rs.getString("tipo_cliente");

                if ("PREMIUM".equalsIgnoreCase(tipo)) {
                    cliente = new ClientePremium(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif_nie"),
                            rs.getDouble("cuota_anual"),
                            rs.getInt("descuento_envio")
                    );
                } else {
                    cliente = new ClienteEstandar(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif_nie")
                    );
                }

                cliente.setId(rs.getInt("id_cliente"));

                Articulo articulo = new Articulo(
                        rs.getInt("id_articulo"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion_min"),
                        rs.getInt("stock_disponible")
                );

                Pedido pedido = new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                        rs.getInt("unidades"),
                        cliente,
                        articulo
                );

                pedido.setEstado(EstadoEnvio.valueOf(rs.getString("estado")));

                lista.add(pedido);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Pedido buscarPorId(int id) {

        String sql =
                "SELECT p.id_pedido, p.fecha_pedido, p.estado, p.unidades, " +
                        "c.id_cliente, c.email, c.nombre, c.domicilio, c.nif_nie, c.tipo_cliente, c.cuota_anual, c.descuento_envio, " +
                        "a.id_articulo, a.codigo, a.descripcion, a.precio_venta, a.gastos_envio, a.tiempo_preparacion_min, a.stock_disponible " +
                        "FROM Pedidos p " +
                        "JOIN Clientes c ON p.id_cliente = c.id_cliente " +
                        "JOIN Articulos a ON p.id_articulo = a.id_articulo " +
                        "WHERE p.id_pedido = ?";

        Pedido pedido = null;

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Cliente cliente;

                    String tipo = rs.getString("tipo_cliente");

                    if ("PREMIUM".equalsIgnoreCase(tipo)) {
                        cliente = new ClientePremium(
                                rs.getString("email"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("nif_nie"),
                                rs.getDouble("cuota_anual"),
                                rs.getInt("descuento_envio")
                        );
                    } else {
                        cliente = new ClienteEstandar(
                                rs.getString("email"),
                                rs.getString("nombre"),
                                rs.getString("domicilio"),
                                rs.getString("nif_nie")
                        );
                    }

                    cliente.setId(rs.getInt("id_cliente"));

                    Articulo articulo = new Articulo(
                            rs.getInt("id_articulo"),
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_venta"),
                            rs.getDouble("gastos_envio"),
                            rs.getInt("tiempo_preparacion_min"),
                            rs.getInt("stock_disponible")
                    );

                    pedido = new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                            rs.getInt("unidades"),
                            cliente,
                            articulo
                    );

                    pedido.setEstado(EstadoEnvio.valueOf(rs.getString("estado")));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedido;
    }
}