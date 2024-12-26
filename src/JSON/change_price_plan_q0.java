package JSON;

import java.sql.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.json.JSONArray;
import org.json.JSONObject;

public class change_price_plan_q0 {

	public static void main(String[] args) throws ClassNotFoundException {	
		int SRV_TRX_S_NO = 92154;
		
		String jdbcURL = "jdbc:oracle:thin:@10.99.192.15:1521:MUAT8DB"; 
		String username = "UATAPP8C"; // Replace with your Oracle username
		String password = "UATAPP8C"; // Replace with your Oracle password
		// Declare the connection object
		Connection connection = null;
		Statement statement = null;
		ResultSet q0Set = null;
		ResultSet q0_ftr_Set = null;
		
		try {
			// Load the Oracle JDBC driver 
			Class.forName("oracle.jdbc.OracleDriver");
			// Establish the connection
			connection = DriverManager.getConnection(jdbcURL, username, password);
			if (connection != null) {
				System.out.println("Connected to the Oracle database successfully!");
				// Create a Statement object to execute the SQL query
				statement = connection.createStatement();
				// Define the SQL query
				String sql_q0 = "SELECT " + "SRV_TRX_S_NO AS srvTrxSNo, " + "NETWORK_IND AS networkInd, "
						+ "MVI_ID AS mviId, " + "ISSUING_DATE_TIME AS issuingDateTime, " + "MIG_STS AS migSts, "
						+ "SYS_CREATION_DATE AS sysCreationDate, " + "EFF_DATE_TIME AS effDateTime, "
						+ "MARKET_CODE AS marketCode, " + "SUBMARKET_CODE AS submarketCode, "
						+ "REASON_CODE AS reasonCode, " + "SRV_TRX_TP_CD AS srvTrxTpCd, " + "TRX_SRC AS trxSrc, "
						+ "USER_ID AS userId, " + "DL_SERVICE_CODE AS dlServiceCode, " + "BAN AS ban, " + "PP AS pp, "
						+ "PREV_PP AS prevPp, " + "SUBSCRIBER_NO AS subscriberNo, "
						+ "PREV_SUBSCRIBER_NO AS prevSubscriberNo, " + "SIM AS sim, " + "IMSI AS imsi, "
						+ "PTN AS ptn, " + "PREV_PTN AS prevPtn, " + "IXC_CODE AS ixcCode, " + "IMEI AS imei, "
						+ "PREV_NETWORK_IND AS prevNetworkInd, " + "LRN AS lrn, " + "NAI AS nai, " + "MNG_NO AS mngNo "
						+ "FROM q0 " + "WHERE SRV_TRX_S_NO="+SRV_TRX_S_NO;

				q0Set = statement.executeQuery(sql_q0);

				JSONArray q0Array = new JSONArray();
				if (q0Set.next()) {
					// Create a JSON object to hold the q0
					JSONObject q0 = new JSONObject();

					// Add all fields to the JSON object
					q0.put("srvTrxSNo", q0Set.getString("srvTrxSNo"));
					q0.put("networkInd", q0Set.getString("networkInd"));
					q0.put("mviId", q0Set.getString("mviId"));
					
					// Create the top-level JSON object with key "q0"
					JSONObject jsonResponse_q0 = new JSONObject();
					jsonResponse_q0.put("q0", q0);
					System.out.println(jsonResponse_q0.toString());
					

					String sql_q0_ftr = "SELECT APPLICATION_ID, SRV_TRX_S_NO, DL_SERVICE_CODE, FEATURES, NEW_OR_PREV, OPERATOR_ID, SYS_CREATION_DATE "
					        + "FROM q0_ftr WHERE SRV_TRX_S_NO = " + SRV_TRX_S_NO + "";
					q0_ftr_Set = statement.executeQuery(sql_q0_ftr);

					{
					    // Create a JSONArray to hold the results
					    JSONArray q0_ftr_Array = new JSONArray();

					    // Process the result set
					    while (q0_ftr_Set.next()) {
					        // Create a JSON object for each row of the result set
					        JSONObject q0_ftr = new JSONObject();
					        
					        // Add values to the JSON object (key names should match the expected output)
					        q0_ftr.put("applicationId", q0_ftr_Set.getString("APPLICATION_ID").trim()); // Trim any extra spaces
					        q0_ftr.put("srvTrxSNo", q0_ftr_Set.getString("SRV_TRX_S_NO"));
					        q0_ftr.put("dlservicecode", q0_ftr_Set.getString("DL_SERVICE_CODE"));
					        q0_ftr.put("features", q0_ftr_Set.getString("FEATURES"));
					        q0_ftr.put("neworprev", q0_ftr_Set.getString("NEW_OR_PREV"));
					        q0_ftr.put("operatorId", q0_ftr_Set.getString("OPERATOR_ID"));
					        q0_ftr.put("syscreationdate", formatDate(q0_ftr_Set.getTimestamp("SYS_CREATION_DATE")));

					        // Create the nested "id" object
					        JSONObject idObject = new JSONObject();
					        idObject.put("srvTrxSNo", q0_ftr_Set.getString("SRV_TRX_S_NO"));
					        idObject.put("features", q0_ftr_Set.getString("FEATURES"));
					        
					        // Add the nested "id" object to the main JSON object
					        q0_ftr.put("id", idObject);

					        // Add the JSON object to the JSONArray
					        q0_ftr_Array.put(q0_ftr);
					    }

					    // Wrap the JSONArray in a JSONObject
					    JSONObject jsonResponse_q0_ftr = new JSONObject();
					    jsonResponse_q0_ftr.put("q0Ftr", q0_ftr_Array);

					    // Output the final JSON (pretty print with an indentation level of 2)
					    System.out.println(jsonResponse_q0_ftr.toString(2));
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Helper method to format the Timestamp
	private static String formatDate(java.sql.Timestamp timestamp) {
		if (timestamp != null) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			return sdf.format(timestamp);
		}
		return null;
	}
}