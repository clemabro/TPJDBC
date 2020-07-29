package tpJDBC;

import java.util.Calendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PointEntre {
	private final static Logger LOG = LogManager.getLogger(PointEntre.class);

	public static void main(String[] args) {
		PersonManager personManager = new PersonManager(MyConnection.getInstance());
		LOG.info("Création des objets person à save");
		
		Calendar cal = Calendar.getInstance();
		cal.set(1999, 6, 13);
		Person personToSave = new Person("Clement", "AZIBEIRO", cal.getTime());
		Person moi = personManager.create(personToSave);
		
		personToSave = null;
		personToSave = new Person("Maxime", "VILLERMIN", Calendar.getInstance().getTime());
		Person maxime = personManager.create(personToSave);
		
		personToSave = null;
		personToSave = new Person("Antoine", "HOULBERT", Calendar.getInstance().getTime());
		Person antoine = personManager.create(personToSave);
		
		personToSave = null;
		personToSave = new Person("Marvyn", "ROCHER", Calendar.getInstance().getTime());
		Person marvyn = personManager.create(personToSave);
		
		// Affichage de tout les prénoms
		for(String first_name : personManager.getAllFistName()) {
			System.out.println(first_name);
		}
		
		// Test du scrollable result set
		personManager.scrollableResultSet();
		
		personManager.updatableResultSet();
		
		// Test de la méthode update
		moi.setLast_name("Mon nom de famille");
		personManager.udpate(moi);
		
		// Test de la méthode delete
		personManager.delete(marvyn.getId());
		
		// Test de la méthode get
		Person personGetted = personManager.getById(moi.getId());
		
		if(personGetted != null) {
			LOG.info("La personne que j'ai get est " + personGetted.toString());
		}
		
		personManager.delete(moi.getId());
		personManager.delete(maxime.getId());
		personManager.delete(antoine.getId());
		
		MyConnection.closeConnection();
	}

}
