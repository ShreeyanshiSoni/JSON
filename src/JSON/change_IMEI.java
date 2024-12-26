package JSON;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class change_IMEI {
	//changes in IMEI

    public static void main(String[] args) {
        int SRV_TRX_SNO = 114215; // Define the SRV_TRX_SNO value

        String jdbcURL = "jdbc:oracle:thin:@10.99.192.15:1521:MUAT8DB";
        String username = "UATAPP8C"; // Replace with your Oracle username
        String password = "UATAPP8C"; // Replace with your Oracle password

        // Declare the connection object
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(jdbcURL, username, password);
            if (connection != null) {
                System.out.println("Connected to the Oracle database successfully!");

                // Create a Statement object to execute the SQL query
                statement = connection.createStatement();

                // SQL query to retrieve data
                String query = "SELECT JSON_OBJECT( " +
                	    "           'q0' VALUE JSON_OBJECT( " +
                	    "               'srvTrxSNo' VALUE SRV_TRX_S_NO, " +
                	    "               'networkInd' VALUE NETWORK_IND, " +
                	    "               'mviId' VALUE MVI_ID, " +
                	    "               'issuingDateTime' VALUE TO_CHAR(ISSUING_DATE_TIME, 'Mon DD, YYYY HH24:MI:SS'), " +
                	    "               'migSts' VALUE MIG_STS, " +
                	    "               'sysCreationDate' VALUE TO_CHAR(SYS_CREATION_DATE, 'Mon DD, YYYY HH24:MI:SS'), " +
                	    "               'effDateTime' VALUE TO_CHAR(EFF_DATE_TIME, 'Mon DD, YYYY HH24:MI:SS'), " +
                	    "               'marketCode' VALUE MARKET_CODE, " +
                	    "               'submarketCode' VALUE SUBMARKET_CODE, " +
                	    "               'reasonCode' VALUE REASON_CODE, " +
                	    "               'srvTrxTpCd' VALUE SRV_TRX_TP_CD, " +
                	    "               'trxSrc' VALUE TRX_SRC, " +
                	    "               'userId' VALUE USER_ID, " +
                	    "               'dlServiceCode' VALUE DL_SERVICE_CODE, " +
                	    "               'ban' VALUE BAN, " +
                	    "               'pp' VALUE PP, " +
                	    "               'prevPp' VALUE PREV_PP, " +
                	    "               'subscriberNo' VALUE SUBSCRIBER_NO, " +
                	    "               'prevSubscriberNo' VALUE PREV_SUBSCRIBER_NO, " +
                	    "               'sim' VALUE SIM, " +
                	    "               'prevSim' VALUE PREV_SIM, " +
                	    "               'imsi' VALUE IMSI, " +
                	    "               'prevImsi' VALUE PREV_IMSI, " +
                	    "               'ptn' VALUE PTN, " +
                	    "               'prevPtn' VALUE PREV_PTN, " +
                	    "               'ixcCode' VALUE IXC_CODE, " +
                	    "               'imei' VALUE IMEI, " +
                	    "               'prevImei' VALUE PREV_IMEI, " +
                	    "               'prevNetworkInd' VALUE PREV_NETWORK_IND, " +
                	    "               'lrn' VALUE LRN, " +
                	    "               'nai' VALUE NAI, " +
                	    "               'mngNo' VALUE MNG_NO " +
                	    "           ) " +
                	    "       ) AS json_output " +
                	    "FROM q0 " +
                	    "WHERE SRV_TRX_S_NO = :SRV_TRX_SNO";

                // Execute the query
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                String JSON = resultSet.getString("JSON_OUTPUT");
                System.out.println(JSON);
                
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources to avoid memory leaks
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
