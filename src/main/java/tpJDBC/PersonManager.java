package tpJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonManager {
	private Connection conn;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Statement st = null;
	private final static Logger LOG = LogManager.getLogger(PersonManager.class);
	

	/**
	 * @param conn
	 */
	public PersonManager(Connection conn) {
		this.conn = conn;
	}
	
	public Person create(Person person) {
		LOG.debug("Debut SAVE PERSON");
		Person personToReturn = person;
		try {
			String sql = "";
			if(person.getId() != null) {
				sql = "INSERT INTO person VALUES (?,?,?,?)";
				ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1,person.getId(),Types.INTEGER);
				ps.setObject(2,person.getFirst_name(), Types.VARCHAR); 
				ps.setObject(3,person.getLast_name(),Types.VARCHAR); 
				ps.setObject(4,person.getDob(),Types.DATE);
				
			} else {
				sql = "INSERT INTO person (first_name,last_name,dob) VALUES (?,?,?);";
				ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1,person.getFirst_name(), Types.VARCHAR); 
				ps.setObject(2,person.getLast_name(),Types.VARCHAR); 
				ps.setObject(3,person.getDob(),Types.DATE);
			}
			
			ps.executeUpdate();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
            	personToReturn.setId(generatedKeys.getInt(1));
            	LOG.debug("Fin SAVE PERSON");
            	return personToReturn;
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
		}  catch(SQLException se) {
			LOG.error(se.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(ps);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		LOG.debug("Fin SAVE PERSON");
		return personToReturn;
	}
	
	public Person getById(Integer id) {
		LOG.debug("Debut getById");
		Person personToReturn = new Person();
		try {
			String sql = "SELECT * FROM person WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1,id,Types.INTEGER);
			rs = ps.executeQuery();

			while(rs.next()) {
				personToReturn.setId(rs.getInt("id"));
				personToReturn.setFirst_name(rs.getString("first_name"));
				personToReturn.setLast_name(rs.getString("last_name"));
				personToReturn.setDob(rs.getDate("dob"));
			}
			LOG.debug("Fin getById");
			return personToReturn;
		}  catch(SQLException se) {
			LOG.error(se.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(rs);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
			try { 
				DbUtils.close(ps);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		LOG.debug("Fin getById");
		return null;
	}
	
	public void udpate(Person person) {
		LOG.debug("Debut udpate PERSON");
		try {
			String sql = "UPDATE person SET first_name = ?, last_name = ?, dob = ? WHERE id =?;";
			ps = conn.prepareStatement(sql);
			
			ps.setObject(1,person.getFirst_name(), Types.VARCHAR); 
			ps.setObject(2,person.getLast_name(),Types.VARCHAR); 
			ps.setObject(3,person.getDob(),Types.DATE);
			ps.setObject(4,person.getId(),Types.INTEGER);
			
			ps.executeUpdate();
		}  catch(SQLException se) {
			LOG.error(se.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(ps); 
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		LOG.debug("Fin udpate PERSON");
	}
	
	public void delete(Integer idToDelete) {
		LOG.debug("Debut delete PERSON");
		try {
			String sql = "DELETE FROM person WHERE id = ?";
			ps = conn.prepareStatement(sql);
			ps.setObject(1,idToDelete,Types.INTEGER);
			ps.executeUpdate(); 
		}  catch(SQLException se) {
			LOG.error(se.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(ps);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		LOG.debug("Fin delete PERSON");
	}
	
	public List<String> getAllFistName() {
		LOG.debug("Debut getAllFistName");
		List<String> listToReturn = new ArrayList<String>();
		try {
			String sql = "SELECT first_name FROM person;";
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				listToReturn.add(rs.getString("first_name"));
			}
		}  catch(SQLException se) {
			LOG.error(se.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(st);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
			try { 
				DbUtils.close(rs);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		LOG.debug("Fin getAllFistName");
		return listToReturn;
	}
	
	public void scrollableResultSet() {
		LOG.debug("Debut Scrollable Result Set");
		String sql = "SELECT * FROM person";
		try {
			st = conn.createStatement(
			        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = st.executeQuery(sql);

	        rs.first();

	        readPersonInfo("first", rs);

	        rs.relative(3);

	        readPersonInfo("relative(3)", rs);

	        rs.previous();

	        readPersonInfo("previous", rs);

	        rs.absolute(4);

	        readPersonInfo("absolute(4)", rs);

	        rs.last();

	        readPersonInfo("last", rs);

	        rs.relative(-2);

	        readPersonInfo("relative(-2)", rs);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			try { 
				DbUtils.close(st);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
			try { 
				DbUtils.close(rs);
			} catch (Exception e) { 
		    	LOG.warn(e.getMessage()); 
	    	}
		}
		
		LOG.debug("Fin Scrollable Result Set");
	}
	
	private static void readPersonInfo(String position, ResultSet result)
            throws SQLException {
		Person person = new Person(result.getInt("id"), result.getString("first_name"),result.getString("last_name"),result.getDate("dob"));
        LOG.info("POSITION DU RESULT SET : " + position + " " + person.toString());
    }
	
	public void updatableResultSet() {
		LOG.debug("Debut Update Scrollable Result Set");
		 try {		        
		        String sql = "SELECT * FROM person";
		        
		        st = conn.createStatement(
				        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				rs = st.executeQuery(sql);
				
				Calendar newDob = Calendar.getInstance();
				newDob.set(2020, 0, 1);
				
		        while (rs.next()) {
		            rs.updateObject( "dob", newDob.getTime());
		            rs.updateRow();
		        }

		    } catch (SQLException e ) {
		    	LOG.error(e.getMessage());
		    } finally {
		    	try { 
					DbUtils.close(st);
				} catch (Exception e) { 
			    	LOG.warn(e.getMessage()); 
		    	}
				try { 
					DbUtils.close(rs);
				} catch (Exception e) { 
			    	LOG.warn(e.getMessage()); 
		    	}
		    }
		 LOG.debug("Fin Update Scrollable Result Set");
	}
}
