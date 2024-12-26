package JSON;

		
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

public class Build_JSON {

	public static void main(String[] args) {
        // Database connection details
        String jdbcURL = "jdbc:oracle:thin:@10.99.192.15:1521:MUAT8DB"; // Replace with your database URL
        String username = "UATAPP8C"; // Replace with your username
        String password = "UATAPP8C"; // Replace with your password

        // Define the SRV_TRX_SNO value
        int SRV_TRX_SNO = 114219;

        // Declare database resources
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
//--------------------------------q0----------------------------------------------------------------------------
                String query_q0 ="SELECT JSON_OBJECT(" +
                        "'q0' VALUE JSON_OBJECT(" +
                        "'srvTrxSNo' VALUE TRIM(SRV_TRX_S_NO), " +
                        "'networkInd' VALUE TRIM(NETWORK_IND), " +
                        "'mviId' VALUE TRIM(MVI_ID), " +
                        "'issuingDateTime' VALUE TO_CHAR(ISSUING_DATE_TIME, 'Mon DD, YYYY HH24:MI:SS'), " +
                        "'migSts' VALUE TRIM(MIG_STS), " +
                        "'sysCreationDate' VALUE TO_CHAR(SYS_CREATION_DATE, 'Mon DD, YYYY HH24:MI:SS'), " +
                        "'effDateTime' VALUE TO_CHAR(EFF_DATE_TIME, 'Mon DD, YYYY HH24:MI:SS'), " +
                        "'marketCode' VALUE TRIM(MARKET_CODE), " +
                        "'submarketCode' VALUE TRIM(SUBMARKET_CODE), " +
                        "'reasonCode' VALUE TRIM(REASON_CODE), " +
                        "'srvTrxTpCd' VALUE TRIM(SRV_TRX_TP_CD), " +
                        "'trxSrc' VALUE TRIM(TRX_SRC), " +
                        "'userId' VALUE TRIM(USER_ID), " +
                        "'dlServiceCode' VALUE TRIM(DL_SERVICE_CODE), " +
                        "'ban' VALUE TRIM(BAN), " +
                        "'pp' VALUE TRIM(PP), " +
                        "'prevPp' VALUE TRIM(PREV_PP), " +
                        "'subscriberNo' VALUE TRIM(SUBSCRIBER_NO), " +
                        "'prevSubscriberNo' VALUE TRIM(PREV_SUBSCRIBER_NO), " +
                        "'sim' VALUE TRIM(SIM), " +
                        "'imsi' VALUE TRIM(IMSI), " +
                        "'ptn' VALUE TRIM(PTN), " +
                        "'prevPtn' VALUE TRIM(PREV_PTN), " +
                        "'ixcCode' VALUE TRIM(IXC_CODE), " +
                        "'imei' VALUE TRIM(IMEI), " +
                        "'prevNetworkInd' VALUE TRIM(PREV_NETWORK_IND), " +
                        "'lrn' VALUE TRIM(LRN), " +
                        "'nai' VALUE TRIM(NAI), " +
                        "'mngNo' VALUE TRIM(MNG_NO)" +
                        ")" +
                        ") AS json_output " +
                        "FROM q0 " +
                        "WHERE SRV_TRX_S_NO = "+ SRV_TRX_SNO;

                // Execute the query
                resultSet = statement.executeQuery(query_q0);
                while (resultSet.next()) {
                String JSON = resultSet.getString("JSON_OUTPUT");
                System.out.println(JSON);
                
                }            
                
//---------------------------------q0_ftr------------------------------------------------------------------
                // Loop to fetch rows in batches of 16
                int startRow = 1;
                int batchSize = 10;
                ResultSet row_count;
                int rowCount = 0;
                String count="SELECT COUNT(*) AS row_count\r\n"
                		+ "FROM q0_ftr  WHERE SRV_TRX_S_NO ="+SRV_TRX_SNO;
                row_count = statement.executeQuery(count);
                
                if (row_count.next()) {
                    rowCount = row_count.getInt("row_count");
                    //System.out.println("Row count: " + rowCount);
                }
                
                

                // Flag to track if there are more results
                boolean hasMoreResults = true;

                while(startRow<rowCount) {
                         // SQL query to fetch rows in batches of 16
                     	String q0_ftr="SELECT JSON_OBJECT(" +
                                "'q0Ftr' VALUE JSON_ARRAYAGG(" +
                                "JSON_OBJECT(" +
                                    "'applicationId' VALUE TRIM(APPLICATION_ID), " +
                                    "'srvTrxSNo' VALUE TRIM(SRV_TRX_S_NO), " +
                                    "'dlservicecode' VALUE TRIM(DL_SERVICE_CODE), " +
                                    "'features' VALUE TRIM(FEATURES), " +
                                    "'neworprev' VALUE TRIM(NEW_OR_PREV), " +
                                    "'operatorId' VALUE TRIM(OPERATOR_ID), " +
                                    "'syscreationdate' VALUE TO_CHAR(SYS_CREATION_DATE, 'Mon DD, YYYY HH24:MI:SS'), " +
                                    "'id' VALUE JSON_OBJECT(" +
                                        "'srvTrxSNo' VALUE TRIM(SRV_TRX_S_NO), " +
                                        "'features' VALUE TRIM(FEATURES)" +
                                    ")" +
                                ")" +
                            ")" +
                        ") AS json_output " +
                        "FROM ( " +
                            "SELECT TRIM(APPLICATION_ID) AS APPLICATION_ID, " +
                                   "TRIM(SRV_TRX_S_NO) AS SRV_TRX_S_NO, " +
                                   "TRIM(DL_SERVICE_CODE) AS DL_SERVICE_CODE, " +
                                   "TRIM(FEATURES) AS FEATURES, " +
                                   "TRIM(NEW_OR_PREV) AS NEW_OR_PREV, " +
                                   "TRIM(OPERATOR_ID) AS OPERATOR_ID, " +
                                   "SYS_CREATION_DATE, " +
                                   "ROW_NUMBER() OVER (ORDER BY SRV_TRX_S_NO) AS row_num " +
                            "FROM q0_ftr " +
                            "WHERE SRV_TRX_S_NO = %d" +
                        ") " +
                        "WHERE row_num BETWEEN %d AND %d";
                         String query_q0_ftr = String.format(q0_ftr,SRV_TRX_SNO, startRow, startRow + batchSize - 1);
                       
                    // Execute the query
                    resultSet = statement.executeQuery(query_q0_ftr);

                    // If no results, exit the loop
                    
                    
                    
                    if (!resultSet.next()) {
                        hasMoreResults = false; // No more data, exit the loop
                    } else {
                        // Process the result (in this case, we print the JSON output)
                        String jsonOutput = resultSet.getString("json_output");
                        System.out.println(jsonOutput);

                        // Increment the row range for the next batch
                        startRow += batchSize;
                    }
                    
                }

                System.out.println("All rows have been printed.");
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