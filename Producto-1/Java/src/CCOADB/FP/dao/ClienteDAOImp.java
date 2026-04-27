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
import java.sql.CallableStatement;

public class ClienteDAOImp implements ClienteDAO {

    @Override
    public void insertar(Cliente cliente) {

        String sql = "{CALL crear_cliente(?, ?, ?, ?, ?, ?, ?)}";

        try (
                Connection con = ConexionBD.getConexion();
                CallableStatement cs = con.prepareCall(sql)
        ) {
            cs.setString(1, cliente.getEmail());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getDomicilio());
            cs.setString(4, cliente.getNIFNIE());

            if (cliente instanceof ClientePremium premium) {
                cs.setString(5, "PREMIUM");
                cs.setDouble(6, premium.getCuotaAnual());
                cs.setInt(7, premium.getDescuentoEnvio());
            } else {
                cs.setString(5, "ESTANDAR");
                cs.setNull(6, java.sql.Types.DECIMAL);
                cs.setNull(7, java.sql.Types.INTEGER);
            }

            cs.execute();

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

                cliente.setId(rs.getInt("id_cliente"));
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

                    cliente.setId(rs.getInt("id_cliente"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cliente;
    }
}