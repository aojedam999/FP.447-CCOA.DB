package CCOADB.FP.dao;

import CCOADB.FP.modelo.Articulo;
import CCOADB.FP.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImp implements ArticuloDAO {

    @Override
    public void insertar(Articulo articulo) {

        String sql = "INSERT INTO Articulos " +
                "(codigo, descripcion, precio_venta, gastos_envio, tiempo_preparacion_min, stock_disponible) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, articulo.getCodigo());
            ps.setString(2, articulo.getDescripcion());
            ps.setDouble(3, articulo.getPrecioVenta());
            ps.setDouble(4, articulo.getGastosEnvio());
            ps.setInt(5, articulo.getTiempoPreparacionMin());
            ps.setInt(6, articulo.getStockDisponible());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Articulo> obtenerTodos() {

        List<Articulo> lista = new ArrayList<>();

        String sql = "SELECT * FROM Articulos";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Articulo articulo = new Articulo(
                        rs.getInt("id_articulo"),
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion_min"),
                        rs.getInt("stock_disponible")
                );

                lista.add(articulo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Articulo buscarPorId(int id) {

        String sql = "SELECT * FROM Articulos WHERE id_articulo = ?";
        Articulo articulo = null;

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    articulo = new Articulo(
                            rs.getInt("id_articulo"),
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_venta"),
                            rs.getDouble("gastos_envio"),
                            rs.getInt("tiempo_preparacion_min"),
                            rs.getInt("stock_disponible")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articulo;
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) {

        String sql = "SELECT * FROM Articulos WHERE codigo = ?";
        Articulo articulo = null;

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    articulo = new Articulo(
                            rs.getInt("id_articulo"),
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_venta"),
                            rs.getDouble("gastos_envio"),
                            rs.getInt("tiempo_preparacion_min"),
                            rs.getInt("stock_disponible")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return articulo;
    }

    @Override
    public void actualizarStock(int idArticulo, int nuevoStock) {

        String sql = "UPDATE Articulos SET stock_disponible = ? WHERE id_articulo = ?";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, nuevoStock);
            ps.setInt(2, idArticulo);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
