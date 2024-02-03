package Controlador;

import Modelo.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistanceController implements Serializable {

    public static EntityManagerFactory emf = null;
    public static EntityManager em = null;
    public static String puName = "SistemaVentasTaniaSoftPU";

    public static void makeEntitiMF() throws RuntimeException {
        try {
            emf = Persistence.createEntityManagerFactory(puName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static void closeEntitiMF() {
        if (emf != null) {
            if (emf.isOpen()) {
                emf.close();
            }
        }

    }

    public static void initEM() {
        makeEntitiMF();
        em = emf.createEntityManager();
    }

    public PersistanceController() {
        makeEntitiMF();
    }

    //Listar Clientes
    public static <T> List<T> buscarPorClase(Class<T> clase) {
        initEM();
        List<T> lista = null;
        try {
            em.getTransaction().begin();
            lista = em.createQuery("select p from " + clase.getSimpleName() + " p").getResultList();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
            emf.close();
            return lista;
        }
    }

    
    /*****************Operaciones CRUD**********************/
    
    //Guardar Cliente (CREATE)
    public static <T> boolean guardar(T entidad) {
        initEM();
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }

    //Actualizar Cliente (UPDATE)
    public static <T> boolean actualizar(T entidad) {
        initEM();
        try {
            em.getTransaction().begin();
            em.merge(entidad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }
    /*public static <T> void actualizar(T entidad) {
        initEM();
        try {
            em.getTransaction().begin();
            em.merge(entidad);
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }*/


    /*public static <T> boolean actualizarEntidad(T entidad) {
        EntityManager em = PersistenceController.getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }*/
    //Buscar Cliente por Id (Usado para editar un cliente) (READ: Leer o Consultar)
    public static <T> Object buscarPorId(Class<T> clase, int id) {
        initEM();
        try {
            em.getTransaction().begin();
            return em.find(clase, id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return null;
        } finally {
            em.close();
            emf.close();
        }
    }

    //Eliminar Cliente por Id (DELETE)
    public static <T> Object eliminarPorId(int id, Class<T> clase) {
        initEM();
        try {
            em.getTransaction().begin();
            T objeto = em.find(clase, id);
            if (objeto != null) {
                em.remove(objeto);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
            emf.close();
        }
    }

}
