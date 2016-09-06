package com.myschool.loaders.usage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.myschool.loaders.db.DatabaseUtil;

public class LoadUsageStatistics {

	public static void main(String[] args) throws Exception {
		LoadUsageStatistics loadUsageStatistics = new LoadUsageStatistics();
		loadUsageStatistics.load();
	}

	private void load() throws Exception {
		List<Integer> userIds = new ArrayList<Integer>();
		//Integer[] sessionTimes = new Integer[] {0, 1, 2, 3, 4, 5, };
		String[] devices = new String[] {"Computer", "Tablet", "Phone","TV"};
		String[] browsers = new String[] {"Internet Explorer", "Google Chrome", "Mozilla Firefox", "Opera", "Safari"};
		String[] features = new String[] {"Admin", "Master", "Student", "Employee", "Notifications","Notice Board", "Downloads", "Privileges", "Statistics", "Find Us", "Web Admin"};

		userIds.add(null);
		Connection connection = DatabaseUtil.getConnection();
		System.out.println(connection);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from users");
		while (resultSet.next()) {
			userIds.add(resultSet.getInt("user_id"));
		}
		System.out.println("userIds " + userIds);
		DatabaseUtil.close(resultSet, statement);

		Integer nextId = null;
		String nextIdSql = "select coalesce(max(request_id), 1) as next_id from USER_activity";
		PreparedStatement nextIdPreparedStatement = connection.prepareStatement(nextIdSql);
		ResultSet nextIdResultSet = nextIdPreparedStatement.executeQuery();
		if (nextIdResultSet.next()) {
			nextId = nextIdResultSet.getInt("next_id");
		}
		System.out.println("nextId " + nextId);

		Random random = new Random();
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 22);
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.YEAR, 2013);

		Calendar currentDate = Calendar.getInstance();
		System.out.println("currentDate " + currentDate);

		while (calendar.before(currentDate)) {
			int numberOflogins = random.nextInt(10);
			System.out.println("numberOflogins " + numberOflogins);
			int loginCount = 0;

			if (numberOflogins == 0) {
				calendar.set(Calendar.SECOND, random.nextInt(60));
				calendar.set(Calendar.MINUTE, random.nextInt(60));
				calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24));

				String insert_USAGE_STATISTICS = "insert into USER_SESSION(SESSION_ID, USER_ID, SESSION_START_TIME, SESSION_END_TIME, DEVICE, BROWSER, IP_ADDRESS) values(?,?,?,?,?,?,?)";
				int index = 0;
				PreparedStatement preparedStatement = connection
						.prepareStatement(insert_USAGE_STATISTICS);
				String sessionId = UUID.randomUUID().toString();
				System.out.println("sessionId=" + sessionId);
				preparedStatement.setString(++index, sessionId);
				Integer userId = userIds.get(random.nextInt(userIds.size()));
				if (userId == null) {
					preparedStatement.setNull(++index, Types.INTEGER);
				} else {
					preparedStatement.setInt(++index, userId);
				}
				Timestamp start = new Timestamp(calendar.getTimeInMillis());
				System.out.println("start = " + start);
				preparedStatement.setTimestamp(++index, start);
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)
						+ random.nextInt(3600));
				Timestamp end = new Timestamp(calendar.getTimeInMillis());
				System.out.println("end = " + end);
				preparedStatement.setTimestamp(++index, end);
				preparedStatement.setString(++index,
						devices[random.nextInt(devices.length)]);
				preparedStatement.setString(++index,
						browsers[random.nextInt(browsers.length)]);
				preparedStatement.setString(++index, random.nextInt(255) + "."
						+ random.nextInt(255) + "." + random.nextInt(255) + "."
						+ random.nextInt(255));
				//preparedStatement.addBatch();
				preparedStatement.executeUpdate();
				DatabaseUtil.close(preparedStatement);
				
			} else {
				while (loginCount++ < numberOflogins) {
					System.out.println("inside while");
					calendar.set(Calendar.SECOND, random.nextInt(60));
					calendar.set(Calendar.MINUTE, random.nextInt(60));
					calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24));

					String insert_USAGE_STATISTICS = "insert into USER_SESSION(SESSION_ID, USER_ID, SESSION_START_TIME, SESSION_END_TIME, DEVICE, BROWSER, IP_ADDRESS) values(?,?,?,?,?,?,?)";
					int index = 0;
					PreparedStatement preparedStatement = connection
							.prepareStatement(insert_USAGE_STATISTICS);
					String sessionId = UUID.randomUUID().toString();
					System.out.println("sessionId=" + sessionId);
					preparedStatement.setString(++index, sessionId);
					Integer userId = userIds.get(random.nextInt(userIds.size()));
					if (userId == null) {
						preparedStatement.setNull(++index, Types.INTEGER);
					} else {
						preparedStatement.setInt(++index, userId);
					}
					Timestamp start = new Timestamp(calendar.getTimeInMillis());
					System.out.println("start = " + start);
					preparedStatement.setTimestamp(++index, start);
					calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)
							+ random.nextInt(3600));
					Timestamp end = new Timestamp(calendar.getTimeInMillis());
					System.out.println("end = " + end);
					preparedStatement.setTimestamp(++index, end);
					preparedStatement.setString(++index,
							devices[random.nextInt(devices.length)]);
					preparedStatement.setString(++index,
							browsers[random.nextInt(browsers.length)]);
					preparedStatement.setString(++index, random.nextInt(255) + "."
							+ random.nextInt(255) + "." + random.nextInt(255) + "."
							+ random.nextInt(255));
					//preparedStatement.addBatch();
					preparedStatement.executeUpdate();
					DatabaseUtil.close(preparedStatement);
					String insert_REQUEST_STATISTICS = "insert into USER_ACTIVITY(REQUEST_ID, SESSION_ID, REQUEST_URL, REQUEST_TIME, SERVED_TIME) values(?,?,?,?,?)";
					PreparedStatement REQUEST_STATISTICS_insert = connection
							.prepareStatement(insert_REQUEST_STATISTICS);
					int reqCount = 0;
					start.setTime(start.getTime() + random.nextInt(120 * 1000));
					while (start.before(end)) {
						int jindex = 0;

						System.out.println("resource time=" + start.getTime());
						REQUEST_STATISTICS_insert.setInt(++jindex, nextId);
						REQUEST_STATISTICS_insert.setString(++jindex, sessionId);
						REQUEST_STATISTICS_insert.setString(++jindex,
								features[random.nextInt(features.length)]);
						REQUEST_STATISTICS_insert.setTimestamp(++jindex, start);
						REQUEST_STATISTICS_insert.setInt(++jindex,
								random.nextInt(4000));
						REQUEST_STATISTICS_insert.addBatch();
						//Timestamp req_time = new Timestamp(calendar.getTimeInMillis());
						nextId++;
						reqCount++;
						start.setTime(start.getTime() + random.nextInt(120 * 1000));
					}
					if (reqCount > 0) {
						REQUEST_STATISTICS_insert.executeBatch();
					}
					DatabaseUtil.close(REQUEST_STATISTICS_insert);
				}
			}
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		}
		DatabaseUtil.close(connection);
	}

}
