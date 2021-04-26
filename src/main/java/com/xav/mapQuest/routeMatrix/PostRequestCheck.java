package com.xav.mapQuest.routeMatrix;

import com.xav.pojo.Location;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostRequestCheck {

    HttpURLConnection http;

    URLConnection con;

    //constructor
    public PostRequestCheck() throws IOException {
        setUrlConnection();
    }

    public void setUrlConnection() throws IOException {
        URL url = new URL("http://www.mapquestapi.com/directions/v2/routematrix?key=YWMyjfJZ1vhh61b01wnP0plntjWyf4Sg");
        con = url.openConnection();
        http = (HttpURLConnection)con;
        http.setRequestMethod("POST"); // PUT is another valid option
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        http.setDoOutput(true);
    }


    public double[][] getAllToAllDistances(List<Location> locations){

        String jsonInputString = createRequestBody(locations);
        double [][] allToAllDistanceMatrix;
        double [][] allToAllTimeMatrix;

        //printing request
       // System.out.println(jsonInputString);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //reading
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String responseString = response.toString();
            //System.out.println(responseString);

            //forming the allToAll distance array
            JSONObject obj = new JSONObject(responseString);
            JSONArray distance = obj.getJSONArray("distance");

            //find length of each 1d array in JSON array
            int lengthOneDDistanceArray = distance.getJSONArray(0).length();

            allToAllDistanceMatrix = new double[distance.length()][lengthOneDDistanceArray];

            for (int i=0,m=0; i<distance.length(); i++,m++)
            {
                for (int j=0,n=0; j<lengthOneDDistanceArray; j++,n++)
                {
                    allToAllDistanceMatrix[m][n] = distance.getJSONArray(i).getDouble(j);

                }
            }

            return allToAllDistanceMatrix;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    //function to get allToAll time matrix
    public double [][] getAllToAllTimeMatrix(List<Location> locations)
    {
        String jsonInputString = createRequestBody(locations);
        double [][] allToAllTimeMatrix;

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //reading
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String responseString = response.toString();


            //forming the allToTime time array
            JSONObject obj = new JSONObject(responseString);
            JSONArray time = obj.getJSONArray("time");
            //finding length of 1d array in each JSON array
            int lengthOneDTimeArray = time.getJSONArray(0).length();

            allToAllTimeMatrix = new double[time.length()][lengthOneDTimeArray];
            for (int i=0; i<time.length(); i++)
            {
                for (int j=0; j<lengthOneDTimeArray; j++)
                {
                    allToAllTimeMatrix[i][j] = time.getJSONArray(i).getDouble(j);

                }
            }

            return allToAllTimeMatrix;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }



    public String createRequestBody(List<Location> locations){

        String jsonRequestBody = "{ \"locations\": [";
        for(int i=0; i<locations.size(); i++)
        {
            String eachLocation = "{ \"latLng\": { \"lat\": " + locations.get(i).getLatitude() + ", \"lng\": " + locations.get(i).getLongitude() +"}},";
            jsonRequestBody = jsonRequestBody + eachLocation;
        }
        jsonRequestBody = jsonRequestBody + "],\"options\": { \"allToAll\": true } }";


        return jsonRequestBody;

    }
}
