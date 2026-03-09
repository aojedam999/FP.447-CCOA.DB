package CCOADB.FP.controlador;

import CCOADB.FP.modelo.Articulo;
import CCOADB.FP.modelo.Cliente;
import CCOADB.FP.modelo.Datos;
import CCOADB.FP.modelo.Pedido;

public class Controlador {

    private Datos datos;

    public Controlador() {
        datos = new Datos();
    }

    public void addCliente(Cliente cliente) {
        datos.addCliente(cliente);
    }

    public void addArticulo(Articulo articulo) {
        datos.addArticulo(articulo);
    }

}