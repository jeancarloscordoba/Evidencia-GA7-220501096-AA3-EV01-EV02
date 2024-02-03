
package Controlador;

import Modelo.Cliente;
import Modelo.ClienteDAO;
import Modelo.Empleado;
import Modelo.EmpleadoDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

    Empleado em = new Empleado();
    EmpleadoDAO edao = new EmpleadoDAO();
    Cliente cl = new Cliente();
    ClienteDAO cdao = new ClienteDAO();
    int ide;
    int idc;
    
    public Controlador(){
        PersistanceController.makeEntitiMF();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu = request.getParameter("menu");
        String accion = request.getParameter("accion");

        if (menu.equals("Principal")) {
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }
        
        //MENU EMPLEADOS
        if (menu.equals("Empleado")) {
            switch (accion) {
                //Caso para la funcionalidad de listar, del menu Empleado
                case "Listar":
                    //Se define una variable de tipo list que tendrá la función del metodo "getListaEmpleados" de la clase EmpleadoDAO
                    List lista = edao.getListaEmpleados(); 
                    
                    request.setAttribute("empleados", lista); //Aquí se esta definiendo empleados como un atributo del arrays lista. Que se llamara en el jsp de Empleado
                    break;
                    
                //Caso para la funcionalidad del boton "Agregar" del jsp de Empleado
                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est = request.getParameter("txtEstado");
                    String user = request.getParameter("txtUser");
                    
                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);
                    
                    //Se llama al metodo "guardarEmpleado" de la clase EmpleadoDAO para guardar un nuevo Empleado
                    edao.guardarEmpleado(em); 

                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                
                //Caso para la funcionalidad del boton "Editar" del jsp de Empleado
                case "Editar":
                    //Se le asigan el valor del "id" (IdEmpleado) a la variable "ide".
                    ide = Integer.parseInt(request.getParameter("id"));
                    
                    //Se le asigna la función del metodo getEditarEmpleado de la clase EmpleadoDAO a la variable e de tipo clase Empleado. 
                    Empleado e = edao.getEditarEmpleado(ide); 
                    
                    //se define empleado, como un atributo de la variable e. 
                    request.setAttribute("empleado", e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                    
                //Caso para la funcionalidad del boton "Actualizar" del jsp de Empleado
                case "Actualizar": 
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String est1 = request.getParameter("txtEstado");
                    String user1 = request.getParameter("txtUser");

                    em.setDni(dni1);
                    em.setNom(nom1);
                    em.setTel(tel1);
                    em.setEstado(est1);
                    em.setUser(user1);
                    em.setId(ide);
                    
                    System.out.println("EMPLEADO ACTUALIZADO: "+ em);
                    
                    //Se ejecuta el metodo "actualizarEmpleado" de la clase EmpleadoDAO para realizar la actualización de la edición realizada a los datos del empleado
                    edao.actualizarEmpleado(em);

                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                //Caso para el boton "Eliminar" del jsp de Empleado
                case "Delete":
                    //Se le asigan el valor del "id" (IdEmpleado) a la variable "ide".
                    ide = Integer.parseInt(request.getParameter("id"));
                    
                    //Se ejecuta el metodo "eliminarEmpleado" de la clase EmpleadoDAO para eliminar el empleado de la base de datos
                    edao.eliminarEmpleado(ide);
                    
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }
        
        //MENU CLIENTES
        if (menu.equals("Cliente")) {
            switch (accion) {
                //Caso para la funcionalidad de listar, del menu Clientes
                case "Listar":
                    //Se define una variable de tipo list que tendrá la función del metodo "getListaClientes" de la clase ClienteDAO
                    List lista = cdao.getListaClientes();
                    
                    request.setAttribute("clientes", lista); //Aquí se esta definiendo clientes como un atributo del arrays lista. Que se llamara en el jsp de Clientes
                    break;
                    
                //Caso para la funcionalidad del boton "Agregar" del jsp de Clientes
                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String dir = request.getParameter("txtDir");
                    String est = request.getParameter("txtEstado");
                    
                    cl.setDni(dni);
                    cl.setNom(nom);
                    cl.setTel(tel);
                    cl.setDir(dir);
                    cl.setEstado(est);
                    
                    //Se llama al metodo "guardarCliente" de la clase ClienteDAO para guardar un nuevo cliente
                    cdao.guardarCliente(cl);

                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break; 
                    
                //Caso para la funcionalidad del boton "Editar" del jsp de Clientes
                case "Editar":
                    //Se le asigan el valor del "id" (IdCliente) a la variable "idc".
                    idc = Integer.parseInt(request.getParameter("id"));
                    
                    //Se le asigna la función del metodo getEditarCliente de la clase ClienteDAO a la variable c de tipo clase Cliente. 
                    Cliente c = cdao.getEditarCliente(idc);
                    
                    System.out.println("EL CLIENTE ES: "+c);
                    
                    //se define cliente, como un atributo de la variable c. 
                    request.setAttribute("cliente", c);
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;
                    
                //Caso para la funcionalidad del boton "Actualizar" del jsp de Clientes
                case "Actualizar":
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String dir1 = request.getParameter("txtDir");
                    String est1 = request.getParameter("txtEstado");

                    cl.setDni(dni1);
                    cl.setNom(nom1);
                    cl.setTel(tel1);
                    cl.setDir(dir1);
                    cl.setEstado(est1);
                    cl.setId(idc);
                    
                    System.out.println("CLIENTE ACTUALIAR: "+ cl);
                    
                    //Se ejecuta el metodo "actualizarCliente" de la clase ClienteDAO para realizar la actualización de la edición realizada a los datos del cliente
                    cdao.actualizarCliente(cl);

                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;
                
                //Caso para el boton "Eliminar" del jsp de Clientes
                case "Delete": 
                    //Se le asigan el valor del "id" (IdCliente) a la variable "idc".
                    idc = Integer.parseInt(request.getParameter("id"));
                    
                    //Se ejecuta el metodo "eliminarCliente" de la clase ClienteDAO para eliminar el cliente de la base de datos
                    cdao.eliminarCliente(idc);
                    
                    request.getRequestDispatcher("Controlador?menu=Cliente&accion=Listar").forward(request, response);
                    break;

                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
