package CCOADB.FP.dao;

import CCOADB.FP.modelo.Cliente;
import CCOADB.FP.modelo.ClienteEstandar;
import CCOADB.FP.modelo.ClientePremium;
import CCOADB.FP.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImp implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes " +
                "(email, nombre, domicilio, nif_nie, tipo_cliente, cuota_anual, descuento_envio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, cliente.getEmail());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDomicilio());
            ps.setString(4, cliente.getNIFNIE());

            if (cliente instanceof ClientePremium premium) {
                ps.setString(5, "PREMIUM");
                ps.setDouble(6, premium.getCuotaAnual());
                ps.setInt(7, premium.getDescuentoEnvio());
            } else {
                ps.setString(5, "ESTANDAR");
                ps.setNull(6, java.sql.Types.DECIMAL);
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM clientes";

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                String tipo = rs.getString("tipo_cliente");
                Cliente cliente;

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

                lista.add(cliente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public Cliente buscarPorEmail(String email) {

        String sql = "SELECT * FROM clientes WHERE email = ?";
        Cliente cliente = null;

        try (
                Connection con = ConexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

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
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }
}