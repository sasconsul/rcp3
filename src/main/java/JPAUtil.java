import java.io.Reader;
import java.sql.*;

/**
 * Based on http://java2s.com
 *
 */
public class JPAUtil {
    static Statement st;

    /**
     * Load and Connect proper JDBC drivers
     *
     * @throws Exception
     */
    public JPAUtil() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:hsqldb:data/wordcount;shutdown=true;hsqldb.write_delay=false;";

        Connection conn = DriverManager.getConnection(url, "sa", "");
        System.out.println("Got Connection.");
        st = conn.createStatement();
    }

    /**
     * Update via the SQL parameter (this is below the JPA/Hibernate layer.)
     *
     * @param sql
     * @throws Exception
     */
    public void executeSQLCommand(String sql) throws Exception {
        st.executeUpdate(sql);
    }

    /**
     * Checks that data in the DB by running the SQL command and displaying the output.
     * @param sql
     * @throws Exception
     */
    public void checkData(String sql) throws Exception {
        ResultSet rs = st.executeQuery(sql);
        ResultSetMetaData metadata = rs.getMetaData();

        for (int i = 0; i < metadata.getColumnCount(); i++) {
            System.out.print("\t" + metadata.getColumnLabel(i + 1));
        }
        System.out.println("\n----------------------------------");

        while (rs.next()) {
            for (int i = 0; i < metadata.getColumnCount(); i++) {
                Object value = rs.getObject(i + 1);
                if (value == null) {
                    System.out.print("\t       ");
                } else {
                    System.out.print("\t" + value.toString().trim());
                }
            }
            System.out.println("");
        }
    }

    /**
     * Shutdown removes the locks on the db.
     *
     * NOTE: it is needed for hsqldb and other file based databases.
     *
     * @throws SQLException
     */

    public void shutdown() throws SQLException {
        st.executeUpdate("SHUTDOWN");
    }
}
