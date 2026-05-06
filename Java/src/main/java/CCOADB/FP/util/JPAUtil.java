package CCOADB.FP.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static final EntityManagerFactory emf = createEMF();

    private static EntityManagerFactory createEMF() {
        Map<String, String> props = new HashMap<>();
        String pass = System.getenv("DB_PASSWORD");
        if (pass != null) {
            props.put("jakarta.persistence.jdbc.password", pass);
        }
        return Persistence.createEntityManagerFactory("miUnidadPersistencia", props);
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}