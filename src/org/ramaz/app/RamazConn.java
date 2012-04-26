package org.ramaz.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RamazConn {

	private int student_id;
	private DefaultHttpClient httpclient;

	RamazConn() {
		this.httpclient = new DefaultHttpClient();
	}

	void downloadData(String username, String password) throws Exception {

		// Login and get valid cookies
		HttpPost httpost = new HttpPost("http://www.ramaz.org/login/login.cfm");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", username));
		nvps.add(new BasicNameValuePair("password", password));

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

		HttpResponse response = this.httpclient.execute(httpost);
		// HttpEntity entity = response.getEntity();

		System.out.println("Login form get: " + response.getStatusLine());
		// EntityUtils.consume(entity);

		System.out.println("Post logon cookies:");
		List<Cookie> cookies = this.httpclient.getCookieStore().getCookies();
		if (cookies.isEmpty()) {
			System.out.println("None");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				System.out.println("- " + cookies.get(i).toString());
			}
		}

		// Need course page for student id
		HttpGet get = new HttpGet("http://www.ramaz.org/course");
		response = this.httpclient.execute(get);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		String html = null;
		int x = 0;
		while ((line = rd.readLine()) != null) {
			System.out.println("On line: " + x);
			sb.append(line);
			x++;
		}
		html = sb.toString();
		int startIndex = html.indexOf("id_student");
		if (startIndex == -1) {
			startIndex = html.indexOf("id_faculty");
			if (startIndex == -1) {
				startIndex = html.indexOf("id_teacher");
				if (startIndex == -1) {
					throw (new Exception("Login Error"));
				}
			}
		}
		int endIndex;
		startIndex += "id_student=".length();
		for (endIndex = startIndex; endIndex < html.length(); endIndex++) {
			if (!Character.isDigit(html.charAt(endIndex)))
				break;
		}
		System.out.println(html); // HTML now contains the course page
		System.out.println("startIndex=" + startIndex);
		System.out.println("endIndex=" + endIndex);
		this.student_id = Integer
				.parseInt(html.substring(startIndex, endIndex));
		System.out.println(this.student_id);
	}

	public ArrayList<ArrayList<ClassRoom>> getSched() throws Exception {
		HttpGet get = new HttpGet(
				"http://www.ramaz.org/course/view_class_schedule.cfm?semester=2&id_student=" + this.student_id);
		HttpResponse response = this.httpclient.execute(get);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String line = new String();
		String html = new String();
		StringBuilder sb = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		html = sb.toString();
		System.out.println(html);

		ArrayList<ArrayList<ClassRoom>> sched = new ArrayList<ArrayList<ClassRoom>>(); // Classes by days of week
		Document doc = Jsoup.parse(html);
		Element table = doc.getElementsByTag("TABLE").get(0);
		Elements rows = table.getElementsByTag("TR");
		for (int i = 2; i < 14; i++) {
			//System.out.println("Row:");
			//System.out.println(rows.get(i).html());
			ArrayList<ClassRoom> rowData = new ArrayList<ClassRoom>();
			for (Element data : rows.get(i).children()) {
				System.out.println("Raw html " + data.html());
				String[] fields = data.html().split("<br />");
				String myClass, myRoom;
				if (fields[2].length() == 0) {
					try {
						Integer.parseInt(fields[0]); // If it just gives a period # as opposed to period #-class-id, it's a free
						myClass = "Free";
						myRoom = "place"; // Need placeholder text even if room is not shown, otherwise throws indexoutofbounds
					} catch (Exception e) {
						break; // This means that it's either Mincha or Homeroom
					}
				} else {
					myClass = fields[1].replaceAll("\\s$", "");
					myClass = myClass.replaceAll("&amp;", "");
					myRoom = fields[2].substring(1, 4);
				}
				rowData.add(new ClassRoom(myClass, myRoom));
				/*System.out.println("Class:"); System.out.println(myClass);
				System.out.println("Room:"); System.out.println(myRoom);*/
			}
			if (rowData.size() > 0) {
				System.out.println("Row size is " + rowData.size());
				sched.add(rowData);
			}
		}
		return sched;
	}
}