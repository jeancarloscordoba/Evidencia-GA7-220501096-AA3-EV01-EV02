
package Modelo;

import Controlador.PersistanceController;
import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class ClienteDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public ClienteDAO() {

    }

    //Listar Clientes
    public List<Cliente> getListaClientes() {
        return PersistanceController.buscarPorClase(Cliente.class);
    }

    //Guardar Cliente 
    public boolean guardarCliente(Cliente cliente) {
        return PersistanceController.guardar(cliente);
    }
    
    //Editar Cliente
    public Cliente getEditarCliente(int idCliente) {
        return (Cliente) PersistanceController.buscarPorId(Cliente.class, idCliente);
    }

    //Actualizar Cliente
    public boolean actualizarCliente(Cliente cliente) { 
        return PersistanceController.actualizar(cliente);    
    }
    
    //Eliminar Cliente
    public Object eliminarCliente(int idCliente) {
        return PersistanceController.<Cliente>eliminarPorId(idCliente, Cliente.class);
    }

}
