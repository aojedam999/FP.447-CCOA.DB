package CCOADB.FP;

import CCOADB.FP.util.JPAUtil;

public class TestJPA {
    public static void main(String[] args) {
        var em = JPAUtil.getEntityManager();
        System.out.println("✅ EntityManager OK");
        em.close();
    }
}