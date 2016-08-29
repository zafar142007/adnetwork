package adnetwork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import com.zafar.adnetwork.model.City;
public class TestSetup {
		@Test
		public void test(){
		   EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("adnetwork");
		   EntityManager entityManager = emfactory.createEntityManager( );
		   entityManager.getTransaction().begin();
		   City c = entityManager.find(City.class, 1);
		   System.out.println(c.getCityName());
	}

}
